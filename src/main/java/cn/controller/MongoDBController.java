package cn.controller;

import cn.bean.User;
import cn.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/rest")
public class MongoDBController {

//    MongoDB相关的注入配置与url访问
//    @Resource(name = "UserDaoImp")
//    private UserDao userDao;
//
//    @RequestMapping("/mongo")
//    public ModelAndView read(){
//        ModelAndView mav = new ModelAndView("mongo");
//        List<User> userModels = userDao.findAll();
//        mav.addObject("users",userModels);
//        return mav;
//    }
}
