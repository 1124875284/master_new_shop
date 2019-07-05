package com.hzq.controller;

import com.google.common.collect.Maps;
import com.hzq.common.ResponseResult;
import com.hzq.dto.UserDto;
import com.hzq.service.UserService;
import com.hzq.vo.PosterVo;
import com.hzq.vo.UserVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(value="用户信息",tags= {"用户信息Controller"},description = "用户信息",protocols = "http")
@Slf4j
@CrossOrigin
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/find",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseResult<PosterVo> findUserByNickName(){

        PosterVo posterVo = userService.findUserByNickname();

        if(posterVo!=null){
            return ResponseResult.createBySuccess(posterVo);
        }

        return ResponseResult.createByErrorMessage("获取用户信息失败");
    }


    @PostMapping(value = "/registry",produces = "application/json;charset=UTF-8",consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseResult registryUserInfo(@Valid @RequestBody UserDto userDto,
                                           BindingResult result){
        UserVo userVo = userService.registryUserInfo(userDto,result);
        if (null!=userVo){
            return ResponseResult.createBySuccess("用户注册成功",userVo);
        }
        return ResponseResult.createByErrorMessage("用户注册失败");
    }


    @PutMapping(value = "/update",produces = "application/json;charset=UTF-8",consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseResult updateUserInfo(@Valid @RequestBody UserVo userVo, BindingResult result){

        UserVo resultVo = userService.updateUserInfo(userVo,result);
        if (null!=resultVo){
            return ResponseResult.createBySuccess("用户信息修改成功",userVo);
        }
        return ResponseResult.createByErrorMessage("用户信息修改失败");
    }

    @PostMapping(value = "/password/reset",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseResult resetPassword(@RequestParam("nickname")String nickname, @RequestParam("rawpassword")String rawpassword
            , @RequestParam("newpassword")String newPassword, @RequestParam("code")String validateCode){
        String result = userService.resetPassword(nickname,rawpassword,newPassword,validateCode);
        return ResponseResult.createBySuccess(result);
    }



    @PostMapping("/send/mail")
    @ResponseBody
    public ResponseResult<String> sendMail(@RequestParam(value = "nickname")String nickname, @RequestParam(value = "email")String email){
        userService.sendEmail(nickname,email);
        return ResponseResult.createBySuccess("发送邮件成功");
    }

    @GetMapping(value = "/token",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseResult getUserToken(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());
        log.info(authentication.getAuthorities().toString());
        log.info(((User)authentication.getPrincipal()).getPassword());
        Map<String,Object> tokenInfo= Maps.newHashMap();
        tokenInfo.put("name",authentication.getName());
        tokenInfo.put("password",((User)authentication.getPrincipal()).getPassword());
        tokenInfo.put("authorities",authentication.getAuthorities());
        return ResponseResult.createBySuccess(tokenInfo);
    }
    @GetMapping("/hello")
    @ResponseBody
    public String a(){
        String a="a";
        String b="jlkjlkj";
        System.out.println(a+b);
        return "/user/find";
    }
}
