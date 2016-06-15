package uk.co.epsilontechnologies.locker;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LockAspect {

    @Pointcut("execution(public !static * *.*(..))")
    public void publicMethodInvocation() { }

    @Around("publicMethodInvocation() && @annotation(lock)")
    public void proceedWithLock(final ProceedingJoinPoint proceedingJoinPoint, final Lock lock) throws Throwable {
        final LockStore lockStore = Context.get(LockStore.class);
        try {
            if (lockStore.secure(lock)) {
                proceedingJoinPoint.proceed();
            } else {
                throw new LockUnavailableException(lock);
            }
        } catch (final Throwable t) {
            throw t;
        } finally {
            lockStore.release(lock);
        }
    }

}