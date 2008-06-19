
package test.com.yubico.base.test;

import com.yubico.base.Modhex;
import com.yubico.base.tools.Hex;

import junit.framework.TestCase;

/**
 * <p>
 *   JUnit 3 
 *   {@link Modhex}
 *   {@link TestCase}.
 * </p>
 * @author Arne
 */
public class TestModhex extends TestCase
{
  private final byte[] m_decoded=Hex.decode("69b6481c8baba2b60e8f22179b58cd56");
  
  private final String m_encoded="hknhfjbrjnlnldnhcujvddbikngjrtgh";

  /**
   * <p>
   *   Tests that byte array encode to known result.
   * </p>
   */
  public void testEncode()
  {
    String encoded=Modhex.encode(m_decoded);
    assertEquals("does not encode to expected", m_encoded, encoded);
  }

  /**
   * <p>
   *   Tests that 
   *   {@link Modhex} 
   *   {@link String} decodes to known result.
   * </p>
   */
  public void testDecode()
  {
    byte[] decoded=Modhex.decode(m_encoded);
    Assert.assertArrayEquals("does not encode to expected", m_decoded, decoded);
  }
  
  /**
   * <p>
   *   Tests that 
   *   {@link Modhex} 
   *   {@link String} all upper case characters decodes to known result.
   * </p>
   */
  public void testDecodeUpperCase()
  {
    byte[] decoded=Modhex.decode(m_encoded.toUpperCase());
    Assert.assertArrayEquals("does not encode to expected", m_decoded, decoded);
  }

}
