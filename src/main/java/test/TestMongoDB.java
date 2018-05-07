package test;

import cn.bean.User;
import cn.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ApplicationContext/applicationContext.xml"})
public class TestMongoDB {

    @Resource(name = "UserDaoImp")
    private UserDao userDao;
    /**
     * 查询测试
     *
     * @author：tuzongxun
     * @Title: monFindTest
     * @param
     * @return void
     * @date May 13, 2016 3:27:51 PM
     * @throws
     */
    @Test
    public void monFindTest() {
        List<User> userModels = userDao.findAll();
        if (userModels != null && userModels.size() > 0) {
            for (User user : userModels) {
                System.out.println(user.getUsername() + ":"
                        + user.getPassword());
            }
        }
    }

    /**
     * 插入数据测试
     *
     * @author：tuzongxun
     * @Title: monInsertTest
     * @param
     * @return void
     * @date May 13, 2016 3:27:38 PM
     * @throws
     */
    @Test
    public void monInsertTest() {
        User user = new User("test111", "123456");
        userDao.insertUser(user);
        this.monFindTest();
    }

    /**
     * 删除测试
     *
     * @author：tuzongxun
     * @Title: monRemoveTest
     * @param
     * @return void
     * @date May 13, 2016 3:28:06 PM
     * @throws
     */
    @Test
    public void monRemoveTest() {
        String userName = "test111";
        userDao.removeUser(userName);
        this.monFindTest();
    }

    /**
     * 测试修改
     *
     * @author：tuzongxun
     * @Title: monUpdateTest
     * @param
     * @return void
     * @date May 13, 2016 3:50:08 PM
     * @throws
     */
    @Test
    public void monUpdateTest() {
        User user = new User("test111", "test");
        userDao.updateUser(user);
        this.monFindTest();
    }

    /**
     * 按条件查询
     *
     * @author：tuzongxun
     * @Title: monFindForRuq
     * @param
     * @return void
     * @date May 13, 2016 4:10:53 PM
     * @throws
     */
    @Test
    public void monFindForRuq() {
        String userName = "test111";
        List<User> userModels = userDao.findForRequery(userName);
        if (userModels != null && userModels.size() > 0) {
            for (User user : userModels) {
                System.out.println(user.getUsername() + ":"
                        + user.getPassword());
            }
        }
    }

}
