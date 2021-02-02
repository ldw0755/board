package com.company.service;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.company.domain.CustomUser;
import com.company.domain.MemberVO;
import com.company.mapper.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUserDetailService implements UserDetailsService {

	@Inject
	private MemberMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.warn("Load User By username "+username);
		MemberVO vo = mapper.read(username);
		return vo==null?null:new CustomUser(vo);
	}

}
