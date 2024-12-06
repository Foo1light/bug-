package org.jeecg.modules.demo.instore.service.impl;

import org.jeecg.modules.demo.instore.entity.DeviceCopy;
import org.jeecg.modules.demo.instore.mapper.DeviceCopyMapper;
import org.jeecg.modules.demo.instore.service.IDeviceCopyService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 设备子表
 * @Author: jeecg-boot
 * @Date:   2024-11-29
 * @Version: V1.0
 */
@Service
public class DeviceCopyServiceImpl extends ServiceImpl<DeviceCopyMapper, DeviceCopy> implements IDeviceCopyService {
	
	@Autowired
	private DeviceCopyMapper deviceCopyMapper;
	
	@Override
	public List<DeviceCopy> selectByMainId(String mainId) {
		return deviceCopyMapper.selectByMainId(mainId);
	}
}
