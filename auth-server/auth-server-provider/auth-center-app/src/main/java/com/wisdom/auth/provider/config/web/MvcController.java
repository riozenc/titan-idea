package com.wisdom.auth.provider.config.web;

import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import com.wisdom.auth.data.client.BaseClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 */
@Controller
@SessionAttributes({"authorizationRequest"})
public class MvcController {

    @Autowired
    private BaseClientService baseClientService;


//    @Autowired
//    private BaseClientService2 baseClientService2;

    /**
     * 登出回调
     * @param request
     * @param response
     */
    @RequestMapping("/backReferer")
    public void sendBack(HttpServletRequest request, HttpServletResponse response) {

        try {
            //sending back to client app
            String referer = request.getHeader("referer");
            System.out.println("===============referer="+referer+"======================");
            if (referer != null) {
                int index = referer.indexOf("?");
                if(index != -1)
                    referer = referer.substring(0, index);
                response.sendRedirect(referer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 授权页面
     * @param model
     * @return
     */
    @RequestMapping("/oauth/confirm_access")
    public ModelAndView authorizePage(Map<String, Object> model) {
        // 获取用户名
        String userName = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUsername();
        model.put("userName", userName);
        return new ModelAndView("authorize", model);
    }

    /**
     * 主页，未从客户端跳转直接登陆会显示
     * @param model
     * @return
     */
    @RequestMapping("/")
    public ModelAndView indexPage(Map<String, Object> model) {
        // 获取用户名
        String userName = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUsername();
        model.put("userName", userName);
        // 获取全部客户端应用
        //多个客户端 TODO ?
        ResponseData responseData=baseClientService.getAllClient();
        if(null!=responseData) {
            System.out.println(responseData.getCode()+"====baseClientService====="+((List)responseData.getData()).size());
        }else {
//            responseData = baseClientService2.getAllClient();
//            System.out.println("====baseClientService2=====");
        }

        if(ResponseCode.SUCCESS.getCode().equals(responseData.getCode()) && responseData.getData() != null) {
            System.out.println("====baseClientService.getAllClient()=====success");
            model.put("client",responseData.getData());
        } else {
            model.put("client",new ArrayList<>());
        }
        return new ModelAndView("index", model);
    }
}
