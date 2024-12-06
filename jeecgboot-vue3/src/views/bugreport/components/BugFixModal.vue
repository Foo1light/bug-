<template>
  <BasicModal v-bind="$attrs" @register="registerModal" :title="'确认修复'" :width="800" @ok="handleSubmit">
    <div class="fix-detail">
      <BasicForm @register="registerForm">
        <template #bugFixdetail>
          <JEditor v-model="fixDetail" />
        </template>
      </BasicForm>
    </div>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form';
  import { JEditor } from '/@/components/Form';
  import { useMessage } from '/@/hooks/web/useMessage';

  const emit = defineEmits(['register', 'success']);
  const fixDetail = ref('');
  let onOk: any = null;

  const { createConfirm } = useMessage();

  const [registerForm, { resetFields }] = useForm({
    labelWidth: 120,
    schemas: [
      {
        label: '修复详情',
        field: 'bug_fixdetail',
        component: 'JMarkdownEditor',
        componentProps: { height: 400 },
        required: false,
      },
    ],
    showActionButtonGroup: false,
  });

  const [registerModal, { closeModal }] = useModalInner(async (data) => {
    await resetFields();
    fixDetail.value = '';
    onOk = data?.onOk;
  });

  async function handleSubmit() {
    if (onOk) {
      const success = await onOk();
      if (success !== false) {
        closeModal();
      }
    }
  }

  defineExpose({
    getFixDetail: () => fixDetail.value,
  });
</script>

<style lang="less" scoped>
  .fix-detail {
    min-height: 400px;
  }
</style>
