package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.vo.TurnoverReportVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "报表接口")
@RequestMapping("/admin/report")
@Slf4j
public class ReportController {

    public Result<TurnoverReportVO> turnoverStatistics() {

        return null;
    }
}
