<template>
  <div class="workbench">
    <div class="left">
      <div class="header">
        <div class="tips">
          {{'HI~我是您的专属顾问：' + (userInfo.opeUserRemark?userInfo.opeUserRemark:userInfo.opeUser)}}<br />
          欢迎注册太博甄选，绑定店铺后即可体验太博甄选
        </div>
        <a href="/bind-store"
           class="bind-store">绑定店铺</a>
      </div>
      <div class="content">
        <div class="content-title">选择套餐</div>
        <div class="content-detial">
          <div class="item item-1">
            <div class="box"><span>年卡</span></div>
            <div class="price">{{priceDataBox.yearCard}}</div>
            <div class="old-price">¥39980.00</div>
            <div :class="cardType==3?'radio active':'radio'"
                 @click="active(3)">
              <img src="../../assets/openService/active.png"
                   :style="cardType==3?'':'display: none;'">
            </div>
          </div>
          <div class="item item-2">
            <div class="box"><span>季卡</span></div>
            <div class="price">{{priceDataBox.seasonCard}}</div>
            <div class="old-price">¥12998.00</div>
            <div :class="cardType==2?'radio active':'radio'"
                 @click="active(2)">
              <img src="../../assets/openService/active.png"
                   :style="cardType==2?'':'display: none;'">
            </div>
          </div>
          <div class="item item-3">
            <div class="box"><span>月卡</span></div>
            <div class="price">{{priceDataBox.monthCard}}</div>
            <div class="old-price">¥5980.00</div>
            <div :class="cardType==1?'radio active':'radio'"
                 @click="active(1)">
              <img src="../../assets/openService/active.png"
                   :style="cardType==1?'':'display: none;'">
            </div>
          </div>
        </div>
        <div class="agree">
          <!-- <el-checkbox v-model="checkedValue"
                       @change="checkedValueChange">已阅读并同意</el-checkbox>
          <a href="https://taizb.com/protocol/service_agreement.html"
             target="_blank">《太博甄选商家服务协议》
          </a> -->
        </div>
        <!-- <div class="agree-prompt"
             :style="checkType?'display: none;':''">
          请同意并勾选《太博甄选商家服务协议》
        </div> -->
        <el-button type="success"
                   class="btn"
                   @click="immediatelyOpenBtn"
                   round>立即开通</el-button>
      </div>
    </div>
    <div class="right">
      <div class="service">
        <div class="title">专属顾问</div>
        <img class="img-erweima"
             :src="opeUserWx">
        <p>微信扫码联系我们吧</p>
      </div>
    </div>
    <!-- 选择要签署的电子签类型：企业或者个人 -->
    <el-dialog title="选择合同签署方式"
               :visible.sync="dianziqianDialogVisible"
               width="50%"
               center>
      <div class="selectdianziqian-box">
        <ul class="select-typ">
          <li @click="writeForm(0)">
            <img src="../../assets/electronicSignature/qiye.png"
                 alt="">
            <div>企业签署</div>
          </li>
          <li @click="writeForm(1)">
            <img src="../../assets/electronicSignature/geren.png"
                 alt="">
            <div>个人签署</div>
          </li>
        </ul>
        <div class="select-tips">合同已备案，选择企业签署或个人签署均具有法律效应</div>
        <!-- 下面这个是放logo图 -->
        <div class="dianziqianlogo">
        </div>
      </div>
    </el-dialog>
    <!-- 填写电子签需要用到的数据 -->
    <el-dialog title="开通权益前需要签署《太博甄选商家服务合同》，请确认信息"
               :visible.sync="dianziqianFormDialogVisible"
               width="42%"
               @close="diziqianDialogClosed"
               center>
      <!-- 企业签 -->
      <!-- 只要key值不同就可以做两个校验 -->
      <div v-if="typeValue==0">
        <el-form :model="dianziqianformData"
                 label-position="left"
                 :rules="rules0"
                 key="0"
                 ref="eldianziqianForm0"
                 label-width="150px">
          <el-form-item label="公司名称："
                        prop="subject">
            <el-input v-model="dianziqianformData.subject"
                      placeholder="请输入公司名称"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="统一社会信用代码："
                        prop="tax">
            <el-input v-model="dianziqianformData.tax"
                      placeholder="请输入统一社会信用代码"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="法定代表人经营者："
                        prop="person">
            <el-input v-model="dianziqianformData.person"
                      placeholder="请输入法定代表人经营者"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="法人手机号："
                        prop="mobile">
            <el-input v-model="dianziqianformData.mobile"
                      placeholder="请输入法人手机号"
                      clearable></el-input>
          </el-form-item>
        </el-form>
        <div class="formTips">手机号一定是法人认证的实名认证号码，否则无法签署合同</div>
        <div class="dianziqianlogo">
        </div>
        <div style='text-align:center;'>
          <el-button @click="confirmFrom">确定无误</el-button>
          <el-button class="kongBackground"
                     @click="selectType(1)">切换至个人签署</el-button>
        </div>
      </div>
      <!-- 个人签 -->
      <div v-if="typeValue==1">
        <el-form :model="dianziqianformData"
                 label-position="left"
                 :rules="rules1"
                 key="1"
                 ref="eldianziqianForm1"
                 label-width="130px">
          <el-form-item label="小店名称："
                        prop="subject">
            <el-input v-model="dianziqianformData.subject"
                      placeholder="请输入小店名称"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="签署人身份证："
                        prop="tax">
            <el-input v-model="dianziqianformData.tax"
                      placeholder="请输入签署人身份证"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="签署人姓名："
                        prop="person">
            <el-input v-model="dianziqianformData.person"
                      placeholder="请输入签署人姓名"
                      clearable></el-input>
          </el-form-item>

          <el-form-item label="签署人手机号："
                        prop="mobile">
            <el-input v-model="dianziqianformData.mobile"
                      placeholder="请输入签署人手机号"
                      clearable></el-input>
          </el-form-item>
        </el-form>
        <div class="formTips">个人信息只用作签约腾讯电子合同，请放心填写个人信息</div>
        <div class="dianziqianlogo">
        </div>
        <div style='text-align:center;'>
          <el-button @click="confirmFrom">确定无误</el-button>
          <el-button class="kongBackground"
                     @click="selectType(0)">切换至企业签署</el-button>
        </div>
      </div>
    </el-dialog>
    <!-- 查看合同的弹框 -->
    <el-dialog title="太博甄选商家服务合同"
               :visible.sync="contractTipsDialogVisible"
               width="80%"
               center
               @close="contractTipsDialogClosed">
      <div v-if="documentIdTeap">
        <iframe style="width:100%;height:600px;"
                :src="'/ZBDH/MERCHANT/contract/preview?id='+documentIdTeap"></iframe>
      </div>
      <div style="text-align:center;">
        <el-button :class="contractTipsBtnValue!=0?'contractTipsBtn1':'contractTipsBtn2'"
                   :disabled="contractTipsBtnValue!=0?true:false"
                   @click="submitFun">{{contractTipsBtnValue!=0?contractTipsBtnValue+'s':'确定合同'}}</el-button>
      </div>
    </el-dialog>
    <!-- 二维码的弹框 -->
    <el-dialog title="微信扫码签署合同"
               :visible.sync="erweimaDialogVisible"
               custom-class="farenweixin"
               width="30%"
               center
               @close="erweimaDialogClosed">
      <div style="text-align:center;line-height:22px;">
        <div class="returnStep"
            v-if="!notSignedStatus"
             @click="returnFun">返回上一步</div>
        <el-button @click="SignedFun" style="margin-bottom:14px;">已签署</el-button>
        <!-- <el-button @click="SignedFun">重新发起电子签</el-button> -->
        <div v-if="notSignedStatus" @click="relaunchElectronicSignature" style="color:blue;cursor: pointer;width:160px;hiehgt:20px;line-height:20px;margin:0 auto;">重新发起电子签</div>
        <img :src="erweimaUrl"
             alt="">
        <div>请使用
          <span style="color:#ff2525;">{{phoneTeap}}</span>
          注册的微信扫码，签署合同。
        </div>
        <div>如果该手机号还未注册微信或注册的微信无法使用。</div>
        <div style="margin-bottom:20px;color:#ff2525;">{{'当前已选'+(typeValue==0?'企业签':'个人签')+'， 未签署将无法进行支付。'}}</div>
        <div class="dianziqianlogo">
        </div>
      </div>
      <div class="footerText"
           style="text-align:center;line-height:20px;width:100%;margin-top:10px;">
        电子合同，安全高效、受法律保护
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { content, submit, signCreate, url, status, shop,check } from "./api.js";
export default {
  data () {
    return {
      // checkedValue: false,//判断是否阅读商家服务协议
      // checkType: true,//用于校验是否点击已阅读
      cardType: 2,//判断选择的服务等级类型
      priceDataBox: {
        yearCard: 28999,
        seasonCard: 8999,
        monthCard: 2589
      },//存放各个服务等级价格
      opeUserWx: '',//客服微信二维码
      userInfo: null,//登录时候传入的用户信息
      dianziqianDialogVisible: false, //控制电子签选择模式弹框的显示与隐藏；
      dianziqianFormDialogVisible: false,//电子签form表单提交弹框的显示与隐藏；
      typeValue: null,//当前选择的是那种签0-企业，1-个人
      dianziqianformData: {},//提交电子签的暂存对象
      // 用于校验是否和之前一样
      dianziqianformDataCheck: null,
      rules0: {//校验规则-企业
        subject: [{ required: true, message: "请输入公司名称", trigger: "blur" }],
        tax: [{ required: true, message: "请输入统一社会信用代码", trigger: "blur" }],
        person: [{ required: true, message: "请输入法定代表人经营者", trigger: "blur" }],
        mobile: [{ required: true, message: "请输入法人手机号", trigger: "blur" },
        { pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/, message: '请输入正确格式的手机号' }],
      },
      rules1: {//校验规则-个人
        subject: [{ required: true, message: "请输入小店名称", trigger: "blur" }],
        tax: [{ required: true, message: "请输入签署人姓名", trigger: "blur" }],
        person: [{ required: true, message: "请输入签署人身份证", trigger: "blur" }],
        mobile: [{ required: true, message: "请输入签署人手机号", trigger: "blur" },
        { pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/, message: '请输入正确格式的手机号' }],
      },
      contractTipsDialogVisible: false,//太博甄选商家服务合同弹框提示
      contractTipsBtnValue: 16,
      intervalBtn: {},//定时器对象
      erweimaDialogVisible: false,//二维码弹框的提示
      erweimaUrl: "",//二维码的base64URL地址；
      phoneTeap: null,//用于存放当前签署人的手机号
      flowIdTeap: null,//用于暂时存储flowId
      documentIdTeap: null,//查看合同的id
      notSignedStatus: false,//用于判断是否直接弹出二维码；2022-9-9加

    }
  },
  created () {
    let priceDataTeap = JSON.parse(localStorage.getItem('priceData'))
    if (priceDataTeap) {
      this.priceDataBox = priceDataTeap
    }
    // console.log(this.priceDataBox)
    this.opeUserWx = JSON.parse(localStorage.getItem('opeUserWx'))
    // console.log(this.opeUserWx)
    this.userInfo = JSON.parse(localStorage.getItem('userInfo'))
    // console.log(this.userInfo)
  },
  methods: {
    // 切换签署方式
    selectType (typeTeap) {
      this.typeValue = typeTeap
    },
    //选择好了是那种模式的；企业或者个人，先掉content
    async writeForm (typeTeap) {
      this.typeValue = typeTeap
      let res = await content()
      // console.log(res)
      if (res.code == 200) {
        if (res.data) {
          this.dianziqianformData = res.data
          this.dianziqianformDataCheck = res.data
        }
        this.dianziqianDialogVisible = false
        this.dianziqianFormDialogVisible = true
      } else {
        this.$message({
          type: "error",
          message: res.msg,
        });
      }
    },

    // 确认表单信息方法
    async confirmFrom () {
      let params = this.dianziqianformData
      let res = await submit(params)
      // console.log(res)
      if (res.code == 200) {
        this.dianziqianFormDialogVisible = false
        this.contractTipsDialogVisible = true
        this.phoneTeap = this.dianziqianformData.mobile

        this.getSecond()
        let params = {
          type: this.typeValue,
          buyType: this.cardType
        }
        let res1 = await signCreate(params)
        if (res1.code == 200) {
          // console.log(res1)
          this.flowIdTeap = res1.data.flowId
          this.documentIdTeap = res1.data.documentId
          this.$message({
            type: "success",
            message: '创建合同成功',
          });

        } else {
          this.$message({
            type: "error",
            message: res1.msg,
          });
        }
      } else {
        this.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    // 合同查看完之后进行提交的方法
    async  submitFun () {
      let params1 = {
        flowId: this.flowIdTeap
      }
      let res1 = await url(params1)
      // console.log(res1)
      if (res1.code == 200) {
        this.contractTipsDialogVisible = false
        this.erweimaDialogVisible = true
        this.erweimaUrl = res1.data;
        // console.log(this.erweimaUrl)
      } else {
        this.$message({
          type: "error",
          message: res1.msg,
        });
      }
    },
    // 返回上一步按钮
    returnFun () {
      this.erweimaDialogVisible = false
      this.contractTipsDialogVisible = true
      this.getSecond()
    },
    // 已签署按钮
    SignedFun () {
      this.erweimaDialogVisible = false
    },
    // 二维码弹框关闭事件，关闭之后就先判断是不是扫码签署了电子签，如果没有就跳回选卡页面，签署后跳到支付页面；
    async  erweimaDialogClosed () {
      this.notSignedStatus=false;//关闭就要把这个状态变为false,防止污染其他的东西，回到正常逻辑该有的状态；
      let params = {
        flowId: this.flowIdTeap
      }
      let res = await status(params)
      if (res.data) {
        this.$router.push({ path: '/open-service/cashier', query: { type: this.cardType, getFlowIdTeap: this.flowIdTeap } })
      } else {
        this.$message({
          type: "error",
          message: '由于您没有扫码签署电子签，所以无法进行支付！',
        });
        this.$router.replace({ path: '/workbench' })
      }

    },
    // 合同查看弹框关闭事件
    contractTipsDialogClosed () {
      this.contractTipsBtnValue = 16
      // this.documentIdTeap = null
      clearInterval(this.intervalBtn)//关闭弹框也要清除定时器
    },
    // 倒计时
    getSecond () {
      let that = this;
      this.intervalBtn = setInterval(() => {
        if (that.contractTipsBtnValue <= 0) {
          // 清除定时器
          clearInterval(that.intervalBtn)
          return //如果不return还会在进行减1
        }
        // 倒计时
        that.contractTipsBtnValue--
      }, 1000)
    },
    // 提交表单关闭事件
    diziqianDialogClosed () {
      if (this.typeValue == 0) {
        this.$refs.eldianziqianForm0.resetFields();//清空校验结果
      } else {
        this.$refs.eldianziqianForm1.resetFields();//清空校验结果
      }
      this.dianziqianformData = {}
    },
    // 当阅读状态发生变化时候的事件
    // checkedValueChange () {
    //   if (this.checkedValue) {
    //     this.checkType = true
    //   }
    // },
    // 立即开通按钮
    async immediatelyOpenBtn () {
      // if (!this.checkedValue) {
      //   this.checkType = false
      // } else 
      let res1 = await shop()
      // console.log(res1)
      if (res1.data.auditStatus != '已通过') {//不等于已通过就直接支付
        this.$router.push({ path: '/open-service/cashier', query: { type: this.cardType } })
      } else {
        let res2 = await check()
        // console.log(res2)
        if(res2.data){
          this.flowIdTeap = res2.data.flowId
        }
        if(res2.code==20){
          this.dianziqianDialogVisible = true;
        }else if(res2.code==21){
          this.$router.push({ path: '/open-service/cashier', query: { type: this.cardType, getFlowIdTeap: res2.data.flowId } })
        }else {
          this.notSignedStatus = true;
          this.erweimaDialogVisible = true
          this.erweimaUrl = res2.data.url;
        }
        // console.log(this.userInfo.shopStatus)
        
      }
    },
    // 重新创建发起电子签
    relaunchElectronicSignature(){
      this.erweimaDialogVisible = false
     this.dianziqianDialogVisible = true;
    },
    // 切换服务等级
    active (typeTeap) {
      this.cardType = typeTeap
    }
  }
}
</script>

<style lang="less" scoped>
.workbench {
  display: flex;
  .left {
    flex: 1;
    box-sizing: border-box;
    .header {
      min-width: 940px;
      background-color: #fff;
      display: flex;
      align-items: center;
      padding: 0 40px;
      height: 128px;
      justify-content: space-between;
      margin-bottom: 17px;
      box-sizing: border-box;
      .tips {
        font-size: 18px;
        line-height: 25px;
      }
      .bind-store {
        text-decoration: none;
        min-width: 138px;
        text-align: center;
        height: 38px;
        line-height: 38px;
        background: rgb(152, 46, 130);
        border-radius: 19px;
        font-size: 16px;
        color: #fff !important;
        padding: 0 26px;
        cursor: pointer;
        box-shadow: 0 3px 8px 0 rgb(152, 46, 130, 61%);
      }
    }
    .content {
      min-width: 940px;
      background: #fff;
      border-radius: 10px;
      padding: 0 26px;
      padding-bottom: 40px;
      .content-title {
        font-size: 18px;
        font-weight: 500;
        padding: 20px 0;
      }
      .content-detial {
        min-height: calc(100vh - 600px);
        margin-top: 26px;
        display: flex;
        .item {
          color: #333;
          cursor: pointer;
          background-repeat: no-repeat;
          background-size: 100%;
          background-position: 50%;
          width: 270px;
          height: 120px;
          margin-right: 50px;
          position: relative;
          padding: 22px 22px 0;
          font-weight: 400;
          .box {
            display: flex;
            align-items: center;
            font-size: 18px;
          }
          .price {
            font-size: 17px;
            margin: 14px 0 7px;
          }
          .old-price {
            font-size: 14px;
            text-decoration: line-through;
            color: #999;
          }
          .radio {
            position: absolute;
            width: 20px;
            height: 20px;
            border-radius: 50%;
            overflow: hidden;
            bottom: 12px;
            right: 12px;
            border: 1px solid #fff;
            img {
              max-width: 100%;
              max-height: 100%;
            }
          }
          .radio.active {
            border: none;
          }
        }
        .item-1 {
          background-image: url("../../assets/openService/yearcard.png");
        }
        .item-2 {
          background-image: url("../../assets/openService/seasoncard.png");
        }
        .item-3 {
          background-image: url("../../assets/openService/monthcard.png");
        }
      }
      .agree-prompt {
        margin-top: 5px;
        color: #ff2525;
        display: flex;
        align-items: center;
        justify-content: center;
        height: 20px;
        font-size: 12px;
      }
      .btn {
        display: block;
        margin: 10px auto 0;
        width: 246px;
        height: 53px;
        padding: 0;
        line-height: 53px;
        text-align: center;
        border-radius: 27px;
        color: #fff;
        background: rgb(152, 46, 130);
        font-size: 20px;
        margin-top: 5px;
        border: none;
      }
    }
  }
  .right {
    width: 250px;
    margin-left: 17px;
    .service {
      background-color: #fff;
      padding: 20px 20px 30px;
      .title {
        font-size: 16px;
        font-weight: 700;
      }
      .img-erweima {
        display: block;
        margin: 20px auto 10px;
        width: 80%;
        height: 100%;
      }
      p {
        text-align: center;
      }
    }
  }
  /* 电子签相关的样式 */
  .selectdianziqian-box {
    width: 100%;
    text-align: center;
    user-select: none;
    .select-typ {
      display: flex;
      justify-content: space-around;
      text-align: center;
      li {
        width: 40%;
        background-color: rgb(247, 248, 250);
        border-radius: 10px;
        height: 150px;
        text-align: center;
        box-sizing: border-box;
        padding-top: 20px;
        cursor: pointer;
        border: 2px solid rgb(247, 248, 250);
        transition: all 0.3s;
        img {
          width: 80px;
          height: 80px;
        }
        div {
          width: 100%;
          text-align: center;
        }
      }
      li:hover {
        border: 2px solid rgb(152, 46, 130);
        color: rgb(152, 46, 130);
      }
      margin-bottom: 20px;
    }
    .select-tips {
      color: #ff2525;
      margin-bottom: 20px;
    }
    .dianziqianlogo {
      height: 50px;
      background: url(../../assets/electronicSignature/dianziqianlogo.png) 50%
        0px no-repeat;
      background-size: 200px 40px;
    }
  }
}
</style>
<style>
.agree {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 20px;
  font-size: 12px;
  margin-top: 60px;
}
.agree .el-checkbox__inner:hover {
  border-color: rgb(152, 46, 130);
}
.agree .el-checkbox__input.is-checked .el-checkbox__inner {
  background-color: rgb(152, 46, 130);
  border-color: rgb(152, 46, 130);
}
.agree .el-checkbox__label {
  color: #000;
  font-size: 12px;
  padding-left: 4px !important;
  vertical-align: middle;
}
.agree .el-checkbox__input.is-checked + .el-checkbox__label {
  color: #000 !important;
}
.agree a {
  text-decoration: none;
  color: rgb(152, 46, 130);
  vertical-align: top;
}
.service-card .content .el-button:hover {
  opacity: 0.8;
  border-color: #ffdebd;
  box-shadow: 0 3px 8px 0 rgba(255, 156, 57, 0.6);
}
</style>
<style>
.workbench .el-button {
  display: block;
  margin: 0 auto;
  text-align: center;
  color: #fff;
  width: 160px;
  height: 39px;
  background-image: linear-gradient(rgb(152, 46, 130), rgb(152, 46, 130)),
    linear-gradient(#000, #000);
  border-radius: 19px;
  border: none;
}
.workbench .kongBackground {
  margin-top: 20px;
  background-image: none;
  background-color: #fff !important;
  color: rgb(152, 46, 130);
  border: 2px solid rgb(152, 46, 130);
}
.workbench .formTips {
  color: #ff2525;
  font-size: 20px;
  text-align: center;
  font-weight: 600;
  margin-bottom: 20px;
}
.workbench .dianziqianlogo {
  height: 40px;
  background: url(../../assets/electronicSignature/dianziqianlogo.png) 50% 0px
    no-repeat;
  /* margin-bottom: 10px; */
  background-size: 150px 30px;
}

.workbench .el-button:hover {
  opacity: 0.8;
  border-color: #ffdebd;
  box-shadow: 0 3px 8px 0 rgb(152, 46, 130, 61%);
}
.workbench .contractTipsBtn1 {
  background-image: none;
  background-color: rgb(204, 204, 204) !important;
  color: #fff;
  border: none;
}
.workbench .contractTipsBtn1:hover {
  opacity: 1;
  border-color: none;
  box-shadow: none;
  color: #fff;
}
.farenweixin {
  border-radius: 10px;
}
.farenweixin .el-dialog__header {
  border-bottom: 1px solid #ccc;
}
.farenweixin .el-dialog__title {
  color: #ff2525;
  font-weight: 600;
}
.returnStep {
  position: absolute;
  top: 20px;
  left: 20px;
  cursor: pointer;
  color: #982e82;
}
</style>
