package com.maslke.spring.demos.licensingservice.util;

import org.springframework.util.Assert;

public class UserContextHolder {
    private static final ThreadLocal<UserContext> USER_CONTEXT = new ThreadLocal<>();

    public static UserContext getContext() {
        UserContext context = USER_CONTEXT.get();

        if (context == null) {
            context = createEmptyContext();
            USER_CONTEXT.set(context);
        }
        return USER_CONTEXT.get();
    }

    private static UserContext createEmptyContext() {
        return new UserContext();
    }

    public static void setContext(UserContext context) {
        Assert.notNull(context, "Only non-null UserContext instances are permitted");
        USER_CONTEXT.set(context);
    }

    public static void removeContext() {
        USER_CONTEXT.remove();
    }

}
