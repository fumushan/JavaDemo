<template>
  <div>
    <h1>编辑Demo</h1>
    <UEditorHelper :editorId="editorId" :config=config content="content" ref="ueditor"></UEditorHelper>
  </div>
</template>


<script>

  export default {

    data() {
      return {
        editorId: "123456",
        config: {
          initialFrameWidth: "80%",
          initialFrameHeight: 320,
        },
        content:"初始化....."
      }
    },


    methods : {
      //获取文档内容
      getContent: function(){
        let content = this.$refs.ueditor.getUEContent();
        console.log(content);
        alert(content);
      }
    }

  }
</script>
