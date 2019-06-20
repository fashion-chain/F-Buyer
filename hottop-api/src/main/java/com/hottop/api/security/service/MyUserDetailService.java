package com.hottop.api.security.service;

import com.hottop.core.model.user.Role;
import com.hottop.core.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class MyUserDetailService implements UserDetailsService, SocialUserDetailsService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //根据用户名查找用户信息
        logger.info("登录用户名：{}", userName);
        com.hottop.core.model.user.User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //查询权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = user.getRoles();
        for (Role r : roles ) {
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        }
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return getUserDetails(userId);
    }

    //根据用户名查找用户,这里的用户名就是手机号
    private SocialUser getUserDetails(String username) {
        com.hottop.core.model.user.User user = userRepository.findByTel(username);
        List<Role> roles = user.getRoles();
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (Role r : roles) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(r.getRoleName());
            authorityList.add(simpleGrantedAuthority);
        }
        String password = user.getPassword();
        return new SocialUser(username, password,
                true, true, true, true,
                authorityList);
    }


}
