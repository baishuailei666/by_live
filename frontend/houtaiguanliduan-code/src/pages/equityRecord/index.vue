<template>
  <div>
    <!-- 面包屑导航区域 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>店铺购买记录</el-breadcrumb-item>
    </el-breadcrumb> -->
    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 上方搜索和添加按钮 -->
      <!-- 搜索区域 -->
      <div class="search">
        <div>
          <el-row style="margin-bottom: 20px;">
            <span>手机号：</span>
            <el-input placeholder="请输入手机号"
                      v-model="searchMobile"
                      clearable
                      @change="getEquityRecordListData()">
            </el-input>
            <span>店铺名称：</span>
            <el-input placeholder="请输入店铺名称"
                      v-model="searchShopName"
                      clearable
                      @change="getEquityRecordListData()">
            </el-input>
            <span>订单号：</span>
            <el-input placeholder="请输入订单号"
                      v-model="searchOrderNo"
                      clearable
                      @change="getEquityRecordListData()">
            </el-input>
            <span>购买等级：</span>
            <el-select v-model="searchBuyType"
                       placeholder="请选择购买等级"
                       clearable
                       @change="getEquityRecordListData()">
              <el-option v-for="item in paymentGradeOption"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
          </el-row>
          <el-row>
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
                         @click="getEquityRecordListData()">搜索</el-button>
              <el-button icon="el-icon-refresh-right"
                         @click="resetBtn">重置</el-button>
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
        <el-table-column prop="mobile"
                         label="联系方式"
                         align="center">
        </el-table-column>
        <el-table-column prop="shop"
                         label="店铺名"
                         align="center">
        </el-table-column>
        <el-table-column prop="buyType1"
                         label="服务等级"
                         align="center">
        </el-table-column>
        <el-table-column prop="money"
                         label="支付金额"
                         align="center">
        </el-table-column>
        <el-table-column prop="opeUser"
                         label="业务员"
                         align="center">
        </el-table-column>
        <el-table-column prop="payType1"
                         label="支付类型"
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

    </el-card>
  </div>
</template>

<script>
import { order } from "./api.js";
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
      searchBuyType: "",//根据购买等级搜索
      valueTime: '',//根据时间进行搜索
      start: '',//开始时间
      end: '',//结束时间
      paymentGradeOption: [
        { label: "月卡", value: 1 },
        { label: "季卡", value: 2 },
        { label: "年卡", value: 3 },
      ],//购买等级选择数据
    }
  },
  created () {
    this.getEquityRecordListData()
  },
  methods: {
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
      this.getEquityRecordListData()//这个方法直接放到这里省去了在写一个@blur方法调这个方法
    },
    // 获取商品列表页面数据
    async getEquityRecordListData (currentTep) {
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
        buyType: this.searchBuyType,
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
      this.searchBuyType = ""
      this.valueTime = ''
      this.start = ''
      this.end = ''
      this.getEquityRecordListData();
    },
    // 改变每页有多少条的方法
    handleSizeChange (val) {
      this.pageSize = val;
      this.getEquityRecordListData();
    },
    // 改变当前所在页的方法
    handleCurrentChange (val) {
      this.currentPage = val;
      this.getEquityRecordListData(val);
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
      width: 220px;
      margin-right: 20px;
    }
    .el-select {
      width: 220px;
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
}
</style>