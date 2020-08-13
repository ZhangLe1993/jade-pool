<template>
    <el-tabs v-model="editableTabsValue" type="card" @tab-remove="removeTab" @tab-click="switchTab">
        <el-tab-pane
                v-for="(item, index) in editableTabs"
                :key="item.name"
                :label="item.title"
                :name="item.name"
                :index="index"
                :closable="item.closable"
        >
            <!--{{item.content}}-->
            <AceEditor :ref="item.name"></AceEditor>
        </el-tab-pane>
    </el-tabs>
</template>

<script>
    import AceEditor from '../components/AceEditor'; // 引入子组件AgentForm
    export default {
        name: "TabsCpt",
        components: {
            AceEditor: AceEditor,
        },
        data() {
            return {
                editableTabsValue: '1',
                editableTabs: [{
                    title: '查询窗口',
                    name: '1',
                    closable: false,
                }],
                tabIndex: 1,
                // 激活的标签
                activeName: 1,
            }
        },
        methods: {
            addTab(targetName) {
                console.log(targetName);
                let newTabName = ++this.tabIndex + '';
                this.editableTabs.push({
                    title: '新建窗口',
                    name: newTabName,
                    closable: true,
                });
                this.editableTabsValue = newTabName;
                this.activeName = newTabName;
            },
            removeTab(targetName) {
                let tabs = this.editableTabs;
                let activeName = this.editableTabsValue;
                if (activeName === targetName) {
                    tabs.forEach((tab, index) => {
                        if (tab.name === targetName) {
                            let nextTab = tabs[index + 1] || tabs[index - 1];
                            if (nextTab) {
                                activeName = nextTab.name;
                            }
                        }
                    });
                }
                this.editableTabsValue = activeName;
                this.activeName = activeName;
                this.editableTabs = tabs.filter(tab => tab.name !== targetName);
            },
            switchTab(tabsCpt) {
              // 更改当前选中的选项卡
              this.activeName = tabsCpt.name;
            },
            getContent() {
                // 获取当前选中的选项卡的编辑器内容
                return this.$refs[this.activeName][0].getContent();
            },
            format() {
              this.$refs[this.activeName][0].format();
            }
        },
        mounted() {

        }
}
</script>

<style scoped>

</style>