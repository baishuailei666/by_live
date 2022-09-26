<template>
  <div class="login">
    <div class="main"
         v-loading="loadingData">
      <div class="banner_box">
        <div class="banner_content">
          <img src="../assets/login/login-logo.687905f.png"
               alt="">
          <div class="content">
            <div class="content_left">
              <img src="../assets/login/login-gongsi.png"
                   alt="">
            </div>
            <div class="login-content-right">
              <div class="select-btn">
                <el-button type="text"
                           :class="type=='code'?'button-select':''"
                           @click="typeSwitchBtn('code')">验证码登录</el-button>
                <el-button type="text"
                           :class="type=='password'?'button-select':''"
                           @click="typeSwitchBtn('password')">密码登录</el-button>
              </div>
              <el-form ref="elLoginCodeForm"
                       :model="codeLoginInfo"
                       :rules="codeRules"
                       v-if="type=='code'">
                <!-- 用户名 -->
                <el-form-item prop="mobile"
                              class="el-input el-input--prefix">
                  <el-input type="text"
                            placeholder="请输入手机号"
                            v-model="codeLoginInfo.mobile"
                            autocomplete="off"></el-input>
                  <span class="el-input__prefix">
                    <i class="iconfont el-icon-mobile-phone"></i>
                  </span>
                </el-form-item>
                <!-- 验证码 -->
                <el-form-item prop="code"
                              class="el-input el-input--prefix">
                  <el-input placeholder="请输入验证码"
                            v-model="codeLoginInfo.code">

                  </el-input>
                  <span class="el-input__prefix">
                    <i class="iconfont el-icon-mobile"></i>
                  </span>
                  <el-button type="primary"
                             disabled
                             class="yanzhengma">{{'('+timeData+'s)后重新获取'}}</el-button>
                  <el-button type="primary"
                             v-if="!timeType"
                             class="yanzhengma"
                             @click="getCodeFun">获取验证码</el-button>
                </el-form-item>
              </el-form>
              <el-form ref="elLoginPasswordForm"
                       :model="passwordLoginInfo"
                       :rules="passwordRules"
                       v-if="type=='password'">
                <!-- 用户名 -->
                <el-form-item prop="mobile"
                              class="el-input el-input--prefix">
                  <el-input type="text"
                            v-model="passwordLoginInfo.mobile"
                            placeholder="请输入手机号"
                            autocomplete="off"></el-input>
                  <span class="el-input__prefix">
                    <i class="iconfont el-icon-mobile-phone"></i>
                  </span>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="pwd"
                              class="el-input el-input--prefix">
                  <el-input :type="typeTeap"
                            placeholder="请输入密码"
                            v-model="passwordLoginInfo.pwd"
                            autocomplete="off"></el-input>
                  <i @click="passwordDisplay"
                     :class="(typeTeap=='password')?'el-icon-view':'el-icon-view blueStyle'"></i>
                  <span class="el-input__prefix">
                    <i class="iconfont el-icon-lock"></i>
                  </span>
                </el-form-item>
              </el-form>

              <el-button type="primary"
                         class="submitBtn"
                         @click="submitForm()">登&nbsp;&nbsp;录</el-button>
            </div>
          </div>
        </div>
      </div>
      <div class="describe_box">
        <ul class="describe_items">
          <li>
            <div class="box">
              <img src="../assets/login/quan1.cd91c2a.png"
                   alt="">
            </div>
            <p class="title">学习中心 从入门到精通</p>
            <p class="content">数百部电商教学视频，让你从抖音小白成为资深运营，打造属于自己的抖音小店</p>
          </li>
          <li>
            <div class="box">
              <img src="../assets/login/quan2.dd101ad.png"
                   alt="">
            </div>
            <p class="title">直播教学 大师实操指导</p>
            <p class="content">抖音认证高级讲师在线指导，帮您在线答疑解惑，电商路上少走弯路，快速崛起</p>
          </li>
          <li>
            <div class="box">
              <img src="../assets/login/quan4.3e28068.png"
                   alt="">
            </div>
            <p class="title">平台担保 公平公正安全</p>
            <p class="content">官方平台备案，安全支付无忧，签署服务合同，按时按量完成客户指定服务</p>
          </li>
          <li>
            <div class="box">
              <img src="../assets/login/quan5.d788cc8.png"
                   alt="">
            </div>
            <p class="title">万千主播 任你挑任你选</p>
            <p class="content">主播择优推荐合作，成千上万主播联系方式提供，客户也可自行联系平台主播合作</p>
          </li>
          <li>
            <div class="box">
              <img src="../assets/login/quan6.9c3cd09.png"
                   alt="">
            </div>
            <p class="title">老品涨销 新品破零倍增</p>
            <p class="content">解决老品滞销问题，带动店铺新品快速起量，实现店铺的快速增涨，缩短店铺新手期</p>
          </li>
          <li>
            <div class="box">
              <img src="../assets/login/quan7.ee2a492.png"
                   alt="">
            </div>
            <p class="title">打造爆款 长尾分发效应</p>
            <p class="content">海量主播种草，矩阵直播增加产品曝光热度，快速提升主播采集频次，从而引爆产品</p>
          </li>
        </ul>
      </div>
      <footer>
        <p>Copyright © 2022 太博甄选 版权所有</p>
        <a href="https://beian.miit.gov.cn/#/Integrated/recordQuery"
           target="_blank">浙ICP备2022023781号</a>
        <a target="_blank"
           href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=33011002016651"
           style="display: inline-block; text-decoration: none; height: 20px; line-height: 20px;"><img src="../assets/login/88ae4b774e1a5a7c9273a50c63820d5.png"
               style="float: left;">
          <p style="float: left; height: 20px; line-height: 20px; margin: 0px 0px 0px 5px; color: rgb(147, 147, 147);">浙公网安备 33011002016651号</p>
        </a>
      </footer>
    </div>
  </div>
</template>

<script>
import { login, getCode, price, info } from "@/api/index.js";
export default {
  data () {
    return {
      typeTeap: 'password',//控制密码输入框的状态，是显示还是隐藏
      type: 'code',//用于判断现在选择的是那种登录方式
      codeLoginInfo: {//验证码登录要传的对象
        mobile: '',
        code: '',
      },
      codeRules: {//验证码登陆表单提交的校验规则
        mobile: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          // { pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/, message: '请输入正确格式的手机号' }
           { min:11,max:11, message: '请输入正确格式的手机号', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
        ]
      },
      passwordLoginInfo: {//密码登录要传的数据
        mobile: '',
        pwd: '',
      },
      passwordRules: {//密码登陆表单提交的校验规则
        mobile: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          // { pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/, message: '请输入正确格式的手机号' }
          { min:11,max:11, message: '请输入正确格式的手机号', trigger: 'blur' }
        ],
        pwd: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ]
      },
      promotion: null,//要传给后台的业务员id
      timeType: false,//现在是否有时间倒计时
      timeData: 300,//倒计时时间数据
      intervalBtn: {},//定时器；
      loadingData: false,//loading效果
    };
  },
  created () {
    let promotionTeap = window.location.href.split('?')[1]
    // console.log(promotionTeap)
    if (promotionTeap) {
      this.promotion = promotionTeap.split('=')[1]
    }
    this.judgeInfo()
    // console.log(promotion)
  },
  methods: {
    async  judgeInfo () {
      let res = await info()
      if (res.data) {
        this.$router.replace({ path: '/workbench' })
      }
    },
    // 密码的显示与隐藏功能
    passwordDisplay () {
      if (this.typeTeap == "password") {
        this.typeTeap = "text"
      } else {
        this.typeTeap = "password"
      }
    },
    // 1、切换到验证码登录
    typeSwitchBtn (typeTeap) {
      this.type = typeTeap;
      // this.$refs.elLoginCodeForm.resetFields();//清空校验结果
      // this.$refs.elLoginPasswordForm.resetFields();//清空校验结果
    },
    // 2、获取验证码
    async getCodeFun () {

      if (this.codeLoginInfo.mobile) {
        this.timeType = true;
        this.getSecond()
        let params = {
          mobile: this.codeLoginInfo.mobile,
        };
        let res = await getCode(params)
        if (res.code == 200) {
          this.$message({
            type: "success",
            message: '验证码发送成功',
          });

        } else {
          this.$message({
            type: "error",
            message: res.msg,
          });
        }
      } else {
        this.$message({
          type: "error",
          message: '请先输入手机号',
        });
      }
    },
    getSecond () {
      let that = this;
      this.intervalBtn = setInterval(() => {
        if (that.timeData <= 0) {
          // 清除定时器
          clearInterval(that.intervalBtn)
          that.timeType = false
          that.timeData = 300
          return //如果不return还会在进行减1
        }
        // 倒计时
        that.timeData--
      }, 1000)
    },
    // 3、登录方法
    submitForm () {
      this.loadingData = true;
      let promotionTeap = window.location.href.split('?')[1]
      // console.log(promotionTeap)
      if (promotionTeap) {
        this.promotion = promotionTeap.split('=')[1]
      }
      if (this.type == 'code') {
        this.$refs.elLoginCodeForm.validate(async valid => {
          if (valid) {
            let params = {
              mobile: this.codeLoginInfo.mobile,
              code: this.codeLoginInfo.code,
              source: 'merchant',
              promotion: this.promotion
            }; //这个传给后台用的做的一个临时对象变量
            // this.$router.push("/home");
            let res = await login(params)
            // console.log(res)
            if (res.code == 200) {

              localStorage.setItem('days', res.data.days)
              localStorage.setItem('userInfo', JSON.stringify(res.data))
              let res1 = await price()
              if (res1.code == 200) {
                localStorage.setItem('priceData', JSON.stringify(res1.data))
                this.$message({
                  type: "success",
                  message: '登录成功',
                });
                this.$nextTick(() => {
                  this.loadingData = false
                  this.$router.push("/home");
                })
              }
            } else {
              this.loadingData = false
              this.$message({
                type: "error",
                message: res.msg,
              });
            }
          } else {
            this.$message.error("请输入用户名或验证码！");
          }
        });
      } else {
        this.$refs.elLoginPasswordForm.validate((valid) => {
          if (valid) {
            let params = {
              mobile: this.passwordLoginInfo.mobile,
              pwd: this.passwordLoginInfo.pwd,
              source: 'merchant',
              promotion: this.promotion
            }; //这个传给后台用的做的一个临时对象变量
            // this.$router.push("/home");
            login(params).then(async res => {
              // console.log(res)
              if (res.code == 200) {
                localStorage.setItem('days', res.data.days)
                localStorage.setItem('userInfo', JSON.stringify(res.data))
                let res1 = await price()
                if (res1.code == 200) {
                  localStorage.setItem('priceData', JSON.stringify(res1.data))
                  this.$message({
                    type: "success",
                    message: '登录成功',
                  });
                  this.$nextTick(() => {
                    this.$router.push("/home");
                  })
                }
              } else {
                this.loadingData = false
                this.$message({
                  type: "error",
                  message: res.msg,
                });
              }
            });
          } else {
            this.$message.error("请输入用户名或密码！");
          }
        });
      }
    }
  },
};
</script>

<style lang="less" scoped>
.login {
  width: 100%;
  height: 100%;
  position: relative;
  .main {
    .banner_box {
      background-image: url(../assets/login/login-bg.00c7fb4.png);
      height: 450px;
      background-repeat: no-repeat;
      background-size: cover;
      .banner_content {
        width: 1272px;
        height: 450px;
        margin: 0 auto;
        box-sizing: border-box;
        position: relative;
        > img {
          position: absolute;
          top: 30px;
          left: 40px;
          width: 164px;
          height: 45px;
          max-height: 45px;
          display: block;
          z-index: 2;
        }
        .content {
          height: 450px;
          background-repeat: no-repeat;
          background-size: cover;
          background-position: 50%;
          padding-top: 90px;
          box-sizing: border-box;
          width: 1200px;
          margin: 0 auto;
          display: flex;
          align-items: center;
          // .content_left {
          // }
        }
      }
    }
    .describe_box {
      width: 100%;
      background-color: #fff;
      .describe_items {
        display: flex;
        justify-content: center;
        justify-content: space-between;
        flex-wrap: wrap;
        max-width: 760px;
        margin: 0 auto;
        padding-top: 15px;
        li {
          box-sizing: border-box;
          width: 220px;
          height: 210px;
          padding: 32px 20px 30px;
          text-align: center;
          margin-bottom: 15px;
          margin-right: 30px;
          background: #fff;
          box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.04);
          cursor: pointer;
          transition: all 0.4s ease 0s;
          .box {
            width: 58px;
            height: 58px;
            background-color: #fff6f0;
            margin: 0 auto;
            img {
              width: 58px;
              height: 58px;
            }
          }
          .title {
            font-size: 16px;
            font-weight: 700;
            margin-top: 16px;
          }
          .content {
            font-size: 12px;
            line-height: 17px;
            font-weight: 400;
            text-align: center;
            color: #333;
            margin-top: 6px;
          }
        }
        li:hover  {
          box-shadow: 2px 2px 10px 2px rgba(152, 46, 130, 0.1);
          margin-top: -6px;
        }
      }
    }
    footer {
      word-break: break-all;
      height: 60px;
      padding: 0 208px;
      background-color: #fff;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 14px;
      padding-bottom: 10px;
      a {
        margin-left: 10px;
      }
    }
  }
}
</style>
<style>
.el-icon-view {
  position: absolute;
  top: 20px;
  right: 12px;
  cursor: pointer;
  font-size: 20px;
  color: #dcdfe6;
}
.blueStyle {
  color: #5cb6ff;
}

.login-content-right {
  width: 330px;
  height: 330px;
  margin-top: -80px;
  background-color: #fff;
  margin-left: 204px;
  margin-left: auto;
  text-align: center;
  padding: 16px;
  padding-top: 20px;
  box-sizing: border-box;
  position: relative;
  border-radius: 6px;
}
.login-content-right .el-button--text:focus,
.login-content-right .el-button--text:hover {
  color: rgb(152, 46, 130);
}
.login-content-right .select-btn .button-select {
  color: rgb(152, 46, 130);
}
.select-btn {
  display: flex;
  justify-content: space-around;
  /* align-items: center; */
  height: 70px;
}
.select-btn .el-button {
  font-size: 20px;
  font-weight: 600;
  color: #45474d;
}
/* .select-btn .button-select {
  color: #409eff;
} */
.yanzhengma {
  width: 126px;
  padding-left: 0px !important;
  padding-right: 0px !important;
  position: absolute;
  top: 0px;
  right: 0px;
  height: 100%;
}
.yanzhengma.el-button--primary {
  color: #fff;
  background-color: rgb(152, 46, 130);
  border-color: rgb(152, 46, 130);
}
.yanzhengma.el-button--primary:focus,
.yanzhengma.el-button--primary:hover {
  background-color: rgb(193, 130, 180);
  border-color: rgb(152, 46, 130);
}
.yanzhengma.el-button--primary.is-disabled,
.yanzhengma.el-button--primary.is-disabled:active,
.yanzhengma.el-button--primary.is-disabled:focus,
.yanzhengma.el-button--primary.is-disabled:hover {
  padding-left: 0px;
  padding-right: 0px;
  background-color: rgb(193, 130, 180);
  border-color: rgb(193, 130, 180);
}
.login-content-right .el-form-item {
  margin-bottom: 31px;
}
.login-content-right .el-input--prefix {
  margin-bottom: 24px;
}
.login-content-right .el-input__prefix {
  line-height: 54px;
  font-size: 16px;
}
.login-content-right .el-input__inner {
  height: 54px;
  font-size: 16px;
  background-color: #fff !important;
}
.login-content-right .submitBtn {
  width: 80%;
  height: 50px;
  font-size: 18px;
  position: absolute;
  line-height: 40px;
  padding: 0;
  bottom: 30px;
  left: 50%;
  transform: translateX(-50%);
  color: #fff;
  background-color: rgb(152, 46, 130);
  border-color: rgb(152, 46, 130);
}
.login-content-right .submitBtn:focus,
.login-content-right .submitBtn:hover {
  background: rgb(193, 130, 180);
  border-color: rgb(193, 130, 180);
  color: #fff;
}
.el-loading-spinner .path {
  stroke-dashoffset: 0;
  stroke-width: 2;
  stroke: rgb(152, 46, 130) !important;
  stroke-linecap: round;
}
</style>