
package com.yubico.base;

/**
 * <p>
 *   Represents decrypted and parsed YubiKey OTP.
 * </p>
 * @author Simon
 */
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
  
  /**
   * <p>
   *   Gets <i>reference</i> to the CRC16 checksum of the OTP.
   * </p>
   * <p>
   *   This property is of little interest to other then unit test code since
   *   the checksum was validated when constructing {@code this}. 
   * </p>
   * @return CRC16.
   */
  public byte[] getCrc(){ return crc; }
  
  /**
   * <p>
   *   Gets <i>reference</i> to the random bytes of the OTP.
   * </p>
   * <p>
   *   This property is of little interest to other then unit test code. 
   * </p>
   * @return Random bytes.
   */
  public byte[] getRandom(){ return random; }

  /**
   * <p>
   *   Gets <i>reference</i> to bytes making up secret id.
   * </p>
   * @return Secret id.
   */
  public byte[] getUid(){ return uid; }

  /**
   * <p>
   *   Gets <i>reference</i> to byte sequence of session counter.
   * </p>
   * @return Session counter byte sequence.
   * @see #getCleanCounter()
   */
  public byte[] getSessionCounter(){ return sessionCounter; }

  /**
   * <p>
   *   Gets high byte of time stamp.
   * </p>
   * @return High time stamp.
   */
  public byte getTimestampHigh(){ return timestampHigh; }

  /**
   * <p>
   *   Gets <i>reference</i> to byte sequence of low part of time stamp.
   * </p>
   * @return Session counter byte sequence of length {@code 2}.
   */
  public byte[] getTimestampLow(){ return timestampLow; }

  /**
   * <p>
   *   Constructor.
   * </p>
   * @param b Decrypted OTP to be parsed.
   * @throws IllegalArgumentException If {@code b} not accepted as being a OTP.
   */
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
	    if (i > 0) sb.append(",");
	    sb.append(Integer.toHexString(b[i] & 0xFF));
	  }
	  return sb.toString();
  }

  /**
   * <p>
   *   Gets session counter bytes with cap-lock triggered bit cleared.
   * </p>
   * @return Session counter.
   */
  public byte[] getCleanCounter()
  {
  	byte[] b = new byte[2];
	  b[0] = (byte) (sessionCounter[0] & (byte) 0xFF);
	  b[1] = (byte) (sessionCounter[1] & (byte) 0x7F);
	  return b;
  }
  
  /**
   * <p>
   *   Gets byte value of counter that increases for each generated OTP during
   *   a session. 
   * </p>
   * @return Value.
   */
  public byte getTimesUsed(){ return timesUsed; }

  /**
   * <p>
   *   Tells if triggered by caps lock.
   * </p>
   * @return {@code true} if, {@code false} if not.
   */
  public boolean wasCapsLockOn()
  {
	  return ((byte) (sessionCounter[1] & (byte) 0x80)) != 0;
  }

  // Overrides.
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
