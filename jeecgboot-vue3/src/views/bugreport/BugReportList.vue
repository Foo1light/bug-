<template>
  <div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" v-auth="'bugreport:bug_report:add'" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button type="primary" v-auth="'bugreport:bug_report:exportXls'" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <j-upload-button type="primary" v-auth="'bugreport:bug_report:importExcel'" preIcon="ant-design:import-outlined" @click="onImportXls"
          >导入</j-upload-button
        >
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined" />
                删除
              </a-menu-item>
            </a-menu>
          </template>
          <a-button v-auth="'bugreport:bug_report:deleteBatch'"
            >批量操作
            <Icon icon="mdi:chevron-down" />
          </a-button>
        </a-dropdown>
        <!-- 高级查询 -->
        <super-query :config="superQueryConfig" @search="handleSuperQuery" />
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)" />
      </template>
      <!--字段回显插槽-->
      <template #bodyCell="{ column, record, index, text }">
        <template v-if="column.dataIndex === 'bugDetail'">
          <!--富文本件字段回显插槽-->
          <div v-html="text"></div>
        </template>
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <BugReportModal @register="registerModal" @success="handleSuccess" />
    <!-- 添加修复确认弹窗 -->
    <BugFixModal @register="registerFixModal" @success="handleSuccess" ref="fixModalRef" />
    <!-- 添加修复详情弹窗 -->
    <BugFixDetailModal @register="registerFixDetailModal"/>
  </div>
</template>

<script lang="ts" name="bugreport-bugReport" setup>
  import { ref, reactive, computed, unref } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useModal } from '/@/components/Modal';
  import { useListPage } from '/@/hooks/system/useListPage';
  import BugReportModal from './components/BugReportModal.vue';
  import { columns, searchFormSchema, superQuerySchema } from './BugReport.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl } from './BugReport.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import { useUserStore } from '/@/store/modules/user';
  import BugFixModal from './components/BugFixModal.vue';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { saveOrUpdate } from './BugReport.api';
  import BugFixDetailModal from './components/BugFixDetailModal.vue';

  const queryParam = reactive<any>({});
  const checkedKeys = ref<Array<string | number>>([]);
  const userStore = useUserStore();
  const { createMessage, createConfirm } = useMessage();
  //注册model
  const [registerModal, { openModal }] = useModal();
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'bug大厅',
      api: list,
      columns,
      canResize: false,
      formConfig: {
        //labelWidth: 120,
        schemas: searchFormSchema,
        autoSubmitOnEnter: true,
        showAdvancedButton: true,
        fieldMapToNumber: [],
        fieldMapToTime: [],
      },
      actionColumn: {
        width: 180,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam);
      },
    },
    exportConfig: {
      name: 'bug大厅',
      url: getExportUrl,
      params: queryParam,
    },
    importConfig: {
      url: getImportUrl,
      success: handleSuccess,
    },
  });

  const [registerTable, { reload }, { rowSelection, selectedRowKeys }] = tableContext;

  // 高级查询配置
  const superQueryConfig = reactive(superQuerySchema);

  const fixModalRef = ref();
  const [registerFixModal, { openModal: openFixModal }] = useModal();
  const [registerFixDetailModal, { openModal: openFixDetailModal }] = useModal();

  /**
   * 高级查询事件
   */
  function handleSuperQuery(params) {
    Object.keys(params).map((k) => {
      queryParam[k] = params[k];
    });
    reload();
  }
  /**
   * 新增事件
   */
  function handleAdd() {
    openModal(true, {
      isUpdate: false,
      showFooter: true,
    });
  }
  /**
   * 编辑事件
   */
  function handleEdit(record: Recordable) {
    openModal(true, {
      record,
      isUpdate: true,
      showFooter: true,
    });
  }
  /**
   * 详情
   */
  function handleDetail(record: Recordable) {
    openModal(true, {
      record,
      isUpdate: false,
      showFooter: false,
    });
  }
  /**
   * 删除事件
   */
  async function handleDelete(record) {
    await deleteOne({ id: record.id }, handleSuccess);
  }
  /**
   * 批量删除事件
   */
  async function batchHandleDelete() {
    await batchDelete({ ids: selectedRowKeys.value }, handleSuccess);
  }
  /**
   * 成功回调
   */
  function handleSuccess() {
    (selectedRowKeys.value = []) && reload();
  }
  /**
   * 操作栏
   */
  function getTableAction(record) {
    const actions=[
      {
        label: '详情',
        onClick: handleDetail.bind(null, record),
      },

    ];

    // 当状态为"正在处理"时添加修复完成选项
    if (record.bugIsfix === '4') {
      actions.push({
        label: '修复完成',
        onClick: handleFix.bind(null, record),
      });
    }

    // 当状态为"已修复"时添加修复详情和已回归选项
    if (record.bugIsfix === '1') {
      actions.push({
        label: '修复详情',
        onClick: handleViewFixDetail.bind(null, record),
      });
      actions.push({
        label: '已回归',
        popConfirm: {
          title: '确认该bug已完成回归测试？',
          confirm: handleRegression.bind(null, record),
          placement: 'topLeft',
        },
      });
    }
    return actions;
  }
  /**
   * 下拉操作栏
   */
  function getDropDownAction(record) {
    const dropactions = [
      {
        label: '编辑',
        onClick: handleEdit.bind(null, record),
        auth: 'bugreport:bug_report:edit',
      },
      // 添加删除选项
      {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
          placement: 'topLeft',
        },
        auth: 'bugreport:bug_report:delete',
      },
    ];





    return dropactions;
  }

  // 修改处理修复完成的函数
  async function handleFix(record) {
    openFixModal(true, {
      record,
      onOk: async () => {
        // 获取修复详情
        const fixDetail = fixModalRef.value?.getFixDetail();

        try {
          await saveOrUpdate(
            {
              ...record,
              bugIsfix: '1', // 1表示已修复
              bug_fixdetail: fixDetail, // 修改为数据库字段名
            },
            true
          );

          createMessage.success('已标记为修复完成');
          handleSuccess();
        } catch (error) {
          console.error('修复确认失败:', error);
          return false;
        }
      }
    });
  }

  // 添加处理回归的函数
  async function handleRegression(record) {
    try {
      await saveOrUpdate(
        {
          ...record,
          bugIsfix: '3', // 3表示已回归
        },
        true
      );
      createMessage.success('已标记为已回归');
      handleSuccess();
    } catch (error) {
      console.error('回归确认失败:', error);
    }
  }

  // 添加查看修复详情的处理函数
  function handleViewFixDetail(record) {
    openFixDetailModal(true, {
      record,
    });
  }
</script>

<style lang="less" scoped>
  :deep(.ant-picker),
  :deep(.ant-input-number) {
    width: 100%;
  }
</style>
