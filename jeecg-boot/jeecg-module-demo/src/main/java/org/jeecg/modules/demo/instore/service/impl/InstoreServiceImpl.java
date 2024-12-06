package org.jeecg.modules.demo.instore.service.impl;

import org.jeecg.modules.demo.instore.entity.Instore;
import org.jeecg.modules.demo.instore.entity.DeviceCopy;
import org.jeecg.modules.demo.instore.mapper.DeviceCopyMapper;
import org.jeecg.modules.demo.instore.mapper.InstoreMapper;
import org.jeecg.modules.demo.instore.service.IInstoreService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 入库
 * @Author: jeecg-boot
 * @Date:   2024-11-29
 * @Version: V1.0
 */
@Service
public class InstoreServiceImpl extends ServiceImpl<InstoreMapper, Instore> implements IInstoreService {

	@Autowired
	private InstoreMapper instoreMapper;
	@Autowired
	private DeviceCopyMapper deviceCopyMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(Instore instore, List<DeviceCopy> deviceCopyList) {
		instoreMapper.insert(instore);
		if(deviceCopyList!=null && deviceCopyList.size()>0) {
			for(DeviceCopy entity:deviceCopyList) {
				//外键设置
				entity.setMainId(instore.getId());
				deviceCopyMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(Instore instore,List<DeviceCopy> deviceCopyList) {
		instoreMapper.updateById(instore);
		
		//1.先删除子表数据
		deviceCopyMapper.deleteByMainId(instore.getId());
		
		//2.子表数据重新插入
		if(deviceCopyList!=null && deviceCopyList.size()>0) {
			for(DeviceCopy entity:deviceCopyList) {
				//外键设置
				entity.setMainId(instore.getId());
				deviceCopyMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		deviceCopyMapper.deleteByMainId(id);
		instoreMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			deviceCopyMapper.deleteByMainId(id.toString());
			instoreMapper.deleteById(id);
		}
	}
	
}
