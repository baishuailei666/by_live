<template>
  <div class="service">
    <div class="service-head">
      <div class="left">
        <h2>对接全网带货主播</h2>
        <p>助力商家品牌曝光及产品销售</p>
      </div>
      <div class="right">
        <div class="btn"
             @click="openServiceBtn">
          立即开通
        </div>
      </div>
    </div>
    <div class="service-box">
      <ul>
        <li>
          <div class="service-card">
            <div class="image"><img src="../../assets/openService/serviceImg1.png"></div>
            <div style="width: 50%;">
              <div class="title">学习中心，从入门到精通</div>
              <div class="content">数百部电商教学视频，让你从抖音小白成为资深运营，打造属于自己的抖音小店</div>
            </div>
          </div>
        </li>
        <li>
          <div class="service-card">
            <div class="image"><img src="../../assets/openService/serviceImg2.png"></div>
            <div style="width: 50%;">
              <div class="title">直播教学，大师实操指导</div>
              <div class="content">抖音认证高级讲师在线指导，帮您在线答疑解惑，电商路上少走弯路，快速崛起</div>
            </div>
          </div>
        </li>
        <li>
          <div class="service-card">
            <div class="image"><img src="../../assets/openService/serviceImg3.png"></div>
            <div style="width: 50%;">
              <div class="title">平台担保，公平公正安全</div>
              <div class="content">官方平台备案，安全支付无忧，签署服务合同，按时按量完成客户指定服务</div>
            </div>
          </div>
        </li>
        <li>
          <div class="service-card">
            <div class="image"><img src="../../assets/openService/serviceImg4.png"></div>
            <div style="width: 50%;">
              <div class="title">万千主播，任你挑任你选</div>
              <div class="content">主播择优推荐合作，成千上万主播联系方式提供，客户也可自行联系平台主播合作</div>
            </div>
          </div>
        </li>
        <li>
          <div class="service-card">
            <div class="image"><img src="../../assets/openService/serviceImg5.png"></div>
            <div style="width: 50%;">
              <div class="title">老品涨销，新品破零倍增</div>
              <div class="content">解决老品滞销问题，带动店铺新品快速起量，实现店铺的快速增涨，缩短店铺新手期</div>
            </div>
          </div>
        </li>
        <li>
          <div class="service-card">
            <div class="image"><img src="../../assets/openService/serviceImg6.png"></div>
            <div style="width: 50%;">
              <div class="title">打造爆款，长尾分发效应</div>
              <div class="content">海量主播种草，矩阵直播增加产品曝光热度，快速提升主播采集频次，从而引爆产品</div>
            </div>
          </div>
        </li>
      </ul>
    </div>
    <el-dialog title="选择开通权益的店铺"
               :visible.sync="dialogVisible"
               width="50%">
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      dialogVisible: false,
    }
  },
  created () {

  },
  methods: {
    openServiceBtn () {
      this.$router.push('/open-service/open-card')
    }
  }
}
</script>

<style lang="less" scoped>
.service {
  min-width: 1100px;
  position: relative;
  .service-head {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 160px;
    background-image: url("../../assets/openService/headerbeijing.png");
    background-repeat: no-repeat;
    background-size: cover;
    background-position: 50%;
    color: #fff;
    padding: 0 20px 0 32px;
    .left {
      h2 {
        font-size: 34px;
        font-weight: 700;
        line-height: 1;
      }
      p {
        font-size: 22px;
        margin-top: 20px;
        line-height: 1;
      }
    }
    .right {
      .btn {
        cursor: pointer;
        text-align: center;
        display: block;
        margin: 10px auto 0;
        color: rgb(152, 46, 130);
        width: 144px;
        height: 46px;
        line-height: 46px;
        background: #fff;
        border-radius: 4px;
        font-size: 22px;
      }
    }
  }
  .service-box {
    display: flex;
    margin-top: 20px;
    ul {
      display: flex;
      justify-content: space-between;
      flex-wrap: wrap;
      li {
        width: 49%;
        height: 170px;
        margin-bottom: 20px;
        .service-card {
          cursor: pointer;
          width: 100%;
          height: 170px;
          background-color: #fff;
          border-radius: 4px;
          padding: 24px 20px;
          display: flex;
          align-items: center;
          justify-content: center;
          border: 2px solid #fff;
          transition: all 0.5s ease 0s;
          .image {
            background-color: #f4f4f4;
            width: 58px;
            height: 58px;
            margin-right: 50px;
            border-radius: 4px;
            img {
              width: 100%;
              height: 100%;
            }
          }
          .title {
            font-size: 18px;
            font-weight: 500;
          }
          .content {
            margin-top: 5px;
            color: #666;
            font-size: 14px;
            line-height: 24px;
          }
        }
      }
      .service-card:hover {
        box-shadow: 2px 2px 10px 2px rgba(152, 46, 130, 0.1);
        margin-top: -6px;
        transition: all 0.5s ease 0s;
      }
    }
  }
}
</style>