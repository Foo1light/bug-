package org.jeecg.modules.demo.bugreport.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
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
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: bug大厅
 * @Author: jeecg-boot
 * @Date:   2024-12-04
 * @Version: V1.0
 */
@Data
@TableName("bug_report")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="bug_report对象", description="bug大厅")
public class BugReport implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**发现者*/
    @ApiModelProperty(value = "发现者")
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
	/**bug简述*/
	@Excel(name = "bug简述", width = 15)
    @ApiModelProperty(value = "bug简述")
    private java.lang.String bugSummary;
	/**是否修复*/
	@Excel(name = "是否修复", width = 15, dicCode = "isfix")
	@Dict(dicCode = "isfix")
    @ApiModelProperty(value = "是否修复")
    private java.lang.String bugIsfix;
	/**复现情况*/
	@Excel(name = "复现情况", width = 15, dicCode = "clicker")
	@Dict(dicCode = "clicker")
    @ApiModelProperty(value = "复现情况")
    private java.lang.String bugTricker;
	/**bug负责人*/
	@Excel(name = "bug负责人", width = 15, dicCode = "peo")
	@Dict(dicCode = "peo")
    @ApiModelProperty(value = "bug负责人")
    private java.lang.String bugCharger;
	/**优先级*/
	@Excel(name = "优先级", width = 15, dicCode = "lineup")
	@Dict(dicCode = "lineup")
    @ApiModelProperty(value = "优先级")
    private java.lang.String bugLineup;
	/**bug详情*/
	@Excel(name = "bug详情", width = 15)
    @ApiModelProperty(value = "bug详情")
    private java.lang.String bugDetail;
	/**bug修复详情*/
	@Excel(name = "bug修复详情", width = 15)
    @ApiModelProperty(value = "bug修复详情")
    private java.lang.String bugFixdetail;
}
