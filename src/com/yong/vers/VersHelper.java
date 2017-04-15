package com.yong.vers;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class VersHelper
{
	//check to see if the time of an event is within 15 minutes to now
  public static boolean isTimeValid(String userTime, String userTimeZone)
  {
    boolean isValid = false;
    try
    {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm");
      
      Calendar cst = new GregorianCalendar(TimeZone.getTimeZone(userTimeZone));
      Date postDate = formatter.parse(userTime);
      cst.setTime(postDate);
      long postMS = cst.getTimeInMillis();
      
      Calendar cst1 = new GregorianCalendar();
      long nowMS = cst1.getTimeInMillis();
      
      long diff = Math.abs(nowMS - postMS);
      if (diff <= (15*60*1000))
      {
        isValid = true;
      }
   }   
   catch(ParseException pe)
   { 
     pe.printStackTrace();
   }
   return isValid;
  }
  
  public static void scramble(String dir, String extPattern, String sizeStr)
  {
    File dirFile = new File(dir);
    
    if(!dirFile.exists())
    {
    	System.out.println(dir + " is invalid.");
    	return;
    }
    else
    {
    	System.out.println(dirFile.getAbsolutePath());
    }
    
    int size = Integer.parseInt(sizeStr);
    
    if(dirFile.isDirectory())
    {
      final String EXT_PATTERN = extPattern;
      
      File [] files = dirFile.listFiles(new FileFilter () 
      {
        public boolean accept(File arg)
        {
          if(arg.isFile() && arg.getName().indexOf(".") != -1)
          {
          	String [] nameParts  = arg.getName().split("\\.");
            String extenssion = nameParts[nameParts.length - 1];
            return extenssion.matches(EXT_PATTERN);
          }
          return false;
        }
      });
      
      File [] dirs = dirFile.listFiles(new FileFilter ()
      {
        public boolean accept(File arg)
        {
          if (arg.isDirectory())
          {
            return true;
          }
          return false;
        }
      });
      
      if((files == null || files.length == 0) && (dirs == null || dirs.length == 0))
      {
        return;
      }
      
      for(int i = 0; i < files.length && files[i].isFile(); i++)
      {
        scrambleFile(files[i], extPattern, size);
      }
      
      if (dirs != null)
      {
        for (int j = 0; j < dirs.length; j++)
        {
          scramble(dirs[j].getAbsolutePath(), extPattern, sizeStr);
        }
      }
    }
    else if(dirFile.isFile())
    {
    	scrambleFile(dirFile, extPattern, size);
    }
  }
  
  public static void scrambleFile(File file, String extPattern, int size)
  {   
      try
      {
      	String [] nameParts = file.getName().split("\\.");
      	
      	String fileExt = nameParts[nameParts.length - 1];
        String fileName = file.getName().split("\\." + fileExt)[0];
        
        if(!fileExt.matches(extPattern))
        {
          System.out.println("Can't scramble file with the extention of " + fileExt);
          return;
        }
        
        byte [] buffer = new byte[size];
        int bytes = 0;
        FileInputStream fi = new FileInputStream(file);
        FileOutputStream fo = new FileOutputStream(file.getParent() + File.separator + fileName + ".ed");
        
        byte [] extBytes = fileExt.getBytes();
        byte [] numByte = {(byte) extBytes.length};
        
        fo.write(numByte);
        fo.write(extBytes);
        
        do 
        {
          bytes = fi.read(buffer, 0, size);
          if(bytes > 0)
          {
            fo.write(buffer, 0, bytes);
            fo.write(1);
          }
        } while(bytes >= size);
        
        fo.close();
        fi.close();
        file.delete();
        VersWizard.count++;
        System.out.println(VersWizard.count);
      }
      catch(IOException ioe)
      {
        ioe.printStackTrace();
      }
  }
  
  public static void descramble(String dir, String extPattern, String sizeStr)
  {
    File dirFile = new File(dir);
    
    if(!dirFile.exists())
    {
    	System.out.println(dir + " is invalid.");
    	return;
    }
    else
    {
    	System.out.println(dirFile.getAbsolutePath());
    }

    int size = Integer.parseInt(sizeStr);
    
    if(dirFile.isDirectory())
    {
      File[] files = dirFile.listFiles(new FileFilter () {
        public boolean accept(File arg)
        {
          return arg.isFile() && (arg.getName().indexOf(".") < 0 || arg.getName().endsWith(".ed"));
        }
      });
      
      File [] dirs = dirFile.listFiles(new FileFilter ()
      {
        public boolean accept(File arg)
        {
          if (arg.isDirectory())
          {
            return true;
          }
          return false;
        }
      });
      
      if((files == null || files.length == 0) && (dirs == null || dirs.length == 0))
      {
        return;
      }
      
      if(files != null)
      {
        for(int i = 0; i < files.length && files[i].isFile(); i++)
        {
          discrambleFile(files[i], extPattern, size);
        }
      }
      
      if (dirs != null)
      {
        for (int j = 0; j < dirs.length; j++)
        {
          descramble(dirs[j].getAbsolutePath(), extPattern, sizeStr);
        }
      }
    }
    else if(dirFile.isFile())
    {
    	discrambleFile(dirFile, extPattern, size);
    }
  }
  
  public static void discrambleFile(File file, String extPattern, int size)
  {   
    try
    {
      String name = file.getName().split("\\.ed")[0];
      
      FileInputStream fi = new FileInputStream(file);
      
      byte [] numByte = new byte [1];
      fi.read(numByte);
      
      if(numByte[0] <= 0)
      {
      	return;
      }
      
      byte [] extBytes = new byte [numByte[0]];
      fi.read(extBytes);
      
      String extenssion = new String(extBytes);
      if(!extenssion.matches(extPattern))
      {
        System.out.println("Can't discramble file with the extention of " + extenssion);
        return;
      }
      
      FileOutputStream fo = new FileOutputStream(file.getParent() + File.separator + name + "." + extenssion);
      byte [] buffer = new byte[size];
      int bytes = 0;
      
      do 
      {
        bytes = fi.read(buffer, 0, size);
        if(bytes > 0)
        {
          fo.write(buffer, 0, bytes);
          fi.read();
        }
      } while(bytes >= size);
      
      fo.close();
      fi.close();
      file.delete();
      VersWizard.count++;
      System.out.println(VersWizard.count);
    }
    catch(IOException ioe)
    {
      ioe.printStackTrace();
    }
  }

  public static String getAmount(double biweekContr, double returnRate)
  {
    Calendar contrDate = Calendar.getInstance();
    contrDate.set(Calendar.YEAR, 2007);
    contrDate.set(Calendar.MONTH, Calendar.JANUARY);
    contrDate.set(Calendar.DAY_OF_YEAR, 1);
    
    Calendar stopDate = Calendar.getInstance();
    stopDate.set(Calendar.YEAR, 2028);
    stopDate.set(Calendar.MONTH, Calendar.DECEMBER);
    stopDate.set(Calendar.DAY_OF_YEAR, 31);
    
    returnRate = returnRate/26;
    double amount = 0.0;
    int times = 0;
    while(contrDate.before(stopDate))
    {
      amount += amount * returnRate;
      amount += biweekContr;
      contrDate.add(Calendar.DAY_OF_YEAR, 14);
      times++;
    }
    System.out.println(times);
    return NumberFormat.getCurrencyInstance().format(amount);
  }

  /**
   * @param file
   * @param how 
   * @throws IOException 
   */
  public static void rename(File file, String how, boolean processSelf) throws IOException
  {
    File absoluteSelf = file.getAbsoluteFile();
    File self = new File(file.getCanonicalPath());
    //File parentFile = self.getParentFile();
    File [] files = !processSelf ? new File[0] : self.listFiles(new FileFilter () 
    {
      public boolean accept(File arg)
      {
        if(arg.isFile())
        {
          String [] nameParts = arg.getName().split("\\.");
          return nameParts.length > 1 ? (!nameParts[1].equals("db") && !nameParts[1].equals("bat")) : true;
        }
        return false;
      }
    });
    
    //change processSelf flag for processing the subfolders
    if(!processSelf)
    {
      processSelf = true;
    }
    
    File [] dirs = self.listFiles(new FileFilter () 
    {
      public boolean accept(File arg)
      {
        if(arg.isDirectory())
        {
          return true;
        }
        return false;
      }
    });
    
    for(int i = 0; i < files.length; i++)
    {
      File oldFile = files[i];
      //skip the files with the name the same as the folder's they reside in
      if(oldFile.getName().startsWith(self.getName()) 
          && (oldFile.getName().split("\\.")[0]).length() == self.getName().length())
      {
        continue;
      }
      System.out.println(oldFile.getName());
      File newFile = null;
      if(how.equalsIgnoreCase("add"))
      {
        newFile = new File(self.getName() + files[i].getName());
      }
      else
      {
        newFile = new File(files[i].getName().split(self.getName())[1]);
      }
      System.out.println(newFile.getName());
      oldFile.renameTo(newFile);
    }
    if(dirs != null)
    {
      for(int j = 0; j < dirs.length; j++)
      {
        File dir = dirs[j];
        rename(dir, how, processSelf);
      }
    }
  }
}
