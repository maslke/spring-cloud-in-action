package com.maslke.spring.demos.licensingservice;

import com.maslke.spring.demos.licensingservice.utils.UserContext;
import com.maslke.spring.demos.licensingservice.utils.UserContextHolder;

import java.util.concurrent.Callable;

public class DelegatingUserContextCallable<T> implements Callable<T> {
    private final Callable<T> delegate;

    private UserContext originalUserContext;

    public DelegatingUserContextCallable(Callable<T> delegate, UserContext userContext) {
        this.delegate = delegate;
        this.originalUserContext = userContext;
    }

    @Override
    public T call() throws Exception {
        UserContextHolder.setContext(originalUserContext);
        try {
            return delegate.call();
        }
        finally {
            this.originalUserContext = null;
        }
    }

    public static <T> Callable<T> create(Callable<T> delegate, UserContext userContext) {
        return new DelegatingUserContextCallable<T>(delegate, userContext);
    }

}
