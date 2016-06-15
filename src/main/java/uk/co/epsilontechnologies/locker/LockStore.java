package uk.co.epsilontechnologies.locker;

public interface LockStore {

    boolean secure(Lock lock);

    void release(Lock lock);

    boolean isHeld(Lock lock);

}
