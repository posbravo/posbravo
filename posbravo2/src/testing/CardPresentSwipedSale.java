package testing;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pointOfSale.*;

public class CardPresentSwipedSale {

	public static void main(String[] args){
		Response set = new Response();
		set.setIDnPas("merchantID2");
		
		String card = "0~IPAD100KB|24~98AC18281702140C|1~33|2~EC5A3972BE3A7DD16559861151BAE7C5F961B63D9ED7076DF0C835F873FA9E48ABD0535C751C322E8EA493ED015F7A0DF3312600C8C4AA9E|3~FD1E93B24B1F14EC989506E6CC04E1C360948EDEC23FFBAFE724DD4C04B847D3E243466E7738ABCC|4~|6~%M4005550000000480^MANUAL ENTRY/^1512000000000000000?|7~;4005550000000480=1512000000000000?|8~|9~00000000|10~000001|11~9500030000081C2000CB|12~00000000|";
		Pattern pregex = Pattern.compile("3~.*\\|4"); //encrypted card data
		Matcher m = pregex.matcher(card);
		m.find();
		System.out.println(m.group());
		Pattern pregex1 = Pattern.compile("11~.*\\|12"); //encrypted key
		Matcher m1 = pregex1.matcher(card);
		m1.find();
//case#1 Sale/Reverse Voidsale
		//replace card data with swiped
/*		
		
		//Sale
		String yuk[] = {"29", "29", "POS BRAVO v1.0", card.substring(m1.start()+3, m1.end()-3), card.substring(m.start()+2, m.end()-2), "2.02", "1661 E. Camelback", "85016", "Swiped"};

		Response test = new Response(6, yuk);

		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");

		//Reversal Voidsale
		String [] info2 = getCodes(test.getResponse());
				
		String three[] = {"29", info2[1], "POS BRAVO v1.0", info2[2], "2.02", info2[3], info2[4], info2[0], "merchantID2" };

		test = new Response(3, three);
				
		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
		*/
//case#2 Sale/Reversal Voidsale/Voidsale
		//Sale
/*		String yuk[] = {"30", "30", "POS BRAVO v1.0", card.substring(m1.start()+3, m1.end()-3), card.substring(m.start()+2, m.end()-2), "24.01", "1661 E. Camelback", "85016", "Swiped"};

		Response test = new Response(6, yuk);

		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");

		//Reversal Voidsale
		String [] info2 = getCodes(test.getResponse());
				
		String three[] = {"30", info2[1], "POS BRAVO v1.0", info2[2], "24.01", info2[3], info2[4], info2[0], "merchantID2" };

		test = new Response(3, three);
				
		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
		
		//Standard Voidsale
		
		test = new Response(5, three);
		
		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
	*/
//case#3 Sale/Adjust/Voidsale
		//Sale
/*		String yuk[] = {"31", "31", "POS BRAVO v1.0", card.substring(m1.start()+3, m1.end()-3), card.substring(m.start()+2, m.end()-2), "2.03", "1661 E. Camelback", "85016", "Swiped"};

		Response test = new Response(6, yuk);

		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
		
		//Adjust
		String [] info2 = getCodes(test.getResponse());
		String []adj = new String[]{"31", info2[1], "POS BRAVO v1.0", info2[2], "2.13", "2.13", "", info2[0], "merchantID2"};
		
		test = new Response(7, adj);

		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
		
		
		//Standard Voidsale
		info2 = getCodes(test.getResponse());
		String three[] = {"31", info2[1], "POS BRAVO v1.0", info2[2], "2.13", info2[3], info2[4], info2[0], "merchantID2" };

		test = new Response(5, three);
		
		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
		*/
//case#4 Sale/Adjust/Voidsale
		//Sale
/*		String yuk[] = {"32", "32", "POS BRAVO v1.0", card.substring(m1.start()+3, m1.end()-3), card.substring(m.start()+2, m.end()-2), "2.04", "1661 E. Camelback", "85016", "Swiped"};

		Response test = new Response(6, yuk);

		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
				
		//Adjust
		String [] info2 = getCodes(test.getResponse());
		String []adj = new String[]{"32", info2[1], "POS BRAVO v1.0", info2[2], "2.04", "2.04", "1.00", info2[0], "merchantID2"};
	
		test = new Response(7, adj);

		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
				
				
		//Standard Voidsale
		//info2 = getCodes(test.getResponse());
		String three[] = {"32", info2[1], "POS BRAVO v1.0", info2[2], "3.04", info2[3], info2[4], info2[0], "merchantID2" };

		test = new Response(5, three);
				
		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
	*/	
//case#5 Sale/Sale
/*		//Sale
		String yuk[] = {"33", "33", "POS BRAVO v1.0", card.substring(m1.start()+3, m1.end()-3), card.substring(m.start()+2, m.end()-2), "23.54", "1661 E. Camelback", "85016", "Swiped"};

		Response test = new Response(6, yuk);

		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
		
		//Sale
		String yuk2[] = {"34", "34", "POS BRAVO v1.0", card.substring(m1.start()+3, m1.end()-3), card.substring(m.start()+2, m.end()-2), "3.54", "1661 E. Camelback", "85016", "Swiped"};

		test = new Response(6, yuk2);

		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
		*/
//case#6 Sale/Reversal Voidsale
		//Sale
/*		String yuk[] = {"35", "35", "POS BRAVO v1.0", card.substring(m1.start()+3, m1.end()-3), card.substring(m.start()+2, m.end()-2), "23.54", "1661 E. Camelback", "85016", "Swiped"};

		Response test = new Response(6, yuk);

		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
		
		//Reversal Voidsale
		String [] info2 = getCodes(test.getResponse());
				
		String three[] = {"35", info2[1], "POS BRAVO v1.0", info2[2], "20.00", info2[3], info2[4], info2[0], "merchantID2" };

		test = new Response(3, three);
				
		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
	*/
		
		
	}
	public static String [] getCodes(String xml) {
		
		
		Scanner reader = null;
		Scanner regex = null;
		String code[] = new String[5];

		
		
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
