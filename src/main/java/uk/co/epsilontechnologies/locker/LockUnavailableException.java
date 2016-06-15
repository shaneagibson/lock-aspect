package uk.co.epsilontechnologies.locker;

public class LockUnavailableException extends Throwable {

    private final Lock lock;

    public LockUnavailableException(final Lock lock) {
        this.lock = lock;
    }

    public Lock getLock() {
        return this.lock;
    }

}
