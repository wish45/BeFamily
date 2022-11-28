package com.befam.api.domain.utils;

import com.befam.api.domain.security.SecurityUser;
import com.befam.api.domain.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class UserUtil {

	public static Optional<User> getCurrentLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			Object principal = authentication.getPrincipal();
			if(principal != null && principal instanceof SecurityUser) {
				SecurityUser su = (SecurityUser) principal;
				return Optional.of(su.getUser());
			}
		}
		return Optional.empty();
	}
}
