package cn.dao.impl;

import cn.bean.User;
import cn.dao.UserDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("UserDaoImp")
public class UserDaoImpl extends AbstractBaseMongoTemplete implements UserDao {

    /**
     * 查询所有数据
     *
     * @author：tuzongxun
     * @Title: findAll
     * @Description: TODO
     * @param @return
     * @date May 13, 2016 3:10:29 PM
     * @throws
     */
    @Override
    public List<User> findAll() {
        // 需要设置集合对应的尸体类和相应的集合名，从而查询结果直接映射
        List<User> userList = mongoTemplate.findAll(User.class,
                "user");
        return userList;
    }

    /**
     * 新增数据
     *
     * @author：tuzongxun
     * @Title: insertUser
     * @Description: TODO
     * @param @param user
     * @date May 13, 2016 3:10:45 PM
     * @throws
     */
    @Override
    public void insertUser(User user) {
        // 设置需要插入到数据库的文档对象
        DBObject object = new BasicDBObject();
        object.put("username", user.getUsername());
        object.put("password", user.getPassword());
        mongoTemplate.insert(object, "user");
    }

    /**
     * 按条件删除数据
     *
     * @author：tuzongxun
     * @Title: removeUser
     * @Description: TODO
     * @param @param userName
     * @date May 13, 2016 3:11:01 PM
     * @throws
     */
    @Override
    public void removeUser(String userName) {
        // 设置删除条件，如果条件内容为空则删除所有
        Query query = new Query();
        Criteria criteria = new Criteria("username");
        criteria.is(userName);
        query.addCriteria(criteria);
        mongoTemplate.remove(query, "user");
    }

    /**
     * 修改数据
     *
     * @author：tuzongxun
     * @Title: updateUser
     * @Description: TODO
     * @param @param user
     * @date May 13, 2016 3:11:12 PM
     * @throws
     */
    @Override
    public void updateUser(User user) {
        // 设置修改条件
        Query query = new Query();
        Criteria criteria = new Criteria("username");
        criteria.is(user.getUsername());
        query.addCriteria(criteria);
        // 设置修改内容
        Update update = Update.update("password", user.getPassword());
        // 参数：查询条件，更改结果，集合名
        mongoTemplate.updateFirst(query, update, "user");
    }

    /**
     * 根据条件查询
     *
     * @author：tuzongxun
     * @Title: findForRequery
     * @Description: TODO
     * @param @param userName
     * @date May 13, 2016 4:08:15 PM
     * @throws
     */
    @Override
    public List<User> findForRequery(String userName) {
        Query query = new Query();
        Criteria criteria = new Criteria("username");
        criteria.is(userName);
        query.addCriteria(criteria);
        // 查询条件，集合对应的实体类，集合名
        List<User> userList = mongoTemplate.find(query, User.class,
                "user");
        return userList;
    }

}