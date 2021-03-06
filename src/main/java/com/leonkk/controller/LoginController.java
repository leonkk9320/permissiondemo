package com.leonkk.controller;


import com.leonkk.codeutil.IVerifyCodeGen;
import com.leonkk.codeutil.SimpleCharVerifyCodeGenImpl;
import com.leonkk.codeutil.VerifyCode;
import com.leonkk.po.User;
import com.leonkk.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * 登录 退出相关
 */
@Controller
public class LoginController {
    @Autowired
    private IUserService userService;

    /**
     * 获取验证码方法
     * @param request
     * @param response
     */
    @RequestMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
        try {
            //设置长宽
            VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
            String code = verifyCode.getCode();
//            LOGGER.info(code);
            //将VerifyCode绑定session
            request.getSession().setAttribute("VerifyCode", code);
            //设置响应头
            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
//            LOGGER.info("", e);
            System.out.println("异常处理");
        }
    }


    /**
     * 登录验证
     */
    @RequestMapping("/loginIn")
    public String loginIndex(HttpServletRequest request, Model model){
        //获取用户
        String username=request.getParameter("username");
        //获取密码
        String password=request.getParameter("password");
        //验证码
        String code=request.getParameter("code");

        //验证登录是否超时
        HttpSession session=request.getSession();
        if(session==null){
            model.addAttribute("msg","session 超时了");
            return "login";
        }
        //获取真正的验证码
        String trueCode= (String) session.getAttribute("VerifyCode");
        if(!trueCode.toLowerCase().equals(code.toLowerCase())){//不区分大小写判断输入的验证码和真实的验证码是否一致
            model.addAttribute("msg","验证码不正确，请重新输入...");
            return "login";
        }

        //判断用户名和密码是否正确
         User user=userService.queryUserInfoByNameAndPwd(username,password);
         if(user==null){//如果该用户存在 登录到主页
             model.addAttribute("msg","用户名密码不正确");
             return "login";
         }
        session.setAttribute("user",user);
        return "redirect:/index";
    }


    /**
     * 退出功能
     */
    @RequestMapping("/logOut")
    public String logOut(HttpServletRequest request){
        HttpSession session=request.getSession();
        session.invalidate();
        return "redirect:/login";
    }


    /**
     * 登录页面的转发
     */
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    /**
     *主页
     */
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

}
