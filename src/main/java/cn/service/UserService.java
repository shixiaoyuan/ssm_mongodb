package cn.service;

import cn.pojo.User;

import java.util.List;

public interface UserService {
    public boolean authenticateUser(User user);

    public List<User> list();
}
