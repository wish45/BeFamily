package com.befam.api.domain.user.dto;

import com.befam.api.domain.user.support.UserValidGroups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPassDto {

    @NotEmpty(groups = UserValidGroups.commonValidGroup.class, message = "userId")
    private String userId;
    
    @NotEmpty(groups = UserValidGroups.commonValidGroup.class, message = "existPwd")
    private String existPwd;

    @NotEmpty(groups = UserValidGroups.commonValidGroup.class, message = "newPwd")
    private String newPwd;

    @NotEmpty(groups = UserValidGroups.commonValidGroup.class, message = "retryPwd")
    private String retryPwd;



}
