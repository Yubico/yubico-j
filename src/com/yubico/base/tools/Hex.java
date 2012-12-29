/* Copyright (c) 2008, Yubico AB.  All rights reserved.

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

package com.yubico.base.tools;

/**
 * <p>
 *   Utility methods that 
 *   {@link #decode(String) decodes} hex 
 *   {@code String} to byte array and
 *   {@link #encode(byte[]) encodes} byte array to hex
 *   {@code String}.
 * </p>
 * <p>
 *   For internal yubico-j use only, client code of yubico-j must <i>not</i>
 *   use.
 * </p>
 * @author Arne
 */
public final class Hex
{
  private Hex(){} // Utility pattern dictates private constructor.
  
  private static final char[] c_hexChars=
  { 
    '0', '1', '2', '3', '4', '5', '6', '7',
    '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' 
  };
  
  private static char highNibble(byte b){ return c_hexChars[(b&0xF0)>>4]; }
  
  private static char lowNibble(byte b){ return c_hexChars[b&0x0F]; }
  
  /**
   * <p>
   *   Encodes.
   * </p>
   * @param b Bytes to encode.
   * @return Encoded.
   */
  public static String encode(byte[] b)
  {
    if (b==null)
    {
      throw new NullPointerException("b");
    }
    
    StringBuilder sb=new StringBuilder();
    
    for (int i=0; i<b.length; i++) 
    {
      byte curr=b[i];
      sb.append(highNibble(curr)).append(lowNibble(curr));
    }
    return sb.toString();
  }
  
  /**
   * <p>
   *   Decodes.
   * </p>
   * @param s {@code String} to decode.
   * @return Bytes.
   * @throws NumberFormatException If {@code s} not accepted.
   */
  public static byte[] decode(String s)
  {
    if (s==null)
    {
      throw new NullPointerException("s");
    }
    
    int n=s.length();
    if (n==0) return new byte[0];
    
    byte[] buffer=new byte[(n+1)/2];
    
    boolean even=((n&1)==0);
    
    byte b=0;
    
    int bufferOffset=0;

    for (int i=0; i<n; i++)
    {
      char c=s.charAt(i);
      
      int  nibble;
    
           if ((c>='0') && (c<='9')) nibble=c-'0';
      else if ((c>='A') && (c<='F')) nibble=c-'A'+0x0a;
      else if ((c>='a') && (c<='f')) nibble=c-'a'+0x0a;
      else
      {
        throw new NumberFormatException("invalid hex digit : "+c);
      }
    
      if (even) 
      {
        b=(byte)(nibble<<4);
      }
      else
      {
        b+=(byte)nibble;
        buffer[bufferOffset++]=b;
      }
    
      even=!even;
    }

    return buffer;
  }
  
}
