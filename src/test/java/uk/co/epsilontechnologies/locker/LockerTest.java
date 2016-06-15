package uk.co.epsilontechnologies.locker;

import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LockerTest {

    @Before
    public void setUp() {
        Context.put(LockStore.class, new InMemoryLockStore());
    }

    @Test
    public void shouldInvokeMethodIfNoLockIsHeld() throws Exception {
        final Lock myLock = lockInstance("mylock");
        final ClassWithLockedMethod classWithLockedMethod = new ClassWithLockedMethod();
        assertFalse(Context.get(LockStore.class).isHeld(myLock));
        classWithLockedMethod.holdLockUntilReleased(myLock);
        assertFalse(Context.get(LockStore.class).isHeld(myLock));
    }

    private Lock lockInstance(final String value) {
        return new Lock() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Lock.class;
            }
            @Override
            public String value() {
                return value;
            }
        };
    }

    class ClassWithLockedMethod {

        @Lock("mylock")
        public void holdLockUntilReleased(final Lock lock) throws Exception {
            assertTrue(Context.get(LockStore.class).isHeld(lock));
        }

    }

    class InMemoryLockStore implements LockStore {

        private final Set<String> locks = new HashSet<>();

        @Override
        public boolean secure(Lock lock) {
            return locks.add(lock.value());
        }

        @Override
        public void release(Lock lock) {
            locks.remove(lock.value());
        }

        @Override
        public boolean isHeld(Lock lock) {
            return locks.contains(lock.value());
        }

    }

}
