package com.testtask.demoelevator1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DelayUtil {
    public void delay(final long durationInMillis) {
        delay(durationInMillis, TimeUnit.MILLISECONDS);
    }

    public void delay(final long duration, final TimeUnit unit) {
        long currentTime = System.currentTimeMillis();
        long deadline = currentTime+unit.toMillis(duration);
        ReentrantLock lock = new ReentrantLock();
        Condition waitCondition = lock.newCondition();

        while ((deadline-currentTime)>0) {
            try {
                lock.lockInterruptibly();
                waitCondition.await(deadline-currentTime, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            } finally {
                lock.unlock();
            }
            currentTime = System.currentTimeMillis();
        }
    }
}