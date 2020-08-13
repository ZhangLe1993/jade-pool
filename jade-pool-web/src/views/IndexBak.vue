<template>
    <el-container>
        <el-container>
            <el-aside width="201px">
                <el-row>
                    <el-col :span="24">
                        <div class="grid-content bg-purple">
                            <el-menu default-active="1-4-1" class="el-menu-vertical-demo" @open="handleOpen" :collapse="isCollapse">
                                <el-menu-item index="0">
                                    <i class="el-icon-loading"></i>
                                    <span slot="title" style="color:#fff;">统计概览</span>
                                </el-menu-item>
                                <el-menu-item index="1">
                                    <i class="el-icon-tickets"></i>
                                    <span slot="title" style="color:#fff;">代理管理</span>
                                </el-menu-item>
                                <el-menu-item index="2">
                                    <i class="el-icon-user"></i>
                                    <span slot="title" style="color:#fff;">用户管理</span>
                                </el-menu-item>
                                <el-menu-item index="3">
                                    <i class="el-icon-setting"></i>
                                    <span slot="title" style="color:#fff;">系统设置</span>
                                </el-menu-item>
                            </el-menu>
                        </div>
                    </el-col>

                </el-row>
            </el-aside>
            <el-main style="text-align: left">
                <el-row>
                    <el-col>
                        <el-form :inline="true" :model="searchForm" ref="searchForm" class="demo-form-inline">
                            <el-form-item label="路径">
                                <el-input v-model="searchForm.path" placeholder="路径"></el-input>
                            </el-form-item>
                            <el-form-item label="状态">
                                <el-select v-model="searchForm.status" placeholder="状态">
                                    <el-option label="创建" value="CREATE"></el-option>
                                    <el-option label="测试" value="TEST"></el-option>
                                    <el-option label="通过" value="PASS"></el-option>
                                    <el-option label="删除" value="DELETE"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="onSubmit">查询</el-button>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="resetForm()">重置</el-button>
                            </el-form-item>
                        </el-form>
                        <el-button type="primary" @click="handleAdd" round>添加代理</el-button>
                        <AgentForm :dialogFormVisible="dialogFormVisible" :cancel="handleCancel" :form="agentForm" :tableRefresh="refresh" />
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24" style="text-align: center">
                        <el-table
                                v-loading="loading"
                                :data="tableData"
                                style="width: 100%">
                            <el-table-column
                                    label="路径"
                                    width="400">
                                <template slot-scope="scope">
                                    <el-popover trigger="hover" placement="top">
                                        <p>目标主机: {{ scope.row.targetHost }}</p>
                                        <p>目标路径: {{ scope.row.targetUrl }}</p>
                                        <p>附加头部: {{ scope.row.header }}</p>
                                        <p>附加Cookie: {{ scope.row.cookie }}</p>
                                        <p>URL附加参数: {{ scope.row.param }}</p>
                                        <p>JSON附加参数: {{ scope.row.body }}</p>
                                        <div slot="reference" class="name-wrapper">
                                            <el-tag size="medium">{{ scope.row.localUrl }}</el-tag>
                                        </div>
                                    </el-popover>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    label="状态"
                                    width="100">
                                <template slot-scope="scope">
                                    <el-tag
                                            type="primary"
                                            close-transition>{{scope.row.status === 'CREATE' ? '创建' : (scope.row.status === 'TEST' ? '连接失败' : (scope.row.status === 'PASS' ? '通过' : '删除'))}}</el-tag>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    label="创建时间"
                                    width="300">
                                <template slot-scope="scope">
                                    <i class="el-icon-time"></i>
                                    <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    label="编辑时间"
                                    width="300">
                                <template slot-scope="scope">
                                    <i class="el-icon-time"></i>
                                    <span style="margin-left: 10px">{{ scope.row.updateTime }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="操作">
                                <template slot-scope="scope">
                                    <el-button
                                            size="mini"
                                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                                    <el-button
                                            size="mini"
                                            @click="handleTest(scope.$index, scope.row)">测试</el-button>
                                    <el-button
                                            size="mini"
                                            type="danger"
                                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col style="padding-top:20px;padding-bottom: 10px;">
                        <el-pagination
                                style="float: right; "
                                @size-change="handleSizeChange"
                                @current-change="handleCurrentChange"
                                :current-page="pagination.currentPage"
                                :page-sizes="pagination.pageSizes"
                                :page-size="pagination.pageSize"
                                layout="total, sizes, prev, pager, next, jumper"
                                :total="pagination.total">
                        </el-pagination>
                        <!--<el-pagination
                                background
                                layout="prev, pager, next"
                                :total="1000"
                                style="float: right; "
                        >
                        </el-pagination>-->
                    </el-col>
                </el-row>
            </el-main>
        </el-container>
    </el-container>

</template>



<script>
    import AgentForm from '../components/AgentForm';   //引入子组件AgentForm
    export default {
        name: "Index",
        // 挂载子组件
        components: {
            AgentForm: AgentForm
        },
        data() {
            return {
                isCollapse: false,
                tableData: [],
                searchForm: {
                    path: null,
                    status: null
                },
                pagination: {
                    currentPage: 1,
                    pageSize: 10,
                    pageSizes: [10, 20, 30, 40],
                    total: 100,
                },
                dialogFormVisible: false,
                agentForm: {
                    id: 0,
                    localUrl: '',
                    targetHost: '',
                    targetUrl: '',
                    header: [{key:'', value:''}],
                    cookie: [{key:'', value:''}],
                    param: [{key:'', value: ''}],
                    body: [{key:'', value:''}],
                },
                loading: true
            };
        },
        methods: {
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.pagination.pageSize = val;
                this.search('', this.pagination.currentPage, val);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.pagination.currentPage = val;
                this.search('', val, this.pagination.pageSize);
            },
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleAdd() {
                // 打开 form
                this.dialogFormVisible = true;
                // form 重置
                this.agentForm = {id: 0, localUrl: '', targetHost: '', targetUrl: '', header: [{key:'', value:''}], cookie: [{key:'', value:''}], param: [{key:'', value: ''}], body: [{key:'', value:''}]}
            },
            handleCancel() {
                this.dialogFormVisible = false;
            },
            handleEdit(index, row) {
                console.log(index, row);
                this.dialogFormVisible = true;
                this.agentForm = {id: row.id, localUrl: row.localUrl, targetHost: row.targetHost, targetUrl: row.targetUrl, header: row.header, cookie: row.cookie, param: row.param, body: row.body}
            },
            handleTest(index, row) {
                console.log(index, row);
                this.loading = true;
            },
            handleDelete(index, row) {
                console.log(index, row);
            },
            onSubmit() {
                this.search(this.searchForm.path, this.searchForm.status, this.pagination.currentPage, this.pagination.pageSize);
            },
            search(keyWord, status, pageNo, pageSize) {
                this.loading = true;
                const that = this;
                that.$api.get('/agent/list', { keyWord: keyWord, status: status, pageNo: pageNo, pageSize: pageSize }, (res) => {
                    console.log(res.data);
                    this.tableData = res.data.list;
                    const paginationTemp = this.pagination;
                    this.pagination = { ... paginationTemp, total: res.data.count };
                    this.loading = false;
                });
            },
            refresh() {
                this.search(null, null,1, 10);
            },
            resetForm() {
                this.searchForm.path = null;
                this.searchForm.status = null;
            }
        },
        // 函数不能写成箭头函数，否则this会变成 undefined
        mounted: function() {
            this.search(null, null,1, 10);
        }
    }
</script>

<style scoped>
    .el-header, .el-footer {
        background-color: #009688;
        color: #fff;
        text-align: center;
        line-height: 60px;
    }

    .el-aside {
        background-color: #303133;
        color: #fff;
        text-align: center;
        line-height: 200px;
    }

    .el-main {
        background-color: #E9EEF3;
        color: #333;
        text-align: center;
        line-height: 160px;
    }

    body > .el-container {
        /*margin-bottom: 40px;*/
    }

    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
        line-height: 260px;
    }

    .el-container:nth-child(7) .el-aside {
        line-height: 320px;
    }

    .el-main {
        background-color: #fff;
        color: #333;
        text-align: center;
        line-height: 60px;
    }

    body {
        margin: 0 !important;
    }

    body > .el-container {
       /* margin-bottom: 40px;*/
    }

    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
        line-height: 260px;
    }

    .el-container:nth-child(7) .el-aside {
        line-height: 320px;
    }

    /* 导航菜单 */
    .el-menu-vertical-demo:not(.el-menu--collapse) {
        width: 200px;
        min-height: 400px;
        background-color: #303133;
        color:#fff;
    }
    .el-menu-item {
        color:#fff !important;
    }

    .is-active {
        background-color: #409EFF;
    }

    .el-menu-item:focus, .el-menu-item:hover {
        outline: 0;
        background-color: #409EFF !important;
    }
    .el-icon-loading:before {
        color:#fff;
    }
    .el-form--inline .el-form-item {
        display: inline-flex !important;
        vertical-align: bottom !important;
    }
</style>