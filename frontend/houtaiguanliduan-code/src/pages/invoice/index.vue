<template>
  <div>
    <!-- 面包屑导航区域 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>发票列表</el-breadcrumb-item>
    </el-breadcrumb> -->
    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 上方搜索和添加按钮 -->
      <!-- 搜索区域 -->
      <div class="search">
        <div>
          <span>手机号：</span>
          <el-input placeholder="请输入手机号"
                    v-model="searchMobile"
                    clearable
                    @change="getInvoiceListData()">
          </el-input>
          <span>公司名称：</span>
          <el-input placeholder="请输入公司名称"
                    v-model="searchCompanyName"
                    clearable
                    @change="getInvoiceListData()">
          </el-input>
          <span>当前状态：</span>
          <el-select v-model="searchStatus"
                     placeholder="请选择当前状态"
                     clearable
                     @change="getInvoiceListData()">
            <el-option v-for="item in statusOption"
                       :key="item.value"
                       :label="item.label"
                       :value="item.value">
            </el-option>
          </el-select>
          <div class="btnModular">
            <el-button type="primary"
                       icon="el-icon-search"
                       @click="getInvoiceListData()">搜索</el-button>
            <el-button icon="el-icon-refresh-right"
                       @click="resetBtn">重置</el-button>
          </div>
        </div>
      </div>
      <el-table :data="tableData">
        <el-table-column prop="id"
                         label="序号"
                         width="55" />
        <el-table-column prop="mobile"
                         label="手机号"
                         align="center">
        </el-table-column>
        <el-table-column prop="company"
                         label="公司名称"
                         align="center">
        </el-table-column>
        <el-table-column prop="tax"
                         label="识别号"
                         align="center">
        </el-table-column>
        <el-table-column prop="opeUser"
                         label="业务员"
                         align="center">
        </el-table-column>
        <el-table-column prop="money"
                         label="发票金额"
                         align="center">
        </el-table-column>
        <el-table-column prop="status"
                         label="状态"
                         align="center">
        </el-table-column>
        <el-table-column prop="email"
                         label="电子邮箱"
                         align="center">
        </el-table-column>
        <el-table-column prop="ct"
                         label="申请时间"
                         align="center">
        </el-table-column>
        <el-table-column prop="ut"
                         label="操作时间"
                         align="center">
        </el-table-column>
        <el-table-column prop="remark"
                         label="备注"
                         align="center">
        </el-table-column>
        <el-table-column label="操作"
                         align="center">
          <template slot-scope="scope">
            <el-button type="text"
                       :disabled="scope.row.status!='未开'"
                       size="small"
                       @click="handleTrue(scope.row.id,1)">通过</el-button>
            <el-button type="text"
                       size="small"
                       :disabled="scope.row.status!='未开'"
                       @click="refuseBtn(scope.row.id)">拒绝</el-button>
          </template>
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
      <el-dialog title="拒绝原因"
                 :visible.sync="dialogVisible"
                 width="40%"
                 style="text-align:left;"
                 @close="remarkDialog">
        <el-input type="textarea"
                  placeholder="请填写拒绝原因"
                  v-model="remarkTeap"></el-input>
        <span slot="footer"
              class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary"
                     @click="handleFalse(2)">确 定</el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import { list, check } from "./api.js";
export default {
  data () {
    return {
      pageSize: 10,
      total: 0,
      currentPage: 1,
      tableData: null,//表格数据
      searchMobile: "",//根据手机号搜索
      searchCompanyName: "",//根据公司名称搜索
      searchStatus: "",//根据状态搜索
      statusOption: [
        { label: "未开", value: 0 },
        { label: "已开", value: 1 },
        { label: "驳回", value: 2 },
      ],//当前状态选择数据
      invoiceIdTeap: null,//当前修改状态项Id
      remarkTeap: '',//拒绝原因临时存储
      dialogVisible: false,//拒绝原因弹框的显示与隐藏
    }
  },
  created () {
    this.getInvoiceListData()
  },
  methods: {
    // 同意方法
    handleTrue (idTeap, status) {
      this.$confirm("同意后，您将为商户开具发票单，是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.allowStatus(idTeap, status);
      });
    },
    // 拒绝按钮
    refuseBtn (id) {
      this.dialogVisible = true;
      this.invoiceIdTeap = id
    },
    // 拒绝方法
    handleFalse (status) {
      this.$confirm("拒绝后，商户将无法为商户开具发票？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.allowStatus(this.invoiceIdTeap, status);
      });
    },
    // 弹框关闭要进行的方法
    remarkDialog () {
      this.remarkTeap = ""//原因置空
    },
    // 同意和拒绝方法
    async allowStatus (idTeap, status) {
      let params = {
        id: idTeap,
        status: status,
        remark: this.remarkTeap
      };
      let res = await check(params)
      if (res.code == 200) {
        this.remarkTeap = ""//原因置空
        this.dialogVisible = false//关闭拒绝弹框
        this.getInvoiceListData();
      } else {
        this.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    // 获取商品列表页面数据
    async getInvoiceListData (currentTep) {
      var that = this;
      if (!currentTep) {
        this.currentPage = 1
      }
      let params = {
        page: this.currentPage,
        size: this.pageSize,
        mobile: this.searchMobile,
        company: this.searchCompanyName,
        status: this.searchStatus
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
      this.searchCompanyName = ""
      this.searchStatus = ""
      this.getInvoiceListData();
    },
    // 改变每页有多少条的方法
    handleSizeChange (val) {
      this.pageSize = val;
      this.getInvoiceListData();
    },
    // 改变当前所在页的方法
    handleCurrentChange (val) {
      this.currentPage = val;
      this.getInvoiceListData(val);
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