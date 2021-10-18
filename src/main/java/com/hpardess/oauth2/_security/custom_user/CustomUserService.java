package com.hpardess.oauth2._security.custom_user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CustomUserService extends UserDetailsService {

	public CustomUser loadUserByUsername(String username);
}
