/* Copyright (c) 2008-2012, Yubico AB.  All rights reserved.

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

package com.yubico.base.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.security.GeneralSecurityException;

import org.junit.Test;

import com.yubico.base.Pof;
import com.yubico.base.Token;

public class PofTest {
	@Test
	public void test1() throws Exception {
		String buf = "0123456789abcdef";
		String key = "abcdef0123456789";
		byte[] facit = new byte[] { (byte) 0x83, (byte) 0x8a, 0x46, 0x7f, 0x34,
				0x63, (byte) 0x95, 0x51, (byte) 0x75, 0x5b, (byte) 0xd3, 0x2a,
				0x4a, 0x2f, 0x15, (byte) 0xe1 };

		byte[] b = Pof.decrypt(key.getBytes(), buf.getBytes());
		for (int i = 0; i < b.length; i++) {
			assertEquals(b[i], facit[i]);
		}
	}

	@Test
	public void testWithToken() throws GeneralSecurityException {
		String buf = "nclefeinnuedtuueebcvnlkhhnbtehit";
		byte key[] = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
				0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };
		byte uid[] = { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06 };

		Token token = Pof.parse(buf, key);
		assertArrayEquals(uid, token.getUid());
	}
}
