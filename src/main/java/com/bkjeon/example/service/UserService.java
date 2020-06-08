package com.bkjeon.example.service;

import com.bkjeon.example.domain.user.UserPrincipal;
import com.bkjeon.example.entity.user.Role;
import com.bkjeon.example.entity.user.User;
import com.bkjeon.example.entity.user.UserRole;
import com.bkjeon.example.mapper.user.RoleMapper;
import com.bkjeon.example.mapper.user.UserMapper;
import com.bkjeon.example.mapper.user.UserRoleMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserService implements UserDetailsService {
	final String className = this.getClass().getSimpleName();

	@Autowired
	private UserMapper userMapper;

	@Autowired
    private RoleMapper roleMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User findUserByLoginId(String loginId) {
		return userMapper.findUserByLoginId(loginId);
	}

	public void saveUser(User user) {
		log.info("MCI > " + className + " -> saveUser()");
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        userMapper.setUserInfo(user);

        Role role = roleMapper.getRoleInfo("ADMIN");

		UserRole userRole = new UserRole();
		userRole.setRoleId(role.getId());
		userRole.setUserId(user.getId());

		userRoleMapper.setUserRoleInfo(userRole);
		log.info("MCO > " + className + " -> saveUser()");
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("MCI > " + className + " -> loadUserByUsername()");
		User user = userMapper.findUserByLoginId(username);
		log.info("MCO > " + className + " -> loadUserByUsername()");
		return new UserPrincipal(user);
	}

}
