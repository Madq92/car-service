package com.mashangshouche.car.common;


import com.mashangshouche.car.entity.User;

public class AuthHold {
    private final static ThreadLocal<User> USER_HOLD = new ThreadLocal<User>();

    private AuthHold() {

    }

    public static User currentUser() {
        return USER_HOLD.get();
    }

    public static void clean() {
        USER_HOLD.remove();
    }

    public static void setUser(User user) {
        USER_HOLD.set(user);
    }
}
