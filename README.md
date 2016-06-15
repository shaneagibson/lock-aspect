# lock-aspect

This simple java library provides an AOP-based locking mechanism for synchronising access to a function, via a @Lock annotation.

The lock is controlled by your implementation of *LockStore* and could be persisted in a database, on a file-system, etc.
