package com.thoughtworks.rslist.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
public class UserDto {
    @Id
    @GeneratedValue
    private int id;
    private String userName;
    private String gender;
    private int age;
    private String email;
    private String phone;
    private int voteNum = 10;
}
