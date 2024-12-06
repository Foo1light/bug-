package org.jeecg.modules.demo.instore.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.instore.entity.DeviceCopy;
import org.jeecg.modules.demo.instore.entity.Instore;
import org.jeecg.modules.demo.instore.vo.InstorePage;
import org.jeecg.modules.demo.instore.service.IInstoreService;
import org.jeecg.modules.demo.instore.service.IDeviceCopyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;


 /**
 * @Description: 入库
 * @Author: jeecg-boot
 * @Date:   2024-11-29
 * @Version: V1.0
 */
@Api(tags="入库")
@RestController
@RequestMapping("/instore/instore")
@Slf4j
public class InstoreController {
	@Autowired
	private IInstoreService instoreService;
	@Autowired
	private IDeviceCopyService deviceCopyService;
	
	/**
	 * 分页列表查询
	 *
	 * @param instore
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "入库-分页列表查询")
	@ApiOperation(value="入库-分页列表查询", notes="入库-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Instore>> queryPageList(Instore instore,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<Instore> queryWrapper = QueryGenerator.initQueryWrapper(instore, req.getParameterMap());
		Page<Instore> page = new Page<Instore>(pageNo, pageSize);
		IPage<Instore> pageList = instoreService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param instorePage
	 * @return
	 */
	@AutoLog(value = "入库-添加")
	@ApiOperation(value="入库-添加", notes="入库-添加")
    @RequiresPermissions("instore:instore:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody InstorePage instorePage) {
		Instore instore = new Instore();
		BeanUtils.copyProperties(instorePage, instore);
		instoreService.saveMain(instore, instorePage.getDeviceCopyList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param instorePage
	 * @return
	 */
	@AutoLog(value = "入库-编辑")
	@ApiOperation(value="入库-编辑", notes="入库-编辑")
    @RequiresPermissions("instore:instore:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody InstorePage instorePage) {
		Instore instore = new Instore();
		BeanUtils.copyProperties(instorePage, instore);
		Instore instoreEntity = instoreService.getById(instore.getId());
		if(instoreEntity==null) {
			return Result.error("未找到对应数据");
		}
		instoreService.updateMain(instore, instorePage.getDeviceCopyList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "入库-通过id删除")
	@ApiOperation(value="入库-通过id删除", notes="入库-通过id删除")
    @RequiresPermissions("instore:instore:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		instoreService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "入库-批量删除")
	@ApiOperation(value="入库-批量删除", notes="入库-批量删除")
    @RequiresPermissions("instore:instore:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.instoreService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "入库-通过id查询")
	@ApiOperation(value="入库-通过id查询", notes="入库-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Instore> queryById(@RequestParam(name="id",required=true) String id) {
		Instore instore = instoreService.getById(id);
		if(instore==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(instore);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "设备子表通过主表ID查询")
	@ApiOperation(value="设备子表主表ID查询", notes="设备子表-通主表ID查询")
	@GetMapping(value = "/queryDeviceCopyByMainId")
	public Result<List<DeviceCopy>> queryDeviceCopyListByMainId(@RequestParam(name="id",required=true) String id) {
		List<DeviceCopy> deviceCopyList = deviceCopyService.selectByMainId(id);
		return Result.OK(deviceCopyList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param instore
    */
    @RequiresPermissions("instore:instore:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Instore instore) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<Instore> queryWrapper = QueryGenerator.initQueryWrapper(instore, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //配置选中数据查询条件
      String selections = request.getParameter("selections");
      if(oConvertUtils.isNotEmpty(selections)) {
         List<String> selectionList = Arrays.asList(selections.split(","));
         queryWrapper.in("id",selectionList);
      }
      //Step.2 获取导出数据
      List<Instore> instoreList = instoreService.list(queryWrapper);

      // Step.3 组装pageList
      List<InstorePage> pageList = new ArrayList<InstorePage>();
      for (Instore main : instoreList) {
          InstorePage vo = new InstorePage();
          BeanUtils.copyProperties(main, vo);
          List<DeviceCopy> deviceCopyList = deviceCopyService.selectByMainId(main.getId());
          vo.setDeviceCopyList(deviceCopyList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "入库列表");
      mv.addObject(NormalExcelConstants.CLASS, InstorePage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("入库数据", "导出人:"+sysUser.getRealname(), "入库"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("instore:instore:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          // 获取上传文件对象
          MultipartFile file = entity.getValue();
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<InstorePage> list = ExcelImportUtil.importExcel(file.getInputStream(), InstorePage.class, params);
              for (InstorePage page : list) {
                  Instore po = new Instore();
                  BeanUtils.copyProperties(page, po);
                  instoreService.saveMain(po, page.getDeviceCopyList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
