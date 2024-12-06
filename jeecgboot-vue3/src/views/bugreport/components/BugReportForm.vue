<template>
    <div style="min-height: 400px">
        <BasicForm @register="registerForm"></BasicForm>
        <div style="width: 100%;text-align: center" v-if="!formDisabled">
            <a-button @click="submitForm" pre-icon="ant-design:check" type="primary">提 交</a-button>
        </div>
    </div>
</template>

<script lang="ts">
    import {BasicForm, useForm} from '/@/components/Form/index';
    import {computed, defineComponent} from 'vue';
    import {defHttp} from '/@/utils/http/axios';
    import { propTypes } from '/@/utils/propTypes';
    import {getBpmFormSchema} from '../BugReport.data';
    import {saveOrUpdate} from '../BugReport.api';
    
    export default defineComponent({
        name: "BugReportForm",
        components:{
            BasicForm
        },
        props:{
            formData: propTypes.object.def({}),
            formBpm: propTypes.bool.def(true),
        },
        setup(props){
            const [registerForm, { setFieldsValue, setProps, getFieldsValue }] = useForm({
                labelWidth: 150,
                schemas: getBpmFormSchema(props.formData),
                showActionButtonGroup: false,
                baseColProps: {span: 24}
            });

            const formDisabled = computed(()=>{
                if(props.formData.disabled === false){
                    return false;
                }
                return true;
            });

            let formData = {};
            const queryByIdUrl = '/bugreport/bugReport/queryById';
            async function initFormData(){
                if(props.formData.dataId) {
                    let params = {id: props.formData.dataId};
                    const data = await defHttp.get({url: queryByIdUrl, params});
                    formData = {...data}
                    //设置表单的值
                    await setFieldsValue(formData);
                } else {
                    // 新增时设置默认值
                    formData = {
                        bugIsfix: '0'  // 默认未修复
                    }
                    await setFieldsValue(formData);
                }
                //默认是禁用
                await setProps({disabled: formDisabled.value})
            }

            async function submitForm() {
                let data = getFieldsValue();
                let params = Object.assign({}, formData, data);
                
                // 如果是新增操作(没有id),则设置默认未修复状态
                if(!params.id) {
                    params.bugIsfix = '0';  // 0表示未修复
                }
                
                console.log('表单数据', params)
                await saveOrUpdate(params, true)
            }

            initFormData();
            
            return {
                registerForm,
                formDisabled,
                submitForm,
            }
        }
    });
</script>