import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
import { Tag } from 'ant-design-vue';
import { h } from 'vue';

// 定义状态对应的颜色
const statusColorMap = {
  '0': 'red', // 未修复
  '1': 'blue', // 已修复
  '2': 'purple', // 后续讨论
  '3': 'green', // 已回归
  '4': 'orange', // 正在处理
};

//列表数据
export const columns: BasicColumn[] = [
  {
    title: '发现者',
    align: 'center',
    dataIndex: 'createBy',
  },
  {
    title: '创建日期',
    align: 'center',
    dataIndex: 'createTime',
  },
  {
    title: 'bug简述',
    align: 'center',
    dataIndex: 'bugSummary',
  },
  {
    title: '是否修复',
    align: 'center',
    dataIndex: 'bugIsfix_dictText',
    customRender: ({ text, record }) => {
      return h(Tag, { color: statusColorMap[record.bugIsfix] }, () => text);
    },
  },
  {
    title: '复现情况',
    align: 'center',
    dataIndex: 'bugTricker_dictText',
  },
  {
    title: 'bug负责人',
    align: 'center',
    dataIndex: 'bugCharger_dictText',
  },
  {
    title: '优先级',
    align: 'center',
    dataIndex: 'bugLineup_dictText',
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: '发现者',
    field: 'createBy',
    component: 'Input', //TODO 范围查询
    //colProps: {span: 6},
  },
  {
    label: '是否修复',
    field: 'bugIsfix',
    component: 'JSelectMultiple',
    componentProps: {
      dictCode: 'isfix',
    },
    //colProps: {span: 6},
  },
  {
    label: 'bug负责人',
    field: 'bugCharger',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'peo',
    },
    //colProps: {span: 6},
  },
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: 'bug简述',
    field: 'bugSummary',
    component: 'InputTextArea',
    componentProps: { rows: 5 },
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入bug简述!' }];
    },
  },
  {
    label: '复现情况',
    field: 'bugTricker',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'clicker',
    },
  },
  {
    label: 'bug负责人',
    field: 'bugCharger',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'peo',
    },
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入bug负责人!' }];
    },
  },
  {
    label: '优先级',
    field: 'bugLineup',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'lineup',
    },
  },
  {
    label: 'bug详情',
    field: 'bugDetail',
    component: 'JMarkdownEditor',
    componentProps: {
      height: 500,
      disabled: false,
    },
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入bug详情!' }];
    },
  },
  {
    label: '是否修复',
    field: 'bugIsfix',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'isfix',
      disabled: true,
    },
    show: false,
    defaultValue: '0',
  },
  {
    label: '修复详情',
    field: 'bug_fixdetail',
    component: 'JMarkdownEditor',
    show: false,
  },
  // TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];

// 高级查询数据
export const superQuerySchema = {
  createBy: { title: '发现者', order: 0, view: 'text', type: 'string' },
  createTime: { title: '创建日期', order: 1, view: 'datetime', type: 'string' },
  bugSummary: { title: 'bug简述', order: 2, view: 'textarea', type: 'string' },
  bugIsfix: { title: '是否修复', order: 3, view: 'radio', type: 'string', dictCode: 'isfix' },
  bugTricker: { title: '复现情况', order: 4, view: 'list', type: 'string', dictCode: 'clicker' },
  bugCharger: { title: 'bug负责人', order: 5, view: 'list', type: 'string', dictCode: 'peo' },
  bugLineup: { title: '优先级', order: 6, view: 'list', type: 'string', dictCode: 'lineup' },
};

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 如果是编辑模式,显示是否修复字段
  if (_formData && _formData.id) {
    return formSchema.map((item) => {
      if (item.field === 'bugIsfix') {
        return {
          ...item,
          show: true,
          component: 'JDictSelectTag',
          componentProps: {
            dictCode: 'isfix',
            disabled: !_formData.showFooter,
          },
        };
      }
      return item;
    });
  }
  return formSchema;
}
