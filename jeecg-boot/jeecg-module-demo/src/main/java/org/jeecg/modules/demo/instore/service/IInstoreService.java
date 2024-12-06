package org.jeecg.modules.demo.instore.service;

import org.jeecg.modules.demo.instore.entity.DeviceCopy;
import org.jeecg.modules.demo.instore.entity.Instore;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 入库
 * @Author: jeecg-boot
 * @Date:   2024-11-29
 * @Version: V1.0
 */
public interface IInstoreService extends IService<Instore> {

	/**
	 * 添加一对多
	 *
	 * @param instore
	 * @param deviceCopyList
	 */
	public void saveMain(Instore instore,List<DeviceCopy> deviceCopyList) ;
	
	/**
	 * 修改一对多
	 *
   * @param instore
   * @param deviceCopyList
	 */
	public void updateMain(Instore instore,List<DeviceCopy> deviceCopyList);
	
	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
