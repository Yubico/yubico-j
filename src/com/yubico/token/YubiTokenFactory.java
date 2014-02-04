/* Copyright (c) 2012, Yubico AB.  All rights reserved.
   Copyright (c) 2013, Victor Boivie <victor@boivie.com>

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
package com.yubico.token;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.yubico.base.Modhex;
import com.yubico.base.Token;

/**
 * <p>
 * Factory for parsing OTP values into YubiKey tokens
 * </p>
 */
public class YubiTokenFactory {

	/**
	 * Decrypt and parse YubiKey OTP. Returns a high-level token.
	 * 
	 * @param otp
	 *            {@link Modhex} encoded representation of the YubiKey OTP.
	 * @param key
	 *            128 bit AES key used to encrypt {@code otp}.
	 * @throws InvalidYubiTokenException
	 *             If the token is invalid
	 */
	public static YubiToken parse(String otp, byte[] key)
			throws InvalidYubiTokenException {
		String publicId = getPublicId(otp);
		String rest = otp.substring(otp.length() - 32);
		try {
			byte[] decoded = Modhex.decode(rest);

			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/Nopadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);

			byte[] b = cipher.doFinal(decoded);
			return new YubiTokenImpl(publicId, new Token(b));
		} catch (IllegalArgumentException e) {
			throw new InvalidYubiTokenException("Invalid token");
		} catch (GeneralSecurityException e) {
			throw new InvalidYubiTokenException("Unable to decrypt token");
		}
	}

	/**
	 * Returns the public ID part of a YubiKey OTP token.
	 * 
	 * @param otp
	 *            {@link Modhex} encoded representation of the YubiKey OTP.
	 * @throws InvalidYubiTokenException
	 *             If the token is invalid
	 */
	public static String getPublicId(String otp)
			throws InvalidYubiTokenException {
		if ((otp.length() < 32) || (otp.length() > 64)) {
			throw new InvalidYubiTokenException("Invalid token length");
		}
		return otp.substring(0, otp.length() - 32);
	}
}
