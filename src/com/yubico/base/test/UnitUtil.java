
package com.yubico.base.test;

// Utilities for unit testing.
final class UnitUtil
{
  private UnitUtil(){} // Utility pattern dictates private constructor.
  
  // Asserts two arrays equal in length and count, null values not allowed.
  static void assertEqual(byte[] a1, byte[] a2)
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
  static void assertStringInAlphabet(String s, String a)
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
  
}
