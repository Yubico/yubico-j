package com.yubico.base.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.yubico.base.Pof;

public class PofTest extends TestCase
{
    public static void main(String[] args) throws Exception
    {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() throws Exception
    {
        return new TestSuite(PofTest.class);
    }

    public void test1() throws Exception
    {
	String buf = "0123456789abcdef";
	String key = "abcdef0123456789";
	byte[] facit =  new byte[]{
	    (byte) 0x83, (byte) 0x8a, 0x46, 0x7f, 0x34, 0x63, 
	    (byte) 0x95, 0x51, (byte) 0x75, 0x5b, (byte) 0xd3, 
	    0x2a, 0x4a, 0x2f, 0x15, (byte) 0xe1};
  
	byte[] b = Pof.decrypt(key.getBytes(), buf.getBytes());
	for (int i=0; i<b.length; i++){
	    assertEquals(b[i], facit[i]);
	}
    }
}