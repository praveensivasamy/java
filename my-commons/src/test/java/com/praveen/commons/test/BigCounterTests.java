package com.praveen.commons.test;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class BigCounterTests {
    BigCounter counter = new BigCounter();

    @Test
    public void isInitiallyZero() {
        assertEquals(counter.count(), BigInteger.ZERO);
    }

    @Test
    public void canIncreaseCounter() {
        counter.inc();
        assertEquals(counter.count(), BigInteger.valueOf(1));

        counter.inc();
        assertEquals(counter.count(), BigInteger.valueOf(2));

        counter.inc();
        assertEquals(counter.count(), BigInteger.valueOf(3));
    }

    @Test
    public void canIncrementCounterFromMultipleThreadsSimultaneously() throws InterruptedException {
        MultithreadedStressTester stressTester = new MultithreadedStressTester(250);

        stressTester.stress(new Runnable() {
            @Override
            public void run() {
                counter.inc();
            }
        });

        stressTester.shutdown();
        assertEquals("final count", counter.count(), BigInteger.valueOf(stressTester.totalActionCount()));
    }

}
