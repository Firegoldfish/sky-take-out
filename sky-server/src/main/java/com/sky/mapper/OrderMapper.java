package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    void insert(Orders orders);

    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    void update(Orders orders);

    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String outTradeNo);

    @Update("update orders set status = #{orderStatus}, pay_status = #{orderPaidStatus}, checkout_time = #{check_out_time} where id = #{id}")
    void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime check_out_time, Long id);

    @Select("select count(id) from orders where status = #{StatusNumber}")
    Integer countByStatus(Integer StatusNumber);

    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> listByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);


    Double sumByMap(LocalDateTime beginTime, LocalDateTime endTime);
}
