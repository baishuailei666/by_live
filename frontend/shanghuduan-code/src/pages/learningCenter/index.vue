<template>
  <div class="square">
    <header>
      <div class="select">
        <el-button
          size="small"
          :type="anchorTypeValue == 0 ? 'primary' : ''"
          @click="selectAnchorType(0)"
          >全部</el-button
        >
        <el-button
          size="small"
          :type="anchorTypeValue == 1 ? 'primary' : ''"
          @click="selectAnchorType(1)"
          >月卡</el-button
        >
        <el-button
          size="small"
          :type="anchorTypeValue == 2 ? 'primary' : ''"
          @click="selectAnchorType(2)"
          >季卡</el-button
        >
        <el-button
          size="small"
          :type="anchorTypeValue == 3 ? 'primary' : ''"
          @click="selectAnchorType(3)"
          >年卡</el-button
        >
      </div>
    </header>
    <div class="list-wrapper" v-if="learningVideoData">
      <ul class="list" v-loading="loading">
        <li v-for="item in learningVideoData" :key="item.id">
          <div class="box" @click="itemFun(item.id)">
            <!-- <img src="../../assets/learningTupian.jpeg"
               class="avatar"> -->
            <div
              class="avatar"
              :style="{ 'background-image': 'url(' + item.cover + ')' }"
              style="background-size: 100% 100%"
            >
              <!-- ../../assets/we103.png -->
              <!-- <img v-if="item.cover"
                 src="../../assets/L02vd5Jm39.png"
                 alt=""
                 srcset="">
            <img v-else
                 src="../../assets/wel03.png"
                 alt=""
                 srcset=""> -->
            </div>
            <p class="name">{{ item.title }}</p>
          </div>
        </li>
      </ul>
    </div>

    <div v-else style="height: 200px; line-height: 200px; text-align: center">
      暂无数据
    </div>
    <!-- 下面是分页功能elementUI自带的-------------------------------------------------------------------------------------- -->
    <div class="page-box" v-if="learningVideoData" style="padding: 10px 0px">
      <el-pagination
        background
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="6"
        layout="prev, pager, next"
        :total="total"
      >
      </el-pagination>
    </div>
    <!-- 观看视频接口 -->
    <el-dialog
      title=""
      :visible.sync="addRemarksDialogVisible"
      width="50%"
      @close="learningOffDialog"
    >
      <video
        controls
        controlslist="nodownload"
        ref="vueRef"
        style="width: 100%"
        :src="playVideoUrl"
      ></video>
    </el-dialog>
  </div>
</template>

<script>
import { centre, play } from "./api.js";
export default {
  data() {
    return {
      anchorTypeValue: 0, //主播类型切换
      total: 0, //总条数
      loading: true,
      servicePower: false, //用于判断是否开通服务权限
      currentPage: 1, //当前页
      learningVideoData: {}, //学习视频数据
      addRemarksDialogVisible: false, //视频播放弹框；
      playVideoUrl: "", //弹框播放的视频地址
    };
  },
  created() {
    this.getLearningCenterListData();
  },
  methods: {
    // 服务类型切换
    selectAnchorType(anchorTypeTeap) {
      this.anchorTypeValue = anchorTypeTeap;
      this.getLearningCenterListData();
    },
    // 每个视频的点击触发事件
    async itemFun(idTeap) {
      let params = {
        id: idTeap,
      };
      let res = await play(params);
      if (res.code == 200) {
        // console.log(res)
        this.addRemarksDialogVisible = true;
        this.playVideoUrl = res.data;
      } else {
        if (!this.servicePower) {
          this.$confirm(
            "学习视频只对开通了权益的商家开放，开通后即观看视频?",
            "提示",
            {
              confirmButtonText: "立即开通",
              cancelButtonText: "取消",
              type: "warning",
            }
          ).then(() => {
            this.$router.push("/open-service/open-card"); //点击立即开通跳转到“开通服务”页面
          });
        }
      }
    },
    // 视频播放对话框关闭事件
    learningOffDialog() {
      this.$refs.vueRef.pause(); //暂停
    },
    // 学习资源页面数据
    async getLearningCenterListData(currentTep) {
      var that = this;
      if (!currentTep) {
        this.currentPage = 1;
      }
      let params = {
        page: this.currentPage,
        type: this.anchorTypeValue,
      };
      let res = await centre(params);
      // console.log(res)
      if (res.code == 200) {
        that.loading = false;
        that.learningVideoData = res.data;
        that.total = res.total;
      } else {
        that.$message({
          type: "error",
          message: "请开通权限后进行观看",
        });
      }
    },
    // 改变当前所在页的方法
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getLearningCenterListData(val);
    },
  },
};
</script>

<style lang="less" scoped>
.square {
  min-width: 1100px;
  header {
    background-color: #fff;
    border-radius: 10px;
    .select {
      padding: 6px 26px 26px;
      .el-button {
        margin: 10px 10px 0 0;
      }
      .el-button:focus,
      .el-button:hover {
        color: rgb(152, 46, 130);
        border-color: #ffdebd;
        background-color: #fff4e9;
      }
      .el-button--primary:focus,
      .el-button--primary:hover {
        background: rgb(152, 46, 130);
        border-color: rgb(152, 46, 130);
        color: #fff;
      }
      .el-button--primary {
        color: #fff;
        background-color: rgb(152, 46, 130);
        border-color: rgb(152, 46, 130);
      }
    }
  }
  .list-wrapper {
    width: 100%;
    display: flex;
    justify-content: center;
  }
  .list {
    width: 1370px;
    display: flex;
    flex-wrap: wrap;
    min-height: 70vh;
    li {
      box-sizing: border-box;
      width: 425px;
      height: 330px;
      padding: 30px 30px 0 0;
      -webkit-user-select: none;
      -moz-user-select: none;
      -ms-user-select: none;
      user-select: none;
      .box {
        box-sizing: border-box;
        cursor: pointer;
        width: 100%;
        height: 300px;
        background: #fff;
        text-align: center;
        transition: all 0.5s ease 0s;
        .avatar {
          width: 100%;
          height: 222px;
          margin-bottom: 30px;
          background-color: #000;
          position: relative;

          img {
            width: 60px;
            height: 60px;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
          }
        }
        .name {
          width: 100%;
          box-sizing: border-box;
          padding: 0 10px;
          line-height: 1;
          font-size: 14px;
          text-align: center;
          line-height: 20px;
          color: rgb(152, 46, 130);
        }
      }
      .box:hover {
        box-shadow: 2px 2px 10px 2px rgba(152, 46, 130, 0.1);
        margin-top: -6px;
      }
    }
  }
  .page-box {
    justify-content: center;
    display: flex;
    .el-pager {
      li {
        background-color: #fff !important;
      }
    }
  }
}
</style>
<style>
.page-box .el-pagination.is-background .el-pager li {
  margin: 0 5px;
  background-color: #fff;
  color: #606266;
  min-width: 30px;
  border-radius: 2px;
}
.el-loading-spinner .path {
  stroke-dashoffset: 0;
  stroke-width: 2;
  stroke: rgb(152, 46, 130);
  stroke-linecap: round;
}
</style>
