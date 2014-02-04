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

import com.yubico.base.Base16;
import com.yubico.base.Token;

/**
 * <p>
 * Implementation of the YubiToken interface
 * </p>
 */
class YubiTokenImpl implements YubiToken {
	private final Token token;
	private final String publicId;

	YubiTokenImpl(String publicId, Token token) {
		this.publicId = publicId;
		this.token = token;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yubico.base.YubiToken2#getPublicId()
	 */
	public String getPublicId() {
		return publicId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yubico.base.YubiToken2#getPrivateId()
	 */
	public String getPrivateId() {
		return Base16.encode(token.getUid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yubico.base.YubiToken2#getCounter()
	 */
	public int getCounter() {
		byte[] cntr = token.getSessionCounter();
		int val1 = cntr[0] & 0xFF;
		int val2 = cntr[1] & 0xFF;
		return val2 * 256 + val1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yubico.base.YubiToken2#getSessionCounter()
	 */
	public int getSessionCounter() {
		return (int) token.getTimesUsed() & 0xFF;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yubico.base.YubiToken2#getTimestamp()
	 */
	public int getTimestamp() {
		int ts1 = token.getTimestampHigh() & 0xFF;
		int ts2 = token.getTimestampLow()[1] & 0xFF;
		int ts3 = token.getTimestampLow()[0] & 0xFF;
		return ts1 * 65536 + ts2 * 256 + ts3;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yubico.base.YubiToken2#getRandomNumber()
	 */
	public int getRandomNumber() {
		int r1 = token.getRandom()[0] & 0xFF;
		int r2 = token.getRandom()[1] & 0xFF;
		return r2 * 256 + r1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yubico.base.YubiToken2#getMonotonicCounter()
	 */
	public long getMonotonicCounter() {
		// 16 bits
		long counter = getCounter();
		// 24 bits
		long timestamp = getTimestamp();
		// 8 bits
		long sessionCounter = getSessionCounter();
		// == 48 bits
		return (counter << 32) + (timestamp << 8) + sessionCounter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yubico.base.YubiToken2#getCrc()
	 */
	public int getCrc() {
		int r1 = token.getCrc()[0] & 0xFF;
		int r2 = token.getCrc()[1] & 0xFF;
		return r2 * 256 + r1;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[YubiToken publicId: " + publicId);
		sb.append(", privateId: " + getPrivateId());
		sb.append(", counter: " + getCounter());
		sb.append(", timestamp: " + getTimestamp());
		sb.append(", session cntr: " + getSessionCounter());
		sb.append(", random: " + getRandomNumber());
		sb.append(", crc: " + getCrc());
		sb.append("]");
		return sb.toString();
	}
}
