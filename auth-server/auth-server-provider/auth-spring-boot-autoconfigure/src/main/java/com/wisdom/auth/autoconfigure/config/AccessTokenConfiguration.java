package com.wisdom.auth.autoconfigure.config;

import com.wisdom.auth.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

@Configuration
public class AccessTokenConfiguration {

    @Value("${security.oauth2.resource.jwt.key-uri}")
    private String key_uri;
    public String getTokenkey(){
        try {
            String publicKey = "";
            URL url = new URL(key_uri);
            InputStream in =url.openStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader bufr = new BufferedReader(isr);
            String str;
            while ((str = bufr.readLine()) != null) {
                publicKey+=str;
            }
            Map map = JsonUtils.serializable(publicKey, Map.class);
            publicKey = map.get("value").toString();
            bufr.close();
            isr.close();
            in.close();
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public String extract(String token) {
        String authHeaderValue = token.substring("Bearer".length()).trim();
        int commaIndex = authHeaderValue.indexOf(44);
        if (commaIndex > 0) {
            authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }
            String publicKey = this.getTokenkey();
            System.out.println(publicKey);
//                    String publicKey = "-----BEGIN PUBLIC KEY-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnGp/Q5lh0P8nPL21oMMrt2RrkT9AW5jgYwLfSUnJVc9G6uR3cXRRDCjHqWU5WYwivcF180A6CWp/ireQFFBNowgc5XaA0kPpzEtgsA5YsNX7iSnUibB004iBTfU9hZ2Rbsc8cWqynT0RyN4TP1RYVSeVKvMQk4GT1r7JCEC+TNu1ELmbNwMQyzKjsfBXyIOCFU/E94ktvsTZUHF4Oq44DBylCDsS1k7/sfZC2G5EU7Oz0mhG8+Uz6MSEQHtoIi6mc8u64Rwi3Z3tscuWG2ShtsUFuNSAFNkY7LkLn+/hxLCu2bNISMaESa8dG22CIMuIeRLVcAmEWEWH5EEforTg+QIDAQAB\n-----END PUBLIC KEY-----";
            RsaVerifier verifier = new RsaVerifier(publicKey);
//            String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2luZm8iOnsiaWQiOjEsInVzZXJJZCI6InN5c2FkbWluIiwidXNlck5hbWUiOiLns7vnu5_nrqHnkIblkZgiLCJwYXNzd29yZCI6bnVsbCwicGhvbmUiOm51bGwsInNleCI6bnVsbCwic3RhdHVzIjoxLCJtYWlsQWRkcmVzcyI6bnVsbCwiaW1hZ2VVcmwiOm51bGwsInJlbWFyayI6bnVsbCwiY3JlYXRlRGF0ZSI6bnVsbCwidXBkYXRlRGF0ZSI6bnVsbH0sInVzZXJfbmFtZSI6Iuezu-e7n-euoeeQhuWRmCIsInNjb3BlIjpbInVzZXIiXSwiZXhwIjoxNTQ5MjEwODc3LCJhdXRob3JpdGllcyI6WyIxIl0sImp0aSI6IjBmYmFlNjE3LTg0N2EtNDA4Yi05OTU1LTY5NzJiNWI5MzY5OSIsImNsaWVudF9pZCI6InRlc3QifQ.mDj7_sTzThWEunQ962Y0GXbpYpAQFMk5WrNaqqh7GPjuhh7Tr5_LytFsTr5xT01KIW4mXn3nbIfuUP-W9xloQq1YFAgvwhM4b4Gj-QsWM_B8XTa-VE3uBB7Y3txYWCZrYodD8qYWWtZg0uZ7IZxVmZlwFIqTjB-hYNNkC5IPDqBwlUzXWZpin-RiCNcPB_Vn32cCNrLC_oFo7hwi6_A6lVljqF0m09QJfxlMkHrKIwku-O-HRq3zuwKY44jfQRW28Lv6Bpdpxx10OJsudT5krZ0JrWV3RFPS6LNLTDohzzdRuFmAesS_7Mnbh9dRRx0NJdaqJGzCz3o6HVhPRxcq5w";
            Jwt jwt = JwtHelper.decodeAndVerify(authHeaderValue, verifier);
            String content = jwt.getClaims();
//        Map map = JsonUtils.serializable(content, Map.class);
//        String json = JsonUtils.deserializer(map.get("user_info"));
//        UserInfo userInfo = JsonUtils.serializable(json, UserInfo.class);
            return content;
//        } else {
//            return null;
//        }
    }
    protected String extractToken(HttpServletRequest request) {
        String token = this.extractHeaderToken(request);
        if (token == null) {
//            logger.debug("Token not found in headers. Trying request parameters.");
            token = request.getParameter("access_token");
            if (token == null) {
//                logger.debug("Token not found in request parameters.  Not an OAuth2 request.");
            } else {
                request.setAttribute("ACCESS_TOKEN_TYPE", "Bearer");
            }
        }

        return token;
    }
    protected String extractHeaderToken(HttpServletRequest request) {
        Enumeration headers = request.getHeaders("Authorization");

        String value;
        do {
            if (!headers.hasMoreElements()) {
                return null;
            }

            value = (String)headers.nextElement();
        } while(!value.toLowerCase().startsWith("Bearer".toLowerCase()));

        String authHeaderValue = value.substring("Bearer".length()).trim();
        request.setAttribute("ACCESS_TOKEN_TYPE", value.substring(0, "Bearer".length()).trim());
        int commaIndex = authHeaderValue.indexOf(44);
        if (commaIndex > 0) {
            authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }

        return authHeaderValue;
    }
}
