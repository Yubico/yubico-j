
package com.yubico.base.test;

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
  
  // Asserts two arrays equal in length and count, null values not allowed.
  private void assertEqual(byte[] a1, byte[] a2)
  {
    if (a1==null)
    {
      throw new NullPointerException("a1");
    }
    if (a2==null)
    {
      throw new NullPointerException("a2");
    }
    
    int n=a1.length;
    if (n!=a2.length)
    {
      throw new RuntimeException("a1.length!=a2.length : "+n+"!="+a2.length);
    }
    
    for (int i=0; i<n; i++)
    {
      if (a1[i]!=a2[i])
      {
        throw new RuntimeException("a1["+i+"]!=a2["+i+"]");
      }
    }
  }
  
  // Checks that every char in s is found in a.
  private void assertStringInAlphabet(String s, String a)
  {
    if (s==null)
    {
      throw new NullPointerException("s");
    }
    
    int n=s.length();
    int m=a.length();
    for (int i=0; i<n; i++)
    {
      char c=s.charAt(i);
      int j=0;
      for (; j<m && c!=a.charAt(j); j++){}
      if (j==m)
      {
        throw new RuntimeException("s["+i+"]="+c+" not in : "+a);
      }
    }
  }
  
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
