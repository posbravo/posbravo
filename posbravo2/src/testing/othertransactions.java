package testing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pointOfSale.Response;

public class othertransactions {

	public static void main(String [] args){
		Response set = new Response();
		set.setIDnPas("merchantID2");
		/*String [] cre = {"1", "1", "POS BRAVO v1.0", "4005550000000480=15125025432198712345", "1.02", "1.02"};
		
		Response test = new Response(1, cre);
		
		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		*/
		
		String card = "0~IPAD100KB|24~98AC18281702140C|1~11|2~FDC81D61C147580AD70E62D9596125FE5411DC43F3B243C7F82AEC7BB6290AF682C574FD2C70D11D400CA88D1E8739C6|3~1F71F0FBE3248FA139BC0C66D76FB6DA5AB3840CFCFC7DDDACE2BE0C966B8A3F7C91909EEB394B81|4~|5~C48F5DF3FD5C8C817EA6B6885F026811FA251F59A225588733FFE647D2203804EB6CE63E15A4DB90E2D05B5DDFA033209CC1ED346BD7C852|6~%B4005550000000480^TEST/MPS^15120000000000000?|7~;4005550000000480=15120000000000000000?|8~|9~00000000|10~000001|11~9500030000081C2000D7|12~00002200|";
		
		Pattern pregex = Pattern.compile("3~.*\\|4"); //encrypted card data
		Matcher m = pregex.matcher(card);
		m.find();
		System.out.println(m.group());
		Pattern pregex1 = Pattern.compile("11~.*\\|12"); //encrypted key
		Matcher m1 = pregex1.matcher(card);
		m1.find();
		
		//PreAuth Encrypted
/*		String one[] = {"53", "53", "POS BRAVO v1.0", card.substring(m1.start()+3, m1.end()-3), card.substring(m.start()+2, m.end()-2), "1.07", "1.07", "Swiped"};
		
		Response test = new Response(4, one);

		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
		
		//PreAuth Capture
		
		String [] info2 = resExtract.getCodes(test.getResponse());
		
		String two[] = {"53", "53", info2[2], "1.07", "1.07", "", info2[0], info2[3]};
				
		test = new Response(2, two);

		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
		
		//PreAuth Encrypted
		String one2[] = {"54", "54", "POS BRAVO v1.0", card.substring(m1.start()+3, m1.end()-3), card.substring(m.start()+2, m.end()-2), "1.07", "1.07", "Swiped"};
		
		test = new Response(4, one2);

		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
		
		//PreAuth Capture
		
		String [] info = resExtract.getCodes(test.getResponse());
		
		String two2[] = {"54", "54", info[2], "1.07", "1.07", "", info[0], info[3]};
				
		test = new Response(2, two2);

		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
		
		// PreAuth Encrypted
		/*String one2[] = { "52", "52", "POS BRAVO v1.0",
				card.substring(m1.start() + 3, m1.end() - 3),
				card.substring(m.start() + 2, m.end() - 2), "1.07", "1.07",
				"Swiped" };
*/
		String one2[] = {"54", "54", "POS BRAVO v1.0", card.substring(m1.start()+3, m1.end()-3), card.substring(m.start()+2, m.end()-2), "1.07", "1.07", "Swiped"};
		
		Response test = new Response(4, one2);

		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");

		// PreAuth Capture

		String[] info3 = resExtract.getCodes(test.getResponse());

		String two3[] = { "54", "54", info3[2], "1.07", "1.07", "", info3[0],
				info3[3] };

		test = new Response(2, two3);

		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
		
		
		
		
		/*String [] cre = {"2", "2", "POS BRAVO v1.0", card.substring(m1.start()+3, m1.end()-3), card.substring(m.start()+2, m.end()-2), "10.01", "Swiped" }; 
		
		Response test = new Response(6 , cre);
		
		System.out.println(test.getXML());
		System.out.println(test.getResponse());
	*/
/*
		String three[] = {"2", "0036", "POS BRAVO v1.0", "kGfglQQHZOIvhJ3G934X/vgdKW4g2iHJIhESEAAjEAjBlw==", "2.02", "aEb053327010104550cRBADd5e00fJj102924332739430924lA  m000005", "|00|410100201000", "VI1001"
				
				, "merchantID2" };

		Response test = new Response(3, three);
				
		System.out.println(test.getXML());
		System.out.println(test.getSoap());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
	*/
	}
}
