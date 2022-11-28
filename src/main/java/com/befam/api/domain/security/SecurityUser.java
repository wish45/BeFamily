package com.befam.api.domain.security;


import com.befam.api.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.AuthorityUtils;

@Getter
@Setter
public class SecurityUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	
	private User user;

	public SecurityUser(User user) {
		super(user.getUserId(), "Dummy", AuthorityUtils.createAuthorityList(
				"ROLE_" + user.getUserType()));
		this.user = user;
	}	
}
