
package com.yubico.base.test;

import static com.yubico.base.test.UnitUtil.assertEqual;

import java.security.GeneralSecurityException;

import com.yubico.base.Pof;
import com.yubico.base.Token;

/**
 * <p>
 *   Unit tests for
 *   {@link Pof} and
 *   {@link Token}.
 * </p>
 * @author Arne Halvorsen (AH) <a href='mailto:arne@bssitt.com'>arne@bssitt.com</a>
 */
public final class PofTokenTest
{
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
   *   Constructor.
   * </p>
   */
  public PofTokenTest(){}
  
  /**
   * <p>
   *   Tests that a known test vector decrypt parses to expected values. 
   * </p>
   * <p>
   *   TODO: Redesign to easily allow testing against a set of test vectors
   *   and do that.
   * </p>
   */
  public void testVector()
  {
    try
    {
      Token t=Pof.parse(m_testVector, m_aesKey);
      
      assertEqual(t.getUid(), m_uid);
      assertEqual(t.getSessionCounter(), m_counter);
      assertEqual(t.getCrc(), m_crc);
      assertEqual(t.getRandom(), m_random);
      assertEqual(t.getTimestampLow(), m_tlow);
      assertEqual(t.getTimestampHigh(), m_thigh);
      assertEqual(t.getTimesUsed(), m_use);
    }
    catch (GeneralSecurityException gsx){ throw new RuntimeException(gsx); }
  }
  
  /**
   * <p>
   *   Runs tests.
   * </p>
   * @param args Command line arguments, not used.
   */
  public static void main(String[] args)
  {
    try
    {
      PofTokenTest test=new PofTokenTest();
      test.testVector();
      System.out.print("all tests ok");
      System.exit(0);
    }
    catch (Throwable t)
    {
      t.printStackTrace();
      System.exit(-1);
    }
  }
  
}
