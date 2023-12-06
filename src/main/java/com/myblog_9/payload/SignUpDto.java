package com.myblog_9.payload;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String name;
     private String username;
     private String email;
     private String password;
}
