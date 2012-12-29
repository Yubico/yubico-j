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

package com.yubico.base;

public class CRC13239
{
	public static final short CRC_OK_RESIDUAL = (short) 0xf0b8;
	
	// only static functions, so private constructor
	private CRC13239() { }

	static public short getCRC(byte[] buf)
	{
		short i;
		short crc = 0x7fff;
		boolean isNeg = true;

		for(byte b : buf) {
			crc ^= b & 0xff;

			for (i = 0; i < 8; i++) {
				if ((crc & 1) == 0) {
					crc >>= 1;
					if (isNeg) {
						isNeg = false;
						crc |= 0x4000;
					}
				} else {
					crc >>= 1;
					if (isNeg) {
						crc ^= 0x4408;
					} else {
						crc ^= 0x0408;
						isNeg = true;
					}
				}
			}
		}

		return isNeg ? (short) (crc | (short) 0x8000) : crc;
	}
}
