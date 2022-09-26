<template>
  <div>
    <!-- 面包屑导航区域 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>商户资源</el-breadcrumb-item>
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
                      @change="getMerchantResourcesListData()">
            </el-input>
            <span>店铺名称：</span>
            <el-input placeholder="请输入店铺名称"
                      v-model="searchShopName"
                      clearable
                      @change="getMerchantResourcesListData()">
            </el-input>
            <span>意向程度：</span>
            <el-select v-model="searchIntentionStatus"
                       placeholder="请选择意向程度"
                       clearable
                       @change="getMerchantResourcesListData()">
              <el-option v-for="item in intentionOption"
                         :key="item.label"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
            <div class="btnModular">
              <el-button type="primary"
                         icon="el-icon-search"
                         @click="getMerchantResourcesListData()">搜索</el-button>
              <el-button icon="el-icon-refresh-right"
                         @click="resetBtn">重置</el-button>
            </div>
          </div>
        </div>
      </el-row>
      <div>
        <span class="extension"
              v-text="'商家推广地址：'+copyText"></span>
        <el-button icon="el-icon-document-copy"
                   style="vertical-align: top;"
                   type="primary"
                   @click="copyTextBtn(copyText)">复制</el-button>
      </div>
      <el-table :data="tableData">
        <el-table-column prop="id"
                         label="序号"
                         width="55" />
        <el-table-column prop="shop"
                         label="店铺名称"
                         align="center">
        </el-table-column>
        <el-table-column prop="mobile"
                         label="联系方式"
                         align="center">
        </el-table-column>
        <el-table-column prop="intention"
                         label="意向程度"
                         align="center">
        </el-table-column>
        <el-table-column prop="content"
                         label="备注"
                         align="center">
        </el-table-column>
        <el-table-column prop="ct"
                         label="创建时间"
                         align="center">
        </el-table-column>
        <el-table-column prop="ut"
                         label="更新时间"
                         align="center">
        </el-table-column>
        <el-table-column label="操作"
                         align="center">
          <template slot-scope="scope">

            <el-button type="text"
                       size="small"
                       @click="intentionBtn(scope.row.id)">意向程度</el-button>
            <el-button type="text"
                       v-if="scope.row.content"
                       size="small"
                       style="color:#f56c6c;"
                       @click="remarksBtn(scope.row.id)">备注</el-button>
            <el-button type="text"
                       v-else
                       size="small"
                       @click="remarksBtn(scope.row.id)">备注</el-button>

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
      <!-- 意向程度修改弹框 -->
      <el-dialog title="意向程度"
                 :visible.sync="intentionDialogVisible"
                 width="20%"
                 style="text-align:left;"
                 @close="intentionDialog">
        <el-select v-model="editIntentionStatus"
                   placeholder="意向程度"
                   clearable>
          <el-option v-for="item in intentionOption"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
        <span slot="footer"
              class="dialog-footer">
          <el-button @click="intentionDialogVisible = false">取 消</el-button>
          <el-button type="primary"
                     @click="selectIntentionStatus()">确 定</el-button>
        </span>
      </el-dialog>
      <!-- 备注列表弹框 -->
      <el-dialog title="备注列表"
                 :visible.sync="remarksDialogVisible"
                 width="45%"
                 style="text-align:left;"
                 @close="remarksDataDialog">
        <el-button type="primary"
                   @click="addRemarksBtn()">新增备注</el-button>
        <el-table :data="remarksTableData">
          <el-table-column prop="ts"
                           label="时间"
                           align="center" />
          <el-table-column prop="note"
                           label="备注"
                           align="center">
          </el-table-column>
          <el-table-column prop="oid"
                           label="操作人"
                           align="center">
          </el-table-column>

        </el-table>
      </el-dialog>
      <!-- 添加备注弹框 -->
      <el-dialog title="添加备注"
                 :visible.sync="addRemarksDialogVisible"
                 width="30%"
                 style="text-align:left;"
                 @close="addRemarksDataDialog">
        <el-input type="textarea"
                  placeholder="请填写备注"
                  v-model="remarksDataTeap"></el-input>
        <span slot="footer"
              class="dialog-footer">
          <el-button @click="addRemarksDialogVisible = false">取 消</el-button>
          <el-button type="primary"
                     @click="getRemarksData()">确 定</el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import { list, edit, note, add } from "./api.js";
export default {
  data () {
    return {
      pageSize: 10,
      total: 0,
      currentPage: 1,
      tableData: null,//表格数据
      searchMobile: "",//根据手机号搜索
      searchShopName: "",//根据店铺名称搜索
      searchIntentionStatus: 0,//根据意向程度搜索
      intentionOption: [
        { label: "未联系", value: 0 },
        { label: "跟进中", value: 1 },
        { label: "已处理", value: 2 },
        { label: "已拒绝", value: 3 },
      ],//意向程度选择数据
      editIntentionStatus: "",//修改意向程度
      intentionDialogVisible: false,//意向程度弹框的显示与隐藏
      idTeap: null,//此时要修改的ID
      remarksDialogVisible: false,//备注列表弹框的显示与隐藏
      remarksId: null,//此时要加备注,要查备注列表项的ID
      remarksTableData: null,//备注列表内容
      addRemarksDialogVisible: false,//添加备注弹框的显示与隐藏
      remarksDataTeap: '',//需要提交的备注信息
      copyText: '',//推广地址
    }
  },
  created () {
    this.getMerchantResourcesListData()
    let userIdTeap = localStorage.getItem("userId")
    this.copyText = 'https://taizb.com/login?promotion=' + userIdTeap
  },
  methods: {
    // 复制功能方法
    copyTextBtn (copytext) {
      const text = document.createElement('textarea'); // 创建节点
      text.setAttribute('readonly', 'readonly');
      text.value = copytext; // 赋值
      document.body.appendChild(text); // 插入节点
      text.setSelectionRange(0, text.value.length);
      text.select(); // 选中节点
      document.execCommand('copy'); // 执行浏览器复制方法
      if (document.body.removeChild(text)) {
        this.$message({ type: 'success', message: '复制成功' })
      } else {
        this.$message({ type: 'error', message: '复制失败' })
      }
    },
    // 获取商品资源页面数据
    async getMerchantResourcesListData (currentTep) {
      var that = this;
      if (!currentTep) {
        this.currentPage = 1
      }
      let params = {
        page: this.currentPage,
        size: this.pageSize,
        mobile: this.searchMobile,
        shop: this.searchShopName,
        intention: this.searchIntentionStatus,
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
    // 意向程度按钮的方法
    intentionBtn (id) {
      this.idTeap = id
      this.intentionDialogVisible = true;
    },
    // 备注按钮的方法，获取这个id，并且获取这一条的备注列表
    async remarksBtn (id) {
      this.remarksId = id
      this.remarksDialogVisible = true
      let params = {
        id: id
      }
      let res = await note(params)
      console.log(res)
      if (res.code == 200) {
        this.remarksTableData = res.data
      } else {
        this.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    // 新增备注按钮方法
    addRemarksBtn () {
      this.addRemarksDialogVisible = true
    },
    // 提交备注方法
    async  getRemarksData () {
      let params = {
        id: this.remarksId,
        note: this.remarksDataTeap
      }
      let res = await add(params)
      if (res.code == 200) {
        this.remarksDataTeap = ''
        this.addRemarksDialogVisible = false
        this.remarksBtn(this.remarksId)
      } else {
        this.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    // 操作里面选择意向程度的接口
    async selectIntentionStatus () {
      // console.log()
      var that = this;
      let params = {
        id: this.idTeap,
        intention: this.editIntentionStatus,
      };
      let res = await edit(params);
      // console.log(res)
      if (res.code == 200) {
        this.editIntentionStatus = ""
        this.intentionDialogVisible = false
        this.getMerchantResourcesListData()
      } else {
        that.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    // 弹框关闭要进行的方法
    intentionDialog () {
      this.editIntentionStatus = ""//意向程度置空
    },
    // 备注列表弹框关闭事件
    remarksDataDialog () {
      this.getMerchantResourcesListData()
      this.remarksTableData = null//备注列表内容关闭弹框置空
    },
    // 添加备注弹框关闭方法
    addRemarksDataDialog () {
      this.remarksDataTeap = ''//关闭弹窗备注内容置空
    },
    // 3、重置方法
    resetBtn () {
      this.searchMobile = ''
      this.searchShopName = ''
      this.searchIntentionStatus = 0
      this.getMerchantResourcesListData();
    },
    // 改变每页有多少条的方法
    handleSizeChange (val) {
      this.pageSize = val;
      this.getMerchantResourcesListData();
    },
    // 改变当前所在页的方法
    handleCurrentChange (val) {
      this.currentPage = val;
      this.getMerchantResourcesListData(val);
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
.extension {
  font-size: 18px;
  display: inline-block;
  margin-right: 20px;
  line-height: 40px;
}
</style>