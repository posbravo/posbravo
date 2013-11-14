package testing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pointOfSale.Response;

public class MercuryGift {

	public static void main(String [] args){
		Response set = new Response();
		set.setIDnPas("merchantID2");
		
		String card = "0~IPAD100KB|24~98AC18281702140C|1~11|2~|3~CA6CBC6DAB1EB39253EEFFE64EEC341A56688FBEADBFDA806594D1736B7310D7|4~|5~ACBF7B3AC3924110950D93E33B30A38BFD94342A13B21CBDE172037BCDD31C8C81D0B9588F7D672A9487A3777F5BECF16FE2CCCB12F8C54F|6~|7~;6050110000900001112=250100000?|8~|9~00000000|10~010001|11~9500030000081C2000CE|12~00002200|";
		Pattern pregex = Pattern.compile("3~.*\\|4"); //encrypted card data
		Matcher m = pregex.matcher(card);
		m.find();
		System.out.println(m.group());
		Pattern pregex1 = Pattern.compile("11~.*\\|12"); //encrypted key
		Matcher m1 = pregex1.matcher(card);
		m1.find();
		
		String elv[] = {"Swiped", "Balance", "merchantID2", "44", "44", "POS BRAVO v1.0", card.substring(m1.start()+3, m1.end()-3), card.substring(m.start()+2, m.end()-2)};
		
		Response test = new Response(11, elv);
		
		System.out.println(test.getXML());
		System.out.println(test.getResponse());
		System.out.println("____________________________________________");
	}
}
