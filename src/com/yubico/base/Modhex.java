
package com.yubico.base;

import java.io.ByteArrayOutputStream;

/**
 * <p>
 *   Utility methods to 
 *   {@link #encode(byte[]) encode} a byte array to Modhex 
 *   {@code String} and to
 *   {@link #decode(String) decode} a Modhex
 *   {@link String} to a the byte array it represents.   
 * </p>
 * <p>
 *   Modehex encoding uses the 
 *   {@link #ALPHABET alphabet} {@code cbdefghijklnrtuv} which has the property
 *   of being at the same position on all keyboards.
 * </p>
 * @author Simon
 */
public class Modhex
{
  private Modhex(){} // Utility pattern dictates private constructor.
  
  /**
  * <p>
  *   The Modhex alphabet: the letters used to decode bytes in.
  * </p>
  */
  public final static String ALPHABET = "cbdefghijklnrtuv";
    
  private static char trans[] = ALPHABET.toCharArray();

  /**
   * <p>
   *   Encodes.
   * </p>
   * @param data Data to encode.
   * @return Modhex.
   */
  public static String encode(byte[] data)
  {
	  StringBuffer result = new StringBuffer();

    for (int i = 0; i < data.length; i++) {
	    result.append(trans[(data[i] >> 4) & 0xf]);
	    result.append(trans[data[i] & 0xf]);
	  }

    return result.toString();
  }

  /**
   * <p>
   *   Decodes.
   * </p>
   * @param s Modhex encoded
   *          {@link String}. Decoding ignores case of {@code s}.
   * @return Bytes {@code s} represents.
   * @throws IllegalArgumentException If {@code s} not valid Modhex.
   */
  public static byte[] decode(String s)
  {
	  ByteArrayOutputStream baos = new ByteArrayOutputStream();
    int len = s.length();
 	  
	  boolean toggle = false;
	  int keep = 0;

    for (int i = 0; i < len; i++) {
      char ch = s.charAt(i);
 	    int n = ALPHABET.indexOf(Character.toLowerCase(ch));
	    if (n == -1) {
		    throw new 
		      IllegalArgumentException(s+" is not properly encoded");
	    }

 	    toggle = !toggle;

 	    if (toggle) {
		    keep = n;
	    } else {
		    baos.write((keep << 4) | n);
 	    }
 	  }
	  return baos.toByteArray();
  }
  
}
