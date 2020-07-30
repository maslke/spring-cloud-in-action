package com.maslke.spring.demos.licensingservice.hystrix;

import com.maslke.spring.demos.licensingservice.util.UserContext;
import com.maslke.spring.demos.licensingservice.util.UserContextHolder;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalAwareStrategy extends HystrixConcurrencyStrategy {

    private HystrixConcurrencyStrategy existingHystrixStrategy;

    public ThreadLocalAwareStrategy(HystrixConcurrencyStrategy strategy) {
        this.existingHystrixStrategy = strategy;
    }

    @Override
    public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
        if (existingHystrixStrategy != null) {
            return existingHystrixStrategy.getBlockingQueue(maxQueueSize);
        }
        return super.getBlockingQueue(maxQueueSize);
    }

    @Override
    public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
        if (existingHystrixStrategy != null) {
            return existingHystrixStrategy.getRequestVariable(rv);
        } else {
            return super.getRequestVariable(rv);
        }
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixProperty<Integer> corePoolSize, HystrixProperty<Integer> maximumPoolSize, HystrixProperty<Integer> keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        if (existingHystrixStrategy != null) {
            return existingHystrixStrategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }
        return super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        return existingHystrixStrategy != null ?
                existingHystrixStrategy.wrapCallable(new DelegatingUserContextCallable<>(callable, UserContextHolder.getContext()))
                : super.wrapCallable(new DelegatingUserContextCallable<>(callable, UserContextHolder.getContext()));
    }
}
