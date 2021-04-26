package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private Integer id;
    private String username;
    private String password;
    //以下字段暂时用不上 后续可以添加功能
    private String nickname;
    private Boolean sex;
    private java.util.Date birthday;
    private String head;
}
