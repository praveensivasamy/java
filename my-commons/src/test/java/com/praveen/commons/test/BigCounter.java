package com.praveen.commons.test;

import java.math.BigInteger;

/**
 * This class is not properly synchronised, to demonstrate how the {@link MultithreadedStressTester} works.
 */
public class BigCounter {

	private BigInteger count = BigInteger.ZERO;

	public BigInteger count() {
		return count;
	}

	public void inc() {
		count = count.add(BigInteger.ONE);
	}

	private void incrementThreadSafe() {

	}

}
