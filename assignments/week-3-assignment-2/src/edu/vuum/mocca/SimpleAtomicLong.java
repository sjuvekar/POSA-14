// Import the necessary Java synchronization and scheduling classes.

package edu.vuum.mocca;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

/**
 * @class SimpleAtomicLong
 *
 * @brief This class implements a subset of the
 *        java.util.concurrent.atomic.SimpleAtomicLong class using a
 *        ReentrantReadWriteLock to illustrate how they work.
 */
class SimpleAtomicLong
{
    /**
     * The value that's manipulated atomically via the methods.
     */
    private long mValue;


    /**
     * The ReentrantReadWriteLock used to serialize access to mValue.
     */
    // TODO - add the implementation
    private ReentrantReadWriteLock mRWLock = new ReentrantReadWriteLock();

    /**
     * Creates a new SimpleAtomicLong with the given initial value.
     */
    public SimpleAtomicLong(long initialValue) {
        // TODO - you fill in here
        mValue = initialValue;
    }

    /**
     * @brief Gets the current value
     * 
     * @returns The current value
     */
    public long get() {
        long value;

        // TODO - you fill in here
        mRWLock.readLock().lock();
        try {
            value = mValue;
            return value;
	}
        finally {
            mRWLock.readLock().unlock();
        }
    }


    /**
     * @param modifyBy +1 for increment and -1 for decrement
     * @param modifyFirst if modifyFirst is true, the method returns updated value else returns old value
     * @return (mValue + modifyBy) or (mValue) depending on modifyFirst flag
     */
    private long modifyAndGetOrdered(long modifyBy, boolean modifyFirst) {
        long value = 0;
        mRWLock.writeLock().lock();
        try {
            if (modifyFirst) {
                mValue += modifyBy;
                value = mValue;
            }
            else {
                value = mValue;
                mValue += modifyBy;
            }
	    return value;
        }
        finally {
            mRWLock.writeLock().unlock();
        }
    }


    /**
     * @brief Atomically decrements by one the current value
     *
     * @returns the updated value
     */
    public long decrementAndGet() {
        // TODO - you fill in here
        return modifyAndGetOrdered(-1, true);
    }

    /**
     * @brief Atomically increments by one the current value
     *
     * @returns the previous value
     */
    public long getAndIncrement() {
        // TODO - you fill in here
        return modifyAndGetOrdered(1, false);
    }

    /**
     * @brief Atomically decrements by one the current value
     *
     * @returns the previous value
     */
    public long getAndDecrement() {
        // TODO - you fill in here
        return modifyAndGetOrdered(-1, false);
    }

    /**
     * @brief Atomically increments by one the current value
     *
     * @returns the updated value
     */
    public long incrementAndGet() {
        // TODO - you fill in here
        return modifyAndGetOrdered(1, true);
    }
}

