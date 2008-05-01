package com.yubico.base.tools;

import org.apache.log4j.Logger;
import java.security.GeneralSecurityException;
import java.io.ByteArrayOutputStream;
import com.yubico.base.Modhex;
import com.yubico.base.Pof;
import com.yubico.base.Token;

public class Display
{

    public static void main(String[] argv)
    {
	if (argv.length != 2) {
	    System.err.println("Need <aeskey> <token>");
	    return;
	}
	String key = argv[0];
	String token = argv[1];

	if (key.length() != 32) {
	    System.err.println("ModHex encoded AES-key must be 32 characters");
	    return;
	}
	int len = token.length();
	if (len > 32) {
	    token = token.substring(len - 32);
	    System.out.println("token too long, truncating to "+token);
	}
	byte[] keyBytes = Modhex.decode(key);
	try {
	    Token t = Pof.parse(token, keyBytes);
	    System.out.println(t);
	} catch (GeneralSecurityException e) {
	    System.err.println(e);
	}
    }
}

