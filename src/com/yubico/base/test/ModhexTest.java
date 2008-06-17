
package com.yubico.base.test;

import static com.yubico.base.test.UnitUtil.assertEqual;
import static com.yubico.base.test.UnitUtil.assertStringInAlphabet;

import java.util.Random;

import com.yubico.base.Modhex;

/**
 * <p>
 *   Unit tests for
 *   {@link Modhex}.
 * </p>
 * @author Arne
 */
public final class ModhexTest
{
  /**
   * <p>
   *   Constructor.
   * </p>
   */
  public ModhexTest(){} 
  
  /**
   * <p>
   *   Tests that a random array of 1000 bytes 
   *   {@link Modhex#encode(byte[]) encodes} to valid Modhex
   *   {@code String} and 
   *   {@link Modhex#decode(String) decodes} back to same byte array. 
   * </p>
   */
  public void testModhexRandomArray()
  {
    Random r=new Random();
    byte[] raw=new byte[1000];
    r.nextBytes(raw);
    String encoded=Modhex.encode(raw);
    assertStringInAlphabet(encoded, Modhex.ALPHABET);
    byte[] decoded=Modhex.decode(encoded);
    assertEqual(raw, decoded);
  }
  
  /**
   * <p>
   *   Same test as
   *   {@link #testModhexRandomArray()} but Modhex 
   *   {@link String} is converted to all upper case before decoded. 
   * </p>
   */
  public void testModhexRandomUpperCaseArray()
  {
    Random r=new Random();
    byte[] raw=new byte[1000];
    r.nextBytes(raw);
    String encoded=Modhex.encode(raw);
    assertStringInAlphabet(encoded, Modhex.ALPHABET);
    encoded=encoded.toUpperCase();
    byte[] decoded=Modhex.decode(encoded);
    assertEqual(raw, decoded);
  }
  
  /**
   * <p>
   *   Runs tests.
   * </p>
   * @param args Command line arguments, not used.
   */
  public static void main(String[] args)
  {
    ModhexTest t=new ModhexTest();
    try
    {
      t.testModhexRandomArray();
      t.testModhexRandomUpperCaseArray();
      System.out.println("all tests ok");
      System.exit(0);
    }
    catch (Throwable x)
    {
      x.printStackTrace();
      System.exit(-1);
    }
  }
  
}
