<template>
  <div>
    <div id="x-spreadsheet-demo"></div>
  </div>
</template>

<script>
// 引入 sheet 组件
import Spreadsheet from "x-data-spreadsheet";

export default {
  name: "Table",
  components: {

  },
  data() {
    return {

    }
  },
  methods: {},
  mounted() {
    const sheet = new Spreadsheet("#x-spreadsheet-demo")
        .loadData({}) // load data
        .change(data => {
          // save data to db
        });
    // data validation
    sheet.validate()
  }
}
</script>

<style scoped>

</style>