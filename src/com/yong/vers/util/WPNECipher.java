package com.yong.vers.util;
import com.rwsol.framework.security.Crypto;
//framework-3.jar
public class WPNECipher
{/*
  public String encrypt(String text)
  {
    return Crypto.getInstance("md5").encrypt(text);
  }

  public static void main(String[] args)
  {
    String password = Crypto.getInstance("md5").encrypt("wpnedev");
    System.out.println("encrypted pword=" + password);
    return;
  }
  */
  public static String encrypt(String text)
  {
    return Crypto.getInstance("md5").encrypt(text);
  }
  
  public static String decrypt(String text)
  {
    return Crypto.getInstance("md5").decrypt(text);
  }
}
