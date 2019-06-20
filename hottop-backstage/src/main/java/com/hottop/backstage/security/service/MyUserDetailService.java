package com.hottop.backstage.security.service;

import com.hottop.backstage.bguser.model.BgUser;
import com.hottop.backstage.bguser.repository.BgUserRepository;
import com.hottop.backstage.bguser.service.BgUserService;
import com.hottop.core.model.user.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private BgUserRepository bgUserRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //根据用户名查找用户信息
        logger.info("登录用户名：{}", userName);
        BgUser bgUser = bgUserRepository.findByUsername(userName);
        if (bgUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //查询权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = bgUser.getRoles();
        for (Role r : roles ) {
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        }
        return new User(bgUser.getUsername(), bgUser.getPassword(), authorities);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return getUserDetails(userId);
    }

    //根据用户名查找用户,这里的用户名就是手机号
    private SocialUser getUserDetails(String username) {
        BgUser bgUser = bgUserRepository.findByTel(username);
        List<Role> roles = bgUser.getRoles();
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (Role r : roles) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(r.getRoleName());
            authorityList.add(simpleGrantedAuthority);
        }
        String password = bgUser.getPassword();
        return new SocialUser(username, password,
                true, true, true, true,
                authorityList);
    }


}
