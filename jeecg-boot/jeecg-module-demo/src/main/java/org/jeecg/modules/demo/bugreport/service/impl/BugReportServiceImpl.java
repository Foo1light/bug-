package org.jeecg.modules.demo.bugreport.service.impl;

import org.jeecg.modules.demo.bugreport.entity.BugReport;
import org.jeecg.modules.demo.bugreport.mapper.BugReportMapper;
import org.jeecg.modules.demo.bugreport.service.IBugReportService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: bug大厅
 * @Author: jeecg-boot
 * @Date:   2024-12-04
 * @Version: V1.0
 */
@Service
public class BugReportServiceImpl extends ServiceImpl<BugReportMapper, BugReport> implements IBugReportService {

}
