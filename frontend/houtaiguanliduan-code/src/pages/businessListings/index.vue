<template>
  <div>
    <!-- 面包屑导航区域 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>商户列表</el-breadcrumb-item>
    </el-breadcrumb> -->
    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 上方搜索和添加按钮 -->
      <el-row>
        <!-- 搜索区域 -->
        <div class="search">
          <div>
            <span>手机号：</span>
            <el-input placeholder="请输入手机号"
                      v-model="searchMobile"
                      clearable
                      @change="getBusinessListingsListData()">
            </el-input>
            <span>店铺名称：</span>
            <el-input placeholder="请输入店铺名称"
                      v-model="searchShopName"
                      clearable
                      @change="getBusinessListingsListData()">
            </el-input>
            <span>店铺状态：</span>
            <el-select v-model="searchShopStatus"
                       placeholder="请选择店铺状态"
                       clearable
                       @change="getBusinessListingsListData()">
              <el-option v-for="item in shopStatusOption"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>

            <div class="btnModular">
              <el-button type="primary"
                         icon="el-icon-search"
                         @click="getBusinessListingsListData()">搜索</el-button>
              <el-button icon="el-icon-refresh-right"
                         @click="resetBtn">重置</el-button>
            </div>
          </div>
        </div>
      </el-row>
      <el-table :data="tableData">
        <el-table-column prop="id"
                         label="序号"
                         width="55" />
        <el-table-column prop="mobile"
                         label="手机号"
                         align="center">
        </el-table-column>
        <el-table-column prop="shop"
                         label="商户店铺名称"
                         align="center">
        </el-table-column>
        <el-table-column prop="shopStatus"
                         label="店铺状态"
                         align="center">
        </el-table-column>
        <el-table-column prop="opeUser"
                         label="业务员"
                         align="center">
        </el-table-column>
        <el-table-column prop="days"
                         label="会员剩余天数"
                         align="center">
        </el-table-column>
        <el-table-column prop="loginCount"
                         label="总登录次数"
                         align="center">
        </el-table-column>
        <el-table-column prop="lt"
                         label="登录时间"
                         align="center">
        </el-table-column>
        <el-table-column prop="ct"
                         label="注册时间"
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
    <!-- <footer>
      @51直播
    </footer> -->
  </div>
</template>

<script>
import { list } from "./api.js";
export default {
  data () {
    return {
      pageSize: 10,
      total: 0,
      currentPage: 1,
      tableData: null,//表格数据
      searchMobile: "",//根据手机号搜索
      searchShopName: "",//根据店铺名称搜索
      searchShopStatus: "",//根据操作类型搜索
      shopStatusOption: [
        { label: "未认证", value: "未认证" },
        { label: "已认证", value: "已认证" },
        { label: "已付费", value: "已付费" },
      ],//店铺状态选择数据
    }
  },
  created () {
    this.getBusinessListingsListData()
  },
  methods: {
    // 获取商品列表页面数据
    async getBusinessListingsListData (currentTep) {
      var that = this;
      if (!currentTep) {
        this.currentPage = 1
      }
      let params = {
        page: this.currentPage,
        size: this.pageSize,
        mobile: this.searchMobile,
        shop: this.searchShopName,
        shopStatus: this.searchShopStatus,
      };
      let res = await list(params);
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
      this.searchShopStatus = ""
      this.getBusinessListingsListData();
    },
    // 改变每页有多少条的方法
    handleSizeChange (val) {
      this.pageSize = val;
      this.getBusinessListingsListData();
    },
    // 改变当前所在页的方法
    handleCurrentChange (val) {
      this.currentPage = val;
      this.getBusinessListingsListData(val);
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