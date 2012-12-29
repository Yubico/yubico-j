/* Copyright (c) 2008, Yubico AB.  All rights reserved.

   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions
   are met:

   * Redistributions of source code must retain the above copyright
     notice, this list of conditions and the following disclaimer.

   * Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following
     disclaimer in the documentation and/or other materials provided
     with the distribution.

   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
   CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
   INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
   MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
   DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
   BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
   EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
   TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
   DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
   ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
   TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
   THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
   SUCH DAMAGE.
*/

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
   *   {@link Modhex} encoded (32 characters) AES key to use for encryption. 
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
