<template>
  <div style="height: 100%;">
    <el-container style="height: 100%;">
      <el-header style="height: 64px;">
        <headers />
      </el-header>
      <el-container class="content">
        <aside width="200px;height: 100%;">
          <asides />
        </aside>
        <div class="customer-service-phone-btn">
          <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACwAAAAsCAYAAAAehFoBAAAAAXNSR0IArs4c6QAAAARzQklUCAgICHwIZIgAAAJKSURBVFiF1Zk9loMgEMcHdpV2b7KN0lBQ5F4W3iuFRRrMhTQ+2GbJIwrKl8b8On3q/N84DDMDUkp9wQfxnfNjjDH+eDy4vi6KogMAuN1uXS4bKNXDlNIGAEBK2awaQqgry7JNFZ8kmFLabAmdgzFuhRBtrM0owYwxPo5jo5Ti209bjCZ4O1hwjFddxHg7SDBjjA/DcHUZBwAwBTDGOADA2t+43+9BCz9IcF3XV5thH0+5wijUy96CbaGAEOr6vr/4GnN9J0S0l2BXKIT+Tk1VVVPst3CMQQAAQkiQZ1+M/se7ic7nm+/6PDSO4yIUUjYAIUSLEIp630twbL5dYy7YN1U644ZS2iiluE1sWZbRO9UaVVVNW5uK1cN6Je/hWY0ujOYopfgwDFdXTC8E13V9zbWTpSClbOq6XmSmF8E6DI6TtY5Sis89/RTMGONn8OwcKWVjin4KnqeuM2H+dWy7eTYWgnVVdWa0xuit+Wh0r4jNizOj8zY2Lz4BDJC3Dd8LrfEZw7HV0xGY2j5CsFlsPQULIVpbYf1uMMYvldtLWksprPcAIdTNe71FHu77/nIGT2OMW1uDa904hBAtIeRi83Zqe7QFQqhb66K9u+Zpmn6VUj8pczET24zDp3P2aq3/PbprbPuunbfUEoyxRa/o2ye+RbBrPufD4YKtfdos166RPIH3xTWmDZ3PZT3jsLF1pBA648guOGQ6HxIKmuyCfRZUypHB7iExJ/VQJnuWcNUhGOOWEHJJ3Sl3yRLmAWNRFFlrj8PSWi7+AGg4dqLaFfcWAAAAAElFTkSuQmCC"
               alt="">
          <div class="phone-text">联系客服</div>
        </div>
        <div class="customer-service-phone"
             style="">
          <div class="phone-box">
            <div class="phone">客服电话：</div>
            <div class="phone"
                 v-if="kef1">{{kef1}}</div>
            <div class="phone"
                 v-if="kef2">{{kef2}}</div>
            <div class="time">{{'服务时间（工作日）：'+kefTime}}</div>
          </div>
          <div class="sanjiao"></div>
        </div>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { getKef } from "@/api/index.js";
import asides from "@/components/aside/index.vue";
import headers from "@/components/header/index.vue";
export default {
  components: {
    asides,
    headers,
  },
  data () {
    return {
      kef1: '',
      kef2: '',
      kefTime: '',//工作时间
    }
  },
  created () {
    this.getKefData()
  },
  methods: {
    // 1、获取客服信息的接口调用
    async getKefData () {
      let res = await getKef()
      // console.log(res)
      if (res.code == 200) {
        this.kef1 = res.data.kef1;
        this.kef2 = res.data.kef2;
        this.kefTime = res.data.kefTime;
        localStorage.setItem('opeUserWx', JSON.stringify(res.data.opeUserWx))
      }
    },
  }
};
</script>

<style lang="less" scoped>
/deep/ .el-header {
  min-width: 1280px;
  width: 100%;
  background-color: #fff;
  height: 64px;
  box-shadow: 0 0 5px 0 rgba(0, 0, 0, 10%);
  padding: 0px;
  position: relative;
  top: 0;
  left: 0;
}
.content {
  height: 100%;
  aside {
    position: fixed;
    z-index: 999;
    top: 0;
    left: 0;
    box-shadow: 4px 0 8px 0 rgba(0, 0, 0, 6%);
    padding: 0 0 15px;
    width: 200px;
    height: 100%;
    box-sizing: border-box;
  }
  .customer-service-phone-btn {
    width: 54px;
    height: 54px;
    background-color: #fff;
    position: fixed;
    z-index: 99;
    bottom: 100px;
    right: 16px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 11%);
    border-radius: 4px;
    img {
      width: 22px;
      height: 22px;
    }
    .phone-text {
      color: #000;
      font-size: 11px;
      margin-top: 4px;
    }
  }
  .customer-service-phone {
    display: none;
    width: 212px;
    height: 80px;
    position: fixed;
    z-index: 99;
    bottom: 87px;
    right: 78px;
    .phone-box {
      width: 200px;
      height: 120%;
      background-color: #fff;
      border-radius: 5px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 9%);
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      line-height: 20px;
      .time {
        font-size: 12px;
        color: #333;
      }
    }
    .sanjiao {
      width: 0;
      height: 0;
      border-top: 12px solid transparent;
      border-bottom: 12px solid transparent;
      border-left: 12px solid #fff;
      position: absolute;
      right: 0;
      top: 27px;
    }
  }
  .customer-service-phone-btn:hover + .customer-service-phone {
    display: block;
  }
  .el-main {
    margin-left: 200px;
    overflow: visible;
    padding: 15px;
  }
}
</style>