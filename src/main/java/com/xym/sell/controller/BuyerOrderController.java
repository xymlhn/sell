package com.xym.sell.controller;

import com.xym.sell.convert.OrderForm2OrderDTOConverter;
import com.xym.sell.dto.OrderDTO;
import com.xym.sell.enums.ResultEnum;
import com.xym.sell.exception.SellException;
import com.xym.sell.form.OrderForm;
import com.xym.sell.service.BuyService;
import com.xym.sell.service.OrderService;
import com.xym.sell.utils.ResultVoUtil;
import com.xym.sell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyService buyService;

    //创建订单
    @PostMapping("/create")
    public ResultVo <Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        log.error("[创建订单] 参数不正确，orderFor11m = {}",orderForm);
                    log.error("[创建订单] 参数不正确，orderForm = {}",orderForm);
        if (bindingResult.hasErrors()){
            log.error("[创建订单] 参数不正确，orderForm = {}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetails())){
            log.error("[创建订单]】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO result = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());
        return ResultVoUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVo<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page" ,defaultValue="0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("[查询订单列表] openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest request = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid,request);

        return ResultVoUtil.success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVo<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderid") String orderid){

        OrderDTO orderDTO =  buyService.findOrderOne(openid,orderid);

        return ResultVoUtil.success(orderDTO);
    }

    //取消订单
    @GetMapping("/cancel")
    public ResultVo cancel(@RequestParam("openid") String openid,
                                     @RequestParam("orderid") String orderid){

        //TODO不安全的做法，改进
        OrderDTO orderDTO = buyService.cancelOrder(openid,orderid);

        return ResultVoUtil.success();
    }


}
