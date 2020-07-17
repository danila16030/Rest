package com.epam.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "public")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Column(name = "username", nullable = false)
    private String username;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private long userId;
    @Column(name = "user_password", nullable = false)
    private String password;
    @Transient
    private int active = 1;
    @Column(name = "role", nullable = false)
    private String role = "USER";

    /*    @CreatedDate
        private long createDate;
        @LastModifiedDate
        private long lastModifiedDate;
        @CreatedBy
        private String createdBy;
        @LastModifiedBy
        private String modifiedBy;
    */
    public User() {
    }

    public User(String username, long userId, String password) {
        this.username = username;
        this.userId = userId;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (active != user.active) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return role != null ? role.equals(user.role) : user.role == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + active;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userId=" + userId +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", role='" + role + '\'' +
                '}';
    }
}
