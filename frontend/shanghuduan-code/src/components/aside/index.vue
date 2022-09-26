<template>
  <div class="aside_box">
    <div @click="tabIndex"
         class="logo">
      <img src="../../assets/home/logo.dd8956c.png"
           alt=""
           srcset="">
    </div>
    <div class="menu">
      <el-scrollbar style="height: 100%">
        <el-menu style="height: 100%"
                 class="el-menu-vertical-demo"
                 :router="true"
                 background-color="#fff"
                 active-text-color="rgb(152,46,130)"
                 :default-active="$route.path">
          <!-- 工作台 -->
          <el-menu-item index="/workbench"
                        class="marginItem">
            <i class="el-icon-monitor"></i>
            <span>工作台</span>
          </el-menu-item>
          <!-- 店铺管理 -->
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-takeaway-box"></i>
              <span>店铺管理</span>
            </template>
            <el-menu-item index="/personal-stores"
                          class="menuItem"
                          key="/personal-stores">我的店铺</el-menu-item>
            <el-menu-item index="/bind-store"
                          class="menuItem"
                          key="/bind-store">绑定店铺</el-menu-item>
          </el-submenu>
          <!-- 主播广场 -->
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>主播广场</span>
            </template>
            <el-menu-item index="/find-anchor"
                          class="menuItem"
                          key="/find-anchor">找主播</el-menu-item>
            <el-menu-item index="/viewed"
                          class="menuItem"
                          key="/viewed">已查看</el-menu-item>
          </el-submenu>
          <!-- 开通服务 -->
          <el-menu-item index="/open-service"
                        class="marginItem">
            <i class="el-icon-shopping-cart-2"></i>
            <span>开通服务</span>
          </el-menu-item>
          <!-- 学习中心 -->
          <el-menu-item index="/learning-center"
                        class="marginItem">
            <i class="el-icon-video-play"></i>
            <span>学习中心</span>
          </el-menu-item>
          <!-- 订单记录 -->
          <el-menu-item index="/order-record"
                        class="marginItem">
            <i class="el-icon-tickets"></i>
            <span>订单记录</span>
          </el-menu-item>
          <!-- 个人中心 -->
          <el-menu-item index="/personal"
                        class="marginItem">
            <i class="el-icon-house"></i>
            <span>个人中心</span>
          </el-menu-item>
        </el-menu>
      </el-scrollbar>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      menus: [],
      arr: [],
    };
  },
  created () {
    let menus = localStorage.getItem("menus");
    this.menus = JSON.parse(menus);
    // console.log(menus)
  },
  methods: {
    // 跳转到工作台
    tabIndex () {

    }
  },
};
</script>

<style lang="less" scoped>
.aside_box {
  height: 100%;
  background-color: #fff;
}
.logo {
  height: 64px;
  padding-left: 22px;
  display: flex;
  align-items: center;
  img {
    height: 38px;
  }
}
.menu {
  height: calc(100vh - 64px);
  display: block;
  box-sizing: border-box;
  /deep/.el-menu {
    width: 200px;
    list-style: none;
    position: relative;
    margin: 0;
    padding-left: 0;
    background-color: #fff;
    box-sizing: border-box;
    border-right: none;
  }
  /deep/.el-submenu {
    width: 200px;
  }
  /deep/.el-submenu__title {
    width: 200px;
    margin: 10px 0px;
  }
  /deep/.el-menu-item {
    width: 200px;
  }
  /deep/.el-submenu.is-active .el-submenu__title {
    color: rgb(152, 46, 130);
    font-weight: 600;
  }
  /deep/.el-submenu.is-active .el-submenu__title i {
    color: rgb(152, 46, 130);
    font-weight: 600;
  }
  /deep/.el-submenu__title:hover {
    background-color: rgba(255, 244, 233) !important;
  }
  .el-menu-item:hover {
    background-color: rgba(255, 244, 233) !important;
  }
  /deep/.menuItem {
    font-size: 14px;
    box-sizing: border-box;
    padding-left: 50px !important;
  }
  .marginItem {
    margin: 10px 0px;
  }
}
</style>