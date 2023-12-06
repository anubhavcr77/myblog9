package com.myblog_9.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}
