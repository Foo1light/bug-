<template>
  <BasicModal v-bind="$attrs" @register="registerModal" destroyOnClose :title="title" :width="800" @ok="handleSubmit">
    <BasicForm @register="registerForm" name="BugReportForm" />
    <template #footer v-if="!showFooter && showActionBtns">
      <div style="text-align: center">
        <a-space>
          <a-button @click="handlePostpone" type="default">后续讨论</a-button>
          <a-button @click="handleProcess" type="primary">进行处理</a-button>
        </a-space>
      </div>
    </template>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, computed, unref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { formSchema } from '../BugReport.data';
  import { saveOrUpdate } from '../BugReport.api';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';

  const { createMessage, createConfirm } = useMessage();
  // Emits声明
  const emit = defineEmits(['register', 'success']);
  const isUpdate = ref(true);
  const showFooter = ref(true);
  const currentRecord = ref<any>({});

  //表单配置
  const [registerForm, { setProps, resetFields, setFieldsValue, validate, scrollToField, getFieldsValue }] = useForm({
    labelWidth: 150,
    schemas: formSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 24 },
  });

  //表单赋值
  const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
    //重置表单
    await resetFields();

    showFooter.value = !!data?.showFooter;
    setModalProps({
      confirmLoading: false,
      showCancelBtn: showFooter.value,
      showOkBtn: showFooter.value,
      defaultFullscreen: true,
    });

    isUpdate.value = !!data?.isUpdate;
    currentRecord.value = data?.record || {};

    if (data?.record) {
      //表单赋值
      await setFieldsValue({
        ...data.record,
      });
    }

    // 设置表单禁用状态
    setProps({

      schemas: formSchema.map((schema) => {
        if (schema.field !== 'bugDetail') {
          return {
            ...schema,

            componentProps: {
              ...schema.componentProps,
              disabled: !showFooter.value,
            },
          };
        }
        return schema;
      }),
    });
  });

  //设置标题
  const title = computed(() => {
    if (showFooter.value) {
      return !isUpdate.value ? '新增' : '编辑';
    }
    return '详情';
  });

  //表单提交事件
  async function handleSubmit(v) {
    try {
      let values = await validate();
      setModalProps({ confirmLoading: true });
      //提交表单
      await saveOrUpdate(values, isUpdate.value);
      //关闭弹窗
      closeModal();
      //刷新列表
      emit('success');
    } catch (error: any) {
      const { errorFields } = error;
      if (errorFields) {
        const firstField = errorFields[0];
        if (firstField) {
          scrollToField(firstField.name, { behavior: 'smooth' });
        }
      }
      return Promise.reject(error);
    } finally {
      setModalProps({ confirmLoading: false });
    }
  }

  // 是否显示操作按钮
  const showActionBtns = computed(() => {
    return currentRecord.value?.bugIsfix === '0';
  });

  // 处理后续讨论
  async function handlePostpone() {
    createConfirm({
      iconType: 'warning',
      title: '确认推迟',
      content: '确定将此bug标记为后续讨论？',
      onOk: async () => {
        try {
          await saveOrUpdate(
            {
              ...currentRecord.value,
              bugIsfix: '2', // 2表示后续讨论
            },
            true
          );
          createMessage.success('已标记为后续讨论');
          closeModal();
          emit('success');
        } catch (error) {
          console.error('操作失败:', error);
        }
      },
    });
  }

  // 处理进行处理
  async function handleProcess() {
    try {
      await saveOrUpdate(
        {
          ...currentRecord.value,
          bugIsfix: '4', // 4表示正在处理
        },
        true
      );
      createMessage.success('已开始处理该bug');
      closeModal();
      emit('success');
    } catch (error) {
      console.error('处理失败:', error);
    }
  }
</script>

<style lang="less" scoped>
  /** 时间和数字输入框样式 */
  :deep(.ant-input-number) {
    width: 100%;
  }

  :deep(.ant-calendar-picker) {
    width: 100%;
  }
</style>
