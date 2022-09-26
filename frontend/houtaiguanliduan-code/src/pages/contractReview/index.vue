<template>
  <div>
    <!-- 面包屑导航区域 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>合同列表</el-breadcrumb-item>
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
                      @change="getContractReviewListData()">
            </el-input>
            <span>店铺名称：</span>
            <el-input placeholder="请输入店铺名称"
                      v-model="searchShopName"
                      clearable
                      @change="getContractReviewListData()">
            </el-input>
            <span>签署方名称：</span>
            <el-input placeholder="请输入签署方名称"
                      v-model="searchCompanyName"
                      clearable
                      @change="getContractReviewListData()">
            </el-input>
            <div class="btnModular">
              <el-button type="primary"
                         icon="el-icon-search"
                         @click="getContractReviewListData()">搜索</el-button>
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
                         label="店铺名"
                         align="center">
        </el-table-column>
        <el-table-column prop="company"
                         label="签署方名称"
                         align="center">
        </el-table-column>
        <el-table-column prop="documentName"
                         label="合同名称"
                         align="center">
        </el-table-column>
        <el-table-column prop="signStatus"
                         label="签署状态"
                         align="center">
        </el-table-column>
        <el-table-column prop="opeUser"
                         label="业务员"
                         align="center">
        </el-table-column>
        <el-table-column prop="buyType"
                         label="服务等级"
                         align="center">
        </el-table-column>
        <el-table-column prop="ct"
                         label="创建时间"
                         align="center">
        </el-table-column>
        <el-table-column label="操作"
                         align="center">
          <template slot-scope="scope">
            <el-button type="text"
                       size="small"
                       @click="downloadFun(scope.row)">下载</el-button>
            <el-button type="text"
                       size="small"
                       @click="seeFun(scope.row.documentId)">查看</el-button>
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
      <el-dialog title="合同查看"
                 :visible.sync="previewVisible"
                 width="80%">
        <iframe style="width:100%;height:500px;"
                :src="'/ZBDH/BACK/contract/preview?id='+documentIdTeap"></iframe>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import { list, down } from "./api.js";
export default {
  data () {
    return {
      pageSize: 10,
      total: 0,
      currentPage: 1,
      tableData: null,//表格数据
      searchMobile: "",//根据手机号搜索
      searchShopName: "",//根据店铺名称搜索
      searchCompanyName: "",//根绝签署方名称搜索
      previewVisible: false,//合同查看
      documentIdTeap: false,//查看的id
    }
  },
  created () {
    this.getContractReviewListData()
  },
  methods: {
    // 下载方法
    async downloadFun (row) {
      // console.log(row)
      let params = {
        id: row.documentId,
      };
      let res = await down(params)
      if (res.code == 200) {
        // console.log(res)
        if (res.data) {
          const a = document.createElement('a')
          a.style.display = 'none'
          a.href = res.data
          a.download = row.company + '的合同'
          document.body.appendChild(a)
          a.click()
          document.body.removeChild(a)
        }
      } else {
        this.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    // 查看方法
    async seeFun (idTeap) {
      this.previewVisible = true;
      this.documentIdTeap = idTeap;
    },
    // 获取商品列表页面数据
    async getContractReviewListData (currentTep) {
      var that = this;
      if (!currentTep) {
        this.currentPage = 1
      }
      let params = {
        page: this.currentPage,
        size: this.pageSize,
        mobile: this.searchMobile,
        shop: this.searchShopName,
        company: this.searchCompanyName,
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
      this.searchCompanyName = ""
      this.getContractReviewListData();
    },
    // 改变每页有多少条的方法
    handleSizeChange (val) {
      this.pageSize = val;
      this.getContractReviewListData();
    },
    // 改变当前所在页的方法
    handleCurrentChange (val) {
      this.currentPage = val;
      this.getContractReviewListData(val);
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