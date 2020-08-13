<template>
  <div>
    <el-row>
      <el-col :span="4">
        <SshTree :addTab="addTab"></SshTree>
      </el-col>
      <el-col :span="20">
        <TerminalTabs ref="terminalTabs"></TerminalTabs>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    import TerminalTabs from '../components/TerminalTabs';   // 引入子组件XTerm
    import SshTree from '../components/SshTree';   // 引入子组件XTerm

    export default {
      name: "Terminal",
      components: {
        TerminalTabs: TerminalTabs,
        SshTree: SshTree,
      },
      methods: {
        addTab(name) {
          // const targetName = this.$refs.terminalTabs.editableTabsValue
          this.$refs.terminalTabs.addTab(name);
        },

      }
    }
</script>

<style scoped>

</style>