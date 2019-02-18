package com.wisdom.auth.provider.config.web;

import com.wisdom.auth.common.pojo.ResponseData;
import com.wisdom.auth.common.utils.JsonUtils;
import com.wisdom.auth.data.api.mapper.model.UserInfo;
import com.wisdom.auth.data.api.pojo.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TokenController {

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @RequestMapping("/extractToken")
    public ResponseData extract(String token) {
        if (!token.toLowerCase().startsWith("Bearer".toLowerCase())){
            return new ResponseData<>(ResponseCode.TOKEN_ERROR.getCode(), ResponseCode.TOKEN_ERROR.getMessage());
        }
        String authHeaderValue = token.substring("Bearer".length()).trim();
        int commaIndex = authHeaderValue.indexOf(44);
        if (commaIndex > 0) {
            authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }
        String publicKey = jwtAccessTokenConverter.getKey().get("value");
//        System.out.println(publicKey);
//      String publicKey = "-----BEGIN PUBLIC KEY-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnGp/Q5lh0P8nPL21oMMrt2RrkT9AW5jgYwLfSUnJVc9G6uR3cXRRDCjHqWU5WYwivcF180A6CWp/ireQFFBNowgc5XaA0kPpzEtgsA5YsNX7iSnUibB004iBTfU9hZ2Rbsc8cWqynT0RyN4TP1RYVSeVKvMQk4GT1r7JCEC+TNu1ELmbNwMQyzKjsfBXyIOCFU/E94ktvsTZUHF4Oq44DBylCDsS1k7/sfZC2G5EU7Oz0mhG8+Uz6MSEQHtoIi6mc8u64Rwi3Z3tscuWG2ShtsUFuNSAFNkY7LkLn+/hxLCu2bNISMaESa8dG22CIMuIeRLVcAmEWEWH5EEforTg+QIDAQAB\n-----END PUBLIC KEY-----";
//      String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2luZm8iOnsiaWQiOjEsInVzZXJJZCI6InN5c2FkbWluIiwidXNlck5hbWUiOiLns7vnu5_nrqHnkIblkZgiLCJwYXNzd29yZCI6bnVsbCwicGhvbmUiOm51bGwsInNleCI6bnVsbCwic3RhdHVzIjoxLCJtYWlsQWRkcmVzcyI6bnVsbCwiaW1hZ2VVcmwiOm51bGwsInJlbWFyayI6bnVsbCwiY3JlYXRlRGF0ZSI6bnVsbCwidXBkYXRlRGF0ZSI6bnVsbH0sInVzZXJfbmFtZSI6Iuezu-e7n-euoeeQhuWRmCIsInNjb3BlIjpbInVzZXIiXSwiZXhwIjoxNTQ5MjEwODc3LCJhdXRob3JpdGllcyI6WyIxIl0sImp0aSI6IjBmYmFlNjE3LTg0N2EtNDA4Yi05OTU1LTY5NzJiNWI5MzY5OSIsImNsaWVudF9pZCI6InRlc3QifQ.mDj7_sTzThWEunQ962Y0GXbpYpAQFMk5WrNaqqh7GPjuhh7Tr5_LytFsTr5xT01KIW4mXn3nbIfuUP-W9xloQq1YFAgvwhM4b4Gj-QsWM_B8XTa-VE3uBB7Y3txYWCZrYodD8qYWWtZg0uZ7IZxVmZlwFIqTjB-hYNNkC5IPDqBwlUzXWZpin-RiCNcPB_Vn32cCNrLC_oFo7hwi6_A6lVljqF0m09QJfxlMkHrKIwku-O-HRq3zuwKY44jfQRW28Lv6Bpdpxx10OJsudT5krZ0JrWV3RFPS6LNLTDohzzdRuFmAesS_7Mnbh9dRRx0NJdaqJGzCz3o6HVhPRxcq5w";
        try{
            RsaVerifier verifier = new RsaVerifier(publicKey);
            Jwt jwt = JwtHelper.decodeAndVerify(authHeaderValue, verifier);
            String content = jwt.getClaims();
            Map map = JsonUtils.serializable(content, Map.class);
            String json = JsonUtils.deserializer(map.get("user_info"));
            UserInfo userInfo = JsonUtils.serializable(json, UserInfo.class);

            return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), userInfo);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.TOKEN_ERROR.getCode(), ResponseCode.TOKEN_ERROR.getMessage());
        }

    }
}
