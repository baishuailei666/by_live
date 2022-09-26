<template>
  <div class="shop-box">
    <div class="shop">
      <div class="navTab"
           style="padding: 0px 26px;">
        <ul class="tabs">
          <li class="active">
            我的店铺
          </li>
        </ul>
        <div class="ml-0"></div>
      </div>
      <div class="list"
           v-loading="storesLoading">
        <!-- 审核中模块开始 -->
        <div class="item"
             v-if="shopData.auditStatus=='审核中'">
          <div class="store">
            <div class="store-msg">
              <div>
                <div class="store-title">
                  <span>{{shopData.shop}}</span>
                  <!-- <img src="../../assets/shops/quanyi1.png"> -->
                </div>
                <div class="store-id">{{'店铺ID: '+shopData.shopId}}</div>
              </div>
            </div>
            <div class="store-state">
              <span>正在审核，请耐心等待</span>
            </div>
          </div>
          <div class="explain">{{shopData.introduce}}</div>
          <div class="operation">
            <div>
              <el-button size="mini"
                         type="primary"
                         @click="modifyBtn">立即修改</el-button>
            </div>
            <div class="tips">
            </div>
            <el-button size="mini"
                       type="primary"
                       @click="removeShopBtn(shopData.shopId)">删除</el-button>
          </div>
        </div>
        <!-- 审核中模块结束 -->
        <!-- 审核通过模块开始 -->
        <div class="item"
             v-if="shopData.auditStatus=='已通过'">
          <div class="store">
            <div class="store-msg">
              <div>
                <div class="store-title">
                  <span>{{shopData.shop}}</span>
                  <!-- <img src="../../assets/shops/quanyi1.png"> -->
                </div>
                <div class="store-id">{{'店铺ID: '+shopData.shopId}}</div>
              </div>
            </div>
          </div>
          <div class="explain">{{shopData.introduce}}</div>
          <div class="operation">
            <div style="margin-bottom:10px;">
              <el-button size="mini"
                         v-if="days>0"
                         type="primary"
                         @click="openServiceFun">续费权益</el-button>
              <el-button size="mini"
                         v-else
                         type="primary"
                         @click="openServiceFun">开通服务</el-button>
            </div>
            <!-- <div class="tips">
              2022-07-30到期
            </div> -->
            <el-button size="mini"
                       type="primary"
                       @click="modifyBtn">立即修改</el-button>
          </div>
        </div>
        <!-- 审核通过模块结束 -->
        <!-- 未通过模块开始 -->
        <div class="item"
             v-if="shopData.auditStatus=='已拒绝'">
          <div class="store">
            <div class="store-msg">
              <div>
                <div class="store-title">
                  <span>{{shopData.shop}}</span>
                  <!-- <img src="../../assets/shops/quanyi1.png"> -->
                </div>
                <div class="store-id">{{'店铺ID: '+shopData.shopId}}</div>
              </div>
            </div>
            <div class="store-state">
              <span>{{'驳回原因：'+shopData.auditRemark}}</span>
            </div>
          </div>
          <div class="explain">{{shopData.introduce}}</div>
          <div class="operation">
            <div>
              <el-button size="mini"
                         type="primary"
                         @click="modifyBtn">立即修改</el-button>
            </div>
            <div class="tips">
            </div>
            <el-button size="mini"
                       type="primary"
                       @click="removeShopBtn(shopData.shopId)">删除</el-button>
          </div>
        </div>
        <!-- 未通过模块结束 -->
        <!-- 无绑定店铺开始 -->
        <div class="normal"
             v-if="shopData.auditStatus=='未绑定'">
          <img src="../../assets/shops/normal1.png"
               alt="">
          <p>您还未绑定店铺</p>
          <a href="/bind-store"
             class="btn-orange">绑定店铺</a>
        </div>
        <!-- 无绑定店铺结束 -->
      </div>
    </div>
    <div class="page-box"
         style="padding: 10px 0px;">
    </div>
    <!-- 1、修改店铺信息开始 -->
    <el-dialog title="修改店铺信息"
               :visible.sync="modStoresDialogVisible"
               width="60%"
               @close="storesDialogClosed">
      <el-form :model="modStoresData"
               :rules="storesFromRules"
               ref="storesFormRef"
               label-width="120px">
        <el-form-item prop="shopId"
                      label="店铺ID：">
          <el-input v-model="modStoresData.shopId"></el-input>
        </el-form-item>
        <el-form-item prop="shop"
                      label="店铺名称：">
          <el-input v-model="modStoresData.shop"></el-input>
        </el-form-item>
        <el-form-item prop="goods"
                      label="任意商品链接：">
          <el-input v-model="modStoresData.goods"></el-input>
        </el-form-item>
        <el-form-item prop="introduce"
                      label="店铺介绍：">
          <el-input autocomplete="off"
                    resize="none"
                    :autosize="{ minRows: 9, maxRows: 9 }"
                    type="textarea"
                    v-model="modStoresData.introduce"
                    placeholder="1：品牌定位、供应链优势等；
2：经营理念、主播寄语等；
3：不得出现手机、微信等联系方式。"
                    show-word-limit
                    maxlength="140"
                    style="min-height: 33px;"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="modStoresDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="modStoresFun">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { shop, modify, del } from "./api.js";
export default {
  data () {
    return {
      storesLoading: true,//转圈的东西
      shopData: {
        auditStatus: '未绑定'
      },//我的店铺的信息
      days: null,//会员剩余天数
      modStoresDialogVisible: false,//修改店铺信息弹框的显示与隐藏；
      modStoresData: {},//修改提交时候的临时对象；
      storesFromRules: {//数据校验
        shopId: [
          { required: true, message: "请输入店铺ID", trigger: "blur" },
        ],
        shop: [
          { required: true, message: "请输入店铺名称", trigger: "blur" },
        ],
        goods: [
          { required: true, message: "请输入任意商品链接", trigger: "blur" }
        ],
        introduce: [
          { required: true, message: "请输入店铺介绍", trigger: "blur" }
        ],
      },
      userInfo: null,//为了解决一个电子签跳除不跳出的问题
    }
  },
  created () {
    this.getShop()
    this.days = localStorage.getItem("days");
    this.userInfo = JSON.parse(localStorage.getItem('userInfo'))
    // console.log(this.days)
  },
  methods: {
    // 立即修改按钮
    modifyBtn () {
      this.modStoresDialogVisible = true
      this.modStoresData = {
        shopId: this.shopData.shopId,
        shop: this.shopData.shop,
        goods: this.shopData.goods,
        introduce: this.shopData.introduce,
      }
    },
    // 修改弹框关闭事件
    storesDialogClosed () {
      this.$refs.storesFormRef.resetFields();//清空校验结果
      this.modStoresData = {}//清空修改内容，下次重新赋值
    },
    // 修改提交方法
    modStoresFun () {
      var that = this
      // 1、进行数据的校验
      this.$refs.storesFormRef.validate(async valid => {
        if (!valid) {
          // 数据校验失败
          return that.$message.error("请检查数据");
        }
        let params = this.modStoresData
        let res = await modify(params)
        if (res.code == 200) {
          this.getShop()
          that.modStoresDialogVisible = false;//关闭弹框
          that.modStoresData = {}//清空输入项
          that.$message({
            type: "success",
            message: '修改成功'
          });
        } else {
          that.$message({
            type: "error",
            message: res.msg
          });
        }
      });
    },
    // 1.删除店铺按钮
    removeShopBtn (shopIdTeap) {
      this.$confirm("删除店铺后，我的店铺中将永久删除店铺，是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.removeShopFun(shopIdTeap);
      });
    },
    // 删除店铺方法
    async removeShopFun (idTeap) {
      var that = this
      let params = {
        shopId: idTeap
      };
      let res = await del(params);
      // console.log(res)
      if (res.code == 200) {
        that.userInfo.shopStatus = null
        localStorage.setItem('userInfo', JSON.stringify(that.userInfo))//删除成功之后把本地存的店铺状态给改了
        that.getShop()
        that.$message({
          type: "success",
          message: '删除成功',
        });

      } else {
        that.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    // 2、续费和开通服务方法
    openServiceFun () {
      this.$router.push('/open-service/open-card')
    },
    // 调我的店铺接口，看是否有店铺和店铺目前的返回信息
    async getShop () {
      let res = await shop();
      if (res.code == 200) {
        this.shopData = res.data
        this.storesLoading = false
      } else {
        this.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
  }
}
</script>

<style lang="less" scoped>
.shop-box {
  min-width: 1100px;
  background-color: #fff;
  border-radius: 10px;
  overflow: hidden;
  padding-bottom: 20px;
  .navTab {
    display: flex;
    align-items: center;
    background-color: #fff;
    border-bottom: 1px solid #e5e5e5;
    .tabs {
      display: flex;
      align-items: center;
      width: 100%;
      height: 46px;
      padding-top: 0;
      li {
        color: rgb(152, 46, 130);
        cursor: pointer;
        position: relative;
        line-height: 46px;
        font-weight: 500;
        font-size: 16px;
        margin-right: 42px;
      }
    }
    .ml-0 {
      margin-left: auto;
    }
  }
  .list {
    min-height: calc(100vh - 220px);
    // 没绑定店铺的时候
    .normal {
      text-align: center;
      padding-top: 120px;
      img {
        border-style: none;
        max-width: 100%;
        max-height: 100%;
        display: block;
        box-sizing: border-box;
        margin: 0 auto;
        width: 400px;
        height: 210px;
      }
      p {
        line-height: 1;
        font-size: 12px;
        margin: 0 0 18px;
      }
      .btn-orange {
        text-decoration: none;
        display: inline-block;
        text-align: center;
        min-width: 116px;
        cursor: pointer;
        background-color: rgb(152, 46, 130);
        color: #fff;
        font-weight: 700;
        height: 36px;
        line-height: 36px;
        padding: 0 30px;
        border-radius: 18px;
      }
    }
    // 有店铺的时候
    .item {
      display: flex;
      margin: 20px 26px 0;
      .store {
        flex-shrink: 0;
        margin-right: 50px;
        .store-msg {
          display: flex;
          cursor: pointer;
          img {
            max-width: 100%;
            max-height: 100%;
            border-style: none;
            display: block;
            box-sizing: border-box;
            width: 62px;
            height: 62px;
            border-radius: 5px;
            overflow: hidden;
            margin-right: 15px;
          }
          .store-title {
            display: flex;
            align-items: flex-start;
            font-size: 16px;
            margin-top: 10px;
            img {
              border-style: none;
              max-width: 100%;
              max-height: 100%;
              display: block;
              box-sizing: border-box;
              width: 18px;
              height: 20px;
              margin-left: 15px;
              flex-shrink: 0;
            }
          }
          .store-id {
            margin-top: 10px;
            font-size: 14px;
            line-height: 1;
          }
        }
        .store-state {
          margin-top: 20px;
          span {
            font-size: 14px;
            margin-top: 18px;
            color: red;
          }
        }
      }
      .explain {
        flex: 1;
        color: #999;
        line-height: 20px;
        font-size: 14px;
      }
      .operation {
        margin-top: -10px;
        .tips {
          height: 20px;
          line-height: 20px;
          font-size: 12px;
          color: #000;
        }
      }
    }
  }
  .page-box {
    padding: 10px 0px;
    display: flex;
    justify-content: center;
  }
}
</style>
<style>
.item .el-button--primary {
  width: 90px;
  color: #fff;
  background-color: rgb(152, 46, 130);
  border-color: rgb(152, 46, 130);
  border-radius: 15px;
}
.item .el-button--primary:focus,
.el-button--primary:hover {
  background: rgb(152, 46, 130);
  border-color: rgb(152, 46, 130);
  color: #fff;
}
.el-loading-spinner .path {
  stroke-dashoffset: 0;
  stroke-width: 2;
  stroke: rgb(152, 46, 130);
  stroke-linecap: round;
}
</style>