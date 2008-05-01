package com.yubico.base;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

public class Pof
{
    /** 
     * Decrypt TOKEN using KEY and store output in OUT structure.  Note
     * that there is no error checking whether the output data is valid or
     * not, use pof_check_* for that. 
     */
    public static Token parse(String block, byte[] key)
	throws GeneralSecurityException
    {
 	byte[] decoded = Modhex.decode(block);
 	byte[] b = decrypt(key, decoded);
	return new Token(b);
    }

    public static byte[] decrypt(byte[] key, byte[] decoded)
	throws NoSuchPaddingException, IllegalBlockSizeException,
	       NoSuchAlgorithmException, InvalidKeyException,
	       BadPaddingException
    {
       SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
       Cipher cipher = Cipher.getInstance("AES/ECB/Nopadding");
       cipher.init(Cipher.DECRYPT_MODE, skeySpec);

       return cipher.doFinal(decoded);
    }
}
