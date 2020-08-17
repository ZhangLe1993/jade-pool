<template>
  <div>
    <div class="total-class" :id="elementId" :style="{width: '100%', height: '300px'}"></div>
  </div>
</template>

<script>
export default {
  name: "Dashboard",
  props: {
    elementId: String,
    value: Object,
  },
  watch: {

  },
  data() {
    return {

    }
  },
  methods: {
    drawDashboard() {
      let myChart = this.$echarts.init( document.getElementById(this.elementId));
      const option = {
        /*tooltip: {
          formatter: '{a} <br/>{b} : {c}%'
        },*/
        series: [
          {
            name: '百分比',
            type: 'gauge',
            detail: {formatter: '{value}%'},
            data: [{value: this.value.percent, name: this.value.host}]
          }
        ]
      };
      // option.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
      myChart.setOption(option, true);
    },
  },
  mounted() {
    this.drawDashboard();
  }
}
</script>

<style scoped>

</style>