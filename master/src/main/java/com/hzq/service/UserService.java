package com.hzq.service;

import com.hzq.dto.UserDto;
import com.hzq.vo.PosterVo;
import com.hzq.vo.UserVo;
import org.springframework.validation.BindingResult;

public interface UserService {
    /**
     * 根据用户名称获取用户
     * @return
     */
    PosterVo findUserByNickname();

    /**
     * 注册用户
     * @param userDto
     * @param result
     * @return
     */
    UserVo registryUserInfo(UserDto userDto, BindingResult result);

    /**
     * 修改用户
     * @param userVo
     * @param result
     * @return
     */
    UserVo updateUserInfo(UserVo userVo, BindingResult result);

    /**
     * 重置密码
     * @param nickname
     * @param rawPassword
     * @param newPassword
     * @param validateCode
     * @return
     */
    String resetPassword(String nickname, String rawPassword, String newPassword, String validateCode);

    /**
     * 发送邮件
     * @param nickname
     * @param email
     */
    void sendEmail(String nickname, String email);
}
