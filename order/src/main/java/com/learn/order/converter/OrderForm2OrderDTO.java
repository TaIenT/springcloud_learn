package com.learn.order.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.learn.order.dataobject.OrderDetail;
import com.learn.order.dto.OrderDTO;
import com.learn.order.enums.ResultEnum;
import com.learn.order.exception.OrderException;
import com.learn.order.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class OrderForm2OrderDTO {

    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO= new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList =new ArrayList<>();

        Gson gson= new Gson();

        try{
            orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());

        }catch (Exception e){
            log.error("[json转换]错误,string={}",orderForm.getItems());
            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;


    }
}
