
package com.yubico.base.tools;

import java.security.GeneralSecurityException;

import com.yubico.base.Modhex;
import com.yubico.base.Pof;
import com.yubico.base.Token;

/**
 * <p>
 *   Command line tool to decrypt and parse One Time Passwords (OTPs) output 
 *   from YubiKey devices.
 * </p>
 * @author Simon
 */
public class Display
{
  private Display(){} // Pure main method class, never instantiated.
  
  /**
   * <p>
   *   Decrypt encrypted part of a YubiKey OTP and prints the parsed result to
   *   {@link System#out standard out}.
   * </p>
   * <p>
   *   First argument is the 128 bit (16 bytes) 
       {@link Modhex} encoded (32 characters) encoded AES 
   *   key to use for encryption. 
   *   
   *   Second argument is the 
   *   {@code Modhex} encoded OTP (i.e. as produced by the YubiKey).
   * </p>
   * <p>
   *   Example (using test data provided by Yubico):
   * </p>
   * <pre>
   *   java -cp yubico-base-1.0.jar com.yubico.base.tools.Display urtubjtnuihvntcreeeecvbregfjibtn hknhfjbrjnlnldnhcujvddbikngjrtgh
   * </pre>
   * <p>
   *   Terminates with exit code {@code 0} if OK and with {@code -1} if fails.
   * </p>
   * @param argv Command line arguments, see above for accepted arguments.
   */
  public static void main(String[] argv)
  {
	  if (argv.length != 2) {
	    System.err.println("Need <aeskey> <token>");
	    System.exit(-1);
	  }
	
	  String key = argv[0];
	  String token = argv[1];

	  if (key.length() != 32) {
	    System.err.println("ModHex encoded AES-key must be 32 characters");
	    System.exit(-1);
	  }
	
	  int len = token.length();
	  if (len > 32) {
	    token = token.substring(len - 32);
	    System.out.println("token too long, truncating to "+token);
	  }
	  byte[] keyBytes = Modhex.decode(key);
	  try {
	    Token t = Pof.parse(token, keyBytes);
	    System.out.println(t);
	    System.exit(0);
	  } catch (GeneralSecurityException e) {
	    System.err.println(e);
	    System.exit(-1);
	  }
	  
  }
  
}
