package com.vocadabulary.auth;

// This holds the mock user info for the current request.
// public class MockUserContext {
//     private static final ThreadLocal<MockUser> currentUser = new ThreadLocal<>();

//     public static void set(MockUser user) {
//         currentUser.set(user);
//     }

//     public static MockUser get() {
//         return currentUser.get();
//     }

//     public static void clear() {
//         currentUser.remove();
//     }
// }

public class MockUserContext {

    private static final ThreadLocal<MockUser> currentUser = new ThreadLocal<>();

    public static void setCurrentUser(MockUser user) {
        currentUser.set(user);
    }

    public static MockUser getCurrentUser() {
        return currentUser.get();
    }

    // existing code

}