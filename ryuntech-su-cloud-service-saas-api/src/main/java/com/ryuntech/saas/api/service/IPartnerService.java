package com.ryuntech.saas.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;
import com.ryuntech.saas.api.model.Partner;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author antu
 * @since 2019-08-15
 */
public interface IPartnerService extends IBaseService<Partner> {
    /**
     * 根据openId获取合伙人数据
     * @param openId
     * @return
     */
    Partner findByOpenId(String openId);
    Partner findByPartner(Partner partner);

    Result<IPage<Partner>> pageList(Partner partner, QueryPage queryPage);

    Partner register(Partner partner);
}
