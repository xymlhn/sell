package com.xym.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0,"成功"),

    PARAM_ERROR(1,"订单支付状态不正确"),

    PRODUCT_NOT_EXIST(10,"商品不存在"),

    PRODUCT_STOCK_ERROR(20,"库存不足"),

    ORDER_NOT_EXIST(30,"商品不存在"),

    ORDER_DETAIL_NOT_EXIST(40,"商品详情不存在"),

    ORDER_STATUS_ERROR(50,"订单状态有问题"),

    ORDER_UPDATE_ERROR(60,"订单更新失败"),

    ORDER_DETAIL_ERROR(60,"订单详情为空"),

    ORDER_PAY_STATUS_ERROR(70,"订单支付状态不正确"),

    CART_EMPTY(80,"购物车为空"),

    ORDER_OWNER_ERROR(90,"该订单不属于当前用户"),

    ORDER_CANCEL_SUCCESS(100,"订单取消成功"),

    ORDER_FINISH_SUCCESS(110,"订单完结成功"),

    PRODUCT_STATUS_ERROR(120,"商品状态不正确"),

    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
