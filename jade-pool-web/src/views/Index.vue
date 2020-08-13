<template xmlns:el-col="http://www.w3.org/1999/html">
  <el-row>
    <el-col :span="12">
      <el-row>
        <el-col :span="12" class="sp">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>内存监控</span>
              <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
            </div>
            <Dashboard :elementId="memoryId" :value="memory"></Dashboard>
          </el-card>
        </el-col>
        <el-col :span="12" class="sp">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>CPU监控</span>
              <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
            </div>
            <Dashboard :elementId="cpuId" :value="cpu"></Dashboard>
          </el-card>
        </el-col>
      </el-row>
    </el-col>
    <el-col :span="12">

    </el-col>
  </el-row>

</template>

<script>
import Dashboard from '../components/Dashboard'
export default {
  name: "Index",
  data() {
    return {
      memoryId: 'memoryId',
      cpuId: 'cpuId',
      memory: {},
      cpu: {},
    }
  },
  methods:{
    getMemory() {
      const that = this;
      that.$api.get('/sys/memory', { }, (res) => {
        console.log(res);
        this.memory = res.data;
        this.$forceUpdate();
      });
    },
    getCPU() {
      const that = this;
      that.$api.get('/sys/cpu', { }, (res) => {
        console.log(res);
        this.cpu = res.data;
        // this.$set(this.cpu, 0, res.data);
      });
    }
  },
  components: {
    Dashboard: Dashboard,
  },
  mounted() {
    this.getMemory();
    this.getCPU();
  }
}
</script>

<style scoped>
  .sp{
    padding: 10px;
  }
</style>