package com.xym.sell.service;

import com.xym.sell.DO.SellerInfo;


public interface SellerService {

    /**
     * 通过openid查询卖家信息
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
