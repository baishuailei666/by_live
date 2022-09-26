<template>
  <div class="pay">
    <div class="nav-title">收银台</div>
    <div class="pay-container">
      <div class="msg">
        <div class="item"><span>支付金额：</span>
          <p class="red">{{'￥'+this.moneyValue}}</p>
        </div>
      </div>
      <div class="select"><span>支付方式：</span>
        <ul>
          <li @click="payModeSelect(1)"
              :class="payModeType==1?'active':''">
            <img src="../../assets/openService/zifubao.png">
            <p>支付宝</p>
            <div class="dit"
                 v-if="payModeType==1">
              <i class="el-icon-check"></i>
            </div>
          </li>
          <li @click="payModeSelect(2)"
              :class="payModeType==2?'active':''">
            <img src="../../assets/openService/weixin.png">
            <p>微信支付</p>
            <div class="dit"
                 v-if="payModeType==2">
              <i class="el-icon-check"></i>
            </div>
          </li>
          <li @click="payModeSelect(3)"
              :class="payModeType==3?'active':''">
            <p>企业如何对公转账？</p>
            <div class="dit"
                 v-if="payModeType==3">
              <i class="el-icon-check"></i>
            </div>
          </li>
        </ul>
      </div>
      <div class="text-tips">
        <!-- <div><span class="start">*</span>点击“立即支付”表示您已阅读并接受<a href="https://taizb.com/protocol/service_agreement.html"
             target="_blank">《授权许可协议》</a> </div> -->
        <div><span class="start">*</span>在线支付成功后重新登录或刷新页面即可自动生效，更多咨询请联系客服</div>
      </div>
      <div class="pay-btn">
        <el-button type="default"
                   class="confirm"
                   @click="payFun">立即支付</el-button>
        <div class="return"
             @click="returnBtn">
          返回
        </div>
      </div>
    </div>
    <!-- 微信扫码支付的弹框 -->
    <el-dialog title="微信支付"
               :visible.sync="erweimaDialogVisible"
               width="30%"
               center
               custom-class="wx-pay"
               :show-close="false"
               @close="erweimaDialogClosed">
      <div style="text-align:center;">
        <canvas ref="myCanvas" />
        <div>使用微信扫描二维码付款</div>
      </div>
    </el-dialog>
    <!-- 对公支付的弹框 -->
    <el-dialog title="企业对公转账"
               :visible.sync="toThePublicDialogVisible"
               custom-class="to-the-public"
               width="40%"
               center>
      <ul class="to-the-public-information">
        <li class="information-item">帐户名称：杭州太博文化传媒有限公司</li>
        <li class="information-item">帐户号码：33050161749500000989</li>
        <li class="information-item">开户银行：中国建设银行股份有限公司杭州西溪支行</li>
      </ul>
      <div class="tips-content">
        <div>1、对公转账方式到账会有延误，建议采用在线支付。</div>
        <div>2、对公转账后，联系客服提供转账凭证以及开通会员服务。</div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { aliPay, wxPay, paySuccess } from "./api.js";
import QrCode from 'qrcode'
export default {
  data () {
    return {
      moneyValue: 0,//他选的的服务需要支付的金额
      payModeType: 1,//支付方式状态
      serviceType: null,//选择的服务类型（这个需要传给后端）
      flowIdTeap: null,//支付时候需要传给后端的flowId
      erweimaUrl: '',//微信支付二维码
      erweimaDialogVisible: false,//控制微信支付二维码的显示与隐藏
      intervalBtn: {},//定时器对象
      toThePublicDialogVisible: false,//控制对公弹框的显示与隐藏
    }
  },

  created () {
    this.serviceType = this.$route.query.type
    this.flowIdTeap = this.$route.query.getFlowIdTeap
    let priceData = JSON.parse(localStorage.getItem('priceData'))
    // console.log(type)
    if (this.serviceType == 1) {//月卡
      this.moneyValue = priceData.monthCard
    } else if (this.serviceType == 2) {//季卡
      this.moneyValue = priceData.seasonCard
    } else {//年卡
      this.moneyValue = priceData.yearCard
    }
  },
  mounted () {
  },
  methods: {
    // 微信支付二维码，窗口关闭事件
    erweimaDialogClosed () {
      this.erweimaUrl = ''
      clearInterval(this.intervalBtn)//关闭弹框也要清除定时器
    },
    // 1、支付方法
    async  payFun () {
      var that = this
      if (this.payModeType == 1) {//支付宝支付
        let params = {
          type: this.serviceType,
          flowId: this.flowIdTeap
        };
        let res = await aliPay(params);
        // console.log(res)
        if (res) {
          // this.runCode(res)
          const div = document.createElement('div')
          /* 此处form就是后台返回接收到的数据 */
          div.innerHTML = res
          document.body.appendChild(div)
          document.forms[0].submit()
        } else {
          this.$message({
            type: "error",
            message: res.msg,
          });
        }
      } else if (this.payModeType == 2) {//微信支付
        let params = {//现在没法用
          type: this.serviceType,
          flowId: this.flowIdTeap
        };
        let res = await wxPay(params);
        // console.log(res)
        if (res.code == 200) {
          // console.log(res)
          this.erweimaDialogVisible = true
          this.erweimaUrl = res.data.codeUrl.codeUrl
          if (!this.erweimaUrl) return
          this.$nextTick(() => {
            // 如果这里 url 写的是网址, 就会跳转到对应网址 (二维码分享效果)
            QrCode.toCanvas(this.$refs.myCanvas, this.erweimaUrl)
            let params1 = {
              orderNo: res.data.orderNo
            }
            that.intervalBtn = setInterval(async () => {
              let res1 = await paySuccess(params1)
              // console.log(res1)
              if (res1.code == 200) {
                // 清除定时器
                clearInterval(that.intervalBtn)
                that.erweimaDialogVisible = false
                that.$router.replace({ path: '/personal', query: { tagType: true } })
                this.$message({
                  type: "success",
                  message: res.msg,
                });
                return //如果不return还会在进行减1
              }
            }, 1500)

          })
        } else {
          this.$message({
            type: "error",
            message: res.msg,
          });
        }
      }
    },
    // 打开新页面方法，不能让其打开新页面，必须在本页面打开；
    // runCode (html) {
    //   var newwin = window.open('', '', '');
    //   newwin.opener = null;
    //   newwin.document.write(html);
    //   newwin.document.close();
    // },

    // 支付方式选择
    payModeSelect (payTeap) {
      if (payTeap != 3) {
        this.payModeType = payTeap
      } else {
        this.payModeType = payTeap
        this.toThePublicDialogVisible = true;
      }

    },
    // 返回到开通服务页面
    returnBtn () {
      this.$router.replace('/open-service')
    }
  },
};
</script>

<style lang="less" scoped>
.pay {
  background-color: #fff;
  border-radius: 15px;
  font-size: 14px;
  min-height: calc(100vh - 80px);
  .nav-title {
    border-bottom: 1px solid #e5e5e5;
    height: 77px;
    line-height: 77px;
    padding: 0 26px;
    font-weight: 500;
    font-size: 18px;
  }
  .pay-container {
    padding: 30px 26px;
    .msg {
      .item {
        margin-bottom: 34px;
        display: flex;
        span {
          margin-right: 48px;
          color: #666;
        }
        .red {
          font-size: 18px;
          color: red;
        }
      }
    }
    .select {
      span {
        color: #666;
      }
      ul {
        display: flex;
        margin-top: 14px;
        li {
          cursor: pointer;
          display: flex;
          align-items: center;
          margin-right: 30px;
          min-width: 92px;
          height: 45px;
          border-radius: 5px;
          border: 1px solid #b7b7b7;
          padding: 0 9px;
          font-size: 10px;
          position: relative;
          img {
            max-width: 100%;
            max-height: 100%;
            width: 25px;
            height: 25px;
            margin-right: 8px;
          }
          .dit {
            display: flex;
            align-items: center;
            justify-content: center;
            position: absolute;
            width: 14px;
            height: 14px;
            background-color: red;
            border-radius: 50%;
            color: #fff;
            right: -7px;
            top: -7px;
          }
        }
        li.active,
        li:hover {
          border-color: red;
        }
      }
    }
    .text-tips {
      margin-top: 20px;
      line-height: 30px;
      color: #666;
      .start {
        display: inline-block;
        color: red;
        font-size: 20px;
        line-height: 26px;
      }
      a {
        text-decoration: none;
        color: rgb(152, 46, 130);
      }
    }
    .pay-btn {
      display: flex;
      align-items: flex-end;
      margin-top: 50px;
      .el-button--default {
        border: none !important;
        color: #fff;
        background-color: rgb(152, 46, 130);
        border-radius: 19px;
        padding: 0;
        transition: all 0.3s;
        box-shadow: 0 2px 4px 0 rgb(255 156 57 / 61%);
        height: 39px;
        line-height: 39px;
        width: 177px;
        font-size: 16px;
      }
      .return {
        cursor: pointer;
        font-size: 20px;
        color: #666;
        margin-left: 60px;
        margin-bottom: 4px;
      }
    }
  }
}
/deep/.el-dialog {
  border-radius: 6px;
}
/deep/.el-dialog__title {
  color: #303133;
  border-radius: 10px;
  font-weight: 600;
}

/deep/.el-dialog__header {
  border-bottom: 1px solid #ccc;
}
.to-the-public-information {
  padding-left: 20px;
  margin-bottom: 20px;
  line-height: 26px;
  .information-item {
    color: #303133;
    list-style: disc;
    vertical-align: middle;
  }
}
.tips-content {
  color: rgb(154, 160, 178);
  line-height: 26px;
}
</style>
<style>
.wx-pay .el-dialog__header {
  background-image: url("../../assets/openService/weixin.png");
  background-repeat: no-repeat;
  background-size: 25px 25px;
  background-position: 39.5% 62%;
}
.wx-pay .el-dialog__title {
  padding-left: 30px;
}
/* .wx-pay .el-dialog__body {
  padding-top: 0px;
} */
.wx-pay canvas {
  width: 68% !important;
  height: 68% !important;
}
</style>