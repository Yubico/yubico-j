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

/**
 * <p>
 * High level interface representing a YubiKey Token.
 * </p>
 */
public interface YubiToken {

	/**
	 * The Public ID of the token
	 * 
	 * This string may be empty if only the last 32 bytes of the OTP was
	 * provided.
	 */
	public String getPublicId();

	/**
	 * The Private ID of the token, represented as a twelve character hex
	 * string, in lower case.
	 */
	public String getPrivateId();

	/**
	 * The counter. This counter increases by one for every time the user
	 * inserts the token.
	 * 
	 * The counter starts at 1 and goes up to 65535. At that point, the token
	 * will need to be reconfigured.
	 */
	public int getCounter();

	/**
	 * The session counter will increase by one for every time the button is
	 * pressed. Note that this value will be reset to 1 every time the token is
	 * inserted into the USB port. It will wrap after 255 back to 1.
	 */
	public int getSessionCounter();

	/**
	 * A timestamp which starts counting when the token is inserted into the USB
	 * port. It is incremented by an 8 Hz internal clock and counts from 1 to
	 * 16,777,216 which gives it a runtime of 24.27 days. When it reaches its
	 * limit, the session is terminated and no more OTPs can be generated.
	 * 
	 */
	public int getTimestamp();

	/**
	 * A random number used to add entropy to the unsigned token plaintext.
	 */
	public int getRandomNumber();

	/**
	 * Returns a monotonic counter that will always increase - even between
	 * session. A token verifier will use this value to verify that it is always
	 * increasing, by comparing it to the previous value.
	 * 
	 */
	public long getMonotonicCounter();

	/**
	 * A cyclic redundancy check of CRC13239 format used to ensure the integrity
	 * of the token.
	 */
	public int getCrc();

}