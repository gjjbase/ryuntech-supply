package com.ryuntech.admin.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.ryuntech.admin.api.entity.Order;
import com.ryuntech.admin.api.entity.Partner;
import com.ryuntech.admin.api.entity.PaymentResult;
import com.ryuntech.admin.biz.mapper.OrderMapper;
import com.ryuntech.admin.biz.mapper.PartnerMapper;
import com.ryuntech.admin.biz.mapper.PaymentResultMapper;
import com.ryuntech.admin.biz.service.IPartnerService;
import com.ryuntech.common.constant.PartnerConstants;
import com.ryuntech.common.constant.PayResultConstant;
import com.ryuntech.common.constant.SysConstant;
import com.ryuntech.common.utils.QRCodeUtil;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.RandomUtil;
import com.ryuntech.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import static com.ryuntech.admin.api.utils.generator.UniqueIdGenerator.generateId;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author antu
 * @since 2019-08-15
 */
@Slf4j
@Service
public class PartnerServiceImpl extends BaseServiceImpl<PartnerMapper, Partner> implements IPartnerService {

    @Autowired
    PartnerMapper partnerMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    PaymentResultMapper paymentResultMapper;
//
//    @Autowired
//    PartnerUserInfoMapper partnerUserInfoMapper;



    @Override
    public Partner findByPartner(Partner partner) {
        QueryWrapper<Partner> partnerQueryWrapper = new QueryWrapper<>();
        if (partner.getPartnerId() != null) {
            partnerQueryWrapper = partnerQueryWrapper.eq("partnerid", partner.getPartnerId());
        }
        if (partner.getOpenId() != null) {
            partnerQueryWrapper = partnerQueryWrapper.eq("open_id", partner.getOpenId());
        }
        partner = partnerMapper.selectOne(partnerQueryWrapper);
        return partner;
    }
    @Override
    public Partner findByOpenId(String openId) {
        Partner partner = partnerMapper.selectOne(new QueryWrapper<Partner>().eq("open_id", openId));
        //统计累计创收
        PaymentResult paymentResult = new PaymentResult();
        ArrayList<String> objects = new ArrayList<>();
        objects.add(PayResultConstant.SETTLE);
        paymentResult.setStatusList(objects);
        paymentResult.setOrder(new Order().setPartnerId(partner.getPartnerId()));
        BigDecimal totalFeed = paymentResultMapper.censusOrderByPartnerId(paymentResult);

        if (totalFeed==null)
            totalFeed = new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);
        partner.setTotalFeed(totalFeed.toString());

        //统计待结算金额
        objects = new ArrayList<>();
        objects.add(PayResultConstant.SETTLEING);
        objects.add(PayResultConstant.SETTLEABLE);
        paymentResult.setStatusList(objects);
        paymentResult.setOrder(new Order().setPartnerId(partner.getPartnerId()));
        BigDecimal totalLending = paymentResultMapper.censusOrderByPartnerId(paymentResult);
        if (totalLending==null)
            totalLending = new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);
        partner.setTotalLending(totalLending.toString());


        //待结算
        objects = new ArrayList<>();
        objects.add(PayResultConstant.SETTLEING);
        paymentResult.setStatusList(objects);
        paymentResult.setOrder(new Order().setPartnerId(partner.getPartnerId()));
        Integer totalNum = paymentResultMapper.censusOrderCountByPartnerId(paymentResult);
        partner.setTotalNum(totalNum);

        //统计订单量
        Integer totalOrderNum = orderMapper.selectCount(new QueryWrapper<Order>().eq("partner_id", partner.getPartnerId()));
        partner.setTotalOrderNum(totalOrderNum);

        return partner;
    }


    @Override
    public Result<IPage<Partner>> pageList(Partner partner, QueryPage queryPage) {
        Page<Partner> page = new Page<>(queryPage.getPageCode(), queryPage.getPageSize());
        if (partner.getPartnerId()!=null)
            queryWrapper.eq("partnerid", partner.getPartnerId());
        return super.pageList(queryWrapper,page);
    }

    @Override
    public Partner register(Partner partner) {
        //插入合伙人信息
        String partnerId = generateId()+"";
        partner.setCreateTime(new Date());
        partner.setPartnerId(partnerId);
        partner.setStatus(PartnerConstants.UNAUTHORIZED);
        Boolean isExits=true;
        while (isExits){
            //生成推荐码
            String numberCode = RandomUtil.createNumberCode(6);
            Partner recommend = partnerMapper.selectOne(new QueryWrapper<Partner>().eq("recommend", numberCode));
            //查询是否相同的推荐码生成
            if (recommend==null){
                isExits=false;
                partner.setRecommend(numberCode);
                log.info("numberCode"+numberCode);
                //生成验证码图片
//                referralCode
                String url = SysConstant.DEBUG_URL+":"+SysConstant.DEBUG_ADMIN_WEB_PORT+SysConstant.OUT_FINANCE+"?referralCode="+numberCode;
                log.info("url"+url);
                BitMatrix qRcodeImg = QRCodeUtil.generateQRCodeStream(url);
                FileOutputStream fileOutputStream = null;
                try {
                    File filePath = new File("/data/wwwroot/default/upload/");
                    log.info("文件的保存路径：" + filePath.getAbsolutePath());
                    if (!filePath.exists() && !filePath.isDirectory()) {
                        log.info("目录不存在，创建目录:" + filePath);
                        filePath.mkdir();
                    }
                    filePath = new File(filePath, numberCode+".png");
                    fileOutputStream = new FileOutputStream(filePath);
                    MatrixToImageWriter.writeToStream(qRcodeImg, "png", fileOutputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (fileOutputStream!=null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
        partnerMapper.insert(partner);
        return partnerMapper.selectById(partnerId);
    }
}
