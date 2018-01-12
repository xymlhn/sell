package com.xym.sell.repository;

import com.xym.sell.DO.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {
    /**
     * 根据openid查找用户
     * @param openid
     * @return
     */
    SellerInfo findByOpenid(String openid);
}
