package com.xym.sell.service.impl;

import com.xym.sell.convert.OrderMaster2OrderDtoConverter;
import com.xym.sell.data.OrderDetail;
import com.xym.sell.data.OrderMaster;
import com.xym.sell.data.ProductInfo;
import com.xym.sell.dto.CartDTO;
import com.xym.sell.dto.OrderDTO;
import com.xym.sell.enums.OrderStatusEnum;
import com.xym.sell.enums.PayStatusEnum;
import com.xym.sell.enums.ResultEnum;
import com.xym.sell.exception.SellException;
import com.xym.sell.repository.OrderDetailRepository;
import com.xym.sell.repository.OrderMasterRepository;
import com.xym.sell.service.OrderService;
import com.xym.sell.service.ProductInfoService;
import com.xym.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //查询商品
        for (OrderDetail orderDetail : orderDTO.getOrderDetails()) {
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if (productInfo == null){
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        //写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //扣库存
        List<CartDTO> cartDTOS = orderDTO.getOrderDetails().stream().map(e->
                new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());

        productInfoService.decreaseStock(cartDTOS);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetails)){
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetails(orderDetails);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);

        List<OrderDTO> orderDTOS = OrderMaster2OrderDtoConverter.convert(orderMasterPage.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOS,pageable,
                orderMasterPage.getTotalElements());

        return orderDTOPage;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

        List<OrderDTO> orderDTOS = OrderMaster2OrderDtoConverter.convert(orderMasterPage.getContent());

        return new PageImpl<>(orderDTOS,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();


        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[取消订单] 订单状态不正确，orderId = {},orderStatus={}",
                    orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster orderMasterUpdate = orderMasterRepository.save(orderMaster);
        if (orderMasterUpdate == null){
            log.error("[取消订单] 更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetails())){
            log.error("[取消订单] 订单中无商品详情 orderDTO = {}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_ERROR);
        }
        List<CartDTO> cartDTOS = orderDTO.getOrderDetails().stream()
                .map(e->new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOS);

        //如果已支付，需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO

        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[完结订单] 订单状态不正确，orderId = {},orderStatus={}",
                    orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster orderMasterUpdate = orderMasterRepository.save(orderMaster);
        if (orderMasterUpdate == null){
            log.error("[完结单] 更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[订单支付] 订单状态不正确，orderId = {},orderStatus={}",
                    orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("[订单支付] 订单支付状态不正确 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster orderMasterUpdate = orderMasterRepository.save(orderMaster);
        if (orderMasterUpdate == null){
            log.error("[订单支付] 更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }
}
