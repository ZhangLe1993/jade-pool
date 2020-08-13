<!-- sql 编辑器控制台 -->
<template>
    <div>
        <el-row>
            <el-col :span="4">
                <StructTree></StructTree>
            </el-col>
            <el-col :span="20">
                <el-row>
                    <el-col>
                        <TabsCpt ref="tabsCpt"></TabsCpt>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <div style="border: 1px solid #eee;height:35px;text-align: left;">
                            <i @click="run" class="el-icon-caret-right select" style="float:left;margin-top:11px;margin-left:10px;margin-right:80px;cursor: pointer;">运行</i>

                            <i @click="stop" class="el-icon-video-pause select" style="float:left;margin-top:11px;margin-right:80px;cursor: pointer;">停止</i>

                            <i @click="addSelectTab" class="el-icon-circle-plus-outline select" style="float:left;margin-top:11px;margin-right:80px;cursor: pointer;">新窗口</i>

                            <i @click="format" class="iconfont iconformat select" style="float:left;margin-top:6px;cursor: pointer;margin-right:250px;">格式化</i>

                            <el-divider direction="vertical"></el-divider>
                            <el-divider direction="vertical"></el-divider>
                            <el-divider direction="vertical"></el-divider>
                        </div>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <OutputTabs ref="outputTabs"></OutputTabs>
                    </el-col>
                </el-row>
            </el-col>

        </el-row>

        <el-tooltip placement="top" content="切换数据源">
            <FloatButton :custom-style="myBackToTopStyle" :visibility-height="300" :back-position="0" transition-name="fade"/>
        </el-tooltip>
    </div>
</template>

<script>
    import StructTree from '../components/StructTree';   // 引入子组件AgentForm
    import TabsCpt from '../components/TabsCpt';   // 引入子组件AgentForm
    import  FloatButton from '../components/FloatButton'
    import  OutputTabs from '../components/OutputTabs'
    export default {
        name: "Console",
        components: {
            StructTree: StructTree,
            TabsCpt: TabsCpt,
            FloatButton: FloatButton,
            OutputTabs: OutputTabs,
        },
        methods: {
          run() {
            // 先清除掉上一次的
            this.$refs.outputTabs.removeResultTab();
            const content = this.$refs.tabsCpt.getContent();
            const that = this;
            that.$api.post('/exec/sql', { sourceId: 1, sql: content }, (res) => {
              console.log(res);
              if(res.status === 200) {
                const data = res.data.data;
                const code = res.data.code;
                if(code === '200') {
                  for(let i = 0; i < data.length; i++) {
                    const list = data[i]['resultList'];
                    const columns = data[i]['columns'];
                    console.log(list);
                    // const tableHeader = that.$_.map(columns, 'name');
                    // console.log(tableHeader);
                    this.addResultTab(columns, list);
                  }
                } else {
                  this.setLogs(data);
                }
              }
            });
          },
          stop() {

          },
          addSelectTab() {
            const targetName = this.$refs.tabsCpt.editableTabsValue
            this.$refs.tabsCpt.addTab(targetName);
          },
          addResultTab(tableHeader, tableData) {
            const targetName = this.$refs.outputTabs.editableTabsValue
            this.$refs.outputTabs.addTab(targetName, tableHeader, tableData);
          },
          format() {
            this.$refs.tabsCpt.format();
          },
          setLogs(content) {

            this.$refs.outputTabs.setRunLogs(content);
          },
        },
        data() {
            return {
                myBackToTopStyle: {
                    right: '30px',
                    bottom: '30px',
                    width: '40px',
                    height: '40px',
                    borderRadius: '4px',
                    lineHeight: '45px', // 请保持与高度一致以垂直居中
                    background: '#e7eaf1'// 按钮的背景颜色
                }
            }
        },
      mounted() {
          
      }
    }
</script>

<style scoped>

/*    .el-header, .el-footer {
        background-color: #009688;
        color: #fff;
        text-align: center;
        line-height: 60px;
    }
    body {
        margin: 0 !important;
    }*/

    /* 下沉效果 */
    i:hover {
        position:relative;
        top:1px;
        left:1px;
        color: #409EFF;
    }

    /* 不可被选中 */
    .select{

        -webkit-user-select:none;

        -moz-user-select:none;

        -ms-user-select:none;

        user-select:none;

    }
    .el-divider--vertical {
        height: 2em;
    }

</style>