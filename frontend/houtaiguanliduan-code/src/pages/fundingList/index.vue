<template>
  <div>
    <!-- 面包屑导航区域 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>订单列表</el-breadcrumb-item>
    </el-breadcrumb> -->
    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 上方搜索和添加按钮 -->
      <!-- 搜索区域 -->
      <div class="search">
        <div>
          <el-row style="margin-bottom: 20px;">
            <span>商户手机号：</span>
            <el-input placeholder="请输入商户手机号"
                      v-model="searchMobile"
                      clearable
                      @change="getFundingListData()">
            </el-input>
            <span>店铺名称：</span>
            <el-input placeholder="请输入店铺名称"
                      v-model="searchShopName"
                      clearable
                      @change="getFundingListData()">
            </el-input>
            <span>订单号：</span>
            <el-input placeholder="请输入订单号"
                      v-model="searchOrderNo"
                      clearable
                      @change="getFundingListData()">
            </el-input>
            <span>购买等级：</span>
            <el-select v-model="searchBuyType"
                       placeholder="请选择购买等级"
                       clearable
                       @change="getFundingListData()">
              <el-option v-for="item in buyTypeOption"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
            <span>支付状态：</span>
            <el-select v-model="searchPaymentStatus"
                       placeholder="请选择支付状态"
                       clearable
                       @change="getFundingListData()">
              <el-option v-for="item in paymentStatusOption"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
          </el-row>
          <el-row>

            <span>业务员：</span>
            <el-input placeholder="请输入业务员ID/备注/手机号"
                      v-model="searchOpeUser"
                      class="openUser"
                      clearable
                      @change="getFundingListData()">
            </el-input>
            <span>时间范围：</span>
            <el-date-picker v-model="valueTime"
                            type="datetimerange"
                            @change="selectBtn"
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            @clear="selectBtn"
                            align="right">
            </el-date-picker>
            <div class="btnModular">
              <el-button type="primary"
                         icon="el-icon-search"
                         @click="getFundingListData()">搜索</el-button>
              <el-button icon="el-icon-refresh-right"
                         @click="resetBtn">重置</el-button>
              <el-button icon="el-icon-plus"
                         type="primary"
                         @click="newAddOrder">新增订单</el-button>
            </div>
          </el-row>
        </div>
      </div>
      <el-table :data="tableData">
        <el-table-column prop="id"
                         label="序号"
                         width="55" />
        <el-table-column prop="orderNo"
                         label="订单号"
                         align="center">
        </el-table-column>
        <el-table-column prop="tradeNo"
                         label="交易号"
                         align="center">
        </el-table-column>
        <el-table-column prop="opeUser"
                         label="业务员"
                         align="center">
        </el-table-column>
        <el-table-column prop="shop"
                         label="店铺名"
                         align="center">
        </el-table-column>
        <el-table-column prop="mobile"
                         label="商户手机号"
                         align="center">
        </el-table-column>
        <el-table-column prop="money"
                         label="金额"
                         align="center">
        </el-table-column>
        <el-table-column prop="payType1"
                         label="支付方式"
                         align="center">
        </el-table-column>
        <el-table-column prop="buyType1"
                         label="购买等级"
                         align="center">
        </el-table-column>
        <el-table-column prop="payStatus"
                         label="支付状态"
                         align="center">
        </el-table-column>
        <el-table-column prop="ct"
                         label="创建时间"
                         align="center">
        </el-table-column>
        <el-table-column prop="ut"
                         label="支付时间"
                         align="center">
        </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     :current-page="currentPage"
                     :page-sizes="[10, 20, 30, 40]"
                     :page-size="pageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="total">
      </el-pagination>
      <!-- 添加订单弹框 -->
      <el-dialog title="订单添加"
                 :visible.sync="addOrderDialogVisible"
                 width="35%"
                 class="dialog_box"
                 style="text-align:left;"
                 @close="addOrderDataDialog">
        <el-form class="demo-ruleForm"
                 :model="form"
                 :rules="rules"
                 ref="elOrderForm">
          <el-form-item prop="merchantId"
                        label="商户店铺名称："
                        class="el-input el-input--prefix">
            <el-select v-model="form.merchantId"
                       filterable
                       clearable
                       placeholder="请输入商户店铺名称">
              <el-option v-for="item in merchantIdOptions"
                         :key="item.id"
                         :label="item.shop"
                         :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="buyType"
                        label="购买类型："
                        class="el-input el-input--prefix">
            <el-select v-model="form.buyType"
                       placeholder="请选择购买等级"
                       clearable>
              <el-option v-for="item in buyTypeOption"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="money"
                        label="购买金额："
                        class="el-input el-input--prefix">
            <el-input placeholder="请输入购买金额"
                      v-model="form.money"
                      clearable>
            </el-input>
          </el-form-item>
          <el-form-item prop="payType"
                        label="支付类型："
                        class="el-input el-input--prefix">
            <el-select v-model="form.payType"
                       placeholder="请选择支付类型"
                       clearable>
              <el-option v-for="item in payTypeOption"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer"
              class="dialog-footer">
          <el-button @click="addOrderDialogVisible = false">取 消</el-button>
          <el-button type="primary"
                     @click="getOrderData()">确 定</el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import { order, ins, search } from "./api.js";
export default {
  data () {
    return {
      pageSize: 10,
      total: 0,
      currentPage: 1,
      tableData: null,//表格数据
      searchMobile: "",//根据手机号搜索
      searchShopName: "",//根据店铺名称搜索
      searchOrderNo: "",//根据订单号搜索
      searchOpeUser: "",//根据业务员搜索
      searchBuyType: "",//根据购买等级搜索
      searchPaymentStatus: "",//根据支付状态搜索
      valueTime: '',//根据时间进行搜索
      start: '',//开始时间
      end: '',//结束时间
      paymentStatusOption: [
        { label: "未支付", value: 0 },
        { label: "已支付", value: 1 },

      ],//支付状态选择数据
      buyTypeOption: [//购买等级选择数据
        { label: "月卡", value: 1 },
        { label: "季卡", value: 2 },
        { label: "年卡", value: 3 },
      ],
      addOrderDialogVisible: false,//新增订单弹框的显示与隐藏
      form: {//新增需要的数据
        merchantId: '',
        buyType: '',
        money: '',
        payType: ''
      },
      payTypeOption: [//支付类型选择数据
        { label: "支付宝", value: 1 },
        { label: "微信", value: 2 },
        { label: "对公", value: 3 },
      ],
      rules: {
        merchantId: [
          { required: true, message: '请输入商户店铺名称', trigger: 'blur' },
        ],
        buyType: [
          { required: true, message: '请选择购买等级', trigger: 'blur' },
        ],
        money: [
          { required: true, message: '请输入购买金额', trigger: 'blur' },
        ],
        payType: [
          { required: true, message: '请选择支付类型', trigger: 'blur' },
        ],
      },
      merchantIdOptions: [],//用于选择的值（这个通过后台接口获取所有数据）
    }
  },
  created () {
    this.getFundingListData()

  },
  methods: {
    // 获取商户店铺ID接口
    async getSearch () {
      let res = await search()
      if (res.code == 200) {
        console.log(res)
        this.merchantIdOptions = res.data;
      } else {
        this.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    // 新增订单按钮
    newAddOrder () {
      this.getSearch()//获取所有店铺ID，新增订单的时候在进行调用；
      this.addOrderDialogVisible = true
    },
    addOrderDataDialog () {
      this.$refs.elOrderForm.resetFields();
      this.form = {//清空数据
        merchantId: '',
        buyType: '',
        money: '',
        payType: ''
      }
    },
    // 表单提交方法
    getOrderData () {
      let params = this.form
      var that = this
      this.$refs.elOrderForm.validate(async (valid) => {
        if (valid) {
          let res = await ins(params);
          if (res.code == 200) {
            that.$message({
              type: "success",
              message: '添加成功',
            });
            that.addOrderDialogVisible = false//关闭弹框
            that.getFundingListData()
          } else {
            that.$message({
              type: "error",
              message: res.msg,
            });
          }
        } else {
          this.$message.error("请检查是否有信息没有填写完整！");
        }
      });
    },
    // 格式化时间方法
    dateFormat (dateInput, format) {
      var day = dateInput.getDate()
      if (day < 10) {
        day = '0' + day
      }
      var month = dateInput.getMonth() + 1
      if (month < 10) {
        month = '0' + month
      }
      var year = dateInput.getFullYear()
      format = format.replace(/yyyy/, year)//年
      format = format.replace(/MM/, month)//月
      format = format.replace(/dd/, day)//日
      return format
    },
    selectBtn () {
      if (this.valueTime) {
        this.start = this.dateFormat(this.valueTime[0], 'yyyy-MM-dd')
        this.end = this.dateFormat(this.valueTime[1], 'yyyy-MM-dd')
      } else {
        this.start = ''
        this.end = ""
      }
      this.getFundingListData()//这个方法直接放到这里省去了在写一个@blur方法调这个方法
    },
    // 获取商品列表页面数据
    async getFundingListData (currentTep) {
      var that = this;
      if (!currentTep) {
        this.currentPage = 1
      }
      let params = {
        page: this.currentPage,
        size: this.pageSize,
        mobile: this.searchMobile,
        shop: this.searchShopName,
        orderNo: this.searchOrderNo,
        opeUser: this.searchOpeUser,
        buyType: this.searchBuyType,
        payStatus: this.searchPaymentStatus,
        start: this.start,
        end: this.end
      };
      let res = await order(params);
      // console.log(res)
      if (res.code == 200) {
        that.tableData = res.data;
        that.total = res.total;
      } else {
        that.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    // 3、重置方法
    resetBtn () {
      this.searchMobile = ""
      this.searchShopName = ""
      this.searchOrderNo = ""
      this.searchOpeUser = ""
      this.searchBuyType = ""
      this.searchPaymentStatus = ""
      this.valueTime = ''//根据时间进行搜索数据清空
      this.start = ''//开始时间清空
      this.end = ''//结束时间清空
      this.getFundingListData();
    },
    // 改变每页有多少条的方法
    handleSizeChange (val) {
      this.pageSize = val;
      this.getFundingListData();
    },
    // 改变当前所在页的方法
    handleCurrentChange (val) {
      this.currentPage = val;
      this.getFundingListData(val);
    },
  }
}
</script>

<style lang="less" scoped>
.el-card {
  margin-top: 15px;
  .search {
    margin-bottom: 20px;
    span {
      font-size: 16px;
    }
    .el-input--suffix {
      width: 180px;
      margin-right: 20px;
    }
    .openUser.el-input--suffix {
      width: 280px;
      margin-right: 20px;
    }
    .el-select {
      width: 186px;
      margin-right: 20px;
    }
    .el-date-editor {
      margin-right: 20px;
    }
    .btnModular {
      float: right;
    }
  }
  .el-table {
    margin: 15px 0;
  }
  .dialog_box {
    span {
      display: inline-block;
      font-size: 16px;
      margin-bottom: 10px;
    }
    .el-input--suffix {
      width: 100%;
      margin-right: 20px;
    }
    .el-select {
      width: 100%;
      margin-right: 20px;
    }
    .el-date-editor {
      margin-right: 20px;
    }
    /deep/.el-input__inner {
      padding-left: 15px;
    }
  }
}
</style>