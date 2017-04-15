package com.yong.vers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *  yongzhang
 *
 */
public class XMLConverter
{
  private final static String DRIVER = "com.jnetdirect.jsql.JSQLDriver";
  private final static String URL_STRING = "jdbc:JSQLConnect://CNXATLSQL09\\JVDEV08/database=hp_prod_copy_tran";
  private final static String USER = "scrub_user";
  private final static String PASSWORD = "password1234";
  
  private static PreparedStatement query = null;
  private static PreparedStatement phoneQuery = null;
  private static PreparedStatement mgaQuery = null;
  
  private static Map <String, PaymentInfo> paymentInfoMap = new HashMap<String, PaymentInfo> ();
  
  
  private static String appXref = "";
  private static String memberPlanXref = "";
  private static String planPremium = "";
  private static List<Member> members = new ArrayList<Member>();
  
  private static int questionId;
  
  private static String frequency = "";
  private static String ongoing = "";
  private static String first = "";
  
  private static String recipient = "";
  private static String addr1 = "";
  private static String addr2 = "";
  private static String zip = "";
  private static String city = "";
  private static String state = "";

  
  public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException
  {
  	if(args.length > 0 && "count".equals(args[0])){
  		String path = "C:\\\\Users\\yongzhang\\documents\\Dropbox\\public\\";
  		String fileName = "environments.txt";
  		Map <String, Integer> map = new HashMap<String, Integer>();
  		File inFile = new File(path + fileName);
  		BufferedReader reader = new BufferedReader(new FileReader(inFile));
  		String aLine = null;
  		while ((aLine = reader.readLine()) != null)
  		{
  			System.out.println(aLine);
  			String [] words = aLine.split("[\\s\\t,.?!\\n\\r\\f:;\\-&%_\\(\\)/]");
  			for(String s : words)
  			{
  				if(s == null || !s.matches("[A-Za-z0-9\\-]+"))
  				{
  					continue;
  				}
  				
  				Integer count = map.get(s);
  				if(count == null)
  				{
  					count = new Integer(1);
  					map.put(s, count);
  				}
  				else
  				{
  					count = new Integer(count.intValue() + 1);
  					map.put(s, count);
  				}
  			}
  		}
  		
  		reader.close();
  		
  		Map.Entry<String, Integer> mostCount = null;
  		Map.Entry<String, Integer> secondMostCount = null;
  		
  		for(Map.Entry <String, Integer> anEntry : map.entrySet())
  		{
  			System.out.println(anEntry.getKey() + anEntry.getValue());
  			
  			if(mostCount == null && secondMostCount == null)
  			{
  				mostCount = anEntry;
  				secondMostCount = anEntry;
  			}
  			else if(anEntry.getValue().intValue() > mostCount.getValue().intValue())
  			{
  				mostCount = anEntry;
  			}
  			else if(anEntry.getValue().intValue() > secondMostCount.getValue().intValue())
  			{
  				secondMostCount = anEntry;
  			}
  		}
  		
  		System.out.println("Most word: " + mostCount.getKey() + "; Count: " + (mostCount != null ? mostCount.getValue() : ""));
  		System.out.println("Second Most word: " + secondMostCount.getKey()  + "; Count: " +  (secondMostCount != null ? secondMostCount.getValue() : ""));
  		
  		return;
  		
  	}
  	
  	Class.forName(DRIVER);
    Connection conn = DriverManager.getConnection(URL_STRING, USER, PASSWORD);
    query = conn.prepareStatement("select app.xref, q.question_id, r.string_value, c.code "
    		+ "from en_application app join en_app_section apps on apps.application_id=app.application_id " 
    		+ "join en_question_set quess on quess.app_section_id=apps.app_section_id "
    		+ "join en_question_response_set quesrs on quesrs.question_set_id=quess.question_set_id and quesrs.survey_id=quess.survey_id "
    		+ "join sv_response_set rs on rs.response_set_id=quesrs.response_set_id and rs.survey_id=quesrs.survey_id "
    		+ "join sv_question q on q.survey_id=rs.survey_id "
    		+ "join sv_response r on r.response_set_id=rs.response_set_id and r.question_id=q.question_id "
    		+ "left join sv_choice c on c.choice_id=r.choice_id "
    		+ "where app.xref in (?) and quess.survey_id in (5,9) and q.question_id in (18,19,20,77,78,79,80,81,82) " 
    		+ "order by app.xref, q.question_id");
    
    phoneQuery = conn.prepareStatement("select CATEGORY, AREA_CODE + NUM as NUMBER " +
    		"from CB_PHONE p join EN_APPLICANT a on a.CONTACT_ID=p.CONTACT_ID " +
    		"join EN_APPLICATION app on app.APPLICATION_ID=a.APPLICATION_ID " +
    		"where app.XREF=(?) and CATEGORY in (3, 1008)");
    
    mgaQuery = conn.prepareStatement("select r3.ROLE_KEY, o3.XREF " +
    "from EN_APPLICATION left join EN_APPLICATION_RESOURCE on EN_APPLICATION_RESOURCE.application_id=EN_APPLICATION.application_id " +
    "join OW_RESOURCE on OW_RESOURCE.resource_id=EN_APPLICATION_RESOURCE.resource_id " +
    "join OW_RESOURCE_OWNER on OW_RESOURCE_OWNER.resource_id=OW_RESOURCE.resource_id " +
    "join OW_OWNER_ROLE r on r.owner_role_id=OW_RESOURCE_OWNER.owner_role_id " +
    "join OW_OWNER_RELATION rel ON rel.to_owner_role_id = r.owner_role_id join OW_OWNER_ROLE r2 on r2.owner_role_id=rel.from_owner_role_id " +
    "join OW_OWNER_RELATION rel2 ON rel2.to_owner_role_id = r2.owner_role_id join OW_OWNER_ROLE r3 on r3.owner_role_id=rel2.from_owner_role_id " +
    "join OW_OWNER o3 on o3.owner_id=r3.owner_id where EN_APPLICATION.xref in (?)");
    
    int appCount = 0;
    int memberTotalCount = 0;
    int memberCount = 0;
		int amtCount = 0;
    
		
		
		String path = "C:\\\\Connecture\\healthplus_config\\temp\\";
		
		//String name = "AppIds_No_XML";
		
		//String name = "AppIds_Bad";
		
		//String name = "AppIds_Exported";
		//String name = "AppIds_Bad";
		
		//String name = "AppIds_HP2";
		
		//String name = "AppIds_HP_Missing";
		//String name = "AppIds_HP_Missing_Cfrmd";	
		
		String name = "Outbound834File_20140112_022507";
    
		if(args.length >= 1)
		{
			if("sql".equals(args[0]))
			{
				generateQueryStrings(path, name, args.length >= 2 && "string".equals(args[1]));
			}
			else if("sort".equals(args[0]))
			{
				sortStrings(path, name);
			}
			
			return;
		}
		
		boolean fromDatabase = false;
		String queryResults = "hp_cr18593.csv";
		
		if(!fromDatabase)
		{
			readData(path + queryResults);
		}
		
    File xmlOutput = new File(path + name + ".xml");
    FileOutputStream writer = new FileOutputStream(xmlOutput);
    
    File ediInput = new File(path + name + ".txt");
    BufferedReader reader = new BufferedReader(new FileReader(ediInput));
    
    String aLine = reader.readLine();
   
    //System.out.println(aLine);
    String [] lineParts = aLine.split("\\*");
    
    writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n".getBytes());
    writer.write(("<IFP_SUPPLEMENTARY ID=\"" + lineParts[13] + "\">\n").getBytes());
    while(true)
    {	
    	aLine = reader.readLine();
    	
    	if(aLine.startsWith("IEA*1*"))
    	{
    		break;
    	}
    	else
    	{
    		lineParts = aLine.split("\\*");
    		if(aLine.startsWith("ST*834*"))
				{
    			appCount++;
    			memberCount = 0;
    			
    			appXref = lineParts[2];
    			//System.out.println(xref);
				} 		
    		else if(aLine.startsWith("INS*"))
    		{	
    			members.add(new Member());
    			memberCount++;
    			amtCount = 0;
    		}	
    		else if(aLine.startsWith("REF*0F*"))
				{
    			String memberId = stripEnding(lineParts[2]);
    			members.get(memberCount -1).setMemberId(memberId);
				}
    		else if(aLine.startsWith("NM1*IL*"))
				{
    			String ln = lineParts[3];
    			String fn = lineParts[4];

    			members.get(memberCount -1).setFn(fn);
    			members.get(memberCount -1).setLn(ln);
				}
    		else if(aLine.startsWith("HD*021*"))
				{
    			if(memberCount == 1)
    			{
      			memberPlanXref = lineParts[4];
    			}
    			
    			members.get(memberCount -1).setMemberPlanXref(lineParts[4]);
				}  
    		else if(aLine.startsWith("AMT*P3*"))
				{
    			amtCount ++;
    			
    			if(memberCount == 1 && amtCount == 1)
    			{
    				planPremium = stripEnding(lineParts[2]);    				
    			}
    			
    			if(amtCount == 2)
    			{
    				String memberPremium = stripEnding(lineParts[2]);
    				members.get(memberCount -1).setMemberPremium(memberPremium);
    			}
    		}	
    		else if(aLine.startsWith("SE*"))
    		{
    			writeApplication(writer, appXref, members, fromDatabase);
    			
    			memberTotalCount += memberCount;
    			
        	appXref = "";
          memberPlanXref = "";
          planPremium = "";
          members = new ArrayList();
          
          questionId = 0;
          
          frequency = "";
          ongoing = "";
          first = "";
          
          recipient = "";
          addr1 = "";
          addr2 = "";
          zip = "";
          city = "";
          state = "";
    		}
			}
    }
    
    reader.close();
    
    writer.write("</IFP_SUPPLEMENTARY>".getBytes());
    System.out.println(appCount);
    System.out.println(memberTotalCount);
  }
  
  private static void writeApplication(FileOutputStream writer, String xref, List<Member> members, boolean fromDatabase) throws SQLException, IOException
  {
    if(fromDatabase)
    {
    	query.setString(1, xref);
    	ResultSet rs = query.executeQuery();
    
	    while(rs.next())
	    {    			
	    	questionId = rs.getInt("question_id");
	    	if(questionId == 18)
	    	{
	    		first = rs.getString("code");
	    	}
	    	else if(questionId == 19)
	    	{
	    		ongoing = rs.getString("code");
	    	}
	    	else if(questionId == 20)
	    	{
	    		frequency = rs.getString("code");
	    	}
	    	else if(questionId == 77)
	    	{
	    		recipient = rs.getString("string_value");
	    	}
	    	else if(questionId == 78)
	    	{
	    		addr1 = rs.getString("string_value");
	    	}
	    	else if(questionId == 79)
	    	{
	    		addr2 = rs.getString("string_value");
	    	}
	    	else if(questionId == 80)
	    	{
	    		city = rs.getString("string_value");
	    	}
	    	else if(questionId == 81)
	    	{
	    		state = rs.getString("string_value");
	    	}
	    	else if(questionId == 82)
	    	{
	    		zip = rs.getString("string_value");
	    	}
	    }
	    
	    rs.close();
    }
    else
    {
    	PaymentInfo info = paymentInfoMap.get(xref);
    	
    	if(info == null)
    	{
    		info = new PaymentInfo();
    	}
    	
    	 frequency = info.getFrequency();
       ongoing = info.getOngoing();
       first = info.getFirst();
       
       recipient = info.getRecipient();
       addr1 = info.getAddr1();
       addr2 = info.getAddr2();
       zip = info.getZip();
       city = info.getCity();
       state = info.getState();
    }
    
    if(ongoing.equals("sameAsOngoing"))
  	{
  		ongoing = first;
  	}
    
    String cellPhone = "";
    String workPhone = "";

    phoneQuery.setString(1, xref);
    ResultSet prs = phoneQuery.executeQuery();
    
    while(prs.next())
    {
    	int category = prs.getInt(1);
    	if(category == 3)
    	{
    		cellPhone = prs.getString(2);
    	}
    	if(category == 1008)
    	{
    		workPhone = prs.getString(2);
    	}
    }
    
    prs.close();
    
    String mga = "";
    /*   
    mgaQuery.setString(1, xref);
    ResultSet mrs = mgaQuery.executeQuery();

    while(mrs.next())
    {
    	//if("mga".equals(mrs.getString(1)) && !"AIMMGA".equals(mrs.getString(2)))
    	if("mga".equals(mrs.getString(1)))
    	{
    		mga = mrs.getString(2);
    	}
    }
    
    mrs.close();
    */   
    //System.out.println("Xref: " + xref + "\t" + "Frequency: " +  frequency + "\t"  + "\t" + "Ongoing: " + ongoing + "\t" + "First: " + first);
    
    String out = "<APPLICATION>\n" +
					        "<APP_ID>" + xref + "</APP_ID>\n" +
					        "<MGA_XREF>" + mga + "</MGA_XREF>\n" +
					        "<PAYMENT_DETAILS>\n" +
					            "<PAYMENT_FREQUENCY>" + frequency + "</PAYMENT_FREQUENCY>\n" +
					            "<PAYMENT_ONGOING>\n" +
					                "<PAYMENT_TYPE>" + ongoing + "</PAYMENT_TYPE>\n";
    if(ongoing.equals("bill_skip"))
    {
							    	out +=  "<BILL_ME>\n" +
							                    "<RECIPIENT_NAME>" + recipient + "</RECIPIENT_NAME>\n" +
							                    "<ADDRESS_LINE1>" + addr1 + "</ADDRESS_LINE1>\n" +
							                    "<ADDRESS_LINE2>" + addr2 + "</ADDRESS_LINE2>\n" +
							                    "<CITY>" + city + "</CITY>\n" +
							                    "<STATE>" + state + "</STATE>\n" +
							                    "<ZIP_CODE>" + zip + "</ZIP_CODE>\n" +
							                "</BILL_ME>\n";
    }
    else
    {
    									out += "<BILL_ME/>\n";
    }
    
					    out +=  "</PAYMENT_ONGOING>\n" +
					    		"</PAYMENT_DETAILS>\n";
    
    int i = 0;
    for(Member member : members)
    {   
    	String cPhone = "";
    	String wPhone = "";
    	if(i == 0)
    	{
    		if(cellPhone != null)
    		{
    			cPhone = cellPhone;
    		}
    		if(workPhone != null)
    		{
    			wPhone = workPhone;
    		}
    	}
    	out += getApplicant(member, planPremium, cPhone, wPhone);
    	i++;
    }
    
    		out += "</APPLICATION>\n";
        
    writer.write(out.getBytes());
  }
  
  private static String getApplicant(Member member, String planPremium, String cPhone, String wPhone)
  {
  	return "<APPLICANT>\n" +
        "<MEMBER_ID>" + member.getMemberId() + "</MEMBER_ID>\n" +
        "<FIRST_NAME>" + member.getFn() + "</FIRST_NAME>\n" +
        "<LAST_NAME>" + member.getLn() + "</LAST_NAME>\n" +
        "<WORK_PHONE>" + wPhone + "</WORK_PHONE>\n" +
        "<CELL_PHONE>" + cPhone + "</CELL_PHONE>\n" +
        "<RATE_RISK_TIER>Standard</RATE_RISK_TIER>\n" +
        "<PLAN>\n" +
            "<PLAN_XREF>" + member.getMemberPlanXref() + "</PLAN_XREF>\n" +
            "<PLAN_PREMIUM>" + planPremium + "</PLAN_PREMIUM>\n" +
            "<MEMBER_PREMIUM>" + member.getMemberPremium() + "</MEMBER_PREMIUM>\n" +
            "<PLAN_OPTION>\n" +
                "<OPTION_XREF>Coinsurance</OPTION_XREF>\n" +
                "<OPTION_VALUE_XREF>" + member.getMemberPlanXref() + "</OPTION_VALUE_XREF>\n" +
            "</PLAN_OPTION>\n" +
        "</PLAN>\n" +
    "</APPLICANT>\n";
  }
  
  private static String stripEnding(String s)
  {
  	String [] parts = s.split("~");
  	return parts[0];	
  }
  
  private static void readData(String fileName) throws IOException
  {
  	File fileInput = new File(fileName);
    BufferedReader reader = new BufferedReader(new FileReader(fileInput));
    
    String aLine = null;
   
    while((aLine = reader.readLine()) != null)
    {
    //System.out.println(aLine);
      String [] lineParts = aLine.split(",");
      String paymentXref = lineParts[0];
      
      PaymentInfo paymentInfo = paymentInfoMap.get(paymentXref);
      if(paymentInfo == null)
      {
      	paymentInfo = new PaymentInfo();
      	paymentInfoMap.put(paymentXref, paymentInfo);
      }
      
      String questionId = lineParts[3];
      if("18".equals(questionId))
      {
      	paymentInfo.setFirst(lineParts[6]);
      }
      else if("19".equals(questionId))
      {
      	paymentInfo.setOngoing(lineParts[6]);
      }
      else if("20".equals(questionId))
      {
      	paymentInfo.setFrequency(lineParts[6]);
      }
      else if("77".equals(questionId))
      {
      	paymentInfo.setRecipient(lineParts[5]);
      }
      else if("78".equals(questionId))
      {
      	paymentInfo.setAddr1(lineParts[5]);
      }
      else if("79".equals(questionId))
      {
      	paymentInfo.setAddr2(lineParts[5]);
      }
      else if("80".equals(questionId))
      {
      	paymentInfo.setCity(lineParts[5]);
      }
      else if("81".equals(questionId))
      {
      	paymentInfo.setState(lineParts[5]);
      }
      else if("82".equals(questionId))
      {
      	paymentInfo.setZip(lineParts[5]);
      }
      /*
       * 	18	First Payment
					19	How do you want to make ongoing payments?
					20	How often are you going to make payments?

        	77	Ongoing payment recipient name.
					78	Ongoing payment check address line 1.
					79	Ongoing payment check address line 2.
					80	Ongoing payment check address City.
					81	Ongoing payment check address State.
					82	Ongoing payment check address ZIP Code.
       */
    }
  }
  
  private static void generateQueryStrings(String filePath, String fileName, boolean withQuotes) throws IOException
  {
  	File inFile = new File(filePath + fileName + ".txt");
    BufferedReader reader = new BufferedReader(new FileReader(inFile));
    
		File outFile = new File(filePath + fileName + "_sql.txt");
    FileWriter writer = new FileWriter(outFile);
    
    int count = 0;
    String xref = null;
    
    while((xref = reader.readLine()) != null)
    {
    	writer.write((withQuotes ? "'" : "") + xref + (withQuotes ? "'," : ","));
    	count++;
    	
    	if(count == 10 || (count - 10) %12 == 0)
    	{
    		writer.write("\n");
    	}
    }
    writer.flush();
    
    System.out.println(count);
  }
  
  private static void sortStrings(String filePath, String fileName) throws IOException
  {
  	File inFile = new File(filePath + fileName + ".txt");
    BufferedReader reader = new BufferedReader(new FileReader(inFile));
		  
    int count = 0;
    String xref = null;
    
    Map<String, String> stringMap = new TreeMap<String, String>();
    
    while((xref = reader.readLine()) != null)
    {
    	stringMap.put(xref, xref);
    	count++;
    }
    
    reader.close();
    
    File outFile = new File(filePath + fileName + "_sorted.txt");
	 	FileWriter writer = new FileWriter(outFile);
	 	
    for(String s : stringMap.keySet())
    {
  	 	
    	writer.write(s + "\n");
    }
    
    writer.flush();
    writer.close();
    
    System.out.println("inpurt count:" + count + "; sorted count:" + stringMap.size());
  }
}