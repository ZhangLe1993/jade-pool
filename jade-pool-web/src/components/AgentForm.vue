<template>
    <el-dialog title="代理配置" :visible.sync="dialogFormVisible" :show-close="false" :close-on-click-modal="false">

        <el-form :model="form" :rules="rules" ref="form">
            <el-form-item label="路径" :label-width="formLabelWidth" prop="localUrl">
                <el-input v-model="form.localUrl" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="目标主机" :label-width="formLabelWidth" prop="targetHost">
                <el-select v-model="form.targetHost" filterable placeholder="请选择目标主机">
                    <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"> </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="目标路径" :label-width="formLabelWidth" prop="targetUrl">
                <el-input v-model="form.targetUrl" auto-complete="off"></el-input>
            </el-form-item>

            <el-divider content-position="left">附加Header</el-divider>
            <el-row>
                <el-col :span="10">
                    <el-form-item
                            v-for="(append, index) in form.header"
                            label="Key" :label-width="formLabelWidth"
                            :key="'header_' + append.key + '_' + index">
                        <el-input v-model="form.header[index].key" auto-complete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="14">
                    <el-form-item
                            v-for="(append, index) in form.header"
                            label="Value" :label-width="formLabelWidth"
                            :key="'header_' + append.value + '_' + index" style="display:block-inline;">
                            <el-row>
                                <el-col :span="14">
                                        <el-input v-model="form.header[index].value" auto-complete="off"></el-input>
                                </el-col>
                                &nbsp;&nbsp;
                                <el-col :span="10" style="display:inline;padding-left:10px;">
                                    <el-button @click="addDomain('header')">新增</el-button>
                                    <el-button v-if="index !== 0" @click.prevent="removeDomain(append, 'header')">删除</el-button>
                                </el-col>
                            </el-row>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-divider content-position="left">附加Cookie</el-divider>
            <el-row>
                <el-col :span="10">
                    <el-form-item
                            v-for="(append, index) in form.cookie"
                            label="Key" :label-width="formLabelWidth"
                            :key="'cookie_' + append.key + '_' + index">
                        <el-input v-model="form.cookie[index].key" auto-complete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="14">
                    <el-form-item
                            v-for="(append, index) in form.cookie"
                            label="Value" :label-width="formLabelWidth"
                            :key="'cookie_' + append.value + '_' + index" style="display:block-inline;">
                        <el-row>
                            <el-col :span="14">
                                <el-input v-model="form.cookie[index].value" auto-complete="off"></el-input>
                            </el-col>
                            &nbsp;&nbsp;
                            <el-col :span="10" style="display:inline;padding-left:10px;">
                                <el-button @click="addDomain('cookie')">新增</el-button>
                                <el-button v-if="index !== 0" @click.prevent="removeDomain(append, 'cookie')">删除</el-button>
                            </el-col>
                        </el-row>
                    </el-form-item>

                </el-col>
            </el-row>
            <el-divider content-position="left">附加URL</el-divider>
            <el-row>
                <el-col :span="10">
                    <el-form-item
                            v-for="(append, index) in form.param"
                            label="Key" :label-width="formLabelWidth"
                            :key="'param_' + append.key + '_' + index">
                        <el-input v-model="form.param[index].key" auto-complete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="14">
                    <el-form-item
                            v-for="(append, index) in form.param"
                            label="Value" :label-width="formLabelWidth"
                            :key="'param_' + append.value+ '_' + index"
                            style="display:block-inline;">
                        <el-row>
                            <el-col :span="14">
                                <el-input v-model="form.param[index].value" auto-complete="off"></el-input>
                            </el-col>
                            &nbsp;&nbsp;
                            <el-col :span="10" style="display:inline;padding-left:10px;">
                                <el-button @click="addDomain('param')">新增</el-button>
                                <el-button v-if="index !== 0" @click.prevent="removeDomain(append, 'param')">删除</el-button>
                            </el-col>
                        </el-row>
                    </el-form-item>

                </el-col>
            </el-row>
            <el-divider content-position="left">附加Body</el-divider>
            <el-row>
                <el-col :span="10">
                    <el-form-item
                            v-for="(append, index) in form.body"
                            label="Key" :label-width="formLabelWidth"
                            :key="'body_' + append.key + '_' + index">
                        <el-input v-model="form.body[index].key" auto-complete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="14">
                    <el-form-item
                            v-for="(append, index) in form.body"
                            label="Value" :label-width="formLabelWidth"
                            :key="'body_' + append.value + '_' + index"
                            style="display:block-inline;">
                        <el-row>
                            <el-col :span="14">
                                <el-input v-model="form.body[index].value" auto-complete="off"></el-input>
                            </el-col>
                            &nbsp;&nbsp;
                            <el-col :span="10" style="display:inline;padding-left:10px;">
                                <el-button @click="addDomain('body')">新增</el-button>
                                <el-button v-if="index !== 0" @click.prevent="removeDomain(append, 'body')">删除</el-button>
                            </el-col>
                        </el-row>
                    </el-form-item>

                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="onCancel">取 消</el-button>
            <el-button type="primary" @click="onSubmit('form')">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
    export default {
        name: "AgentForm",
        props: {
            dialogFormVisible: {
                type: Boolean,
                default: false
            },
            cancel: Function,
            form: Object,
            tableRefresh:Function,
        },
        data() {
            return {
                options: [{
                    value: 'http://172.172.0.11:8080',
                    label: '华东1'
                }],
                /*form: {
                    localUrl: '',
                    targetHost: '',
                    targetUrl: '',
                    headerAppend: [{key:'', value:''}],
                    cookieAppend: [{key:'', value:''}],
                    urlAppend: [{key:'', value: ''}],
                    bodyAppend: [{key:'', value:''}],
                },*/
                formLabelWidth: '120px',
                rules: {
                    localUrl: [
                        { required: true, message: '请输入路径', trigger: 'blur' },
                        { min: 1, max: 500, message: '长度在 1 到 500 个字符', trigger: 'blur' }
                    ],
                    targetHost: [
                        { required: true, message: '请选择目标主机', trigger: 'change' }
                    ],
                    targetUrl: [
                        { min: 0, max: 200, message: '长度在 0 到 200 个字符', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            openLayer(title, msg, type) {

                if(type === 'error') {
                    this.$notify.error({
                        title: title,
                        message: msg
                    });
                } else {
                    this.$notify({
                        title: title,
                        message: msg,
                        type: type
                    });
                }
            },
            onSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const that = this;
                        const formData = that.form;
                        console.log(formData);
                        console.log(JSON.stringify(formData));
                        if(formData.id === 0) {
                            // 新增
                            that.$api.post('/agent', JSON.stringify(formData), (res) => {
                                /*console.log(res.data);
                                this.tableData = res.data;*/
                                if(res !== undefined && res.status !== undefined && res.status === 200) {
                                    this.openLayer('消息', '恭喜你，新增成功。', 'success');
                                    // 关闭弹出层
                                    this.onCancel();
                                    this.tableRefresh();
                                } else {
                                    this.openLayer('消息', res.data, 'error');
                                }
                            });
                        } else {
                            // 更新
                            that.$api.put('/agent', JSON.stringify(formData), (res) => {
                                console.log(res);
                                if(res !== undefined && res.status !== undefined && res.status === 200) {
                                    this.openLayer('消息', '恭喜你，修改成功。', 'success');
                                    // 关闭弹出层
                                    this.onCancel();
                                    this.tableRefresh();
                                } else {
                                    this.openLayer('消息', res.data, 'error');
                                }
                            });
                        }
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            onCancel() {
                this.cancel();
            },
            removeDomain(item, type) {
                const index = this.form[type].indexOf(item);
                if (index !== -1) {
                    this.form[type].splice(index, 1);
                }
            },
            addDomain(type) {
                this.form[type].push({
                    value: '',
                    key: '',
                });
            }
        }
    }
</script>

<style scoped>

</style>