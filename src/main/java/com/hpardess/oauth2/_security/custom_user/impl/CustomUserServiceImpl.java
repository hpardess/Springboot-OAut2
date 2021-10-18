package com.hpardess.oauth2._security.custom_user.impl;

import java.util.Arrays;
import java.util.Optional;

import com.hpardess.oauth2._security.custom_user.CustomUser;
import com.hpardess.oauth2._security.custom_user.CustomUserService;
import com.hpardess.oauth2.user.User;
import com.hpardess.oauth2.user.UserRepository;
import com.hpardess.oauth2.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserServiceImpl implements CustomUserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public CustomUser loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("Entry to CustomUserService: " + email);
		User user = null;
		Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            System.out.println("user: " + userOpt);
            user = userOpt.get();
        }
	
		if (user == null) {
			throw new UsernameNotFoundException("Invalid email or password.");
		}

		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
		return new CustomUser(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, Arrays.asList(authority));
	}

}
