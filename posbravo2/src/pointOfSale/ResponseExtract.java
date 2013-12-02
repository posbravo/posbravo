package pointOfSale;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class ResponseExtract {

	public ResponseExtract(){

	}
	
	

	public static String[] getData(String receiptName, String extractFrom) {
		Scanner reader = null;
		String[] toReturn = null;
		if (extractFrom.equals("PreAuth")) {
			try {
				reader = new Scanner(new File("Files/Transaction/Response/1/"
						+ receiptName + ".xml"));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				reader = new Scanner(new File("Files/Transaction/Response/2/"
						+ receiptName + ".xml"));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				// return null;
			}
		}

		if (reader != null) {
			while (reader.hasNextLine()) {
				String read = reader.nextLine();
				Scanner regex = new Scanner(read);
				try {
					if (read.contains("MerchantID")) {
						toReturn[0] = regex
								.findInLine("<MerchantID>[\\da-zA-Z =]+</MerchantID>");
						toReturn[0] = toReturn[0]
								.substring("<MerchantID>".length(),toReturn[0].length()
										- "</MerchantID>".length()).trim();
					} else if (read.contains("InvoiceNo")) {
						toReturn[1] = regex
								.findInLine("<InvoiceNo>[\\d]+</InvoiceNo>");
						toReturn[1] = toReturn[0].substring(
								"<InvoiceNo>".length(), toReturn[1].length()
								- "</InvoiceNo>".length());
					} else if (read.contains("RefNo")) {
						toReturn[2] = regex
								.findInLine("<RefNo>[\\d]+</RefNo>");
						toReturn[2] = toReturn[2].substring("<RefNo>".length(),
								toReturn[2].length() - "</RefNo>".length());
					} else if (read.contains("Purchase")) {
						toReturn[3] = regex
								.findInLine("<Purchase>[\\d\\.]+</Purchase>");
						toReturn[3] = toReturn[3].substring(
								"<Purchase>".length(), toReturn[3].length()
								- "</Purchase>".length());
					} else if (read.contains("Authorize")) {
						toReturn[4] = regex
								.findInLine("<Authorize>[\\d\\.]*</Authorize>");
						toReturn[4] = toReturn[4].substring(
								"<Authorize>".length(), toReturn[4].length()
								- "</Authorize>".length());
					} else if (read.contains("RecordNo")) {
						toReturn[5] = regex
								.findInLine("<RecordNo>.+</RecordNo>");
						toReturn[5] = toReturn[5].substring(
								"<RecordNo>".length(), toReturn[5].length()
								- "</RecordNo>".length());
					} else if (read.contains("AuthCode")) {
						toReturn[6] = regex
								.findInLine("<AuthCode>[\\da-zA-Z]+</AuthCode>");
						toReturn[6] = toReturn[6].substring(
								"<AuthCode>".length(), toReturn[6].length()
								- "</AuthCode>".length());
					} else if (read.contains("AcqRefData")) {
						toReturn[7] = regex
								.findInLine("<AcqRefData>[\\da-zA-Z ]+</AcqRefData>");
						toReturn[7] = toReturn[7].substring(
								"<AcqRefData>".length(), toReturn[7].length()
								- "</AcqRefData>".length());
					} else if (read.contains("ProcessData")) {
						toReturn[8] = regex
								.findInLine("<ProcessData>[\\da-zA-Z \\|]+</ProcessData>");
						toReturn[8] = toReturn[8].substring(
								"<ProcessData>".length(), toReturn[8].length()
								- "</ProcessData>".length());
					}
				} catch (Exception e) {
					
				
				}
			}
			reader.close();

		}

		return toReturn;
	}
}