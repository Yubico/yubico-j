/* Copyright (c) 2012, Yubico AB.  All rights reserved.

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

import org.junit.Test;

import com.yubico.base.CRC13239;

public class CRC13239Test {
	
	@Test
	public void testCrc() {

		byte[] data = {0x0, 0x1, 0x2, 0x3, 0x4};
		short crc = CRC13239.getCRC(data);
		assertEquals((short)62919, crc);
	}

	@Test
	public void testCrc2() {
		byte[] data = {(byte) 0xfe};
		/*
		 * >>> test_common.crc16('fe'.decode('hex'))
		 * 4470
		 * >>>
		 */
		short crc = CRC13239.getCRC(data);
		assertEquals((short)4470, crc);
	}
	
	@Test
	public void testCrc3() {
		byte[] data = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, /* uid */
				0x30, 0x75, /* use_ctr */
				0x00, 0x09, /* ts_low */
				0x3d, /* ts_high */
				(byte) 0xfa, /* session_ctr */
				0x60, (byte) 0xea /* rnd */
		};
		short crc = CRC13239.getCRC(data);

		assertEquals((short)35339, crc);
	}
	
	@Test
	public void testCrc4() {
		byte[] data = {0x55, (byte) 0xaa, 0x00, (byte) 0xff};
		short crc = CRC13239.getCRC(data);
		assertEquals((short)52149, crc);
	}
}
