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

/**
 * <p>
 * Utility methods for creating byte arrays suitable for sending directly to the
 * api of a YubiKey.
 * <p>
 * 
 */
public class Configurator {
	// Size of secret ID field
	static public final byte UID_SIZE = 6;
	// Max size of fixed field
	static public final byte FIXED_SIZE = 16;
	// Size of AES key
	static public final byte KEY_SIZE = 16;
	// Size of OATH-HOTP key (key field + first 4 of UID field)
	static public final byte KEY_SIZE_OATH = 20;
	// Size of access code to re-program device
	static public final byte ACC_CODE_SIZE = 6;

	static public final byte CFG_FIXED_OFFS = 0;
	static public final byte CFG_UID_OFFS = FIXED_SIZE;
	static public final byte CFG_KEY_OFFS = CFG_UID_OFFS + UID_SIZE;
	static public final byte CFG_ACC_CODE_OFFS = CFG_KEY_OFFS + KEY_SIZE;
	static public final byte CFG_FIXED_SIZE_OFFS = CFG_ACC_CODE_OFFS
			+ ACC_CODE_SIZE;
	static public final byte CFG_EXT_FLAGS_OFFS = (short) (CFG_FIXED_SIZE_OFFS + 1);
	static public final byte CFG_TKT_FLAGS_OFFS = (short) (CFG_EXT_FLAGS_OFFS + 1);
	static public final byte CFG_CFG_FLAGS_OFFS = (short) (CFG_TKT_FLAGS_OFFS + 1);
	static public final byte CFG_CRC_OFFS = (short) (CFG_CFG_FLAGS_OFFS + 3);
	static public final byte CFG_SIZE = (short) (CFG_CRC_OFFS + 2);

	// Ticket flags

	// Send TAB before first part
	static public final byte TKTFLAG_TAB_FIRST = 0x01;
	// Send TAB after first part
	static public final byte TKTFLAG_APPEND_TAB1 = 0x02;
	// Send TAB after second part
	static public final byte TKTFLAG_APPEND_TAB2 = 0x04;
	// Add 0.5s delay after first part
	static public final byte TKTFLAG_APPEND_DELAY1 = 0x08;
	// Add 0.5s delay after second part
	static public final byte TKTFLAG_APPEND_DELAY2 = 0x10;
	// Append CR as final character
	static public final byte TKTFLAG_APPEND_CR = 0x20;
	// Block update of config 2 unless config 2 is configured and has this bit
	// set
	static public final byte TKTFLAG_PROTECT_CFG2 = (byte) 0x80;

	// Send reference string (0..F) before data
	static public final byte CFGFLAG_SEND_REF = 0x01;
	// Add 10ms intra-key pacing
	static public final byte CFGFLAG_PACING_10MS = 0x04;
	// Add 20ms intra-key pacing
	static public final byte CFGFLAG_PACING_20MS = 0x08;
	// Static ticket generation
	static public final byte CFGFLAG_STATIC_TICKET = 0x20;

	// Send ticket first (default is fixed part)
	static public final byte CFGFLAG_TICKET_FIRST = 0x02;
	// Allow trigger through HID/keyboard
	static public final byte CFGFLAG_ALLOW_HIDTRIG = 0x10;

	// Send truncated ticket (half length)
	static public final byte CFGFLAG_SHORT_TICKET = 0x02;
	// Strong password policy flag #1 (mixed case)
	static public final byte CFGFLAG_STRONG_PW1 = 0x10;
	// Strong password policy flag #2 (subtitute 0..7 to digits)
	static public final byte CFGFLAG_STRONG_PW2 = 0x40;
	// Allow manual (local) update of static OTP
	static public final byte CFGFLAG_MAN_UPDATE = (byte) 0x80;

	// OATH HOTP mode
	static public final byte TKTFLAG_OATH_HOTP = 0x40;
	// Generate 8 digits HOTP rather than 6 digits
	static public final byte CFGFLAG_OATH_HOTP8 = 0x02;
	// First byte in fixed part sent as modhex
	static public final byte CFGFLAG_OATH_FIXED_MODHEX1 = 0x10;
	// First two bytes in fixed part sent as modhex
	static public final byte CFGFLAG_OATH_FIXED_MODHEX2 = 0x40;
	// Fixed part sent as modhex
	static public final byte CFGFLAG_OATH_FIXED_MODHEX = 0x50;
	// Mask to get out fixed flags
	static public final byte CFGFLAG_OATH_FIXED_MASK = 0x50;

	// Challenge-response enabled (both must be set)
	static public final byte TKTFLAG_CHAL_RESP = 0x40;
	// Mask to get out challenge type
	static public final byte CFGFLAG_CHAL_MASK = 0x22;
	// Flag to indicate if configuration is challenge-response
	static public final byte CFGFLAG_IS_CHAL_RESP = 0x20;
	// Challenge-response enabled - Yubico OTP mode
	static public final byte CFGFLAG_CHAL_YUBICO = 0x20;
	// Challenge-response enabled - HMAC-SHA1
	static public final byte CFGFLAG_CHAL_HMAC = 0x22;
	// Set when HMAC message is less than 64 bytes
	static public final byte CFGFLAG_HMAC_LT64 = 0x04;
	// Challenge-response operation requires button press
	static public final byte CFGFLAG_CHAL_BTN_TRIG = 0x08;

	// Serial number visible at startup (button press)
	static public final byte EXTFLAG_SERIAL_BTN_VISIBLE = 0x01;
	// Serial number visible in USB iSerial field
	static public final byte EXTFLAG_SERIAL_USB_VISIBLE = 0x02;
	// Serial number visible via API call
	static public final byte EXTFLAG_SERIAL_API_VISIBLE = 0x04;

	// Use numeric keypad for digits
	static public final byte EXTFLAG_USE_NUMERIC_KEYPAD = 0x08;
	// Use fast trig if only cfg1 set
	static public final byte EXTFLAG_FAST_TRIG = 0x10;
	// Allow update of existing configuration (selected flags + access code)
	static public final byte EXTFLAG_ALLOW_UPDATE = 0x20;
	// Dormant configuration (can be woken up and flag removed = requires update
	// flag)
	static public final byte EXTFLAG_DORMANT = 0x40;

	static public final byte TKTFLAG_UPDATE_MASK = (byte) (TKTFLAG_TAB_FIRST
			| TKTFLAG_APPEND_TAB1 | TKTFLAG_APPEND_TAB2 | TKTFLAG_APPEND_DELAY1
			| TKTFLAG_APPEND_DELAY2 | TKTFLAG_APPEND_CR);
	static public final byte CFGFLAG_UPDATE_MASK = (byte) (CFGFLAG_PACING_10MS | CFGFLAG_PACING_20MS);
	static public final byte EXTFLAG_UPDATE_MASK = (byte) (EXTFLAG_SERIAL_BTN_VISIBLE
			| EXTFLAG_SERIAL_USB_VISIBLE
			| EXTFLAG_SERIAL_API_VISIBLE
			| EXTFLAG_USE_NUMERIC_KEYPAD
			| EXTFLAG_FAST_TRIG
			| EXTFLAG_ALLOW_UPDATE | EXTFLAG_DORMANT);

	static public final int AES_MODE = 0;
	static public final int HMAC_SHA1_MODE = 1;

	private byte[] fixed;
	private byte[] uid;
	private byte[] key;
	private byte[] accCode;
	private byte[] curAccCode;
	private byte cfgFlags;
	private byte extFlags;
	private byte tktFlags;

	public Configurator() {
		fixed = new byte[16];
		uid = new byte[6];
		key = new byte[16];
		accCode = new byte[6];
		curAccCode = new byte[6];
	}

	/**
	 * @return the fixed
	 */
	public byte[] getFixed() {
		return fixed;
	}

	/**
	 * @param fixed
	 *            the fixed to set
	 */
	public void setFixed(byte[] fixed) {
		int length = fixed.length > 16 ? 16 : fixed.length;
		this.fixed = new byte[length];
		System.arraycopy(fixed, 0, this.fixed, 0, length);
	}

	/**
	 * @return the uid
	 */
	public byte[] getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(byte[] uid) {
		System.arraycopy(uid, 0, this.uid, 0, UID_SIZE);
	}

	/**
	 * @return the key
	 */
	public byte[] getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(int mode, byte[] key) {
		System.arraycopy(key, 0, this.key, 0, KEY_SIZE);
		if (mode == HMAC_SHA1_MODE) {
			// in the hmac-sha1 modes we store the last 4 bytes of the key in
			// the uid
			System.arraycopy(key, 16, uid, 0, 4);
		}
	}

	/**
	 * @return the accCode
	 */
	public byte[] getAccCode() {
		return accCode;
	}

	/**
	 * @param accCode
	 *            the accCode to set
	 */
	public void setAccCode(byte[] accCode) {
		System.arraycopy(accCode, 0, this.accCode, 0, ACC_CODE_SIZE);
	}

	/**
	 * @return the curAccCode
	 */
	public byte[] getCurAccCode() {
		return curAccCode;
	}

	/**
	 * @param curAccCode
	 *            the curAccCode to set
	 */
	public void setCurAccCode(byte[] curAccCode) {
		System.arraycopy(curAccCode, 0, this.curAccCode, 0, ACC_CODE_SIZE);
	}

	/**
	 * @return the cfgFlags
	 */
	public byte getCfgFlags() {
		return cfgFlags;
	}

	/**
	 * @param cfgFlags
	 *            the cfgFlags to set
	 */
	public void setCfgFlags(byte cfgFlags) {
		this.cfgFlags = cfgFlags;
	}

	/**
	 * @return the extFlags
	 */
	public byte getExtFlags() {
		return extFlags;
	}

	/**
	 * @param extFlags
	 *            the extFlags to set
	 */
	public void setExtFlags(byte extFlags) {
		this.extFlags = extFlags;
	}

	/**
	 * @return the tktFlags
	 */
	public byte getTktFlags() {
		return tktFlags;
	}

	/**
	 * @param tktFlags
	 *            the tktFlags to set
	 */
	public void setTktFlags(byte tktFlags) {
		this.tktFlags = tktFlags;
	}

	public byte[] getConfigStructure() {
		byte[] cfg = new byte[CFG_SIZE + ACC_CODE_SIZE];

		if (fixed != null)
			System.arraycopy(fixed, 0, cfg, CFG_FIXED_OFFS, fixed.length);
		if (uid != null)
			System.arraycopy(uid, 0, cfg, CFG_UID_OFFS, uid.length);
		if (key != null)
			System.arraycopy(key, 0, cfg, CFG_KEY_OFFS, key.length);
		if (accCode != null)
			System.arraycopy(accCode, 0, cfg, CFG_ACC_CODE_OFFS, accCode.length);
		if (curAccCode != null)
			System.arraycopy(curAccCode, 0, cfg, CFG_SIZE, curAccCode.length);
		if (fixed != null)
			cfg[CFG_FIXED_SIZE_OFFS] = (byte) fixed.length;
		cfg[CFG_EXT_FLAGS_OFFS] = extFlags;
		cfg[CFG_TKT_FLAGS_OFFS] = tktFlags;
		cfg[CFG_CFG_FLAGS_OFFS] = cfgFlags;

		short crc = (short) ~CRC13239.getCRC(cfg, CFG_SIZE - 2);
		cfg[CFG_CRC_OFFS] = (byte) crc;
		cfg[CFG_CRC_OFFS + 1] = (byte) (crc >> 8);

		return cfg;
	}
}
