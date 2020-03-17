package com.alan.community.provider;

import com.alan.community.dto.AccessTokenDTO;
import com.alan.community.dto.GiteeUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


/**
 * @author alan
 * @date 2020/3/17 10:41
 */
@Component
public class GiteeProvider {
    /**
     * @author alan
     * @date 2020/3/17 12:05
     * @param
     * @return
     * 获取token
     * @throws
     * @since
    */
    public  String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO),mediaType);
            Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            JSONObject jsonObject = JSONObject.parseObject(string);
            //返回token
            return jsonObject.getString("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @author alan
     * @date 2020/3/17 12:07
     * @param accessToken
     * @return giteeuser
     * @throws
     * @since
    */
    public GiteeUser getUser( String accessToken){

        OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://gitee.com/api/v5/user?access_token="+accessToken)
                    .build();
        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //把string的json解析称java object
            GiteeUser giteeUser = JSON.parseObject(string, GiteeUser.class);
            System.out.println(giteeUser.getName());
            return giteeUser;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
