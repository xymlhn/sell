package com.xym.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xym.sell.DO.OrderDetail;
import com.xym.sell.enums.OrderStatusEnum;
import com.xym.sell.enums.PayStatusEnum;
import com.xym.sell.utils.EnumUtil;
import com.xym.sell.utils.serializer.Date2LongSerialzer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus;

    private Integer payStatus;

    @JsonSerialize(using = Date2LongSerialzer.class)
    private Date createTime;
    @JsonSerialize(using = Date2LongSerialzer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetails = new ArrayList<>();

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
