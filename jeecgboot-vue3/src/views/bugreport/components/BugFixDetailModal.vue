<template>
  <BasicModal v-bind="$attrs" @register="registerModal" :title="'修复详情'" :width="800">
    <div class="fix-detail">
      <div v-html="fixDetail"></div>
    </div>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';

  const fixDetail = ref('');

  const [registerModal] = useModalInner(async (data) => {
    fixDetail.value = data?.record?.bug_fixdetail || '暂无修复详情';
  });
</script>

<style lang="less" scoped>
.fix-detail {
  min-height: 100px;
  padding: 12px;
}
</style> 