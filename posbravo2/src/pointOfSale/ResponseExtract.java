package pointOfSale;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ResponseExtract {

	public ResponseExtract(String receiptName){
		
		try{
		Scanner reader = new Scanner(new File("Files/Transaction/Response/1/"
				+ receiptName + ".xml"));}
		catch(Exception e){
			e.printStackTrace();
		}
		
		int file2;
		Scanner reader;
		String file1;
		
		
		try {
			reader = new Scanner(new File(file1 + "Sent/1" + file2));
		} catch (FileNotFoundException e) {
			try {
				reader = new Scanner(new File(file1 + "Sent/2" + file2));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				System.out.println("HERE2");
			//	return null;
			}
	}
		
		while (reader.hasNextLine()) {
			String read = reader.nextLine();
			Scanner regex = new Scanner(read);
			String[] toReturn;
			try{
			if (read.contains("MerchantID")) {
				toReturn[8] = regex
						.findInLine("<MerchantID>[\\da-zA-Z =]+</MerchantID>");
				toReturn[8] = toReturn[8].substring("<MerchantID>".length(),
						toReturn[8].length() - "</MerchantID>".length()).trim();
				/*Pattern p = Pattern.compile("[\\da-zA-z=]+");
				Matcher m = p.matcher(toReturn[8]);
			    m.find();
			    toReturn[8] = m.group();*/
				
			}
			
			else if (read.contains("InvoiceNo")) {
				toReturn[0] = regex
						.findInLine("<InvoiceNo>[\\da-zA-Z]*</InvoiceNo>");
				toReturn[0] = toReturn[0].substring("<InvoiceNo>".length(),
						toReturn[0].length() - "</InvoiceNo>".length());
			} else if (read.contains("RefNo")) {
				// try getting from recieved
				toReturn[1] = regex.findInLine("<RefNo>[\\da-zA-Z]*</RefNo>");
				toReturn[1] = toReturn[1].substring("<RefNo>".length(),
						toReturn[1].length() - "</RefNo>".length());
			} else if (read.contains("Purchase")) {
				toReturn[4] = regex
						.findInLine("<Purchase>[\\d\\.]*</Purchase>");
				toReturn[4] = toReturn[4].substring("<Purchase>".length(),
						toReturn[4].length() - "</Purchase>".length());
		
			} else if (read.contains("Memo")) {
				toReturn[2] = regex.findInLine("<Memo>[\\da-zA-Z \\.]*</Memo>");
				toReturn[2] = toReturn[2].substring("<Memo>".length(),
						toReturn[2].length() - "</Memo>".length());
			} else if (read.contains("RecordNo")) {
				toReturn[3] = regex.findInLine("<RecordNo>.*</RecordNo>");
				//if(toReturn[3] != null){
				toReturn[3] = toReturn[3].substring("<RecordNo>".length(),
						toReturn[3].length() - "</RecordNo>".length());
			//	}
			} else if (read.contains("AuthCode")) {
				toReturn[7] = regex
						.findInLine("<AuthCode>[\\da-zA-Z]</AuthCode>");
			//	if(toReturn[7] != null){
				toReturn[7] = toReturn[7].substring("<AuthCode>".length(),
						toReturn[7].length() - "</AuthCode>".length());
			//	}
			} else if (read.contains("AcqRefData")) {
				toReturn[5] = regex
						.findInLine("<AcqRefData>[\\da-zA-Z ]*</AcqRefData>");
			//	if(toReturn[5] != null){
				toReturn[5] = toReturn[5].substring("<AcqRefData>".length(),
						toReturn[5].length() - "</AcqRefData>".length());
			//	}
			}
		}
		catch(NullPointerException e){
			
		}
		reader.close();
		try {
			reader = new Scanner(new File(file1 + "Response/1" + file2));
		} catch (FileNotFoundException e) {
			try {
				reader = new Scanner(new File(file1 + "Response/2" + file2));
			} catch (FileNotFoundException e1) {
				System.out.println("HERE3");
				return null;
			}
		}
		while (reader.hasNextLine()) {
			String read = reader.nextLine();
			regex = new Scanner(read);
			if (read.contains("InvoiceNo")) {
				toReturn[0] = regex
						.findInLine("<InvoiceNo>[\\da-zA-Z]*</InvoiceNo>");
				toReturn[0] = toReturn[0].substring("<InvoiceNo>".length(),
						toReturn[0].length() - "</InvoiceNo>".length());
			} else if (read.contains("RefNo")) {
				// try getting from recieved
				toReturn[1] = regex.findInLine("<RefNo>[\\da-zA-Z]*</RefNo>");
				toReturn[1] = toReturn[1].substring("<RefNo>".length(),
						toReturn[1].length() - "</RefNo>".length());
			} else if (read.contains("Authorize")) {
				toReturn[4] = regex
						.findInLine("<Authorize>[\\d\\.]*</Authorize>");
				toReturn[4] = toReturn[4].substring("<Authorize>".length(),
						toReturn[4].length() - "</Authorize>".length());
			} else if (read.contains("Memo")) {
				toReturn[2] = regex.findInLine("<Memo>[\\da-zA-Z \\.]*</Memo>");
				toReturn[2] = toReturn[2].substring("<Memo>".length(),
						toReturn[2].length() - "</Memo>".length());
			} else if (read.contains("RecordNo")) {
				toReturn[3] = regex.findInLine("<RecordNo>.*</RecordNo>");
				toReturn[3] = toReturn[3].substring("<RecordNo>".length(),
						toReturn[3].length() - "</RecordNo>".length());
			} else if (read.contains("ProcessData")) {
				toReturn[6] = regex
						.findInLine("<ProcessData>[\\d\\|]*</ProcessData>");
				toReturn[6] = toReturn[6].substring("<ProcessData>".length(),
						toReturn[6].length() - "</ProcessData>".length());
			} else if (read.contains("AuthCode")) {
				toReturn[7] = regex
						.findInLine("<AuthCode>[\\da-zA-Z]*</AuthCode>");
				toReturn[7] = toReturn[7].substring("<AuthCode>".length(),
						toReturn[7].length() - "</AuthCode>".length());
			} else if (read.contains("AcqRefData")) {
				toReturn[5] = regex
						.findInLine("<AcqRefData>[\\da-zA-Z ]*</AcqRefData>");
				toReturn[5] = toReturn[5].substring("<AcqRefData>".length(),
						toReturn[5].length() - "</AcqRefData>".length());
			}

		}
		reader.close();

		if(voidSale){
			try {
				reader = new Scanner(new File(file1 + "Response/2" + file2));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			while (reader.hasNextLine()) {
				String read = reader.nextLine();
				regex = new Scanner(read);
				if (read.contains("RefNo")) {
					toReturn[1] = regex.findInLine("<RefNo>[\\da-zA-Z]*</RefNo>");
					toReturn[1] = toReturn[1].substring("<RefNo>".length(),
							toReturn[1].length() - "</RefNo>".length());
				}
				else if (read.contains("Authorize")) {
					toReturn[4] = regex.findInLine("<Authorize>.*</Authorize>");
					toReturn[4] = toReturn[4].substring("<Authorize>".length(),
							toReturn[4].length() - "</Authorize>".length()).trim();
					
				}
			}
			voidsale = false;
		}
		reader.close();
		

		return toReturn;
	}
	
	
}
