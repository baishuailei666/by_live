<template>
  <div>
    <!-- 面包屑导航区域 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>配置列表</el-breadcrumb-item>
    </el-breadcrumb> -->
    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 上方搜索和添加按钮 -->
      <el-row>
        <!-- 搜索区域 -->
        <div class="search">
          <div>
            <!-- <span>手机号：</span>
            <el-input placeholder="请输入手机号"
                      v-model="searchMobile"
                      clearable
                      @change="getConfigurationListData()">
            </el-input> -->
            <div class="btnModular">
              <!-- <el-button type="primary"
                         icon="el-icon-search"
                         @click="getConfigurationListData()">搜索</el-button>
              <el-button icon="el-icon-refresh-right"
                         @click="resetBtn">重置</el-button> -->
              <el-button icon="el-icon-setting"
                         type="primary"
                         @click="modPriceBtn">修改服务价格</el-button>
            </div>
          </div>
        </div>
      </el-row>
      <el-row style="margin-bottom: 20px;">
        <!-- <el-button type="primary"
                   icon="el-icon-plus"
                   @click="addUserBtn()">新增代理商</el-button> -->

      </el-row>
      <el-table :data="tableData">
        <el-table-column prop="id"
                         label="序号"
                         width="55" />
        <!-- <el-table-column prop="mobile"
                         label="手机号"
                         align="center">
        </el-table-column>
        <el-table-column prop="agentUser"
                         label="管理员"
                         align="center">
        </el-table-column> -->
        <el-table-column prop="emailSend"
                         label="发件邮箱"
                         align="center">
        </el-table-column>
        <el-table-column prop="emailReceive"
                         label="收件邮箱"
                         align="center">
        </el-table-column>
        <el-table-column prop="kef1"
                         label="客服电话1"
                         align="center">
        </el-table-column>
        <el-table-column prop="kef2"
                         label="客服电话2"
                         align="center">
        </el-table-column>
        <el-table-column label="操作"
                         align="center">
          <template slot-scope="scope">
            <el-button type="text"
                       size="small"
                       @click="modifyBtn(scope.row)">修改</el-button>
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
      <!-- 1、添加和修改代理商组件对话框开始 -->
      <el-dialog :title="title"
                 :visible.sync="addUserDialogVisible"
                 width="50%"
                 @close="userDialogClosed">
        <el-form :model="addUserData"
                 :rules="userFromRules"
                 ref="userFormRef"
                 label-width="90px">
          <!-- <el-form-item label="代理商"
                        prop="agentUser">
            <el-input v-model="addUserData.agentUser"
                      :disabled="title=='修改代理商'"
                      placeholder="请输入代理商"
                      clearable></el-input>
          </el-form-item> -->
          <el-form-item label="发件邮箱"
                        prop="emailSend">
            <el-input v-model="addUserData.emailSend"
                      placeholder="请输入发件邮箱"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="收件邮箱"
                        prop="emailReceive">
            <el-input v-model="addUserData.emailReceive"
                      placeholder="请输入收件邮箱"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="客服电话1"
                        prop="kef1">
            <el-input v-model="addUserData.kef1"
                      placeholder="请输入客服电话1"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="客服电话2"
                        prop="kef2">
            <el-input v-model="addUserData.kef2"
                      placeholder="请输入客服电话2"
                      clearable></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer"
              class="dialog-footer">
          <el-button @click="addUserDialogVisible = false">取 消</el-button>
          <el-button type="primary"
                     @click="addUserFun">确 定</el-button>
        </span>
      </el-dialog>
      <!-- 2、修改服务价格对话框开始 -->
      <el-dialog title="修改价格"
                 :visible.sync="modifyPriceDialogVisible"
                 width="36%"
                 @close="priceDialogClosed">
        <el-form :model="modPriceData"
                 :rules="priceFromRules"
                 ref="priceFormRef"
                 label-width="90px">
          <el-form-item label="月卡价格"
                        prop="monthCard">
            <el-input v-model="modPriceData.monthCard"
                      placeholder="请输入月卡价格"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="季卡价格"
                        prop="seasonCard">
            <el-input v-model="modPriceData.seasonCard"
                      placeholder="请输入季卡价格"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="年卡价格"
                        prop="yearCard">
            <el-input v-model="modPriceData.yearCard"
                      placeholder="请输入年卡价格"
                      clearable></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer"
              class="dialog-footer">
          <el-button @click="modifyPriceDialogVisible = false">取 消</el-button>
          <el-button type="primary"
                     @click="modPriceFun">确 定</el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import { data, modify, price, modifyPrices } from "./api.js";
export default {
  data () {
    return {
      pageSize: 10,
      total: 0,
      currentPage: 1,
      tableData: null,//表格数据
      searchMobile: "",//根据手机号搜索
      addUserDialogVisible: false,//新增和修改User弹框
      title: "",//根据title来判断是修改还是编辑
      addUserData: {},//提交时候的数据
      userFromRules: {
        agentUser: [{ required: true, message: "请输入代理商", trigger: "blur" },],
        emailSend: [
          { required: true, message: "请输入发件邮箱", trigger: "blur" },
          { pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/, message: '请输入正确格式的发件邮箱' }
        ],
        emailReceive: [
          { required: true, message: "请输入收件邮箱", trigger: "blur" },
          { pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/, message: '请输入正确格式的收件邮箱' }
        ],
        kef1: [{ required: true, message: "请输入客服电话1", trigger: "blur" }],
      }, // 代理商添加时表单的验证规则
      modifyPriceDialogVisible: false,//修改价格弹框的显示与隐藏控制数据
      modPriceData: {},//提交时候的数据
      priceFromRules: {
        monthCard: [
          { required: true, message: "请输入月卡价格", trigger: "blur" },
        ],
        seasonCard: [
          { required: true, message: "请输入季卡价格", trigger: "blur" },
        ],
        yearCard: [{ required: true, message: "请输入年卡价格", trigger: "blur" }],
      },
    }
  },
  created () {
    this.getConfigurationListData()
  },
  methods: {
    // 修改价格按钮触发按钮
    async modPriceBtn () {
      this.modifyPriceDialogVisible = true;
      let res = await price()
      if (res.code == 200) {
        this.modPriceData = res.data
      } else {
        this.$message({
          type: "error",
          message: res.msg
        });
      }
    },
    // 价格修改关闭事件
    priceDialogClosed () {
      this.$refs.priceFormRef.resetFields();//清空校验结果
      this.modPriceData = {}
    },
    // 修改价格确定方法
    modPriceFun () {
      var that = this
      // 1、进行数据的校验
      this.$refs.priceFormRef.validate(async valid => {
        if (!valid) {
          // 数据校验失败
          return that.$message.error("请检查数据");
        }
        // 数据校验成功
        let params = {
          month: parseInt(that.modPriceData.monthCard),
          quarter: parseInt(that.modPriceData.seasonCard),
          year: parseInt(that.modPriceData.yearCard),
        };
        let res = await modifyPrices(params)
        console.log(res)
        if (res.code == 200) {
          that.modifyPriceDialogVisible = false;//关闭弹框
          that.modPriceData = {}//清空输入项
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
    // 新增按钮
    addUserBtn () {
      this.addUserData = {
        // emailSend: "", //发件邮箱
        // emailReceive: "", //收件邮箱
        // kef1: "", //客服电话1
        // kef2: "", //客服电话2
      }
      this.title = "新增代理商"
      this.addUserDialogVisible = true;
    },
    // 修改按钮
    modifyBtn (row) {
      console.log(row)
      this.addUserData = {
        id: row.id,
        agentUser: row.agentUser, //管理员
        emailSend: row.emailSend, //发件邮箱
        emailReceive: row.emailReceive, //收件邮箱
        kef1: row.kef1, //客服电话1
        kef2: row.kef2, //客服电话2
      }
      this.title = "修改代理商"
      this.addUserDialogVisible = true;
    },
    // 新增和修改弹框关闭事件
    userDialogClosed () {
      this.$refs.userFormRef.resetFields();//清空校验结果
      this.addUserData = {//关闭弹框清空填入数据
        // emailSend: "", //发件邮箱
        // emailReceive: "", //收件邮箱
        // kef1: "", //客服电话1
        // kef2: "", //客服电话2
      }
    },
    // 新增和修改代理商的提交方法
    addUserFun () {
      var that = this;
      // 1、进行数据的校验
      this.$refs.userFormRef.validate(async valid => {
        if (!valid) {
          // 数据校验失败
          return this.$message.error("请检查数据");
        }
        // 数据校验成功
        let params = that.addUserData;
        let res = await modify(params)
        if (params.id) {
          if (res.code == 200) {
            that.$message({
              type: "success",
              message: '修改成功'
            });
            that.getConfigurationListData();
            that.addUserDialogVisible = false;
          } else {
            that.$message({
              type: "error",
              message: res.msg
            });
          }
        } else {
          if (res.code == 200) {
            that.$message({
              type: "success",
              message: "新增成功"
            });
            that.getConfigurationListData();
            that.addUserDialogVisible = false;
          } else {
            that.$message({
              type: "error",
              message: res.msg
            });
          }
        }
      });
    },
    // 获取商品列表页面数据
    async getConfigurationListData (currentTep) {
      var that = this;
      if (!currentTep) {
        this.currentPage = 1
      }
      let params = {
        page: this.currentPage,
        size: this.pageSize,
        mobile: this.searchMobile,
      };
      let res = await data(params);
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
      this.getConfigurationListData();
    },
    // 改变每页有多少条的方法
    handleSizeChange (val) {
      this.pageSize = val;
      this.getConfigurationListData();
    },
    // 改变当前所在页的方法
    handleCurrentChange (val) {
      this.currentPage = val;
      this.getConfigurationListData(val);
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
    .el-select {
      width: 180px;
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