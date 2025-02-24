package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron = "0 * * * * ?")    // 每分钟执行一次
    public void processTimeoutOrder() {
        log.info("定时处理超时订单：{}", LocalDateTime.now());
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(-15);
        List<Orders> orders = orderMapper.listByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, localDateTime);
        if (orders != null && orders.size() > 0) {
            for (Orders order : orders) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("超时取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }

    @Scheduled(cron = "0 0 1 * * ?")    // 每天凌晨1点执行一次
    public void processDeliveryOrder(){
        log.info("处理一直配送的订单{}", LocalDateTime.now());
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(-60);
        List<Orders> orders = orderMapper.listByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, localDateTime);
        if (orders != null && orders.size() > 0) {
            for (Orders order : orders) {
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }
    }
}
