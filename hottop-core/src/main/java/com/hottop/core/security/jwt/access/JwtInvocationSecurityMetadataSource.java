package com.hottop.core.security.jwt.access;

import com.hottop.core.model.user.Capability;
import com.hottop.core.repository.user.CapabilityRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

//
@Service
public class JwtInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CapabilityRepository capabilityRepository;

    private HashMap<Capability, Collection<ConfigAttribute>> map =null;

    //加载权限表中所有权限
    private void loadAllPermissions() {
        map = new HashMap<>();
        Collection<ConfigAttribute> configAttributeArray;
        ConfigAttribute configAttribute;
        List<Capability> capabilities = capabilityRepository.findAll();
        for(Capability p : capabilities) {
            if(!p.getState().equals("0")) continue;
            configAttributeArray = new ArrayList<>();
            configAttribute = new SecurityConfig(p.getCapabilityName());
            configAttributeArray.add(configAttribute);
            map.put(p, configAttributeArray);
        }
    }

    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，
    // 用来判定用户是否有此权限。如果不在权限表中则放行。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if(map == null) {
            //加载用户权限信息
            loadAllPermissions();
        }
        //object 中包含用户请求的request信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        RegexRequestMatcher matcher;
        String resUrl;
        Capability capability;
        String method;
        Iterator<Capability> iterator = map.keySet().iterator();
        while(iterator.hasNext()) {
            capability = iterator.next();
            if(StringUtils.isBlank(capability.getUrl())) continue;
            resUrl = capability.getUrl();
            method = capability.getMethod();
            matcher = new RegexRequestMatcher(resUrl, method);
            boolean matchesResult = matcher.matches(request);
            logger.info("权限URL:{}, method:{}, 是否和请求request匹配:{}", resUrl, method, matchesResult);
            if(matchesResult) {
                return map.get(capability);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
