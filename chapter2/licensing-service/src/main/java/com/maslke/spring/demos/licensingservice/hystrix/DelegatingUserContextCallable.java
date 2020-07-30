package com.maslke.spring.demos.licensingservice.hystrix;

import com.maslke.spring.demos.licensingservice.util.UserContext;
import com.maslke.spring.demos.licensingservice.util.UserContextHolder;

import java.util.concurrent.Callable;

public class DelegatingUserContextCallable<T> implements Callable<T> {

    private Callable<T> callable;
    private UserContext userContext;

    public DelegatingUserContextCallable(Callable<T> callable, UserContext userContext) {
        this.callable = callable;
        this.userContext = userContext;
    }

    @Override
    public T call() throws Exception {
        // call方法在被@HystrixCommand注解保护的方法之前调用
        // 设置了userContext之后，相当于保存了线程变量
        UserContextHolder.setContext(userContext);
        try {
            return callable.call();
        } finally {
            this.userContext = null;
        }
    }
}
