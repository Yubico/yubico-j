package com.yubico.base;

public class Token
{
    static int BLOCK_SIZE = 16;
    static int KEY_SIZE = 16;
    static int UID_SIZE = 6;
    static long CRC_OK_RESIDUE = 0xf0b8;

    /* Unique (secret) ID. */
    byte[] uid;  //UID_SIZE

    /* Session counter (incremented by 1 at each startup + real use).
       High bit indicates whether caps-lock triggered the token. */
    byte[] sessionCounter;  //2

    /* Timestamp incremented by approx 8Hz (low part). */
    byte[] timestampLow; //2

    /* Timestamp (high part). */
    byte timestampHigh;

    /* Number of times used within session + activation flags. */
    byte timesUsed;

    /* Pseudo-random value. */
    byte[] random; //2

    /* CRC16 value of all fields. */
    byte[] crc; //2

    private static int calculateCrc(byte[] b)
    {
	//System.out.println("in calc crc, b[] = "+toString(b));
	int crc = 0xffff;

	for (int i = 0; i < b.length; i += 1) {
	    crc ^= b[i] & 0xFF;
	    for (int j = 0; j < 8; j++){
		int n = crc & 1;
		crc >>= 1;
		if (n != 0) {
		    crc ^= 0x8408;
		}
	    }
	}
	return crc;
    }


    public byte[] getSessionCounter()
    {
	return sessionCounter;
    }

    public byte getTimestampHigh()
    {
	return timestampHigh;
    }

    public byte[] getTimestampLow()
    {
	return timestampLow;
    }


    public Token(byte[] b)
    {
	if (b.length != BLOCK_SIZE) {
	    throw new IllegalArgumentException("Not "+BLOCK_SIZE+" length");
	}

	int calcCrc = Token.calculateCrc(b);
	//System.out.println("calc crc  = "+calcCrc);
	//System.out.println("ok crc is = "+Token.CRC_OK_RESIDUE);
	if (calcCrc != Token.CRC_OK_RESIDUE) {
	    throw new IllegalArgumentException("CRC failure");
	}
	int start = 0;

	uid = new byte[UID_SIZE];
	System.arraycopy(b, start, uid, 0, UID_SIZE);
	start += UID_SIZE;

	sessionCounter = new byte[2];
	System.arraycopy(b, start, sessionCounter, 0, 2);
	start += 2;

	timestampLow = new byte[2];
	System.arraycopy(b, start, timestampLow, 0, 2);
	start += 2;

	timestampHigh = b[start];
	start += 1;

	timesUsed = b[start];
	start += 1;

	random = new byte[2];
	System.arraycopy(b, start, random, 0, 2);
	start += 2;

	crc = new byte[2];
	System.arraycopy(b, start, crc, 0, 2);
    }

    private static String toString(byte b)
    {
	return toString(new byte[]{b});
    }
    
    static String toString(byte[] b)
    {
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < b.length; i += 1){
	    if (i > 0) {sb.append(",");}
	    sb.append(Integer.toHexString(b[i] & 0xFF));
	}
	return sb.toString();
    }


    public byte[] getCleanCounter()
    {
	byte[] b = new byte[2];
	b[0] = (byte) (sessionCounter[0] & (byte) 0x7F);
	b[1] = (byte) (sessionCounter[1] & (byte) 0xFF);
	return b;
    }

    public boolean wasCapsLockOn()
    {
	return ((byte) (sessionCounter[0] & (byte) 0x80)) != 0;
    }


    public String toString()
    {
	StringBuffer sb = new StringBuffer();
	sb.append("[Token uid: "+Token.toString(uid));
	sb.append(", counter: "+Token.toString(sessionCounter));
	sb.append(", timestamp (low): "+Token.toString(timestampLow));
	sb.append(", timestamp (high): "+Token.toString(timestampHigh));
	sb.append(", session use: "+Token.toString(timesUsed));
	sb.append(", random: "+Token.toString(random));
	sb.append(", crc: "+Token.toString(crc));
	sb.append(", clean counter: "+Token.toString(getCleanCounter()));
	sb.append(", CAPS pressed: "+wasCapsLockOn()+"]");
	return sb.toString();
    }

}
