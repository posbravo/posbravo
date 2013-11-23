import static org.junit.Assert.*;

import org.junit.Test;

import com.mercurypay.ws.sdk.MercuryWebRequest;


public class MercuryWebRequestTest {
	private final String webURL = "https://ws1.mercurydev.net/ws/ws.asmx";
	MercuryWebRequest mwr;
	@Test
	public void test() throws Exception {
		mwr  = new MercuryWebRequest(webURL);
		mwr.addParameter("pw", "xyz");
		mwr.setWebMethodName("CreditTransaction");
		
		mwr.sendRequest();
	}

}
