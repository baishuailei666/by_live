<template>
  <div style="height: 100%">
    <el-container style="height: 100%">
      <el-header>
        <headers />
      </el-header>
      <el-container>
        <el-aside width="200px">
          <asides />
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { msg } from "@/api/index.js";
import asides from "@/components/aside/index.vue";
import headers from "@/components/header/index.vue";
export default {
  components: {
    asides,
    headers,
  },
  data () {
    return {
      notice: '有一个待审核店铺'
    }
  },
  created () {

  },
  mounted () {
    this.noticeFun()
    var that = this
    setInterval(function () { that.noticeFun() }, 5000)
  },
  methods: {
    // 通知方法
    async noticeFun () {
      let params = {
        source: 'back',
      };
      let res = await msg(params)
      if (res.code == 200) {
        // console.log(res)
        if (res.data) {
          this.$notify({
            title: '信息提示',
            customClass: "notifyTips",
            message: this.notice,
            position: 'bottom-right',
            duration: 0
          });
        }
      }
    },
  }
}
</script>

<style lang="less" scoped>
/deep/ .el-header {
  background-color: #353d41;
}
.el-container {
  height: 100%;
  .el-aside {
    background-color: #323744;
    color: #ccc;
  }
  .el-main {
    position: relative;
    height: 100%;
    background-color: #e9edf1;
    padding-bottom: 60px;
    // overflow-y: hidden;
  }
  // .el-main::after {
  //   content: "@51直播";
  //   font-size: 20px;
  //   width: 100%;
  //   position: absolute;
  //   bottom: 0;
  //   left: 0;
  //   text-align: center;
  // }
}
</style>
<style>
.el-notification__content {
  color: #fe6c6f !important;
}
</style>