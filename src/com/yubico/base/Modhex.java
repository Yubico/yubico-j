package com.yubico.base;

import org.apache.log4j.Logger;
import java.io.ByteArrayOutputStream;


public class Modhex
{
    private static String transString = "cbdefghijklnrtuv";
    private static char trans[] = transString.toCharArray();

    public static String encode(byte[] data)
    {
	//byte[] data = s.getBytes();
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < data.length; i++) {
	    result.append(trans[(data[i] >> 4) & 0xf]);
	    result.append(trans[data[i] & 0xf]);
	}

        return result.toString();
    }

    public static byte[] decode(String s)
    {
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = s.length();
 	int b;
	boolean toggle = false;
	int keep = 0;

        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
 	    int n = transString.indexOf(ch);
	    if (n == -1) {
		throw new 
		    IllegalArgumentException(s+" is not properly encoded");
	    }

 	    toggle = !toggle;

 	    if (toggle) {
		keep = n;
	    } else {
		baos.write((keep << 4) | n);
 	    }
 	}
	return baos.toByteArray();
     }
}

