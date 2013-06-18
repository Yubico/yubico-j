package com.yubico.token;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.yubico.base.Base16;

public class InvalidTokenTestCase {
	private static final byte[] KEY = Base16
			.decode("0123456789abcdef0123456789abcdef");
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void tooLongOTP() throws InvalidYubiTokenException {
		String token = "cbdefghijklnbvhgbhebfuurheknkvulgtdejrljhifnc"
				+ "bdefghijklnbvhgbhebfuurheknkvulgtdejrljhifn";
		expectedEx.expect(InvalidYubiTokenException.class);
		YubiTokenFactory.parse(token, KEY);
	}

	@Test
	public void wrongCharacters() throws InvalidYubiTokenException {
		String token = "cadefghijklnavhgaheafuurheknkvulgtdejrljhifn";
		expectedEx.expect(InvalidYubiTokenException.class);
		YubiTokenFactory.parse(token, KEY);
	}

	@Test
	public void tooShortAesKey() throws InvalidYubiTokenException {
		byte[] key = Base16.decode("012345678abcdef0123456789abcde");
		String token = "cbdbvhgbhebfuurheknkvulgtdejrljhifn";
		expectedEx.expect(InvalidYubiTokenException.class);
		YubiTokenFactory.parse(token, key);
	}
}
