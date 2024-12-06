import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '创建人',
    align:"center",
    dataIndex: 'createBy'
   },
   {
    title: '创建日期',
    align:"center",
    dataIndex: 'createTime'
   },
   {
    title: '所属文件夹',
    align:"center",
    dataIndex: 'parentFolder_dictText'
   },
   {
    title: '资产名称',
    align:"center",
    dataIndex: 'assetName'
   },
   {
    title: '资产描述',
    align:"center",
    dataIndex: 'assetDetail'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "所属文件夹",
      field: 'parentFolder',
      component: 'JSelectMultiple',
      componentProps:{
          dictCode:"folder,folder_name,id"
      },
      //colProps: {span: 6},
 	},
	{
      label: "资产名称",
      field: 'assetName',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '所属文件夹',
    field: 'parentFolder',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"folder,folder_name,id"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入所属文件夹!'},
          ];
     },
  },
  {
    label: '资产名称',
    field: 'assetName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入资产名称!'},
          ];
     },
  },
  {
    label: '资产描述',
    field: 'assetDetail',
    component: 'InputTextArea',
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  createBy: {title: '创建人',order: 0,view: 'text', type: 'string',},
  createTime: {title: '创建日期',order: 1,view: 'datetime', type: 'string',},
  parentFolder: {title: '所属文件夹',order: 2,view: 'list', type: 'string',dictTable: "folder", dictCode: 'id', dictText: 'folder_name',},
  assetName: {title: '资产名称',order: 3,view: 'text', type: 'string',},
  assetDetail: {title: '资产描述',order: 4,view: 'textarea', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}