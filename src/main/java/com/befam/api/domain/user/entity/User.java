package com.befam.api.domain.user.entity;

import com.befam.api.domain.user.dto.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "tb_user")
public class User implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="user_id", nullable=false, length=20)
    private String userId;

    @Column(name="user_pw", nullable=false, length=100)
    private String userPw;

    @Column(name="user_nm", nullable=false, length=50)
    private String userNm;

    @Enumerated(EnumType.STRING)
    @Column(name="login_Type", nullable=false)
    private LoginType loginType;

    @Column(name="user_type", nullable=false, length=1)
    private String userType;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fnl_login_dttm")
    private Date fnlLoginDttm;

    @Column(name="email", nullable=false, length = 30)
    private String email;

    @Column(name="phone_number", nullable=false, length = 20)
    private String phoneNumber;

    @Column(name="socail_number", nullable=false, length = 20)
    private Long socailNumber;

    @Column(name="age", nullable = false, length=3)
    private Long age;

    @Override
    public String getId() {
        return this.userId;
    }

    @Override
    public boolean isNew() {
        return true;
    }

}
