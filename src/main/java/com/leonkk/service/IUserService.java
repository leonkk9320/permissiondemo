package com.leonkk.service;

import com.github.pagehelper.PageInfo;
import com.leonkk.po.User;

import java.util.List;

public interface IUserService {
    /**
     * 分页查询用户信息
     */
    PageInfo<User> findUserAll(int page,int pageSize,User user);

    /**
     * 用户添加功能
     */
    void addUser(User user);

    /**
     * 删除用户信息
     */
    void deleteUserInfoByIds(List<Integer> ids);

    /**
     * 根据id查询用户信息
     */
    User queryUserById(Integer id);

    //修改提交
    void updateUserSubmit(User user);

    User queryUserInfoByNameAndPwd(String username,String password);

}
