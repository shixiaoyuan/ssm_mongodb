package cn.controller;

import cn.pojo.Elec;
import cn.pojo.User;
import cn.service.UserService;
import cn.utils.HBaseUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/rest")
public class UserController {

    @Resource(name = "UserServiceImpl")
    private UserService userService;

    @RequestMapping("/user")
    public ModelAndView user(){
        ModelAndView mav = new ModelAndView("user");
        List<User> lists = userService.list();
        mav.addObject("users",lists);
        return mav;
    }

//返回获取hbase中test库中的所有内容
    @RequestMapping("/hbase")
    public ModelAndView hbase(){
        ModelAndView mav = new ModelAndView("hbase");
        try {
//            List<Elec> elecList = HBaseUtils.get("test","1");
            List<List<Elec>> elecList = HBaseUtils.scan("test");
            mav.addObject("hbase",elecList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping("/authenticate")
    public String authenticate(User user){
        if (userService.authenticateUser(user)){
            return "redirect:/show/index.html";
        }
        return "redirect:/rest/login";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
