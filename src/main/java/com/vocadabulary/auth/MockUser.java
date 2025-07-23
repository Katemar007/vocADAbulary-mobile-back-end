package com.vocadabulary.auth;

// public class MockUser {
//     private Integer userId;
//     private String role;

//     public MockUser(Integer userId, String role) {
//         this.userId = userId;
//         this.role = role;
//     }

//     public Integer getUserId() {
//         return userId;
//     }

//     public String getRole() {
//         return role;
//     }
// }
public class MockUser {
    private long id;
    private String role;

    public MockUser(long id, String role) {
        this.id = id;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}