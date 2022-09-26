<template>
  <div class="login">
    <div class="main">
      <!-- <img src="../assets/login/34@2x.png"
           alt="" /> -->
      <p>欢迎来到「太博甄选后台管理系统」</p>
      <!-- :rules="rules" -->
      <el-form class="demo-ruleForm"
               :model="userInfo"
               :rules="rules"
               ref="elLoginForm">
        <!-- 用户名 -->
        <el-form-item prop="mobile"
                      class="el-input el-input--prefix">
          <el-input type="text"
                    v-model="userInfo.mobile"
                    autocomplete="off"></el-input>
          <span class="el-input__prefix">
            <i class="iconfont el-icon-user"></i>
          </span>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="pwd"
                      class="el-input el-input--prefix">
          <el-input type="password"
                    v-model="userInfo.pwd"
                    autocomplete="off"></el-input>
          <span class="el-input__prefix">
            <i class="iconfont el-icon-lock"></i>
          </span>
        </el-form-item>
        <el-form-item class="but_box">
          <el-button type="primary"
                     @click="submitForm()">登&nbsp;&nbsp;录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { login } from "@/api/index.js";
export default {
  data () {
    return {
      userInfo: {
        mobile: "",
        pwd: "",
      },
      rules: {
        mobile: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        pwd: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ]
      }
    };
  },
  created () {
  },
  methods: {
    submitForm () {
      this.$refs.elLoginForm.validate((valid) => {
        if (valid) {
          let params = {
            mobile: this.userInfo.mobile,
            pwd: this.userInfo.pwd,
            source: 'back',
          }; //这个传给后台用的做的一个临时对象变量
          // this.$router.push("/home");
          login(params).then((res) => {
            if (res.code == 200) {
              console.log(res)
              localStorage.setItem('userId', res.data.id)
              localStorage.setItem('level', res.data.level)
              localStorage.setItem('userInfo', JSON.stringify(res.data))
              this.$message.success('登录成功');
              let menus = []
              // res.data.level: 1：超级管理员，2：管理员，3：业务员
              if (res.data.level == 1) {
                menus = [
                  {
                    name: "商户管理",
                    rightUrl: null,
                    icon: 'el-icon-setting',
                    childRights: [
                      {
                        name: "商户列表",
                        rightUrl: "business-listings",
                        childRights: null
                      },
                      {
                        name: "店铺审核列表",
                        rightUrl: "store-audit-list",
                        childRights: null
                      },
                      {
                        name: "店铺购买记录",
                        rightUrl: "equity-record",
                        childRights: null
                      },
                    ]
                  },
                  {
                    name: "资源管理",
                    rightUrl: null,
                    icon: 'el-icon-suitcase',
                    childRights: [
                      {
                        name: "商户资源",
                        rightUrl: "merchant-resources",
                        childRights: null
                      },
                      {
                        name: "订单列表",
                        rightUrl: "funding-list",
                        childRights: null
                      },
                      {
                        name: "发票列表",
                        rightUrl: "invoice",
                        childRights: null
                      }
                    ]
                  },
                  {
                    name: "操作",
                    rightUrl: null,
                    icon: 'el-icon-monitor',
                    childRights: [
                      {
                        name: "合同列表",
                        rightUrl: "contract-review",
                        childRights: null
                      },
                      {
                        name: "配置列表",
                        rightUrl: "configuration-list",
                        childRights: null
                      }
                    ]
                  },
                  {
                    name: "用户管理",
                    rightUrl: null,
                    icon: 'el-icon-menu',
                    childRights: [
                      {
                        name: "用户管理",
                        rightUrl: "user",
                        childRights: null
                      },
                    ]
                  },
                  {
                    name: "素材管理",
                    rightUrl: null,
                    icon: 'el-icon-folder-add',
                    childRights: [
                      {
                        name: "素材管理",
                        rightUrl: "material-management",
                        childRights: null
                      },
                    ]
                  },
                  // {
                  //   name: "支付凭证",
                  //   rightUrl: null,
                  //   icon: 'el-icon-tickets',
                  //   childRights: [
                  //     {
                  //       name: "支付凭证",
                  //       rightUrl: "payment-voucher",
                  //       childRights: null
                  //     },
                  //   ]
                  // },
                  {
                    name: "个人中心",
                    rightUrl: null,
                    icon: 'el-icon-s-custom',
                    childRights: [
                      {
                        name: "个人中心",
                        rightUrl: "personal",
                        childRights: null
                      },
                    ]
                  },
                ]
              } else if (res.data.level == 2) {
                menus = [
                  {
                    name: "商户管理",
                    rightUrl: null,
                    icon: 'el-icon-setting',
                    childRights: [
                      {
                        name: "商户列表",
                        rightUrl: "business-listings",
                        childRights: null
                      },
                      {
                        name: "店铺审核列表",
                        rightUrl: "store-audit-list",
                        childRights: null
                      },
                      {
                        name: "店铺购买记录",
                        rightUrl: "equity-record",
                        childRights: null
                      },
                    ]
                  },
                  {
                    name: "资源管理",
                    rightUrl: null,
                    icon: 'el-icon-suitcase',
                    childRights: [
                      {
                        name: "商户资源",
                        rightUrl: "merchant-resources",
                        childRights: null
                      },
                      {
                        name: "订单列表",
                        rightUrl: "funding-list",
                        childRights: null
                      },
                      {
                        name: "发票列表",
                        rightUrl: "invoice",
                        childRights: null
                      }
                    ]
                  },
                  {
                    name: "合同列表",
                    rightUrl: null,
                    icon: 'el-icon-monitor',
                    childRights: [
                      {
                        name: "合同列表",
                        rightUrl: "contract-review",
                        childRights: null
                      },
                    ]
                  },
                  {
                    name: "用户管理",
                    rightUrl: null,
                    icon: 'el-icon-menu',
                    childRights: [
                      {
                        name: "用户管理",
                        rightUrl: "user",
                        childRights: null
                      },
                    ]
                  },
                  {
                    name: "素材管理",
                    rightUrl: null,
                    icon: 'el-icon-folder-add',
                    childRights: [
                      {
                        name: "素材管理",
                        rightUrl: "material-management",
                        childRights: null
                      },
                    ]
                  },
                  {
                    name: "个人中心",
                    rightUrl: null,
                    icon: 'el-icon-s-custom',
                    childRights: [
                      {
                        name: "个人中心",
                        rightUrl: "personal",
                        childRights: null
                      },
                    ]
                  },
                ]
              } else if (res.data.level == 3) {
                menus = [
                  {
                    name: "商户管理",
                    rightUrl: null,
                    icon: 'el-icon-setting',
                    childRights: [
                      {
                        name: "商户列表",
                        rightUrl: "business-listings",
                        childRights: null
                      },
                      {
                        name: "店铺审核列表",
                        rightUrl: "store-audit-list",
                        childRights: null
                      },
                      {
                        name: "店铺购买记录",
                        rightUrl: "equity-record",
                        childRights: null
                      },
                    ]
                  },
                  {
                    name: "资源管理",
                    rightUrl: null,
                    icon: 'el-icon-suitcase',
                    childRights: [
                      {
                        name: "商户资源",
                        rightUrl: "merchant-resources",
                        childRights: null
                      },
                      {
                        name: "发票列表",
                        rightUrl: "invoice",
                        childRights: null
                      }
                    ]
                  },
                  {
                    name: "合同列表",
                    rightUrl: null,
                    icon: 'el-icon-monitor',
                    childRights: [
                      {
                        name: "合同列表",
                        rightUrl: "contract-review",
                        childRights: null
                      },
                    ]
                  },
                  {
                    name: "个人中心",
                    rightUrl: null,
                    icon: 'el-icon-s-custom',
                    childRights: [
                      {
                        name: "个人中心",
                        rightUrl: "personal",
                        childRights: null
                      },
                    ]
                  },
                ]
              }
              localStorage.setItem('menus', JSON.stringify(menus))
              this.$router.push("/home");
            } else {
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
    },
  },
};
</script>

<style lang="less" scoped>
.login {
  width: 100%;
  height: 100%;
  background-image: url(../assets/login/denglubeijing.jpeg);
  background-size: 100% 100%;
  background-position: center center;
  position: relative;
  .main {
    width: 491px;
    height: 419px;
    font-size: 24px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    text-align: center;
    padding: 30px;
    background-color: rgba(0, 0, 0, 0.4);
    border-radius: 10px;
    img {
      width: 456px;
      height: 62px;
    }
    p {
      margin: 38px 0;
      font-size: 24px;
      font-family: PingFangSC-Regular-, PingFangSC-Regular;
      font-weight: normal;
      color: rgba(255, 255, 255, 0.8);
    }
    .el-input--prefix {
      margin-bottom: 32px;
    }

    /deep/.el-form-item__content {
      line-height: 53px;
      font-size: 21px;
    }
    /deep/.el-input__inner {
      height: 53px;
      font-size: 20px;
    }
    .el-button {
      width: 490px;
      height: 53px;
      font-size: 20px;
    }
  }
}
</style>