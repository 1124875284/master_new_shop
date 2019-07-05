package com.hzq.util;

import com.hzq.mappers.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class AuthenticationInfoUtil {


    public static User getAuthenticationInfo(){
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }
    //redis保存用户认证信息
    public static com.hzq.domain.User getUser(UserMapper userMapper, StringRedisTemplate stringRedisTemplate){
        User authenticationInfo = AuthenticationInfoUtil.getAuthenticationInfo();
        String userJson = stringRedisTemplate.opsForValue().get(authenticationInfo.getUsername());
        com.hzq.domain.User user;
        if (!StringUtils.isBlank(userJson)){
            user= JsonSerializableUtil.string2Obj(userJson, com.hzq.domain.User.class);

        }else{
            user=userMapper.selectByNickname(authenticationInfo.getUsername());
        }
        return user;
    }

}
