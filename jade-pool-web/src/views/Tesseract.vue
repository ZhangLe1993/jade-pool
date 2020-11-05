<template>
  <div>
    <el-card class="box-card" style="margin: 0 auto;">
      <div style="width:1100px">
        <div class="file-upload">
          <div class="file-upload-text">上传图片</div>
          <input name="uploadFile" class="file-upload-input" id="uploadFile" @change="preview" type="file" accept="image/*">
        </div>
      </div>
    </el-card>
    <el-card class="box-card" style="margin: 0 auto;">
      <div slot="header" class="clearfix">
        <span style="float: left">图片识别</span>
      </div>
      <div style="width:1100px">
        <div style="width:500px;float: left">
          <editor id="editor" v-model="content" @init="editorInit" lang="text" theme="monokai" width="500px" height="500px" readonly :options="options"></editor>
        </div>
        <div style="width:600px;float: right">
          <img id="text-img" alt="图片" :src="imageUrl">
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
// 引入 sql Format 组件
// import sqlFormatter from 'sql-formatter';

/* eslint-disable */
import { createWorker, PSM, OEM } from 'tesseract.js';
const worker = createWorker({
  logger: m => console.log(m),
});

export default {
  name: "Tesseract",
  components: {
    editor: require('vue2-ace-editor'),
  },
  data() {
    return {
      content: '',
      options : {
        tabSize:2,
        fontSize:20,
        showPrintMargin:false,   //去除编辑器里的竖线
      },
      imageUrl: require('@/assets/testocr.png')
    }
  },
  methods: {
    editorInit() {
      require('brace/ext/language_tools') //language extension prerequsite...
      // 语言
      require('brace/mode/sql')    //language
      // 主题
      require('brace/theme/monokai')
      // 自动补全
      require('brace/snippets/text') //snippet

    },
    async preview() {
      const that = this;
      console.log('111');
      const upload = document.getElementById("uploadFile");
      if(upload.files && upload.files[0]) {
        this.imageUrl = window.URL.createObjectURL(upload.files[0]);
        const text = await this.recognize()
        that.content = text;
      }
    },
    getContent() {
      return this.content;
    },
    recognize: async () => {
      const img = document.getElementById('text-img');
      console.log(img);
      await worker.load();
      await worker.loadLanguage('eng');
      await worker.initialize('eng', OEM.LSTM_ONLY);
      await worker.setParameters({
        tessedit_pageseg_mode: PSM.SINGLE_BLOCK,
      });
      const { data: { text } } = await worker.recognize(img);
      console.log(text);
      return text;
    }
  },
  async mounted() {
    const that = this;
    const text = await this.recognize()
    that.content = text;
  }
}
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 1100px;
}
img{
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
}

.file-upload {
  width: 100px;
  height: 30px;
  position: relative;
  overflow: hidden;
  border: 1px solid #0F996B ;
  display: inline-block;
  border-radius: 4px;
  font-size: 15px;
  color: #0F996B;
  text-align: center;
  line-height: 30px;
  float: left;
  margin: 10px 0 10px auto;
}
.file-upload-input {
  background-color: transparent;
  position: absolute;
  width: 999px;
  height: 999px;
  top: -10px;
  right: -10px;
  cursor: pointer;
}
</style>