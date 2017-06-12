package com.mmall.service;

import com.mmall.common.ServiceResponse;
import com.mmall.pojo.User;

/**
 * Created by mayilong on 2017/6/6.
 */
public interface IUserService {
    ServiceResponse<User> login(String username, String password);

    ServiceResponse<String> register(User user);

    ServiceResponse<String> checkValid(String str,String type);

    ServiceResponse<String> forgetGetQuestion(String username);

    ServiceResponse<String> checkAnswer(String username,String question,String answer);

    ServiceResponse<String> forgetRestPassword(String username,String passwordNew,String forgetToken);

    ServiceResponse<String> resetPassword(String passwordOld,String passwordNew,User user);

    ServiceResponse<User> update_Information(User user);

    ServiceResponse<User> get_Information(Integer userId);

    ServiceResponse<String> isAdmin(User user);
}
