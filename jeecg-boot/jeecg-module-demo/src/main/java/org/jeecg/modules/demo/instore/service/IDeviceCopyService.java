package org.jeecg.modules.demo.instore.service;

import org.jeecg.modules.demo.instore.entity.DeviceCopy;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 设备子表
 * @Author: jeecg-boot
 * @Date:   2024-11-29
 * @Version: V1.0
 */
public interface IDeviceCopyService extends IService<DeviceCopy> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<DeviceCopy>
	 */
	public List<DeviceCopy> selectByMainId(String mainId);
}
