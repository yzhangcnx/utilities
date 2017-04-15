package com.yong.vers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Pattern;
import javax.mail.Session;
import javax.swing.JOptionPane;

import com.rwsol.framework.security.Crypto;
import com.yong.vers.util.AuthenticatorHelper;
import com.yong.vers.util.EmailUtil;
import com.yong.vers.util.WPNECipher;

public class VersWizard
{ 
  public static int count = 0;
  public static void main(String [] args) throws Exception
  {
  	/*System.out.println("");
  	Pattern letterOnly = Pattern.compile("\\w+");
  	
  	String sourceStr = "0Z:0Z:::::NA::NA::";
  	
  	StringTokenizer tokenizer = new StringTokenizer(sourceStr, ":");
  	String tokenizedArray[] = new String[tokenizer.countTokens()];
  	
    int ii = 0;
    while (tokenizer.hasMoreTokens())
    {
    	tokenizedArray[ii++] = tokenizer.nextToken();
    }
    
    StringTokenizer stringTokonizer = new StringTokenizer(sourceStr, ":");
    String stringTokenizedArray[] = new String[stringTokonizer.countTokens()];
    
    int jj = 0;
    while (stringTokonizer.hasMoreTokens())
    {
    	stringTokenizedArray[jj++] = stringTokonizer.nextToken();
    }
    
    String splittedArray [] = sourceStr.split("\\:", 11);
    */
    
    if(args.length >= 1)
    {
      String func = args[0];
      
      if(func.equalsIgnoreCase("time")) 
      {
        //an example of possible input: "20070308 12:30", "America/Chicago"
        if(VersHelper.isTimeValid(args[1], args[2])) 
        {
          System.out.println("Time Ok");
        }
        else
        {
          System.out.println("Time Invalid");
        }
      }
      else if(func.equals("friendly"))
      {
      	String s1 = "timmetabi";
      	String s2 = "bammittie";
      	//StringTokenizer st1 = new StringTokenizer(s1);
      	//StringTokenizer st2 = new StringTokenizer(s2);
      	Map<Character, Integer> sm1 = new HashMap<Character, Integer>();
      	Map<Character, Integer> sm2 = new HashMap<Character, Integer>();
      	
      	List<Character> sl1 = new ArrayList<Character>();
      	List<Character> sl2 = new ArrayList<Character>();
      	
      	for (int i = 0; i < s1.length(); i++)
      	{
      		char c = s1.charAt(i);
      		Character cObject = new Character(c);
      		sl1.add(cObject);
      		if(!sm1.keySet().contains(cObject))
      		{
      			sm1.put(cObject, new Integer(1));
      		}
      		else
      		{
      			int newCount = sm1.get(cObject).intValue() + 1;
      			sm1.put(cObject, new Integer(newCount));
      		}
      	}
      	
      	for (int j = 0; j < s2.length(); j++)
      	{
      		char c = s2.charAt(j);
      		Character cObject = new Character(c);
      		sl2.add(cObject);
      		if(!sm2.keySet().contains(cObject))
      		{
      			sm2.put(cObject, new Integer(1));
      		}
      		else
      		{
      			int newCount = sm2.get(cObject).intValue() + 1;
      			sm2.put(cObject, new Integer(newCount));
      		}
      	}
      	
      	Character [] s1ListArray = sl1.toArray(new Character[sl1.size()]);
      	Character [] s2ListArray = sl2.toArray(new Character[sl2.size()]);
      	Arrays.sort(s1ListArray);
      	Arrays.sort(s2ListArray);
      	
      	if(sl1.equals(sl2))
      	{
      		System.out.println("List sorted by Arrays.sort()");
      	}
      	
      	Collections.sort(sl1);
      	Collections.sort(sl2);
      	
      	String [] s1Array = s1.split("");
      	String [] s2Array = s2.split("");
      	Arrays.sort(s1Array);
      	Arrays.sort(s2Array);
      	
      	char [] s1CharArray = s1.toCharArray();
      	char [] s2CharArray = s2.toCharArray();
      	Arrays.sort(s1CharArray);
      	Arrays.sort(s2CharArray);
      	
      	if(!sm1.isEmpty() && sm1.equals(sm2))
      	{
      		System.out.println("HashMap compare " + true);
      	}
      	else
      	{
      		System.out.println("HashMap compare " + false);
      	}
      	
      	if(Arrays.equals(s1ListArray, s2ListArray))
      	{
      		System.out.println("List Array compare " + true);
      	}
      	else
      	{
      		System.out.println("List Array compare " + false);
      	}
      	
      	if(!sl1.isEmpty() && sl1.equals(sl2))
      	{
      		System.out.println("Array List compare " + true);
      	}
      	else
      	{
      		System.out.println("Array List compare " + false);
      	}
      	
      	if(Arrays.equals(s1Array, s2Array))
      	{
      		System.out.println("String Arrays compare " + true);
      	}
      	else
      	{
      		System.out.println("String Arrays compare " + false);
      	}
      	
      	if(Arrays.equals(s1CharArray, s2CharArray))
      	{
      		System.out.println("Charater Arrays compare " + true);
      	}
      	else
      	{
      		System.out.println("Charater Arrays compare " + false);
      	}
      }
      else if(func.equalsIgnoreCase("encrypt"))
      {
        try
        {
        	for(int i = 1; i < args.length; i++)
        	{
        		//SSO Face
        		System.out.println("Cipher: " + args[i] + "\t" + AuthenticatorHelper.encrypt(args[i]) + "\tFor SSO Face");
        		System.out.println("Crypto: " + args[i] + "\t" + Crypto.getInstance().encrypt(args[i]));
        		//password login
        		System.out.println("WPNECipher: " + args[i]+ "\t" + WPNECipher.encrypt(args[i] + "\tFor password login"));
        	}
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
      else if(func.equalsIgnoreCase("decrypt"))
      {
        try
        {
        	for(int i = 1; i < args.length; i++)
        	{
        		//SSO Face
        		System.out.println("Cipher: " + args[i] + "\t" + AuthenticatorHelper.decrypt(args[i]) + "\tFor SSO Face");
        		//No decryption supported
        		//System.out.println("Crypto: " + args[i] + "\t" + Crypto.getInstance().decrypt(args[i]));
            //System.out.println("WPNECipher: " + args[i]+ "\t" + WPNECipher.decrypt(args[i]));
        	}
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
      else if (func.equalsIgnoreCase("calculate"))
      {
        double biweekContr = 0.0;
        double returnRate = 0.0;
        if(args.length != 3)
        {
          Properties props = new Properties();
          try
          {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("vers.properties"));
            biweekContr = Double.parseDouble(props.getProperty("cal.biweek.contribution"));
            returnRate = Double.parseDouble(props.getProperty("cal.return.rate"));
          }
          catch(Exception e)
          {
            e.printStackTrace();
          }
        }
        else
        {
          biweekContr = Double.parseDouble(args[1]);
          returnRate = Double.parseDouble(args[2]);
        }
        System.out.println(VersHelper.getAmount(biweekContr, returnRate));
      }
      else if(func.equalsIgnoreCase("inserts"))
      {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd MMM yyyy");
        Date d = formatter.parse(args[1]);
        Calendar begin = Calendar.getInstance();
        begin.setTime(d);
        d = formatter.parse(args[2]);
        Calendar end = Calendar.getInstance();
        end.setTime(d);
        while(begin.before(end))
        {
          String insert = "insert into #t_date values('" + formatter1.format(begin.getTime()) 
          + "', '" + formatter.format(begin.getTime()) + "');";
          begin.add(Calendar.DAY_OF_YEAR, 1);
          System.out.println(insert);
        }
      }
      else if(func.equalsIgnoreCase("rename"))
      {
        String dir = ".";
        VersHelper.rename(new File(dir), args[1], args.length > 2 ? true : false);
      }
      else if(func.equalsIgnoreCase("email"))
      {
      	System.out.println("Send Email Start");
        
        //String smtpHostServer = "mailbrk.ct.com";
        String smtpHostServer = "mailrelay.connecture.net";
        String emailID = "yongzhang@connecture.com";
         
        //Properties props = System.getProperties();
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHostServer);
        props.put("mail.host", smtpHostServer);
        //props.list(System.out);
 
        Session session = Session.getInstance(props, null);
        //session.getProperties().setProperty("mail.smtp.host", smtpHostServer);
        EmailUtil.sendEmail(session, emailID, "SimpleEmail Testing Subject", "SimpleEmail Testing Body");
      }
      else if(func.equalsIgnoreCase("addSuffix") || func.equalsIgnoreCase("removeSuffix"))
      {
        File dir = new File(args[1]);
        File[] files = dir.listFiles();
        for (File file : files)
        {
        	if(file.isFile())
        	{
        		String parentPath = file.getParent();
        		String fileName = file.getName();

        		if(func.equalsIgnoreCase("addSuffix"))
        		{
        			fileName = fileName + args[2];
        		}
        		else
        		{
        			fileName = fileName.split(".")[0];
        		}
        		
        		file.renameTo(new File(parentPath + File.pathSeparator + fileName));
        	}
        }
      }
      else if(func.equalsIgnoreCase("compare"))
      {
      	String path = "C:\\\\Connecture\\misc\\notes\\";
      	
      	Set <String> dataSet = new TreeSet <String> ();
      	
      	File fileA = new File(path + args[1]);
        BufferedReader readerA = new BufferedReader(new FileReader(fileA));
        
        String lineA = readerA.readLine();
        while(lineA != null && lineA.length() > 0)
        {
        	String[] elems = lineA.split(";");
        	
        	if(elems != null && elems.length > 0)
        	{
        		for(String elem : elems)
        		{
        			if(elem != null && elem.trim().length() > 0)
        			{
        				dataSet.add(elem.trim().toLowerCase());
        			}
        		}
        	}       	
        	lineA = readerA.readLine();
        }

        
        int counter = 0;
        
        File fileB = new File(path + args[2]);
        BufferedReader readerB = new BufferedReader(new FileReader(fileB));
        
        String lineB = readerB.readLine();
        while(lineB != null && lineB.length() > 0)
        {
        	String[] elems = lineB.split(";");
        	
        	if(elems != null && elems.length >0)
        	{
        		System.out.println("#########Data Unique in B file##########");
        		for(String elem : elems)
        		{
        			if(elem != null && (elem = elem.trim().toLowerCase()).length() > 0)
        			{
        				counter++;
        				if(counter == 99)
        				{
        					System.out.println("######## the 99th: " + elem + " in B file########");
        				}
        				
        				if(dataSet.contains(elem))
        				{
        					dataSet.remove(elem);
        				}
        				else
        				{
        					System.out.println(elem);
        				}
        			}
        		}
        	}       	
        	lineB = readerB.readLine();
        }
        
        readerB.close();
        
        System.out.println("#########Data Unique in A file##########");
        for(String s : dataSet)
        {
        	System.out.println(s);
        }
      }
      else
      {
        if(!func.equalsIgnoreCase("scramble") && !func.equalsIgnoreCase("descramble"))
        {
          System.out.println("Undefined function...");
          return;
        }
        
        System.out.println("Code?");
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String code = reader.readLine();
        
        if(code == null || code.trim().length() == 0)
        {
          return;
        }
        
        
        try
        {
        	String cipher = null;
        	
        	File propsFile = new File(VersWizard.class.getResource("vers.properties").getFile());

          if(propsFile.exists())
          {
          	Properties props = new Properties();
          	props.load(new FileInputStream(propsFile));
          	String cipherProp = props.getProperty("run.cipher");
          	
          	if(cipherProp != null && cipherProp.trim().length() > 0)
          	{
          		cipher = cipherProp;
          	}
          }
          
          if(cipher == null)
        	{
          	System.out.println("Cypher? (s-dl-uc-u-30690885)");
        		URL remoteURL = new URL(reader.readLine());
        		
        		BufferedReader remoteReader = new BufferedReader(new InputStreamReader(remoteURL.openStream()));
        		String ciperRemote = remoteReader.readLine();
        		
        		String remoteLine = remoteReader.readLine();
        		while(remoteLine != null && remoteLine.length() > 0)
        		{
        			System.out.println(remoteLine);
        			remoteLine = remoteReader.readLine();
        		}
        		
        		if(ciperRemote != null && ciperRemote.trim().length() > 0)
          	{
          		cipher = ciperRemote;
          	}
        	}
          
          if (cipher == null || !AuthenticatorHelper.encrypt(code).equalsIgnoreCase(cipher))
          {
            System.out.println("Incorrect security code.");
            return;
          }
        }
        catch(Exception e)
        {
          e.printStackTrace();
          return;
        }
        
        System.out.println("file/dir?");
        String fileName = reader.readLine();
        reader.close();
        
        if(fileName == null || fileName.trim().length() == 0)
        {
        	fileName = args[1];
        }
        
        if(func.equalsIgnoreCase("scramble"))
        {
            VersHelper.scramble(fileName, args[2], code);
        }
        else
        {
            VersHelper.descramble(fileName, args[2], code);
        }
        
        System.out.println(func + "d " + count + " file(s)");
      }
    }
    else
    {
      for(int i = 0; i < args.length; i++)
      {
        System.out.println(args[i] + "\t");
      }
      System.out.println("Usage: 'Function' 'Parameter'");
    }
  }
}
