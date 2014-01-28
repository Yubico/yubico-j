/* Copyright (c) 2014, Yubico AB.  All rights reserved.

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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.yubico.base.Configurator;

public class ConfiguratorTest {
	Configurator cfg;
	
	@Before
	public void setup() {
		cfg = new Configurator();
	}
	@Test
	public void testStructure() {
		byte[] fixed = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06};
		byte[] uid = {0x11, 0x12, 0x13, 0x14, 0x15, 0x16};
		byte[] key = {0x20, 0x21, 0x22, 0x23, 0x24, 0x25,
				0x26, 0x27, 0x28, 0x29, 0x2a, 0x2b, 0x2c, 0x2d, 0x2e, 0x2f};
		byte tktFlags = Configurator.TKTFLAG_APPEND_CR;
		cfg.setFixed(fixed);
		cfg.setUid(uid);
		cfg.setKey(Configurator.AES_MODE, key);
		cfg.setTktFlags(tktFlags);
		byte[] config = cfg.getConfigStructure();
		
		assertEquals(58, config.length);
		assertEquals(fixed.length, config[Configurator.CFG_FIXED_SIZE_OFFS]);
		assertEquals(tktFlags, config[Configurator.CFG_TKT_FLAGS_OFFS]);
	}
	
	@SuppressWarnings("unused")
	private void dumpHex(byte[] bytes) {
		String out = "";
		for(byte b : bytes) {
			out += String.format("%02x", b);
		}
		System.out.println(out);
	}
}
