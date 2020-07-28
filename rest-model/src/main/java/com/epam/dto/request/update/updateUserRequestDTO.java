package com.epam.dto.request.update;

import javax.validation.constraints.*;

public class updateUserRequestDTO {
    @Size(min = 2, max = 30, message = "Incorrect username(Should have size 2-30)")
    @Pattern(regexp = "[a-zA-Z 0-9]+",
            message = "Incorrect username (Should contain Latin letters, spaces or numbers)")
    @NotBlank(message = "Incorrect username(Should contain not only spaces)")
    @NotEmpty(message = "Incorrect username(Should contain some information)")
    private String username;
    @Min(value = 1, message = "Incorrect id value (Value must be more then 0)")
    private long userId;
    @Size(min = 2, max = 30, message = "Incorrect password(Should have size 2-30)")
    @Pattern(regexp = "[a-zA-Z0-9]+",
            message = "Incorrect password (Should contain Latin letters, spaces or numbers)")
    @NotBlank(message = "Incorrect password(Should contain not only spaces)")
    @NotEmpty(message = "Incorrect password(Should contain some information)")
    private String password;

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
}
