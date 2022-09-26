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
                      @change="getUserListData()">
            </el-input>
            <div style="display:inline-block;"
                 v-if="levelTeap==1">
              <span>用户等级：</span>
              <el-select v-model="searchLevel"
                         placeholder="请选择用户等级"
                         clearable
                         @change="getUserListData()">
                <el-option v-for="item in levelTypeOption"
                           :key="item.value"
                           :label="item.label"
                           :value="item.value">
                </el-option>
              </el-select>
            </div>
            <div class="btnModular">
              <el-button type="primary"
                         icon="el-icon-plus"
                         @click="addUserBtn">新增用户</el-button>
              <el-button type="primary"
                         icon="el-icon-search"
                         @click="getUserListData()">搜索</el-button>
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
                         label="账号（手机号）"
                         align="center">
        </el-table-column>
        <el-table-column prop="level"
                         label="用户等级"
                         align="center">
        </el-table-column>
        <el-table-column prop="remark"
                         label="用户备注"
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
                       @click="deleteBtn(scope.row.id)">删除</el-button>
            <el-button type="text"
                       size="small"
                       @click="resetPwdBtn(scope.row.mobile)">重置密码</el-button>
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
      <!-- 1、新增用户对话框开始 -->
      <el-dialog title="新增用户"
                 :visible.sync="addUserDialogVisible"
                 width="36%"
                 @close="userDialogClosed">
        <el-form :model="addUserData"
                 :rules="userFromRules"
                 ref="userFormRef"
                 label-width="90px">
          <el-form-item label="手机号"
                        prop="mobile">
            <el-input v-model="addUserData.mobile"
                      placeholder="请输入用户手机号"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="用户等级"
                        prop="level">
            <div v-if="levelTeap==1">
              <el-select v-model="addUserData.level"
                         placeholder="请选择用户等级"
                         style="width:100%;"
                         clearable>
                <el-option v-for="item in userLevelOption1"
                           :key="item.value"
                           :label="item.label"
                           :value="item.value">
                </el-option>
              </el-select>
            </div>
            <div v-else>
              <el-select v-model="addUserData.level"
                         placeholder="请选择用户等级"
                         style="width:100%;"
                         clearable>
                <el-option v-for="item in userLevelOption2"
                           :key="item.value"
                           :label="item.label"
                           :value="item.value">
                </el-option>
              </el-select>
            </div>
          </el-form-item>
          <el-form-item label="用户备注"
                        prop="remark">
            <el-input v-model="addUserData.remark"
                      placeholder="请输入用户备注"
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
    </el-card>
  </div>
</template>

<script>
import { user, create, del, reset } from "./api.js";
export default {
  data () {
    return {
      pageSize: 10,
      total: 0,
      currentPage: 1,
      tableData: null,//表格数据
      searchMobile: "",//根据手机号搜索
      searchLevel: '',//根据用户等级搜索-------------------------------------------》
      levelTypeOption: [
        { label: "管理员", value: 2 },
        { label: "业务员", value: 3 },
      ],//店铺状态选择数据
      addUserDialogVisible: false,//新增用户弹框的显示与隐藏
      addUserData: {},//提交时候的数据
      levelTeap: null,//用于判断是管理员还是超级管理员
      userLevelOption1: [
        { label: "管理员", value: 2 },
        { label: "业务员", value: 3 },
      ],//店铺状态选择数据，超管可选
      userLevelOption2: [
        { label: "业务员", value: 3 },
      ],//店铺状态选择数据，管理员可选
      userFromRules: {//数据校验
        mobile: [{ required: true, message: "请输入手机号", trigger: "blur" },
        { pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/, message: '请输入正确格式的手机号' }],
        level: [{ required: true, message: "请选择用户等级", trigger: "blur" }],
      },
    }
  },
  created () {
    this.getUserListData()
    this.levelTeap = localStorage.getItem("level")
  },
  methods: {
    // 新增用户按钮方法
    addUserBtn () {
      this.addUserDialogVisible = true
    },
    // 新增用户弹框关闭事件
    userDialogClosed () {
      this.$refs.userFormRef.resetFields();//清空校验结果
      this.addUserData = {}
    },
    // 新增用户提交方法
    addUserFun () {
      var that = this
      // 1、进行数据的校验
      this.$refs.userFormRef.validate(async valid => {
        if (!valid) {
          // 数据校验失败
          return that.$message.error("请检查数据");
        }
        // 数据校验成功
        let params = that.addUserData
        let res = await create(params)
        // console.log(res)
        if (res.code == 200) {
          that.addUserDialogVisible = false;//关闭弹框
          that.addUserData = {}//清空输入项
          that.getUserListData()//新增成功重新获取列表
          that.$message({
            type: "success",
            message: '新增成功'
          });
        } else {
          that.$message({
            type: "error",
            message: res.msg
          });
        }
      });
    },
    // 删除按钮
    deleteBtn (diTeap) {
      this.$confirm("删除后，此用户将永久不在用户列表中展示，是否继续删除？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.deleteFun(diTeap);
      });
    },
    // 删除方法
    async deleteFun (idTeap) {
      var that = this
      let params = {
        id: idTeap
      };
      let res = await del(params);
      // console.log(res)
      if (res.code == 200) {
        this.getUserListData()
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
    // 重置密码按钮
    resetPwdBtn (mobileTeap) {
      this.$confirm("重置后，此用户的密码将变为“123456”，是否继续重置密码？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.resetFun(mobileTeap);
      });
    },
    // 重置密码方法
    async resetFun (mobileTeap) {
      var that = this
      let params = {
        mobile: mobileTeap,
        source: "back"
      };
      let res = await reset(params);
      // console.log(res)
      if (res.code == 200) {
        this.getUserListData()
        that.$message({
          type: "success",
          message: "密码重置成功",
        });

      } else {
        that.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    // 获取商品列表页面数据
    async getUserListData (currentTep) {
      var that = this;
      if (!currentTep) {
        this.currentPage = 1
      }
      let params = {
        page: this.currentPage,
        size: this.pageSize,
        keyword: this.searchMobile,
        level: this.searchLevel
      };
      let res = await user(params);
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
      this.searchLevel = ''
      this.getUserListData();
    },
    // 改变每页有多少条的方法
    handleSizeChange (val) {
      this.pageSize = val;
      this.getUserListData();
    },
    // 改变当前所在页的方法
    handleCurrentChange (val) {
      this.currentPage = val;
      this.getUserListData(val);
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
      font-size: 20px;
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