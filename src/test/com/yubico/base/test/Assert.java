
package test.com.yubico.base.test;

// Place for JUnit 3.8 missing Asserts.
final class Assert
{
  private Assert(){} // Only static methods.
  
  /**
   * <p>
   *   Asserts two byte arrays has same content.
   * </p>
   * @param msg      Error message if assertions fails.
   * @param expected Expected array.
   * @param actual   Actual array.
   * @throws NullPointerException if {@code expected==null || actual==null}.
   * @throws AssertionError If arrays do not have same content.
   */
  static void assertArrayEquals(String msg, byte[] expected, byte[] actual)
  {
    if (expected==null)
    {
      throw new NullPointerException("expected");
    }
    if (actual==null)
    {
      throw new NullPointerException("actual");
    }
    
    if (expected==actual) return;
    
    int n=expected.length;
    if (n!=actual.length)
    {
      throw new AssertionError("expected.length!=actual.length : "+n+"!="+
        actual.length);    
    }
    
    for (int i=0; i<n; i++)
    {
      if (expected[i]!=actual[i])
      {
        throw new AssertionError("a0["+i+"]!=a1["+i+"]");
      }
    }
  }
  
}
