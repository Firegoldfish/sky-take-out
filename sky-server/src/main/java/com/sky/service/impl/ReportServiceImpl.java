package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ReportServiceImpl {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 营业额统计
     * @param begin 开始日期
     * @param end 结束日期
     */
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();

        dateList.add(begin);
        while (!begin.equals(end)) {
            dateList.add(begin.plusDays(1));
        }
        List<Double> turnoverList = new ArrayList<>();  // 用于存放每天的营业额
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap<>();
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(beginTime, endTime);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }
        return TurnoverReportVO.builder().dateList(StringUtils.join(dateList, ",")).turnoverList(StringUtils.join(turnoverList, ",")).build();
    }

    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            dateList.add(begin.plusDays(1));
        }

        List<Integer> userList = new ArrayList<>();  // 用于存放每天的用户数

        List<Integer> totalUserList = new ArrayList<>();  // 用于存放每天的订单数

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap<>();
            map.put("end", endTime);
            Integer totalUser = userMapper.countByMap(map);    // 查询总用户数
            map.put("begin", beginTime);
            Integer newUser = userMapper.countByMap(map);     // 查询新增用户数

            totalUserList.add(totalUser);
            userList.add(newUser);
        }
        // 返回结果
        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .newUserList(StringUtils.join(userList, ","))
                .build();
    }
}
