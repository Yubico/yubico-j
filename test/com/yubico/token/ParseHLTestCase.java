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

package com.yubico.token;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.yubico.base.Base16;

public class ParseHLTestCase {

	@Test
	public void testNormalToken() throws InvalidYubiTokenException {
		String buf = "cbdefghijklnbvhgbhebfuurheknkvulgtdejrljhifn";
		byte[] key = Base16.decode("0123456789abcdef0123456789abcdef");

		YubiToken t = YubiTokenFactory.parse(buf, key);

		assertThat(t.getPublicId(), is("cbdefghijkln"));
		assertThat(t.getPrivateId(), is("ab1234512345"));
		assertThat(t.getSessionCounter(), is(244));
		assertThat(t.getCounter(), is(41345));
		assertThat(t.getTimestamp(), is(12123456));
		assertThat(t.getRandomNumber(), is(32999));
	}

	@Test
	public void testShortPublicId() throws InvalidYubiTokenException {
		String buf = "cbdbvhgbhebfuurheknkvulgtdejrljhifn";
		byte[] key = Base16.decode("0123456789abcdef0123456789abcdef");

		YubiToken t = YubiTokenFactory.parse(buf, key);

		assertThat(t.getPublicId(), is("cbd"));
		assertThat(t.getPrivateId(), is("ab1234512345"));
		assertThat(t.getSessionCounter(), is(244));
		assertThat(t.getCounter(), is(41345));
		assertThat(t.getTimestamp(), is(12123456));
		assertThat(t.getRandomNumber(), is(32999));
	}
}
