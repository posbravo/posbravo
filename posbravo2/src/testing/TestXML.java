package testing;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pointOfSale.Response;

public class TestXML {

	public static void main(String[] args) {
		
		String ref = "0";
		String one[] = {"10", "10", "POS BRAVO v1.0", "4003000123456781", "1215", "1.05", "1.05", "", "", ""};
		String two[] = {"10", "10", "ADl1bYRUI2Pi5H6WO1fOHANtuxLlwlnUIhESEAAjEAeDBA==", "1.05", "1.05", "1.00", "VI0105", "KfJ"};
		String three[] = {"10", "10", "POS BRAVO v1.0", "KqaoIkv1k9wUbUmJ9wmtTKFWZhyPMas0IhESEAAjEAeDBw==", "1.05", "KfJ", "|15|410100700000", "VI0105"};
		String four[] = {"16", "16", "POS BRAVO v1.0", "9500030000081C20001A", "2B11F45ABEE6A6B288A76FED3BBCCE63130970C742BDD607D75F09821AAF2C6482F1AB593E0A97BA", "2.00", "2.00"};
		//non encrypted cc 
		Response set = new Response();
		set.setIDnPas("merchantID1");
		Response test = new Response(1, one);
		
		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
		//adjust tips
		
		test = new Response(2, two);
		System.out.println(test.getXML());
		System.out.println(test.getResponse());

		ref = getRefcode(test.getResponse());
		System.out.println("____________________________________________");
		//void
		/*test = new Response(3, three);
		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");*/
		//encrypted cc with magtek ipad 100kb
		/*test = new Response(4, four);
		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");*/
		
		//adjust
		String sev[] = {"0001", ref, "POS BRAVO v1.0", "KqaoIkv1k9wUbUmJ9wmtTKFWZhyPMas0IhESEAAjEAeDBw==", "0.05", "KfJ", "|15|410100700000", "VI0105", "395347306=TOKEN", "0.05", "1.00"};
		
		test = new Response(7, sev);
		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
		
		//standard voidsale
		String fiv[] = {"0001", ref, "POS BRAVO v1.0", "KqaoIkv1k9wUbUmJ9wmtTKFWZhyPMas0IhESEAAjEAeDBw==", "1.05", "KfJ", "|15|410100700000", "VI0105", "395347306=TOKEN", "1.05"};
		
		test = new Response(5, fiv);
		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		
		
	}
	public int getAuthcode(String xml) {
		
		
		Scanner reader = null;
		Scanner regex = null;
		int code = 0;

		
		
			reader = new Scanner(xml);
	
		while (reader.hasNextLine()) {
			String read = reader.nextLine();
			regex = new Scanner(read);
            if (read.contains("AuthCode")) {
            	String temp = null;
				temp = regex
						.findInLine("<RefNo>[\\da-zA-Z]</RefNo>");
				temp = temp.substring("<RefNo>".length(),
						temp.length() - "</RefNo>".length());
				code = Integer.parseInt(temp);
            }
            
					reader.close();
		}
		return code;
	}
	public static String getRefcode(String xml) {
	
	
		Scanner reader = new Scanner(xml);
		Scanner regex = null;
		String temp = null;

		
	
	
		while (reader.hasNextLine()) {
			String read = reader.nextLine();
			regex = new Scanner(read);
            if (read.contains("RefNo")) {
				temp = regex
						.findInLine("<RefNo>[\\da-zA-Z]+</RefNo>");
				temp = temp.substring("<RefNo>".length(),
						temp.length() - "</RefNo>".length());
				
            }
            
		}
		reader.close();
		
		return temp;
	}
	
}
