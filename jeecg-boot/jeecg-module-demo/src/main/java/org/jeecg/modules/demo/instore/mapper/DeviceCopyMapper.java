package org.jeecg.modules.demo.instore.mapper;

import java.util.List;
import org.jeecg.modules.demo.instore.entity.DeviceCopy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 设备子表
 * @Author: jeecg-boot
 * @Date:   2024-11-29
 * @Version: V1.0
 */
public interface DeviceCopyMapper extends BaseMapper<DeviceCopy> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<DeviceCopy>
   */
	public List<DeviceCopy> selectByMainId(@Param("mainId") String mainId);
}
