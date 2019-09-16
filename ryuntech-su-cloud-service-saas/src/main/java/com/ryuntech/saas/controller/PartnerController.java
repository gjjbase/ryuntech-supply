package com.ryuntech.saas.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;
import com.ryuntech.saas.api.helper.SecurityUtils;
import com.ryuntech.saas.api.helper.constant.PartnerConstants;
import com.ryuntech.saas.api.helper.constant.WeChatConstant;
import com.ryuntech.saas.api.model.Partner;
import com.ryuntech.saas.api.model.WeChatSession;
import com.ryuntech.saas.api.service.IPartnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;

import static com.ryuntech.common.constant.enums.CommonEnums.PARAM_ERROR;
import static com.ryuntech.common.constant.enums.CommonEnums.PARAM_PARSE_ERROR;

/**
 * <p>
 *  合伙人信息前端控制器
 * </p>
 *
 * @author antu
 * @since 2019-08-15
 */

@Slf4j
@RestController
@RequestMapping("/partner")
@Api(value = "PartnerController", tags = {"合伙人管理接口"})
public class PartnerController extends ModuleBaseController {
    @Autowired
    private IPartnerService iPartnerService;

    @Autowired
    RedisTemplate redisTemplate;



    /**
     * 分页查询列表数据，条件查询
     *
     * @param partner
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "分页、条件查询用户列表信息")
    @ApiImplicitParam(name = "partner", value = "查询条件", required = true, dataType = "Partner", paramType = "body")
    public Result<IPage<Partner>> list(Partner partner, QueryPage queryPage) {
        return iPartnerService.pageList(partner,queryPage);

    }

    /**
     * 根据ID查询用户信息
     *
     * @param partnerId
     * @return
     */
    @GetMapping("/{partnerId}")
    @ApiOperation(value = "查询详细融资客户信息", notes = "partnerId存在")
    @ApiImplicitParam(name = "partnerId", value = "用户编号", required = true, dataType = "String")
    public Result<Partner> findById(@PathVariable String partnerId) {
        if (StringUtils.isBlank(partnerId)) {
            return new Result<>();
        } else {
            return new Result<>(iPartnerService.getById(partnerId));
        }
    }

    /**
     * 更新用户信息
     *
     * @param partner
     * @return
     */
    @PostMapping("/edit")
    @ApiOperation(value = "更新合伙人信息")
    @ApiImplicitParam(name = "partner", value = "更新合伙人信息", required = true, dataType = "Partner", paramType = "body")
    public Result edit(@RequestBody Partner partner) {
        iPartnerService.saveOrUpdate(partner);
        return new Result();
    }
    /**
     * 根据ID更新融资客户信息
     *
     * @param partnerId
     * @return
     */
    @GetMapping("/updateById/{partnerId}")
    @ApiOperation(value = "更新融资客户信息", notes = "partnerId存在")
    @ApiImplicitParam(name = "partnerId", value = "用户编号", required = true, dataType = "String")
    public Result  updateById(@PathVariable String partnerId) {
        if (StringUtils.isBlank(partnerId)) {
            return new Result();
        } else {
            Partner partner =new Partner();
            partner.setPartnerId(partnerId);
            String username = SecurityUtils.getUsername();
            partner.setReviewerName(username);
            partner.setReviewerId("");
            partner.setStatus(PartnerConstants.AUTHORIZED);
            return new Result(iPartnerService.updateById(partner),"更新成功");
        }
    }
    /**
     * 根据ID查询用户信息
     *
     * @param openId
     * @return
     */
    @GetMapping("/outFindByOpenId")
    @ApiOperation(value = "查询详细融资客户信息", notes = "openId存在")
    @ApiImplicitParam(name = "openId", value = "用户编号", required = true, dataType = "String")
    public Result<Partner> findByOpenId(String openId) {
        if (StringUtils.isBlank(openId)) {
            return new Result<>();
        } else {
            return new Result<>(iPartnerService.findByOpenId(openId));
        }
    }

    /**
     * 注册
     *
     * @param partner
     * @return
     */
    @PostMapping("/outRegister")
    @ApiOperation(value = "用户在注册")
    @ApiImplicitParam(name = "partner", value = "用户实体信息", required = true, dataType = "Partner", paramType = "body")
    public Result register(@RequestBody Partner partner) {
        iPartnerService.register(partner);
        return new Result();
    }
      /**
     * 用户更新
     *
     * @param partner
     * @return
     */
    @PostMapping("/outUpdateUser")
    @ApiOperation(value = "用户更新")
    @ApiImplicitParam(name = "partner", value = "用户用户更新", required = true, dataType = "Partner", paramType = "body")
    public Result updateUser(@RequestBody Partner partner) {
        //校验用户信息
        if (StringUtils.isBlank(partner.getRegisterMobile())) {
            return new Result(PARAM_ERROR);
        }
        boolean b = iPartnerService.saveOrUpdate(partner);
        if (b) {
            partner = iPartnerService.findByPartner(partner);
            return new Result(partner);
        } else {
            return new Result();
        }
    }
    /**
     * 用户更新
     *
     * @param partner
     * @return
     */
    @PostMapping("/outRegisterUpdateUser")
    @ApiOperation(value = "注册用户更新")
    @ApiImplicitParam(name = "partner", value = "注册用户用户更新", required = true, dataType = "Partner", paramType = "body")
    public Result registerUpdateUser(@RequestBody Partner partner) {
        //校验用户信息
        Object value =   redisTemplate.opsForValue().get(partner.getRegisterMobile() + "ryun_code");
        if (value!=null&&value.toString().equals(partner.getCodeValue())){
            if (StringUtils.isBlank(partner.getRegisterMobile())){
                return  new Result(PARAM_ERROR);
            }
            boolean b = iPartnerService.saveOrUpdate(partner);
            if (b){
                partner=iPartnerService.findByPartner(partner);
                return new Result(partner);
            }else {
                return new Result();
            }
        }else {
            return  new Result(PARAM_PARSE_ERROR);
        }

    }


    /**
     * 小程序获取openid
     *
     * @param partner
     * @return
     */
    @PostMapping("/outAuth")
    @ApiOperation(value = "用户注册")
    @ApiImplicitParam(name = "code", value = "小程序登录的code", required = true, dataType = "String", paramType = "body")
    public Result auth(
            @RequestBody Partner partner)

     {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + WeChatConstant.APPID+
                "&secret="+WeChatConstant.APPSECRET+
                "&js_code="+ partner.getCodeValue() +
                "&grant_type=authorization_code";
        //发送post请求读取调用微信接口获取openid用户唯一标识
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        if(responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK)
        {
            String sessionData = responseEntity.getBody();
            Gson gson = new Gson();
            //解析从微信服务器获得的openid和session_key;
            WeChatSession weChatSession = gson.fromJson(sessionData,WeChatSession.class);
            //获取用户的唯一标识
            String openid = weChatSession.getOpenid();
            log.info("openid",openid);
            //获取会话秘钥
            String session_key = weChatSession.getSession_key();
            log.info("session_key",session_key);
            partner.setOpenId(openid);
            //判断openid是否存在用户
            Partner byOpenId = iPartnerService.findByPartner(new Partner().setOpenId(openid));
                if (byOpenId==null)  byOpenId=iPartnerService.register(partner);
            return new Result(byOpenId);
        }
        return new Result();
    }


    public static JSONObject getUserInfo(String encryptedData,String sessionKey,String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init( Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSON.parseObject(result);
            }
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
        } catch (InvalidParameterSpecException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
        } catch (InvalidAlgorithmParameterException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchProviderException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 删除合伙人信息
     *
     * @param partnerId
     * @return
     */
    @DeleteMapping("/{partnerId}")
    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(name = "partnerId", value = "用户编号", required = true, dataType = "String")
    public Result delete(@PathVariable String partnerId) {
        iPartnerService.removeById(partnerId);
        return new Result();
    }
}
