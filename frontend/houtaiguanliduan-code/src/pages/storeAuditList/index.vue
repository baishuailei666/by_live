<template>
  <div>
    <!-- 面包屑导航区域 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>商户审核列表</el-breadcrumb-item>
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
                      @change="getStoreAuditListData()">
            </el-input>
            <span>店铺名称：</span>
            <el-input placeholder="请输入店铺名称"
                      v-model="searchShopName"
                      clearable
                      @change="getStoreAuditListData()">
            </el-input>
            <span>当前状态：</span>
            <el-select v-model="searchShopStatus"
                       placeholder="请选择当前状态"
                       clearable
                       @change="getStoreAuditListData()">
              <el-option v-for="item in shopStatusOption"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>

            <div class="btnModular">
              <el-button type="primary"
                         icon="el-icon-search"
                         @click="getStoreAuditListData()">搜索</el-button>
              <el-button icon="el-icon-refresh-right"
                         @click="resetBtn">重置</el-button>
            </div>
          </div>
        </div>
      </el-row>
      <el-table v-if="this.searchShopStatus==0"
                :data="tableData">
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
        <el-table-column prop="opeUser"
                         label="业务员"
                         align="center">
        </el-table-column>
        <el-table-column prop="status"
                         label="当前状态"
                         align="center">
        </el-table-column>
        <el-table-column prop="introduce"
                         label="商家介绍"
                         align="center">
          <template slot-scope="scope">
            <div v-if="scope.row.introduce">
              <el-popover placement="top"
                          trigger="hover"
                          v-if="scope.row.introduce.length>12"
                          :content="scope.row.introduce">
                <span slot="reference"
                      style="display: inline-block;white-space: nowrap; width: 80%; overflow: hidden;text-overflow:ellipsis;">{{scope.row.introduce}}</span>
              </el-popover>
              <span v-else>{{ scope.row.introduce }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="loginCount"
                         label="审核类型"
                         align="center">
        </el-table-column>
        <el-table-column prop="ct"
                         label="申请时间"
                         align="center">
        </el-table-column>
        <el-table-column label="操作"
                         align="center">
          <template slot-scope="scope">
            <el-button type="text"
                       size="small"
                       @click="handleTrue(scope.row,1)">同意</el-button>
            <el-button type="text"
                       size="small"
                       @click="refuseBtn(scope.row)">拒绝</el-button>
            <el-dialog title="拒绝原因"
                       :visible.sync="dialogVisible"
                       width="40%"
                       style="text-align:left;"
                       @close="reasonDialog">
              <el-input type="textarea"
                        placeholder="请填写拒绝原因"
                        v-model="reasonTeap"></el-input>
              <span slot="footer"
                    class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary"
                           @click="handleFalse(2)">确 定</el-button>
              </span>
            </el-dialog>
          </template>
        </el-table-column>
      </el-table>
      <el-table v-if="this.searchShopStatus==2"
                :data="tableData">
        <el-table-column prop="id"
                         label="ID"
                         width="55" />
        <el-table-column prop="mobile"
                         label="手机号"
                         align="center">
        </el-table-column>
        <el-table-column prop="shop"
                         label="商户店铺名称"
                         align="center">
        </el-table-column>
        <el-table-column prop="opeUser"
                         label="业务员"
                         align="center">
        </el-table-column>
        <el-table-column prop="status"
                         label="当前状态"
                         align="center">
        </el-table-column>
        <el-table-column prop="introduce"
                         label="商家介绍"
                         align="center">
        </el-table-column>
        <el-table-column prop="remark"
                         label="驳回原因"
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
import { auditList, audit } from "./api.js";
export default {
  data () {
    return {
      pageSize: 10,
      total: 0,
      currentPage: 1,
      tableData: null,//表格数据
      searchMobile: "",//根据手机号搜索
      searchShopName: "",//根据店铺名称搜索
      searchShopStatus: 0,//根据当前状态搜索
      shopStatusOption: [
        { label: "待审核", value: 0 },
        { label: "已拒绝", value: 2 },
      ],//当前状态选择数据
      reasonTeap: "",//拒绝原因
      dialogVisible: false,//弹框控制弹出状态变量
      merchantRowTeap: null,//此时要修改的ID+merchantId
    }
  },
  created () {
    this.getStoreAuditListData()
  },
  methods: {
    // 同意方法
    handleTrue (rowTeap, status) {
      this.$confirm("同意后，商户将具有开通服务权限？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.allowShop(rowTeap, status);
      });
    },
    // 拒绝按钮
    refuseBtn (rowTeap) {
      this.dialogVisible = true;
      this.merchantRowTeap = rowTeap
    },
    // 拒绝方法
    handleFalse (status) {
      this.$confirm("拒绝后，商户将无法开通相关服务权限？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.allowShop(this.merchantRowTeap, status);
      });
    },
    // 弹框关闭要进行的方法
    reasonDialog () {
      this.reasonTeap = ""//原因置空
    },
    // 同意和拒绝方法
    async allowShop (rowTeap, status) {
      let params = {
        merchantId: rowTeap.merchantId,
        id: rowTeap.id,
        status: status,
        reason: this.reasonTeap
      };
      let res = await audit(params)
      if (res.code == 200) {
        this.reasonTeap = ""//原因置空
        this.dialogVisible = false//关闭拒绝弹框
        this.getStoreAuditListData();
      } else {
        this.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    // 获取商品列表页面数据
    async getStoreAuditListData (currentTep) {
      var that = this;
      if (!currentTep) {
        this.currentPage = 1
      }
      let params = {
        page: this.currentPage,
        size: this.pageSize,
        mobile: this.searchMobile,
        shop: this.searchShopName,
        status: this.searchShopStatus,
      };
      let res = await auditList(params);
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
      this.searchMobile = ''
      this.searchShopName = ''
      this.searchShopStatus = 2
      this.getStoreAuditListData();
    },
    // 改变每页有多少条的方法
    handleSizeChange (val) {
      this.pageSize = val;
      this.getStoreAuditListData();
    },
    // 改变当前所在页的方法
    handleCurrentChange (val) {
      this.currentPage = val;
      this.getStoreAuditListData(val);
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