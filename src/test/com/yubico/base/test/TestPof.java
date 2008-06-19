
package test.com.yubico.base.test;

import junit.framework.TestCase;

import com.yubico.base.Pof;
import com.yubico.base.Token;
import com.yubico.base.tools.Hex;

/**
 * <p>
 *   JUnit 3 
 *   {@link Pof}
 *   {@link TestCase}.
 * </p>
 * @author Arne
 */
public final class TestPof extends TestCase
{
  // Test vector to be decrypted and parsed.
  private final String m_testVector="hknhfjbrjnlnldnhcujvddbikngjrtgh";
  
  // AES key OTP the test vector was encrypted with.
  private final byte[] m_aesKey=Hex.decode("ecde18dbe76fbd0c33330f1c354871db");
  
  // Secret id of OTP the test vector represents.
  private final byte[] m_uid=Hex.decode("8792ebfe26cc");
  
  // Lay out of session counter in OTP the test vector represents.
  private final byte[] m_counter=Hex.decode("1300");
  
  // Lay out of crc in OTP the test vector represents.
  private final byte[] m_crc=Hex.decode("23c8");
  
  // Lay out of random bytes in OTP the test vector represents.
  private final byte[] m_random=Hex.decode("c89f");
  
  // Lay out of low part of the time stamp in OTP the test vector represents.
  private final byte[] m_tlow=Hex.decode("30c2");
  
  // Lay out of high part of the time stamp in OTP the test vector represents.
  private final byte m_thigh=Hex.decode("00")[0];
  
  // Lay out of use counter of the time stamp in OTP the test vector represents.
  private final byte m_use=Hex.decode("11")[0];
  
  /**
   * <p>
   *   Tests that a test vector decrypt and parses to known result using
   *   {@link Pof#parse(String, byte[])}.
   * </p>
   */
  public void testParse()
  {
    try
    {
      Token t=Pof.parse(m_testVector, m_aesKey);
    
      Assert.assertArrayEquals("uid", m_uid, t.getUid());
      Assert.assertArrayEquals("counter", m_counter, t.getSessionCounter());
      Assert.assertArrayEquals("crc", m_crc, t.getCrc());
      Assert.assertArrayEquals("random", m_random, t.getRandom());
      Assert.assertArrayEquals("time-low", m_tlow, t.getTimestampLow());
      assertEquals("time-high", m_thigh, t.getTimestampHigh());
      assertEquals("used", m_use, t.getTimesUsed());     
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  
}
