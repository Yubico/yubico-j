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
package com.yubico.base;

/**
 * <p>
 * Utility methods to {@link #encode(byte[]) encode} a byte array to hex
 * {@code String} and to {@link #decode(String) decode} a hex {@link String} to
 * a the byte array it represents.
 * </p>
 */
public class Base16 {
	private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
	private static final byte[] INV_HEX_CHARS = new byte[128];

	static {
		for (int i = 0; i < 10; i++) {
			INV_HEX_CHARS['0' + i] = (byte) i;
		}
		for (int i = 0; i < 6; i++) {
			INV_HEX_CHARS['A' + i] = (byte) (10 + i);
			INV_HEX_CHARS['a' + i] = (byte) (10 + i);
		}
	}

	public static String encode(byte[] src) {
		char[] dest = new char[src.length * 2];

		for (int si = 0, di = 0; si < src.length; si++) {
			byte b = src[si];
			dest[di++] = HEX_CHARS[(b & 0xf0) >>> 4];
			dest[di++] = HEX_CHARS[(b & 0x0f)];
		}

		return new String(dest);
	}

	public static byte[] decode(String string) {
		byte[] dest = new byte[string.length() / 2];

		for (int si = 0, di = 0; di < dest.length; di++) {
			byte high = INV_HEX_CHARS[string.charAt(si++) & 0x7f];
			byte low = INV_HEX_CHARS[string.charAt(si++) & 0x7f];
			dest[di] = (byte) ((high << 4) + low);
		}

		return dest;
	}
}