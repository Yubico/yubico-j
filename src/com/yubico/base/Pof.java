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

package com.yubico.base;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>
 *   Utility methods of use when decryption and parsing of YubiKey 
 *   One Time Passwords (OTPs).
 * </p>
 * @author Simon
 */
public class Pof
{
  private Pof(){} // Utility pattern dictates private constructor.
  
  //
  // Original comment: 
  // Decrypt TOKEN using KEY and store output in OUT structure.  Note
  // that there is no error checking whether the output data is valid or
  // not, use pof_check_* for that. 
  //
  
  /**
   * <p>
   *   Decrypt and parse YubiKey OTP.
   * </p>
   * @param block {@link Modhex} encoded representation of the YubiKey OTP.
   * @param key   128 bit AES key used to encrypt {@code block}, now will be
   *              used to ecrypt.
   * @throws GeneralSecurityException If decryption fails.               
   */
  public static Token parse(String block, byte[] key)
	  throws GeneralSecurityException
  {
 	  byte[] decoded = Modhex.decode(block);
 	  byte[] b = decrypt(key, decoded);
	  return new Token(b);
  }

  /**
   * <p>
   *   Does AES encryption of a YubiKey OTP byte sequence. 
   * </p>
   * @param key     AES key.
   * @param decoded YubiKey OTP byte sequence, 
   *                {@link Modhex#decode(String)} is the typical producer of 
   *                this.
   * @return Decrypted. 
   * @throws GeneralSecurityException If decryption fails.
   */
  public static byte[] decrypt(byte[] key, byte[] decoded)
    throws GeneralSecurityException
	  /*throws NoSuchPaddingException, IllegalBlockSizeException,
	         NoSuchAlgorithmException, InvalidKeyException,
	         BadPaddingException*/
  {
    SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
    Cipher cipher = Cipher.getInstance("AES/ECB/Nopadding");
    cipher.init(Cipher.DECRYPT_MODE, skeySpec);

    return cipher.doFinal(decoded);
  }
  
}
