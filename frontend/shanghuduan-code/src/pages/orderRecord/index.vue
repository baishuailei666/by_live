<template>
  <div class="order-record-box">
    <div class="order_nav_title">订单记录
      <!-- <div class="invoicing-style">
        <el-button type="primary"
                   round
                   @click="invoicingBtn()">开票</el-button>
      </div> -->
    </div>
    <el-table :data="tableData"
              stripe
              style="width: 100%">
      <el-table-column type="index"
                       width="50"
                       label="序号" />
      <el-table-column prop="orderNo"
                       label="订单号">
      </el-table-column>
      <el-table-column prop="buyType"
                       label="权益类型">
      </el-table-column>
      <el-table-column prop="payType"
                       label="支付类型">
      </el-table-column>
      <el-table-column prop="money"
                       label="金额">
      </el-table-column>
      <el-table-column prop="ut"
                       label="支付时间">
      </el-table-column>
    </el-table>
    <!-- 1、开票功能对话框开始 -->
    <!-- <el-dialog title="开发票"
               :visible.sync="invoiceDialogVisible"
               width="36%"
               @close="invoiceDialogClosed">
      <el-form :model="invoiceData"
               :rules="invoiceFromRules"
               ref="invoiceFormRef"
               label-width="110px">
        <el-form-item label="发票公司名称"
                      prop="company">
          <el-input v-model="invoiceData.company"
                    type="text"
                    placeholder="请输入发票公司名称"
                    autocomplete="off"
                    clearable></el-input>
        </el-form-item>
        <el-form-item label="发票金额"
                      prop="money">
          <el-input v-model="invoiceData.money"
                    type="text"
                    placeholder="请输入发票金额（开票金额需与支付订单金额相符）"
                    autocomplete="off"
                    clearable></el-input>
        </el-form-item>
        <el-form-item label="纳税识别号"
                      prop="tax">
          <el-input v-model="invoiceData.tax"
                    type="text"
                    placeholder="请输入纳税识别号"
                    autocomplete="off"
                    clearable></el-input>
        </el-form-item>
        <el-form-item label="电子邮箱"
                      prop="email">
          <el-input v-model="invoiceData.email"
                    type="text"
                    placeholder="请输入电子邮箱"
                    autocomplete="off"
                    clearable></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="invoiceDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="invoiceFun">确 定</el-button>
      </span>
    </el-dialog> -->
  </div>
</template>

<script>
import { order, create } from "./api.js";
export default {
  data () {
    return {
      tableData: [],
      invoiceDialogVisible: false,//控制开发票接口的显示与隐藏
      invoiceData: {},//要提交的数据
      invoiceFromRules: {//数据校验
        company: [
          { required: true, message: "请输入发票公司名称", trigger: "blur" },
        ],
        money: [
          { required: true, message: "请输入发票发票金额", trigger: "blur" },
        ],
        tax: [
          { required: true, message: "请输入纳税识别号", trigger: "blur" },
        ],
        email: [
          { required: true, message: "请输入电子邮箱", trigger: "blur" },
        ],
      },
    }
  },
  created () {
    this.getOrderRecordData()
  },
  methods: {
    // 开票按钮
    // invoicingBtn () {
    //   this.invoiceDialogVisible = true;
    // },
    // 开发票提交方法
    async  invoiceFun () {
      // 1、进行数据的校验
      this.$refs.invoiceFormRef.validate(async valid => {
        if (!valid) {
          // 数据校验失败
          return this.$message.error("请检查数据");
        }
        // 数据校验成功
        let params = this.invoiceData;
        let res = await create(params)
        if (res.code == 200) {
          this.$message({ type: 'success', message: '操作成功' })
          this.invoiceDialogVisible = false;
        } else {
          this.$message({
            type: "error",
            message: res.msg,
          });
        }
      });
    },
    // 开票弹框关闭事件
    // invoiceDialogClosed () {
    //   this.$refs.invoiceFormRef.resetFields();//清空校验结果
    //   this.invoiceData = {}
    // },
    // 获取订单记录方法
    async getOrderRecordData () {
      let res = await order()
      // console.log(res)
      if (res.code == 200) {
        this.tableData = res.data
      }
    }
  }
}
</script>

<style lang="less" scoped>
.order-record-box {
  min-width: 1100px;
  box-sizing: border-box;
  background-color: #fff;
  min-height: calc(100vh - 100px);
  border-radius: 15px;
  overflow: hidden;
  padding: 10px;
  .order_nav_title {
    border-bottom: 1px solid #e5e5e5;
    height: 77px;
    line-height: 77px;
    padding: 0 26px;
    font-weight: 500;
    font-size: 18px;
    color: rgb(152, 46, 130);
    .invoicing-style {
      float: right;
    }
  }
}
</style>
<style>
.order-record-box .el-table {
  width: 98%;
  box-sizing: border-box;
  padding-top: 20px;
}
.invoicing-style .el-button--primary {
  width: 120px;
  color: #fff;
  background-color: rgb(152, 46, 130);
  border-color: rgb(152, 46, 130);
  border-radius: 20px;
}
.invoicing-style .el-button--primary:focus,
.invoicing-style .el-button--primary:hover {
  background: rgb(152, 46, 130);
  border-color: rgb(152, 46, 130);
  color: #fff;
}
</style>