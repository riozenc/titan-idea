package com.wisdom.auth.provider.config.web;

import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import com.wisdom.auth.data.client.BaseClientService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

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
//    @RequestMapping("/oauth/confirm_access")
//    public void authorizePage(HttpServletRequest request, HttpServletResponse response) {
//        // 获取用户名
//        String userName = ((UserDetails) SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getPrincipal())
//                .getUsername();
//
////        model.put("userName", userName);
////        return new ModelAndView("authorize", model);
//        try {
//            //post参数
//            Map<String,Object> params = new LinkedHashMap<>();
//            params.put("user_oauth_approval", "true");
//            params.put("authorize", "Authorize");
//            params.put("scope.user", "true");
//            //开始访问
//            StringBuilder postData = new StringBuilder();
//            for (Map.Entry<String,Object> param : params.entrySet()) {
//                if (postData.length() != 0) postData.append('&');
//                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
//                postData.append('=');
//                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
//            }
//            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
//            String s = request.getRequestURL().toString().replaceFirst("confirm_access","authorize");
//            URL url = new URL(s);
//            HttpURLConnection conn = (HttpURLConnection )url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
//            conn.setReadTimeout(10000);
//            conn.setConnectTimeout(10000);
//            conn.setUseCaches(false);
//            String back="";
//            HttpSession session = request.getSession();
//
//            String  verCode=(String) session.getAttribute("verCode");
//            String  JSESSIONID=(String) session.getAttribute("JSESSIONID");
//
//            System.out.println(verCode);
//            System.out.println(JSESSIONID);
//
//            if(verCode!=null){
//                back=verCode;
//            }
//
//            if(JSESSIONID!=null){
//                if(!StringUtils.isEmpty(back)){
//                    back+=" ";
//                }
//                back+=JSESSIONID;
//            }
//
//            System.out.println(back);
//            conn.setRequestProperty("Cookie",back);
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            DataOutputStream dos=new DataOutputStream(conn.getOutputStream());
//            dos.writeBytes(postData.toString());
//            dos.flush();
//            dos.close();
//            int resultCode=conn.getResponseCode();
//            Map<String, List<String>> headers = conn.getHeaderFields();
//            Set<String> keys = headers.keySet();
//            for( String key : keys ){
//                String val = conn.getHeaderField(key);
//                System.out.println(key+"    "+val);
//            }
////            conn.getOutputStream().write(postDataBytes);
//            String loc = conn.getHeaderField("Location");
//            System.out.println("Location:"+loc);
//            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//            String readLine = "";
//            while ((readLine = in.readLine()) != null)
//            {
//                System.out.println(readLine);
//            }
//            in.close();
//            conn.disconnect();
//            response.sendRedirect(loc);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }
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
