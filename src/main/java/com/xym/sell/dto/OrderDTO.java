package com.xym.sell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xym.sell.data.OrderDetail;
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
}
