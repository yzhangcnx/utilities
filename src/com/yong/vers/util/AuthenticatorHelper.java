package com.yong.vers.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;


/**
 * Class that contains helper methods used by the authenticators
 * 
 * @created Apr/May, 2006
 */
public class AuthenticatorHelper
{
  public static final String ENCRYPTION_TYPE = "Blowfish";
  private static final byte[] paramSpec = new byte[8];
  private static final String key = "WLPIQ07ERen02214";
  private static final String algo = "Blowfish/CBC/PKCS5Padding";
  private static final String provider = "BC";

  /**
   * Encrypts a string using a key.
   * @param cleartext The clear text string to encrypt.
   * @param key The key to use for the encryption.
   * @return a string representing the resulting ciphertext.
   */
  public static String encrypt(String cleartext) throws Exception 
  {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    byte[] encrypted = (crypt(cleartext.getBytes(), key, Cipher.ENCRYPT_MODE)).toByteArray();
    Base64.encode(encrypted, output);
    return output.toString();

  }
  
  public static void main(String[] args)
  {
	  try
	  {
		  //encrypt some value and print it
		  String value = encrypt("A000030004");
		  System.out.println(value);
	  }
	  catch (Exception ex)
	  {
		  System.out.println(ex.toString());
	  }
    
    
  }

  /**
   * Decrypts a string using a key.
   * @param ciphertext The encrypted string.
   * @param key The key to use for the decryption.
   * @return a string representing the resulting cleartext.
   */
  public static String decrypt(String ciphertext) throws Exception 
  {
    byte[] bytedecryptedData = Base64.decode(ciphertext);
    return crypt(bytedecryptedData, key, Cipher.DECRYPT_MODE).toString();
  }
 
  /*
   * This actual does the encryption/decryption.
   */
  private static ByteArrayOutputStream crypt(byte[] input, String key, int mode) 
  throws Exception 
  {
      if (Security.getProvider(provider) == null)
      {
        Security.addProvider(new BouncyCastleProvider());
      }
      KeyGenerator kgen = KeyGenerator.getInstance(ENCRYPTION_TYPE);
      kgen.init(128);
      byte[] raw = key.getBytes();
      SecretKeySpec skeySpec = new SecretKeySpec(raw, ENCRYPTION_TYPE);
      IvParameterSpec ivSpec = new IvParameterSpec(paramSpec);
          
      Cipher cipher = Cipher.getInstance(algo, provider);
      cipher.init(mode, skeySpec, ivSpec);

      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ByteArrayInputStream bis = new ByteArrayInputStream(input);
      CipherOutputStream cos = new CipherOutputStream(bos, cipher);

      int length = 0;
      byte[] buffer =  new byte[8192];

      while ((length = bis.read(buffer)) != -1) {
         cos.write(buffer, 0, length);
      }

      bis.close();
      cos.close();

      return bos;
  }
  
  private static String bytesToHex(byte[] b) 
  {    
    int size = b.length;
    StringBuffer h = new StringBuffer(size);
    for (int i = 0; i < size; i++) 
    {
        int u = b[i]&255; // unsigned conversion
        if (u<16) 
        {
            h.append("0"+Integer.toHexString(u));
        } 
        else 
        {
            h.append(Integer.toHexString(u));
        }
    }
    return h.toString();
  }
  
  private static byte[] hexToBytes(String str) 
  {
    if (str == null)
        return new byte[0] ;

    int len = str.length();    // probably should check length
    char hex[] = str.toCharArray();
    byte[] buf = new byte[len/2];

    for (int pos = 0; pos < len / 2; pos++)
        buf[pos] = (byte)( ((toDataNibble(hex[2*pos]) << 4) & 0xF0)
                        | ( toDataNibble(hex[2*pos + 1])   & 0x0F) );
    return buf;
  } 

  private static byte toDataNibble(char c) 
  {
    if (('0' <= c) && (c <= '9' ))
        return (byte)((byte)c - (byte)'0');
    else if (('a' <= c) && (c <= 'f' ))
        return (byte)((byte)c - (byte)'a' + 10);
    else if (('A' <= c) && (c <= 'F' ))
        return (byte)((byte)c - (byte)'A' + 10);
    else
        return -1;
  }
}
