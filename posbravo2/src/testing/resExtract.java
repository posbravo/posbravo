package testing;

import java.util.Scanner;

public class resExtract {

	public static Scanner reader = null;
	public static Scanner regex = null;
	public static String code[] = new String[5];

	
	public resExtract(){
		
	}
	
	public static String [] getCodes(String xml) {
		
			reader = new Scanner(xml);
	
		while (reader.hasNextLine()) {
			String read = reader.nextLine();
			regex = new Scanner(read);
            if (read.contains("AuthCode")) {
            	String temp = null;
				temp = regex
						.findInLine("<AuthCode>[\\da-zA-Z]*</AuthCode>");
				temp = temp.substring("<AuthCode>".length(),
						temp.length() - "</AuthCode>".length());
				code[0] = temp;
            }
            else if (read.contains("RefNo")) {
            	String temp = null;
        				temp = regex
        						.findInLine("<RefNo>[\\da-zA-Z]+</RefNo>");
        				temp = temp.substring("<RefNo>".length(),
        						temp.length() - "</RefNo>".length());
        				code[1] = temp;
                    }
            else if (read.contains("RecordNo")) {
            	String temp = null;
        				temp = regex
        						.findInLine("<RecordNo>.*</RecordNo>");
        				temp = temp.substring("<RecordNo>".length(),
        						temp.length() - "</RecordNo>".length());
        				code[2] = temp;
                    }
            else if (read.contains("AcqRefData")) {
            	String temp = null;
        				temp = regex
        						.findInLine("<AcqRefData>[\\da-zA-Z ]+</AcqRefData>");
        				temp = temp.substring("<AcqRefData>".length(),
        						temp.length() - "</AcqRefData>".length());
        				code[3] = temp;
                    }
            else if (read.contains("ProcessData")) {
            	String temp = null;
        				temp = regex
        						.findInLine("<ProcessData>[\\da-zA-Z |]+</ProcessData>");
        				temp = temp.substring("<ProcessData>".length(),
        						temp.length() - "</ProcessData>".length());
        				code[4] = temp;
                    }
            
            
					regex.close();
		}
		return code;
	}
}
