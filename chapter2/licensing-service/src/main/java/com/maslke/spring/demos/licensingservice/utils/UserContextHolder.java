package com.maslke.spring.demos.licensingservice.utils;

import org.springframework.util.Assert;

public class UserContextHolder {

    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<UserContext>();

    public static final UserContext getContext() {
        UserContext context = userContext.get();

        if (context == null) {
            context = createEmptyContext();
            userContext.set(context);
        }
        return userContext.get();
    }

    public static final void setContext(UserContext context) {
        Assert.notNull(context, "only non-null UserContext instances are permitted");
        userContext.set(context);
    }

    private static UserContext createEmptyContext() {
        return new UserContext();
    }
}
