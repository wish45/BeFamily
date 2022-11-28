package com.befam.api.domain.user.dto;

import com.befam.api.domain.user.entity.User;
import com.befam.api.domain.user.support.UserValidGroups;
import com.befam.api.domain.utils.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @NotEmpty(groups = UserValidGroups.commonValidGroup.class, message = "userId")
    private String userId;

    @NotEmpty(groups = UserValidGroups.commonValidGroup.class, message = "userPw")
    private String userPw;

    @NotEmpty(groups = UserValidGroups.registValidGroup.class,  message = "userNm")
    private String userNm;

    @Enumerated(EnumType.STRING)
    @NotEmpty(groups = UserValidGroups.registValidGroup.class,  message = "loginType")
    private LoginType loginType;

    private String userType;

    private String fnlLoginDttm;

    @NotEmpty(groups = UserValidGroups.commonValidGroup.class, message = "email")
    private String email;

    @NotEmpty(groups = UserValidGroups.commonValidGroup.class, message = "phoneNumber")
    private String phoneNumber;

    @NotEmpty(groups = UserValidGroups.commonValidGroup.class, message = "socialNumber")
    private Long socialNumber;

    @NotEmpty(groups = UserValidGroups.commonValidGroup.class, message = "age")
    private Long age;


    public static UserDto from(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .userNm(user.getUserNm())
                .loginType(user.getLoginType())
                .userType(user.getUserType())
                .fnlLoginDttm(user.getFnlLoginDttm() != null ? DateTimeUtil.toDateTimeStringFrom(user.getFnlLoginDttm()) : null)
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .socialNumber(user.getSocailNumber())
                .age(user.getAge())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userPw(userPw)
                .userNm(userNm)
                .loginType(loginType)
                .userType(userType)
                .email(email)
                .phoneNumber(phoneNumber)
                .socailNumber(socialNumber)
                .age(age)
                .build();
    }
}
