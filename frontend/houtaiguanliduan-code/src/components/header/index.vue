<template>
  <div class="header_box">
    <div class="header_left">
      <img src="../../assets/logo.png"
           alt="">
      <span>太博甄选后台管理系统</span>
    </div>
    <el-button type="danger"
               @click="loginoutsystem">退出</el-button>
  </div>
</template>

<script>
export default {
  data () {
    return {}
  },

  created () { },
  mounted () {
  },
  methods: {
    // 退出登录功能
    loginoutsystem () {
      window.localStorage.clear()
      this.$router.replace('/login')
    },
  },
};
</script>

<style lang="less" scoped>
.header_box {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  .el-button {
    font-size: 18px;
  }
  .header_left {
    height: 100%;
    display: flex;
    align-items: center;
    img {
      width: 40px;
      height: 40px;
      margin-right: 10px;
    }
    span {
      font-size: 22px;
      display: inline-block;
      color: #fff;
    }
  }
}
</style>
