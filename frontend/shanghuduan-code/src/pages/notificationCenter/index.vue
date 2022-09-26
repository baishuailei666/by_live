<template>
  <div class="my-notice">
    <div class="navTab flex ai-center borderTop">
      <ul class="tabs">
        <li class="active">
          全部消息
          <div class="border-box"></div>
        </li>
      </ul>
    </div>
    <div class="notice-list">
      <!-- 下面加个loading 加到ul上面-->
      <ul v-loading="storesLoading">
        <!-- v-for="(item,index) in notificationCenterData"
            :key="index" -->
        <li v-for="(item,index) in notificationCenterData"
            :key="index">
          <div class="notice-list-left">
            <img src="../../assets/notice/tips.png"
                 alt="">
            <div class="tipsText">{{item.note}}</div>
          </div>
          <div class="notice-list-right">{{item.ts}}</div>
        </li>
      </ul>
      <div class="page-box"
           style="padding: 10px 0px;">
      </div>
    </div>
  </div>
</template>

<script>
import { center } from "./api.js";
export default {
  data () {
    return {
      storesLoading: true,//转圈的东西
      notificationCenterData: {}//通知中心数据
    }
  },
  created () {
    this.getNotificationData()
  },
  methods: {
    async  getNotificationData () {
      let params = {
        page: 1
      }
      let res = await center(params)
      // console.log(res)
      if (res.code == 200) {
        this.storesLoading = false
        this.notificationCenterData = res.data
      }
    }
  }
}
</script>

<style lang="less" scoped>
.my-notice {
  border-radius: 15px;
  overflow: hidden;
  .navTab {
    padding: 0px 26px;
    border-bottom: 1px solid #e5e5e5;
    background-color: #fff;
    display: flex;
    align-items: center;
    .tabs {
      width: 100%;
      height: 46px;
      display: flex;
      align-items: center;
      li {
        position: relative;
        line-height: 46px;
        font-weight: 500;
        font-size: 16px;
        margin-right: 42px;
        cursor: pointer;
        color: rgb(152, 46, 130);
        .border-box {
          position: absolute;
          bottom: -1px;
          left: 50%;
          transform: translateX(-50%);
          font-weight: 600;
          width: 60px;
          height: 4px;
          background: rgb(152, 46, 130);
          border-radius: 2px;
          display: block;
          box-sizing: border-box;
        }
      }
    }
  }
  .notice-list {
    background-color: #fff;
    ul {
      min-height: calc(100vh - 220px);
      li {
        width: 100%;
        height: 80px;
        padding-left: 40px;
        box-sizing: border-box;
        border-bottom: 1px solid #e5e5e5;
        position: relative;
        .notice-list-left {
          position: absolute;
          left: 40px;
          top: 20px;
          img {
            display: inline-block;
          }
          div {
            display: inline-block;
            vertical-align: top;
            line-height: 34px;
            margin-left: 30px;
          }
        }
        .notice-list-right {
          position: absolute;
          top: 30px;
          left: 60%;
          font-size: 14px;
          color: #aaa;
        }
      }
    }
    .page-box {
      padding: 10px 0px;
      display: flex;
      justify-content: center;
    }
  }
}
</style>
<style>
.el-loading-spinner .path {
  stroke-dashoffset: 0;
  stroke-width: 2;
  stroke: rgb(152, 46, 130);
  stroke-linecap: round;
}
</style>