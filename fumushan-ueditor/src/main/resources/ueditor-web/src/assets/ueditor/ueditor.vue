<template>
  <div>
    <script type="text/plain" ref="editor"></script>
  </div>
</template>

<script>

  import "../../../static/ueditor/ueditor.config.js"
  import "../../../static/ueditor/ueditor.all.js"
  import "../../../static/ueditor/ueditor.parse.js"

  export default {
    props: {
      editorId: {
        type: String
      },
      config: {
        type: Object
      },
      content: {
        type: String
      }
    },
    data() {
      return {
        editor: null
      }
    },

    //初始化UE
    mounted() {
      this.$nextTick(
        function f1() {
          this.$refs.editor.id = this.editorId;
          this.editor = UE.getEditor(this.editorId, this.config);
          this.editor.ready(function f2() {
            if (this.content) {
              this.editor.setContent(this.content);
            }
          });
        }
      );
    },
    /*mounted() {
      const _this = this;
      this.editor = UE.delEditor("editor");
      this.editor = UE.getEditor("editor", this.config);
    },*/

    //摧毁UE
    destoryed() {
      this.editor.destory();
    },

    methods: {
      getUEContent: function () {
        return this.editor.getContent();
      },
    }

  }
</script>
