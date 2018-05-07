package cn.service.impl;

import java.util.List;
import cn.dao.mapper.UserMapper;
import cn.pojo.User;
import cn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public boolean authenticateUser(User user) {
        List<User> lists = userMapper.list();
        System.out.println(user.getUsername() + ":" + user.getPassword() + ":" + lists + ":" + userMapper.getClass());
        for (User u : lists) {
            if (user.getUsername().equals(u.getUsername()) && user.getPassword().equals(u.getPassword())){
                return true;
            }
        }
        return false;
    }
}
