package pointOfSale;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.mercurypay.ws.sdk.MercuryWebRequest;

public class Response{
	
	private Properties properties;
	private File file;
	

	private FileInputStream fileReader;
	
	private final String webURL = "https://w1.mercurydev.net/ws/ws.asmx";
	private int type, timeout;
	private static String merchantID /*"023358150511666""395347306=TOKEN"*/ , entryType , cashback, encPin, dervk, cvv, addr, zip, tranType /*= "Credit"*/, tranCode, invoiceNo, refNo, memo, frequency /*= "OneTime"*/, recordNo, partialAuth /*"Allow"*/, accountNum, expDate, purchase, authorize, gratuity, authCode, acqRefData, processData, encryptedBlock, encryptedKey;
	private static String password /*= "123TOKEN"*/;
	private String result = "", response = "";
	
	public static boolean batch = false;
    public Response()
    {
    
    }
	
	public Response(int type__, String[] data)
	{
		onLoad();
		this.type = type__;
		switch(type)
		{
		case 1: tranCode = "PreAuth";
			invoiceNo = data[0];
			refNo = data[1];
			memo = data[2];
			recordNo = "RecordNumberRequested";
			accountNum = data[3];
			expDate = data[4];
			purchase = data[5];
			authorize = data[6];
			gratuity = "0.0";
			cvv = data[7];
			addr = data[8];
			zip = data[9];
			result = getResult1();
			break;
		case 2: tranCode = "PreAuthCaptureByRecordNo";
			invoiceNo = data[0];
			refNo = data[1];
			recordNo = data[2];
			purchase = data[3];
			authorize = data[4];
			gratuity = data[5];
			authCode = data[6];
			acqRefData = data[7];
			result = getResult2();
			break;
		case 3: tranCode = "VoidSaleByRecordNo";
		    setIDnPas(data[8]);
			invoiceNo = data[0];
			refNo = data[1];
			memo = data[2];
			recordNo = data[3];
			purchase = data[4];
			acqRefData = data[5];
			processData = data[6];
			authCode = data[7];
			result = getResult3();
			break;
			
		//case for encrypted reader
		case 4:
			tranCode = "PreAuth";
			invoiceNo = data[0];
			refNo = data[1];
			memo = data[2];
			recordNo = "RecordNumberRequested";
			encryptedKey = data[3];
			encryptedBlock = data[4];
			purchase = data[5];
			authorize = data[6];
			gratuity = "0.0";
			addr = data[8];
			zip = data[9];
			entryType = data[10];
			result = getResult4();
		break;
		
		//standard voidsale
		case 5: tranCode = "VoidSaleByRecordNo";
	    setIDnPas(data[8]);
		invoiceNo = data[0];
		refNo = data[1];
		memo = data[2];
		recordNo = data[3];
		purchase = data[4];
		acqRefData = data[5];
		processData = data[6];
		authCode = data[7];
		result = getResult5();
		break;
		 
		//Credit Sale
		case 6: tranCode = "Sale";
		invoiceNo = data[0];
		refNo = data[1];
		memo = data[2];
		recordNo = "RecordNumberRequested";
		//accountNum = data[3];
		//expDate = data[4];
		encryptedKey = data[3];
		encryptedBlock = data[4];
		purchase = data[5];
		//authorize = data[6];
		//cvv = data[6];
		addr = data[6];
		zip = data[7];
		entryType = data[8];
		//result = getResult6();
		result = getResult13();
		break;
		
		case 7: tranCode = "AdjustByRecordNo";
		setIDnPas(data[8]);
		invoiceNo = data[0];
		refNo = data[1];
		memo = data[2];
		recordNo = data[3];
		purchase = data[4];
		//acqRefData = data[5];
		//processData = data[6];
		authCode = data[7];
		authorize = data[5];
		gratuity = data[6];
		result = getResult7();
		break;
		
		case 8: tranCode = "Return";
		invoiceNo = data[0];
		refNo = data[1];
		memo = data[2];
		recordNo = "RecordNumberRequested";
		//accountNum = data[3];
		//expDate = data[4];
		encryptedKey = data[3];
		encryptedBlock = data[4];
		purchase = data[5];
		authorize = data[6];
		//gratuity = "0.0";
		//cvv = data[7];
		entryType = data[7];
		if(entryType.equals("Keyed")){
			addr = data[8];
			zip = data[9];
		}
		result = getResult4();
		break;
		
		case 9: tranCode = "VoidReturnByRecordNo";
	    setIDnPas(data[6]);
		invoiceNo = data[0];
		refNo = data[1];
		memo = data[2];
		recordNo = data[3];
		purchase = data[4];
		//acqRefData = data[5];
		//processData = data[6];
		authCode = data[5];
		result = getResult5();
		break;
		
		//debit sale
		case 10:
			tranCode = "Debit";
			invoiceNo = data[0];
			refNo = data[1];
			memo = data[2];
			encryptedKey = data[3];
			encryptedBlock = data[4];
			purchase = data[5];
			cashback = data[6];
			encPin = data[7];
			dervk = data[8];
			result = getResult10();
		break;
		
		//gift card for non-encrypted
		case 11: entryType = data[0];
		tranCode = data[1];
		setIDnPas(data[2]);
		tranType = "PrePaid";
		invoiceNo = data[3];
		refNo = data[4];
		memo = data[5];
		//recordNo = "RecordNumberRequested";
		encryptedKey = data[6];
		encryptedBlock = data[7];
		//expDate = data[4];
		if(!tranCode.equals("Balance")){
			purchase = data[8];
		}
		if(tranCode.contains("Void")){
			authCode = data[9];
		}
		
		//authorize = data[6];
		//gratuity = "0.0";
		//cvv = data[7];
		//addr = data[8];
		//zip = data[9];
		result = getResult11();
		break;
		
		//gift for encrypted
		
		//batch summary
		case 12: tranCode = data[0];
		setIDnPas(data[1]);
		result = getResult12();
	
		}
		System.out.println(password);
		//System.out.println(result);
		send();
		
	}
	
	 private void initProperties() {
			properties = new Properties();
			file = new File("Files/Properties/MercuryMerchantIDDev.properties");
			try {
				fileReader  = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			//InputStream fileReader2 = getClass().getResourceAsStream("/Files/Properties/MercuryMerchantIDDev.properties");

			try{
			properties.load(fileReader);}catch(IOException e){
				e.printStackTrace();}
			finally{try{fileReader.close();fileReader = null;
			}
			catch(IOException e){
				e.printStackTrace();}}
	 }
	 private void onLoad() {
	    initProperties();
		this.tranType = properties.getProperty("tranType");
		this.frequency = properties.getProperty("frequency");
		this.partialAuth = properties.getProperty("partialAuth");
		this.timeout = Integer.parseInt(properties.getProperty("timeout"));
	
	}
	 
	 //<?xml version="1.0"?> <TStream>  <Transaction>   <MerchantID>395347308=E2ETKN</MerchantID>   <TranType>Credit</TranType>   <TranCode>PreAuth</TranCode>   <InvoiceNo>16</InvoiceNo>   <RefNo>16</RefNo> //use RefNo=InvoiceNo on PreAuth requests   <Memo>MPS Example XML v1.0</Memo>         <PartialAuth>Allow</PartialAuth> //Required to "Allow" partial approvals   <Frequency>OneTime</Frequency> //use to request a Token for "one time" use(6 months)   <RecordNo>RecordNumberRequested</RecordNo>   <Account> //use for encrypted data elements in place of Track1 or Track2    <EncryptedFormat>MagneSafe</EncryptedFormat>   <AccountSource>Swiped</AccountSource>   <EncryptedBlock>F40DDBA1F645CC8DB85A6459D45AFF8002C244A0F74402B479 ABC9915EC9567C81BE99CE4483AF3D</EncryptedBlock> //for E2E (P2PE), always use Track2 block <EncryptedKey>9012090B01C4F200002B</EncryptedKey>  </Account>   <Amount>    <Purchase>2.00</Purchase> //Purchase=Authorize on request    <Authorize>2.00</Authorize>   </Amount>  </Transaction> </TStream> 
	
	private String getResult1()
	{
		String temp = "<TStream>\n\t<Transaction>\n\t\t<MerchantID>";
		temp += merchantID;
		temp += "</MerchantID>";
		temp += "\n\t\t<OperatorID>test</OperatorID>";
		temp += "\n\t\t<TranType>";
		temp += tranType;
		temp += "</TranType>\n\t\t<TranCode>";
		temp += tranCode;
		temp += "</TranCode>\n\t\t<InvoiceNo>";
		temp += invoiceNo;
		temp += "</InvoiceNo>\n\t\t<RefNo>";
		temp += refNo;
		temp += "</RefNo>\n\t\t<Memo>";
		temp += memo;
		temp += "</Memo>\n\t\t<Frequency>";
		temp += frequency;
		temp += "</Frequency>\n\t\t<RecordNo>";
		temp += recordNo;
		temp += "</RecordNo>\n\t\t<PartialAuth>";
		temp += partialAuth;
		temp += "</PartialAuth>\n\t\t<Account>\n\t\t\t<AcctNo>";
		temp += accountNum;
		temp += "</AcctNo>\n\t\t\t<ExpDate>";
		temp += expDate;
		temp += "</ExpDate>\n\t\t</Account>\n\t\t<Amount>\n\t\t\t<Purchase>";
		temp += purchase;
		temp += "</Purchase>\n\t\t\t<Authorize>";
		temp += authorize;
		temp += "</Authorize>\n\t\t</Amount>";
		temp += "\n\t\t<CVVData>" + cvv + "</CVVData>";
		temp += "\n\t\t<AVS>\n\t\t\t<Address>" + addr + "</Address>";
		temp += "\n\t\t\t<Zip>" + zip + "</Zip>\n\t\t</AVS>";
		temp+= "\n\t</Transaction>\n</TStream>";
		
		return temp;
	}
	
	private String getResult2()
	{
		String temp = "<TStream>\n\t<Transaction>\n\t\t<MerchantID>";
		temp += merchantID;
		temp += "</MerchantID>\n\t\t<OperatorID>test</OperatorID>\n\t\t<TranType>";
		temp += tranType;
		temp += "</TranType>\n\t\t<TranCode>";
		temp += tranCode;
		temp += "</TranCode>\n\t\t<PartialAuth>";
		temp += partialAuth;
		temp += "</PartialAuth>\n\t\t<InvoiceNo>";
		temp += invoiceNo;
		temp += "</InvoiceNo>\n\t\t<RefNo>";
		temp += refNo;
		temp += "</RefNo>\n\t\t<RecordNo>";
		temp += recordNo;
		temp += "</RecordNo>\n\t\t<Frequency>";
		temp += frequency;
		temp += "</Frequency>\n\t\t<Amount>\n\t\t\t<Purchase>";
		temp += purchase;
		temp += "</Purchase>\n\t\t\t<Authorize>";
		temp += authorize;
		temp += "</Authorize>\n\t\t\t<Gratuity>";
		temp += gratuity;
		temp += "</Gratuity>\n\t\t</Amount>\n\t\t<TranInfo>\n\t\t\t<AuthCode>";
		temp += authCode;
		temp += "</AuthCode>\n\t\t\t<AcqRefData>";
		temp += acqRefData;
		temp += "</AcqRefData>\n\t\t</TranInfo>\n\t</Transaction>\n</TStream>";
		
		return temp;
	}
	
	private String getResult3()
	{
		String temp = "<TStream>\n\t<Transaction>\n\t\t<MerchantID>";
		temp += merchantID;
		temp += "</MerchantID>\n\t\t<OperatorID>test</OperatorID>\n\t\t<TranType>";
		temp += tranType;
		temp += "</TranType>\n\t\t<TranCode>";
		temp += tranCode;
		temp += "</TranCode>\n\t\t<InvoiceNo>";
		temp += invoiceNo;
		temp += "</InvoiceNo>\n\t\t<RefNo>";
		temp += refNo;
		temp += "</RefNo>\n\t\t<Memo>";
		temp += memo;
		temp += "</Memo>\n\t\t<RecordNo>";
		temp += recordNo;
		temp += "</RecordNo>\n\t\t<Frequency>";
		temp += frequency;
		temp += "</Frequency>\n\t\t<Amount>\n\t\t\t<Purchase>";
		temp += purchase;
		temp += "</Purchase>\n\t\t</Amount>\n\t\t<TranInfo>\n\t\t\t<AcqRefData>";
		temp += acqRefData;
		temp += "</AcqRefData>\n\t\t\t<ProcessData>";
		temp += processData;
		temp += "</ProcessData>\n\t\t\t<AuthCode>";
		temp += authCode;
		temp += "</AuthCode>\n\t\t</TranInfo>\n\t</Transaction>\n</TStream>";
		
		return temp;
	}
	//for magtek ipad 100kb
	private String getResult4()
	{
		String temp = "<TStream>\n\t<Transaction>\n\t\t<MerchantID>";
		temp += merchantID;
		temp += "</MerchantID>\n\t\t<OperatorID>test</OperatorID>\n\t\t<TranType>";
		temp += tranType;
		temp += "</TranType>\n\t\t<TranCode>";
		temp += tranCode;
		temp += "</TranCode>\n\t\t<InvoiceNo>";
		temp += invoiceNo;//"38";
		temp += "</InvoiceNo>\n\t\t<RefNo>";
		temp += refNo;//"38";
		temp += "</RefNo>\n\t\t<Memo>";
		temp += memo;
		temp += "</Memo>\n\t\t<PartialAuth>";
		temp += partialAuth;
		temp += "</PartialAuth>\n\t\t<Frequency>";
		temp += frequency;
		temp += "</Frequency>\n\t\t<RecordNo>";
		temp += recordNo;
		temp += "</RecordNo>";
		temp += "\n\t\t<Account>";
		temp += "\n\t\t\t<EncryptedFormat>MagneSafe</EncryptedFormat>\n\t\t\t<AccountSource>";
		temp += entryType + "</AccountSource>";
		temp += "\n\t\t\t<EncryptedBlock>";
		temp += encryptedBlock;
		temp += "</EncryptedBlock>\n\t\t\t<EncryptedKey>";
		temp += encryptedKey;
		temp += "</EncryptedKey>\n\t\t</Account>";
		temp += "\n\t\t<Amount>\n\t\t\t<Purchase>";
		temp += purchase;
		temp += "</Purchase>\n\t\t\t<Authorize>";
		temp += authorize;
		temp += "</Authorize>\n\t\t</Amount>";
		if(entryType != null){
			if(entryType.equals("Keyed")){	
		temp += "\n\t\t<AVS>\n\t\t\t<Address>" + addr + "</Address>";
		temp += "\n\t\t\t<Zip>" + zip + "</Zip>\n\t\t</AVS>";
			}
		}
		temp += "\n\t</Transaction>\n</TStream>";
		
		return temp;
	}

	private String getResult5()
	{
		String temp = "<TStream>\n\t<Transaction>\n\t\t<MerchantID>";
		temp += merchantID;
		temp += "</MerchantID>\n\t\t<TranType>";
		temp += tranType;
		temp += "</TranType>\n\t\t<TranCode>";
		temp += tranCode;
		temp += "</TranCode>\n\t\t<InvoiceNo>";
		temp += invoiceNo;
		temp += "</InvoiceNo>\n\t\t<RefNo>";
		temp += refNo;
		temp += "</RefNo>\n\t\t<Memo>";
		temp += memo;
		temp += "</Memo>\n\t\t<RecordNo>";
		temp += recordNo;
		temp += "</RecordNo>\n\t\t<Frequency>";
		temp += frequency;
		temp += "</Frequency>\n\t\t<Amount>\n\t\t\t<Purchase>";
		temp += purchase;
		temp += "</Purchase>\n\t\t</Amount>\n\t\t\t<AuthCode>";
		temp += authCode;
		temp += "</AuthCode>\n\t</Transaction>\n</TStream>";
		
		return temp;
	}
	
	private String getResult6()
	{
		String temp = "<TStream>\n\t<Transaction>\n\t\t<MerchantID>";
		temp += merchantID;
		temp += "</MerchantID>\n\t\t<TranType>";
		temp += tranType;
		temp += "</TranType>\n\t\t<TranCode>";
		temp += tranCode;
		temp += "</TranCode>\n\t\t<InvoiceNo>";
		temp += invoiceNo;
		temp += "</InvoiceNo>\n\t\t<RefNo>";
		temp += refNo;
		temp += "</RefNo>\n\t\t<Memo>";
		temp += memo;
		temp += "</Memo>\n\t\t<Frequency>";
		temp += frequency;
		temp += "</Frequency>\n\t\t<RecordNo>";
		temp += recordNo;
		temp += "</RecordNo>\n\t\t<PartialAuth>";
		temp += partialAuth;
		temp += "</PartialAuth>\n\t\t<Account>\n\t\t\t<AcctNo>";
		temp += accountNum;
		temp += "</AcctNo>\n\t\t\t<ExpDate>";
		temp += expDate;
		temp += "</ExpDate>\n\t\t</Account>\n\t\t<Amount>\n\t\t\t<Purchase>";
		temp += purchase;
		temp += "</Purchase>\n\t\t\t<Authorize>";
		temp += authorize;
		temp += "</Authorize>\n\t\t</Amount>";
		temp += "\n\t\t<CVVData>" + cvv + "</CVVData>";
		temp += "\n\t<AVS>\n\t\t<Address>" + addr + "</Address>";
		temp += "\n\t\t<Zip>" + zip + "</Zip>\n\t</AVS>";
		temp+= "\n\t</Transaction>\n</TStream>";
		
		return temp;
	}
	
	private String getResult7()
	{
		String temp = "<TStream>\n\t<Transaction>\n\t\t<MerchantID>";
		temp += merchantID;
		temp += "</MerchantID>\n\t\t<TranType>";
		temp += tranType;
		temp += "</TranType>\n\t\t<TranCode>";
		temp += tranCode;
		temp += "</TranCode>\n\t\t<InvoiceNo>";
		temp += invoiceNo;
		temp += "</InvoiceNo>\n\t\t<RefNo>";
		temp += refNo;
		temp += "</RefNo>\n\t\t<Memo>";
		temp += memo;
		temp += "</Memo>\n\t\t<RecordNo>";
		temp += recordNo;
		temp += "</RecordNo>\n\t\t<Frequency>";
		temp += frequency;
		temp += "</Frequency>\n\t\t<Amount>\n\t\t\t<Purchase>";
		temp += purchase;
		temp += "</Purchase>\n\t\t\t<Authorize>";
		temp += authorize;	
		temp +=	"</Authorize>";
		temp += "\n\t\t\t<Gratuity>" + gratuity + "</Gratuity>";		
        temp += "\n\t\t</Amount>";
		temp += "\n\t\t\t<AuthCode>" + authCode + "</AuthCode>";
		temp += "\n\t</Transaction>\n</TStream>";
		return temp;
		
	}
	private String getResult10()
	{
		String temp = "<TStream>\n\t<Transaction>\n\t\t<MerchantID>";
		temp += merchantID;
		temp += "</MerchantID>\n\t\t<OperatorID>test</OperatorID>\n\t\t<TranType>";
		temp += tranType;
		temp += "</TranType>\n\t\t<TranCode>";
		temp += tranCode;
		temp += "</TranCode>\n\t\t<InvoiceNo>";
		temp += invoiceNo;//"38";
		temp += "</InvoiceNo>\n\t\t<RefNo>";
		temp += refNo;//"38";
		temp += "</RefNo>\n\t\t<Memo>";
		temp += memo;
		temp += "</Memo>";
		temp += "\n\t\t<Account>";
		temp += "\n\t\t\t<EncryptedFormat>MagneSafe</EncryptedFormat>\n\t\t\t<AccountSource>Swiped</AccountSource>";
		temp += "\n\t\t\t<EncryptedBlock>";
		temp += encryptedBlock;
		temp += "</EncryptedBlock>\n\t\t\t<EncryptedKey>";
		temp += encryptedKey;
		temp += "</EncryptedKey>\n\t\t</Account>";
		temp += "\n<Amount>\n\t\t\t<Purchase>";
		temp += purchase;
		temp += "</Purchase>\n\t\t\t<CashBack>";
		temp += cashback;
		temp += "</CashBack>\n\t\t</Amount>";
		temp += "\n\t\t<PIN>\n\t\t\t<PINBlock>";
		temp += encPin;
		temp += "</PINBlock>\n\t\t\t<DervdKey>";
		temp += dervk;
		temp += "</DervdKey>\n\t\t</PIN>";
		temp += "\n\t</Transaction>\n</TStream>";
		
		return temp;
	}
	private String getResult11()
	{
		String temp = "<TStream>\n\t<Transaction>\n\t\t<IpPort>9100</IpPort>\n\t\t<MerchantID>";
		temp += merchantID;
		temp += "</MerchantID>";
		temp += "\n\t\t<OperatorID>test</OperatorID>";
		temp += "\n\t\t<TranType>";
		temp += tranType;
		temp += "</TranType>\n\t\t<TranCode>";
		temp += tranCode;
		temp += "</TranCode>\n\t\t<InvoiceNo>";
		temp += invoiceNo;
		temp += "</InvoiceNo>\n\t\t<RefNo>";
		temp += refNo;
		temp += "</RefNo>\n\t\t<Memo>";
		temp += memo;
		temp += "</Memo>";
		temp += "\n\t\t<Account>\n\t\t\t";
		temp += "\n\t\t\t<EncryptedFormat>MagneSafe</EncryptedFormat>\n\t\t\t<AccountSource>";
		temp += entryType + "</AccountSource>";
		temp += "\n\t\t\t<EncryptedBlock>";
		temp += encryptedBlock;
		temp += "</EncryptedBlock>\n\t\t\t<EncryptedKey>";
		temp += encryptedKey;
		temp += "</EncryptedKey>\n\t\t</Account>";
		if(!tranCode.equals("Balance")){
			temp += "\n\t\t<Amount>\n\t\t\t<Purchase>" + purchase;
			temp += "</Purchase>\n\t\t</Amount>";
		}
		if(tranCode.contains("Void")){
			temp += "\n\t\t<AuthCode>" + authCode + "</AuthCode>";
		}
		temp+= "\n\t</Transaction>\n</TStream>";
		
		return temp;
	}
	private String getResult12()
	{
		String temp = "<TStream>\n\t<Admin>\n\t\t<MerchantID>";
		temp += merchantID;
		temp += "</MerchantID>\n\t\t<TranCode>";
		temp += tranCode;
		temp += "</TranCode>\n\t\t<Memo>";
		temp += memo;
		temp += "</Memo>\n\t\t</Admin>";
		temp += "\n</TStream>";
		
		return temp;
	}
	//for magtek ipad 100kb Credit Sale
		private String getResult13()
		{
			String temp = "<TStream>\n\t<Transaction>\n\t\t<MerchantID>";
			temp += merchantID;
			temp += "</MerchantID>\n\t\t<OperatorID>test</OperatorID>\n\t\t<TranType>";
			temp += tranType;
			temp += "</TranType>\n\t\t<TranCode>";
			temp += tranCode;
			temp += "</TranCode>\n\t\t<InvoiceNo>";
			temp += invoiceNo;//"38";
			temp += "</InvoiceNo>\n\t\t<RefNo>";
			temp += refNo;//"38";
			temp += "</RefNo>\n\t\t<Memo>";
			temp += memo;
			temp += "</Memo>\n\t\t<PartialAuth>";
			temp += partialAuth;
			temp += "</PartialAuth>\n\t\t<Frequency>";
			temp += frequency;
			temp += "</Frequency>\n\t\t<RecordNo>";
			temp += recordNo;
			temp += "</RecordNo>";
			temp += "\n\t\t<Account>";
			temp += "\n\t\t\t<EncryptedFormat>MagneSafe</EncryptedFormat>\n\t\t\t<AccountSource>";
			temp += entryType + "</AccountSource>";
			temp += "\n\t\t\t<EncryptedBlock>";
			temp += encryptedBlock;
			temp += "</EncryptedBlock>\n\t\t\t<EncryptedKey>";
			temp += encryptedKey;
			temp += "</EncryptedKey>\n\t\t</Account>";
			temp += "\n\t\t<Amount>\n\t\t\t<Purchase>";
			temp += purchase;
			temp += "</Purchase>\n\t\t</Amount>";
			if(entryType != null){
				if(entryType.equals("Keyed")){
					temp += "\n\t\t<AVS>\n\t\t\t<Address>" + addr + "</Address>";
					temp += "\n\t\t\t<Zip>" + zip + "</Zip>\n\t\t</AVS>";
				}
			}
			temp += "\n\t</Transaction>\n</TStream>";
			
			return temp;
		}

	
	public void send()
	{
		try {
			MercuryWebRequest test = new MercuryWebRequest(webURL);
			//if(!batch)
				test.addParameter("tran", result);
			//else
				//test.addParameter("merchant", merchantID);
			
			System.out.println(password);
			//test.addParameter("merchant", merchantID);
			test.addParameter("pw", /*"123TOKEN""xyz" */password);
			if(tranType.equals("Credit")){
				test.setWebMethodName("CreditTransaction");
			}
			else{
				test.setWebMethodName("GiftTransaction");

			}
			//if(batch){
				//test.setWebMethodName("CBatch");
			//}

			//
			test.setTimeout(timeout);
			String response = test.sendRequest();
			//JOptionPane.showMessageDialog(null, response);
			if(response.contains("\t")){
			this.response = response.replaceAll(">\t", ">\n\t");}
			else{this.response = response.replaceAll("><", ">\n\t<").replaceAll("> {3,6}", ">\n\t");}
			//System.out.println(response.replaceAll(">\t", ">\n\t"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof java.net.ConnectException || e instanceof java.net.UnknownHostException){
				this.response = "Timeout";
			}
			else if(e instanceof java.net.SocketTimeoutException){
				this.response = "Timeout2";
			}
			
		}
		
		
	}
	public void setIDnPas(String id){
		initProperties();
		//for voidsale, reference appropriate ids based on merchantID on the sent receipt 
		if(id.equals("395347306=TOKEN")){
			id = "merchantID1";
		}
		else if(id.equals("395347308=E2ETKN")){
			id = "merchantID2";
		}
		
		this.merchantID = properties.getProperty(id);
		System.out.println(merchantID);
		String temp = id.substring(id.length()-1);
		
		temp = "password" + temp;
		this.password = properties.getProperty(temp);
		System.out.println(password);
		
	}
	public String getResponse() {
		return this.response;
	}
	
	public String getXML() {
		return this.result;
	}
}
