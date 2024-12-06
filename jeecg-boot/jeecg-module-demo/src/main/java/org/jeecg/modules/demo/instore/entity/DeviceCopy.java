package org.jeecg.modules.demo.instore.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 设备子表
 * @Author: jeecg-boot
 * @Date:   2024-11-29
 * @Version: V1.0
 */
@ApiModel(value="device_copy对象", description="设备子表")
@Data
@TableName("device_copy")
public class DeviceCopy implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
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
	/**设备名称*/
	@Excel(name = "设备名称", width = 15)
    @ApiModelProperty(value = "设备名称")
    private java.lang.String deviceName;
	/**设备类型*/
	@Excel(name = "设备类型", width = 15, dictTable = "device_type", dicText = "devicetype_name", dicCode = "id")
    @ApiModelProperty(value = "设备类型")
    private java.lang.String deviceType;
	/**制造商*/
	@Excel(name = "制造商", width = 15)
    @ApiModelProperty(value = "制造商")
    private java.lang.String manufacturer;
	/**设备型号*/
	@Excel(name = "设备型号", width = 15)
    @ApiModelProperty(value = "设备型号")
    private java.lang.String model;
	/**固件版本*/
	@Excel(name = "固件版本", width = 15)
    @ApiModelProperty(value = "固件版本")
    private java.lang.String firmwareVersion;
	/**设备位置*/
    @Excel(name = "设备位置", width = 15, dictTable = "sys_category", dicText = "name", dicCode = "id")
    @ApiModelProperty(value = "设备位置")
    private java.lang.String location;
	/**设备状态*/
	@Excel(name = "设备状态", width = 15, dicCode = "device_status")
    @ApiModelProperty(value = "设备状态")
    private java.lang.String statusEnum;
	/**最后活动时间*/
	@Excel(name = "最后活动时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后活动时间")
    private java.util.Date lastActive;
	/**主表外键*/
    @ApiModelProperty(value = "主表外键")
    private java.lang.String mainId;
	/**性别*/
	@Excel(name = "性别", width = 15, dicCode = "sex")
    @ApiModelProperty(value = "性别")
    private java.lang.String sex;
	/**pop测试*/
	@Excel(name = "pop测试", width = 15)
    @ApiModelProperty(value = "pop测试")
    private java.lang.String tet;
}
