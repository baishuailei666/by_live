<template>
  <div class="header_box">
    <div class="core">
      <!-- a标签也是可以跳转路由的，只有特定场景下用，因为有的按钮不是说，直接点击就进行跳转 -->
      <a href="/notification-center">
        <el-badge :is-dot="notificationDataType"
                  class="item">通知中心</el-badge>
      </a>
    </div>
    <div class="user">
      <!-- 头像 -->
      <div class="avatar">
        <img src="../../assets/home/132.jpg"
             alt=""
             srcset="">
      </div>
      <!-- 账号 -->
      <el-dropdown>
        <span class="pointer el-dropdown-link">
          <!-- p标签里面的数据是活的 -->
          <p style="padding-right: 4px;">
            {{mobileData}}
          </p>
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="loginoutsystem">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { logout } from "@/api/index.js";
export default {
  data () {
    return {
      notificationDataType: true,//用于判断是否有未读的通知
      mobileData: '',//用户手机号
    }
  },

  created () {
    this.mobileData = JSON.parse(localStorage.getItem('userInfo')).mobile
  },
  mounted () {
  },
  methods: {
    // 退出登录功能（这个退出不够完善，可能要调取一下后台的接口，然后再进行退出功能）
    async loginoutsystem () {
      let params = {
        tag:'merchant'
      }
      let res = await logout(params)
      if (res.code == 200) {
        window.sessionStorage.clear()
        this.$router.replace('/login')
      }
    },
  },
};
</script>

<style lang="less" scoped>
.header_box {
  height: 100%;
  padding: 0 17px 0 200px;
  display: flex;
  min-width: 1320px;
  align-content: center;
  background-color: #fff;
  .core {
    margin-right: 50px;
    display: flex;
    align-items: center;
    margin-left: auto;
    font-size: 14px;
  }
  .user {
    display: flex;
    align-items: center;
    box-sizing: border-box;
    .avatar {
      width: 32px;
      height: 32px;
      overflow: hidden;
      border-radius: 50%;
      border: 2px solid #fff;
      box-shadow: 0 1px 3px 0 rgb(61, 207, 248, 0.31);
      margin-right: 16px;
      box-sizing: border-box;
      img {
        width: 100%;
        height: 100%;
      }
    }
    .pointer {
      cursor: pointer;
      display: flex;
    }
  }
}
.el-dropdown-menu__item:not(.is-disabled):hover {
  background-color: #fff4e9;
  color: rgb(152, 46, 130);
}
</style>
