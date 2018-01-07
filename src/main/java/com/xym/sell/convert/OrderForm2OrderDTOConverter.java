package com.xym.sell.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xym.sell.data.OrderDetail;
import com.xym.sell.dto.OrderDTO;
import com.xym.sell.enums.ResultEnum;
import com.xym.sell.exception.SellException;
import com.xym.sell.form.OrderForm;
import com.xym.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerName(orderForm.getName());

        List<OrderDetail> orderDetails = new ArrayList<>();
        try {
            orderDetails = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("[对象转换] 错误，string={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetails(orderDetails);
        return orderDTO;
    }
}
