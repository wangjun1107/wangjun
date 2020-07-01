package com.wangjun.modules.wechat.controller;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangjun.modules.wechat.bo.AccessToken;
import com.wangjun.modules.wechat.bo.QRCodeParameters;
import com.wangjun.modules.wechat.bo.WxCodeUnlimitedResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * <标题>
 *
 * @author wangjun
 * @date 2020-03-19 14:09
 */
@Slf4j
@RestController
@RequestMapping("api/wx")
public class WeChatController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String APP_ID = "wx4b101b63d1f6b108";

    private static final String SECRET = "792badfe83c8e9f156579834ce1fa5a6";

    /**
     * 获取微信小程序码
     *
     * @return 小程序url
     */
    @GetMapping("/access-token")
    public AccessToken getUnlimitedQRcode(QRCodeParameters param) throws IOException {
        WxCodeUnlimitedResponse res = new WxCodeUnlimitedResponse();
        String url = String.format(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", APP_ID, SECRET);

        AccessToken responseEntity = restTemplate.getForObject(url, AccessToken.class);

        assert responseEntity != null;
        if (!"7200".equals(responseEntity.getExpires_in())) {
            throw new ServerException(responseEntity.getErrmsg());
        }

        String qrurl = String.format("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s",
                responseEntity.getAccess_token());
        Map<String, Object> params = new HashMap<>();
        params.put("scene", "21342345");
        byte[] byteArray;
        ResponseEntity<byte[]> entity = restTemplate.postForEntity(qrurl, JSON.toJSONString(params), byte[].class);

        byteArray = entity.getBody();

        assert byteArray != null;
        String wxReturnStr = new String(byteArray);
        if (wxReturnStr.contains("errcode")) {
            JSONObject object = JSONObject.parseObject(wxReturnStr);
            throw new ServerException(object.getString("errmsg"));
        } else {
            res.setErrcode("0");
            res.setErrmsg("ok");
            res.setBuffer(byteArray);
        }
        return responseEntity;
    }

}
