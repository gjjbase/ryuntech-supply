package com.ryuntech.admin.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryuntech.admin.api.entity.Partner;
import com.ryuntech.admin.api.entity.PartnerUserInfo;
import com.ryuntech.admin.biz.mapper.PartnerMapper;
import com.ryuntech.admin.biz.mapper.PartnerUserInfoMapper;
import com.ryuntech.admin.biz.service.IPartnerService;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.RandomUtil;
import com.ryuntech.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class PartnerServiceImpl extends BaseServiceImpl<PartnerMapper, Partner> implements IPartnerService {

    @Autowired
    PartnerMapper partnerMapper;

    @Autowired
    PartnerUserInfoMapper partnerUserInfoMapper;

    @Override
    public Partner findByOpenId(String openId) {
        Partner partner = partnerMapper.selectOne(new QueryWrapper<Partner>().eq("openId", openId));
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
    public Boolean register(Partner partner) {
        //插入合伙人信息
        //车
        String partnerId = generateId()+"";
        partner.setCreateTime(new Date());
        partner.setPartnerId(partnerId);

        //插入ryn_partner_user_info
        /*PartnerUserInfo partnerUserInfo = new PartnerUserInfo();
        String userId = generateId()+"";
        partnerUserInfo.setUserid(userId);
        //手机号
        String registerMobile = partner.getRegisterMobile();
        //姓名
        String partnerName = partner.getPartnerName();
        String nickname = partner.getNickname();
        //状态
        partnerUserInfo.setStatus(PartnerConstants.UNAUTHORIZED);
        //手机号
        partnerUserInfo.setMobile(registerMobile);
        //名称
        partnerUserInfo.setOperatorname(partnerName);
        //创建时间
        partnerUserInfo.setCreatetime(new Date());
        //昵称
        partnerUserInfo.setNickname(nickname);*/
        Boolean isExits=true;
        while (isExits){
            //生成推荐码
            String numberCode = RandomUtil.createNumberCode(6);
            PartnerUserInfo recommend = partnerUserInfoMapper.selectOne(new QueryWrapper<PartnerUserInfo>().eq("recommend", numberCode));
            //查询是否相同的推荐码生成
            if (recommend==null){
                isExits=false;
                partner.setRecommend(numberCode);
            }
        }
        partnerMapper.insert(partner);
        return null;
    }
}
