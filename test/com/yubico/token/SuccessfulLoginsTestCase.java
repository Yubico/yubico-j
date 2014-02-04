package com.yubico.token;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.yubico.base.Base16;

public class SuccessfulLoginsTestCase {

	public static final String PUBLIC_ID = "rjvklurjjuvg";
	public static final String PRIVATE_ID = "f236286248e5";
	public static final String SECRET_KEY_STR = "a6c418df6d67e57983b775e69c8061fd";
	public static final byte[] TOKEN_KEY = Base16.decode(SECRET_KEY_STR);
	String[] insert1 = new String[] {
			"rjvklurjjuvgkcdkvtnrrhgjikfccfigdkuihebfrgbg",
			"rjvklurjjuvghrhgrhvvljhbbcijvbdcbrhccruvnibl",
			"rjvklurjjuvgjdrlviuhcrvhfkijgdthjukgjukbtvld",
			"rjvklurjjuvgbkjtknndbjdigdblndbkrejtlgdnfbbj" };

	String[] insert2 = new String[] {
			"rjvklurjjuvgvvtddftfrbvciftttdjfruirlgikhhcv",
			"rjvklurjjuvgvejijitrgtfvgklncvurrbgvvciclltj",
			"rjvklurjjuvgdrbjiuntuehfdhggtnnbhdlcunjurdkn" };
	String[] insert3 = new String[] { "rjvklurjjuvghdevefjtbekhdlhjugeijnccueldtvld" };

	@Test
	public void testInsert1() throws InvalidYubiTokenException {
		int lastTs = 0;
		for (int i = 0; i < insert1.length; i++) {
			YubiToken token = YubiTokenFactory.parse(insert1[i], TOKEN_KEY);
			assertThat(token.getCounter(), is(1));
			assertThat(token.getTimestamp(), is(greaterThan(lastTs)));
			assertThat(token.getSessionCounter(), is(i));
			assertThat(token.getPublicId(), is(PUBLIC_ID));
			assertThat(token.getPrivateId(), is(PRIVATE_ID));
			lastTs = token.getTimestamp();
		}
	}

	@Test
	public void testInsert2() throws InvalidYubiTokenException {
		int lastTs = 0;
		for (int i = 0; i < insert2.length; i++) {
			YubiToken token = YubiTokenFactory.parse(insert2[i], TOKEN_KEY);
			assertThat(token.getCounter(), is(2));
			assertThat(token.getTimestamp(), is(greaterThan(lastTs)));
			assertThat(token.getSessionCounter(), is(i));
			assertThat(token.getPublicId(), is(PUBLIC_ID));
			assertThat(token.getPrivateId(), is(PRIVATE_ID));
			lastTs = token.getTimestamp();
		}
	}

	@Test
	public void testInsert3() throws InvalidYubiTokenException {
		int lastTs = 0;
		for (int i = 0; i < insert3.length; i++) {
			YubiToken token = YubiTokenFactory.parse(insert3[i], TOKEN_KEY);
			assertThat(token.getCounter(), is(3));
			assertThat(token.getTimestamp(), is(greaterThan(lastTs)));
			assertThat(token.getSessionCounter(), is(i));
			assertThat(token.getPublicId(), is(PUBLIC_ID));
			assertThat(token.getPrivateId(), is(PRIVATE_ID));
			lastTs = token.getTimestamp();
		}
	}

	@Test
	public void testMonotonicCounter() throws InvalidYubiTokenException {
		long lastCtr = 0;
		for (String token : insert1) {
			long newCtr = YubiTokenFactory.parse(token, TOKEN_KEY)
					.getMonotonicCounter();
			assertThat(newCtr, is(greaterThan(lastCtr)));
			lastCtr = newCtr;
		}
		for (String token : insert2) {
			long newCtr = YubiTokenFactory.parse(token, TOKEN_KEY)
					.getMonotonicCounter();
			assertThat(newCtr, is(greaterThan(lastCtr)));
			lastCtr = newCtr;
		}
		for (String token : insert3) {
			long newCtr = YubiTokenFactory.parse(token, TOKEN_KEY)
					.getMonotonicCounter();
			assertThat(newCtr, is(greaterThan(lastCtr)));
			lastCtr = newCtr;
		}
	}
}
