<template>
  <div class="user-box">
    <div class="nav-title">个人中心</div>
    <div class="box">
      <!-- <div class="avatar">
        <img src="https://thirdwx.qlogo.cn/mmopen/vi_32/PaUu51pceFiaCkjmiblpictAaVHibSIKzj1sAgommZjhSwAjqs3DPJckHVnRJ5WibqcDxsPaWZF8DibcBzH79jOcm2ng/132">
      </div> -->
      <div class="item"><span>账号（手机号）：</span>
        <p style="margin-right: 20px;">{{userInfo.mobile}}</p>
        <el-button size="mini"
                   type="primary"
                   @click="modBtn">修改密码</el-button>
      </div>
      <div class="item">
        <span>权益等级：</span>
        <p>{{userInfo.buyType?userInfo.buyType:'暂无'}}</p>
      </div>
      <div class="item">
        <span>剩余天数：</span>
        <p>{{userInfo.days?userInfo.days:'0'}}</p>
      </div>
    </div>
    <!-- 1、修改密码对话框开始 -->
    <el-dialog title="修改密码"
               :visible.sync="modPasswordDialogVisible"
               width="36%"
               @close="passwordDialogClosed">
      <el-form :model="modPasswordData"
               :rules="passwordFromRules"
               ref="passwordFormRef"
               label-width="100px">
        <el-form-item label="手机号"
                      prop="mobile">
          <el-input v-model="modPasswordData.mobile"
                    placeholder="请输入用户手机号"
                    clearable></el-input>
        </el-form-item>
        <el-form-item label="验证码"
                      prop="code">
          <el-input v-model="modPasswordData.code"
                    placeholder="请输入验证码"
                    clearable></el-input>
          <!-- <el-button plain
                     type="primary"
                     size="mini"
                     @click="getCode">
            获取验证码
          </el-button> -->
          <el-button type="primary"
                     disabled
                     class="yanzhengma">{{'('+timeData+'s)后重新获取'}}</el-button>
          <el-button type="primary"
                     v-if="!timeType"
                     class="yanzhengma"
                     @click="getCode">获取验证码</el-button>
        </el-form-item>
        <el-form-item label="新密码"
                      prop="pwd">
          <el-input v-model="modPasswordData.pwd"
                    type="text"
                    placeholder="请输入新密码"
                    auto-complete="new-password"
                    autocomplete="off"
                    clearable></el-input>
        </el-form-item>
        <el-form-item label="确定新密码"
                      prop="pwdTeap">
          <el-input v-model="modPasswordData.pwdTeap"
                    type="text"
                    auto-complete="new-password"
                    autocomplete="off"
                    placeholder="请输入确定新密码"
                    clearable></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="modPasswordDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="modPasswordFun">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { info, code, modify } from "./api.js";
export default {
  data () {
    var validatePass1 = (rule, value, callback) => {
      //验证新密码
      if (value === "") {
        callback(new Error("请输入新密码"));
      } else {
        if (this.modPasswordData.pwdTeap !== "") {
          this.$refs.passwordFormRef.validateField("pwdTeap");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      //验证确定密码
      if (value === "") {
        callback(new Error("请再次输入新密码"));
      } else if (value !== this.modPasswordData.pwd) {
        callback(new Error("两次输入新密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      timeType: false,//现在是否有时间倒计时
      timeData: 300,//倒计时时间数据
      intervalBtn: {},//定时器；

      userInfo: {},//个人中心信息
      modPasswordDialogVisible: false,//修改密码弹框的显示与隐藏控制
      modPasswordData: {},//修改密码提交对象
      tagType: false,//用于判断是从支付跳过来的还是自己路由切换进来的
      passwordFromRules: {//数据校验
        mobile: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          { pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/, message: '请输入正确格式的手机号' }
        ],
        code: [
          { required: true, message: "请输入验证码", trigger: "blur" },
        ],
        pwd: [
          { validator: validatePass1, required: true, trigger: "blur" },
        ],
        pwdTeap: [
          { validator: validatePass2, required: true, trigger: "blur" },
        ],
      },
    }
  },
  created () {
    this.tagType = (this.$route.query.tagType ? this.$route.query.tagType : false)
    this.getInfo()
  },
  methods: {
    // 获取个人中心数据

    async getInfo () {
      if (this.tagType) {
        let params = {
          tag: 'pay'
        }
        let res = await info(params)
        // console.log(res)
        if(res.data){ // 就这一个info接口做跳登录的特殊处理
          this.userInfo = res.data
          this.tagType = false
        }else {
          this.$router.replace('/login')
        }
      } else {
        let res = await info()
        // console.log(res)
        if(res.data){
          this.userInfo = res.data
        }else {
          this.$router.replace('/login')
        }
      }
    },
    // 修改密码按钮
    modBtn () {
      this.modPasswordData.mobile = this.userInfo.mobile
      this.modPasswordDialogVisible = true;
    },
    // 获取验证码方法
    // 获取验证码方法
    async getCode () {
      var that = this
      that.timeType = true;
      that.getSecond()
      let params = {
        mobile: this.modPasswordData.mobile
      }
      if (this.modPasswordData.mobile) {
        let res = await code(params)
        if (res.code == 200) {
          that.$message({
            type: "success",
            message: '验证码已发送请注意查看'
          });
        } else {
          that.$message({
            type: "error",
            message: res.msg
          });
        }
      } else {
        that.$refs.passwordFormRef.validateField("mobile");
        that.$message({
          type: "error",
          message: '请先输入手机号'
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
    // 修改密码弹框关闭事件
    passwordDialogClosed () {
      this.$refs.passwordFormRef.resetFields();//清空校验结果
      this.modPasswordData = {}
    },
    // 数据提交方法
    modPasswordFun () {
      var that = this
      // 1、进行数据的校验
      this.$refs.passwordFormRef.validate(async valid => {
        if (!valid) {
          // 数据校验失败
          return that.$message.error("请检查数据");
        }
        // 数据校验成功
        let params = that.modPasswordData
        params.source = 'merchant'
        let res = await modify(params)
        // console.log(res)
        if (res.code == 200) {
          that.modPasswordDialogVisible = false;//关闭弹框
          that.modPasswordData = {}//清空输入项
          that.$message({
            type: "success",
            message: '修改成功，请重新登录'
          });
          setTimeout(function () {
            window.localStorage.clear()
            that.$router.replace('/login')
          }, 1000)
        } else {
          that.$message({
            type: "error",
            message: res.msg
          });
        }
      });
    }
  }
}
</script>

<style lang="less" scoped>
.user-box {
  min-width: 1100px;
  background-color: #fff;
  min-height: calc(100vh - 100px);
  // height: 100%;
  border-radius: 15px;
  overflow: hidden;
  .nav-title {
    border-bottom: 1px solid #e5e5e5;
    height: 77px;
    line-height: 77px;
    padding: 0 26px;
    font-weight: 500;
    font-size: 18px;
  }
  .box {
    font-size: 18px;
    padding: 38px 33px;
    .avatar {
      display: flex;
      align-items: center;
      margin-bottom: 34px;
      img {
        width: 62px;
        height: 62px;
        overflow: hidden;
        border-radius: 50%;
        margin-right: 58px;
      }
    }
    .item {
      margin-bottom: 46px;
      display: flex;
      align-items: center;
      span:nth-child(1) {
        width: 180px;
      }
    }
  }
}
</style>
<style>
.user-box .el-button--primary {
  color: #fff;
  background-color: rgb(152, 46, 130);
  border-color: rgb(152, 46, 130);
}
.user-box .el-button--primary:focus,
.el-button--primary:hover {
  background: rgb(152, 46, 130);
  border-color: rgb(152, 46, 130);
  color: #fff;
}
</style>