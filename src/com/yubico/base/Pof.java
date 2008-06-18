
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
   * <p>
   *   Note that this
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
