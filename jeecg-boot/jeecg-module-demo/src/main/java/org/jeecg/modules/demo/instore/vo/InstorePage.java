package org.jeecg.modules.demo.instore.vo;

import java.util.List;
import org.jeecg.modules.demo.instore.entity.Instore;
import org.jeecg.modules.demo.instore.entity.DeviceCopy;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 入库
 * @Author: jeecg-boot
 * @Date:   2024-11-29
 * @Version: V1.0
 */
@Data
@ApiModel(value="instorePage对象", description="入库")
public class InstorePage {

	/**主键*/
	@ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
	@ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**入库操作员*/
	@Excel(name = "入库操作员", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "id")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "id")
	@ApiModelProperty(value = "入库操作员")
    private java.lang.String instoreUser;
	/**设备弹窗测试*/
	@Excel(name = "设备弹窗测试", width = 15)
	@ApiModelProperty(value = "设备弹窗测试")
    private java.lang.String device;

	@ExcelCollection(name="设备子表")
	@ApiModelProperty(value = "设备子表")
	private List<DeviceCopy> deviceCopyList;

}
