<template>
  <div>
    <!-- 面包屑导航区域 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>个人中心</el-breadcrumb-item>
    </el-breadcrumb> -->
    <!-- 卡片视图区域 -->
    <el-card>
      <div class="bottomSty">{{'推广链接：'+copyText}}</div>
      <div class="bottomSty">{{'手机号：'+userInfo.mobile}}</div>
      <div class="bottomSty">{{'等级：'+(userInfo.level==1?'超级管理员':(userInfo.level==2?'管理员':'业务员'))}}</div>
      <div class="bottomSty">
        {{'备注：'+ (userInfo.remark?userInfo.remark:"暂无")}}
      </div>
      <div class="bottomSty">
        客服二维码：
        <img :src="userInfo.wx"
             v-if="userInfo.wx"
             alt=""
             srcset="">

      </div>
      <div class="bottomSty">
        <el-button plain
                   style="margin-right:20px;"
                   type="primary"
                   size="small"
                   @click="modPasswordBtn">
          修改密码
        </el-button>
        <el-upload :action="uploadURL"
                   style="display:inline-block;"
                   name="file"
                   :data={id:this.userInfo.id}
                   accept=".jpeg,.png,.jpg,.bmp,.gif"
                   :on-success="handleSuccess"
                   :limit=1
                   :show-file-list="false"
                   list-type="picture">
          <el-button size="small"
                     type="primary">上传图片</el-button>
        </el-upload>
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
            <el-button plain
                       type="primary"
                       size="mini"
                       @click="getCode">
              获取验证码
            </el-button>
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
    </el-card>
  </div>
</template>

<script>
import { code, modify } from "./api.js";
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
      userInfo: null,//登录账号信息
      modPasswordDialogVisible: false,//修改密码弹框的显示与隐藏数据
      modPasswordData: {},//提交修改密码所需数据
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
      uploadURL: `/ZBDH/BACK/common/upload/img`,//服务器上传图片的地址
      copyText: '',//存放推广链接
    }
  },
  created () {
    this.userInfo = JSON.parse(localStorage.getItem('userInfo'))
    // console.log(this.userInfo)
    let userIdTeap = localStorage.getItem("userId")
    this.copyText = 'https://taizb.com/login?promotion=' + userIdTeap
  },
  methods: {
    // 监听图片上传成功的事件
    handleSuccess (response) {
      // console.log(response)
      if (response.code == 200) {
        this.$message.success('修改成功')
        this.userInfo.wx = response.data//保存图片的临时路径地址；
        localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
      } else {
        this.$message.success('修改失败')
      }

    },
    // 修改密码按钮
    modPasswordBtn () {
      this.modPasswordData.mobile = this.userInfo.mobile
      this.modPasswordDialogVisible = true
    },
    // 修改密码弹框关闭方法
    passwordDialogClosed () {
      this.$refs.passwordFormRef.resetFields();//清空校验结果
      this.modPasswordData = {}
    },
    // 获取验证码方法
    async getCode () {
      var that = this
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
    // 修改密码提交方法
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
        params.source = 'back'
        let res = await modify(params)
        console.log(res)
        if (res.code == 200) {
          that.modPasswordDialogVisible = false;//关闭弹框
          that.modPasswordData = {}//清空输入项
          that.$message({
            type: "success",
            message: '修改成功，请重新登录'
          });
          window.localStorage.clear()
          this.$router.replace('/login')
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
.el-card {
  margin-top: 15px;
  .bottomSty {
    font-size: 20px;
    margin-bottom: 20px;
    img {
      vertical-align: top;
      width: 300px;
      height: 300px;
    }
  }
}
</style>