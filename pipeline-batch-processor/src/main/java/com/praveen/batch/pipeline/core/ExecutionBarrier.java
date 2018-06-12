package com.praveen.batch.pipeline.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.praveen.commons.enums.AppExceptionIdentifier;
import com.praveen.commons.exception.ApplicationException;
import com.praveen.commons.utils.ToStringUtils;

public class ExecutionBarrier {

    private CyclicBarrier barrier;

    private List<Throwable> exceptions = new ArrayList<>();
    private boolean failed = false;

    public ExecutionBarrier(int parties) {
        barrier = new CyclicBarrier(parties);
    }

    public int await(String where) {

        try {
            return barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION).details("Exception " + where + "  during wait on background pipeline processing" + e);
        }
    }

    public synchronized List<Throwable> getExceptions() {
        return exceptions;
    }

    public synchronized void setExceptions(Throwable exception) {
        exceptions.add(exception);
        failed = true;
    }

    public synchronized boolean isFailed() {
        return failed;
    }

    public synchronized int getPendingParties() {
        return barrier.getParties() - barrier.getNumberWaiting();
    }

    public void reset() {
        barrier.reset();
    }

    @Override
    public String toString() {
        return ToStringUtils.asString(this, "exceptions", "barrier");
    }

}
