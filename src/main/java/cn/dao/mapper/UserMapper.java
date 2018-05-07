package cn.dao.mapper;

import cn.pojo.User;

import java.util.List;

public interface UserMapper {
    public void add(User user);

    public List<User> list();
}
