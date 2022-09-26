<template>
  <div class="manage">
    <el-table :data="viewedData">
      <el-table-column prop="nickname"
                       label="抖音昵称">
      </el-table-column>
      <el-table-column prop="authorId"
                       label="抖音ID">
      </el-table-column>
      <el-table-column prop="level"
                       label="等级">
      </el-table-column>
      <el-table-column prop="url"
                       label="主页链接">
        <template slot-scope="scope">
          <div v-if="scope.row.url">
            <el-popover placement="top"
                        trigger="hover"
                        v-if="scope.row.url.length>12"
                        :content="scope.row.url">
              <span slot="reference"
                    style="display: inline-block;white-space: nowrap; width: 80%; overflow: hidden;text-overflow:ellipsis;">{{scope.row.url}}</span>
            </el-popover>
            <span v-else>{{ scope.row.url }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="fans"
                       label="粉丝">
      </el-table-column>
      <el-table-column prop="introduce"
                       label="签名">
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
      <el-table-column prop="category"
                       width="105"
                       label="主推类目"
                       sortable>
      </el-table-column>
      <el-table-column prop="sales"
                       width="105"
                       label="橱窗销量"
                       sortable>
      </el-table-column>
      <el-table-column prop="liveDay"
                       width="105"
                       label="直播天数"
                       sortable>
      </el-table-column>
      <el-table-column prop="sales"
                       width="105"
                       label="推广数量"
                       sortable>
      </el-table-column>
      <el-table-column prop="rate"
                       width="105"
                       label="转化指数"
                       sortable>
      </el-table-column>
      <el-table-column label="操作"
                       align="center"
                       width='188'>
        <template slot-scope="scope">
          <div class="operation-box">
            <el-button type="primary"
                       :disabled="scope.row.add==1"
                       size="small"
                       @click="addWxFun(scope.row.id)">
              已加微信
            </el-button>
            <el-button size="small"
                       type="primary"
                       @click="remarksBtn(scope.row.id)">
              跟进记录
            </el-button>
            <el-button size="small"
                       type="primary"
                       @click="questionBtn">
              问题反馈
            </el-button>
            <el-button size="small"
                       class="removeBtn"
                       type="primary"
                       @click="removeBtn(scope.row.id)">
              移除主播
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <!-- 1、问题反馈弹框 -->
    <el-dialog title="问题反馈"
               :visible.sync="questionDialogVisible"
               width="50%">
      <el-input type="textarea"
                :rows="4"
                style="width:100%;"
                placeholder="请输入反馈内容"
                v-model="questiontextarea">
      </el-input>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="questionDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="questionFun">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 跟进记录列表弹框 -->
    <el-dialog title="跟进记录"
               :visible.sync="remarksDialogVisible"
               width="45%"
               style="text-align:left;">
      <el-button type="primary"
                 @click="addRemarksBtn()">新增记录</el-button>
      <el-table :data="remarksTableData">
        <el-table-column prop="note"
                         label="记录内容"
                         align="center" />
        <el-table-column prop="ts"
                         label="时间"
                         align="center">
        </el-table-column>
      </el-table>
    </el-dialog>
    <!-- 添加记录弹框 -->
    <el-dialog title="新增记录"
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
  </div>
</template>

<script>
import { collect, remove, addWx, follow, add } from "./api.js";
export default {
  data () {
    return {
      viewedData: [],
      questionDialogVisible: false,//问题反馈弹框显示与隐藏控制数据
      questiontextarea: '',//反馈数据内容
      remarksDialogVisible: false,//跟进记录列表弹框显示与隐藏控制
      remarksId: null,//此时要加备注,要查备注列表项的ID
      remarksTableData: null,//跟进记录列表
      addRemarksDialogVisible: false,//添加备注弹框的显示与隐藏
      remarksDataTeap: '',//需要提交的备注信息
    }
  },
  created () {
    this.getViewedListData()
  },
  methods: {
    // 问题反馈相关的----------------------------------------
    // 问题反馈按钮
    questionBtn () {
      this.questionDialogVisible = true;
    },
    // 问题反馈提交方法
    questionFun () {
      this.$message({
        type: "success",
        message: '提交反馈成功',
      });
      this.questionDialogVisible = false;
      this.questiontextarea = ''
    },
    // 问题反馈相关的----------------------------------------
    // 跟进记录相关的--------------------------------------
    // 跟进记录按钮,获取对应id列表数据
    async remarksBtn (idTeap) {
      this.remarksId = idTeap
      this.remarksDialogVisible = true
      let params = {
        id: idTeap
      }
      let res = await follow(params)
      // console.log(res)
      if (res.code == 200) {
        this.remarksTableData = res.data
      } else {
        this.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    // // 新增备注按钮方法
    addRemarksBtn () {
      this.addRemarksDialogVisible = true
    },
    // // 添加备注弹框关闭方法
    addRemarksDataDialog () {
      this.remarksDataTeap = ''//关闭弹窗备注内容置空
    },
    // 提交备注方法
    async  getRemarksData () {
      let params = {
        id: this.remarksId,
        content: this.remarksDataTeap
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
    // 跟进记录相关的--------------------------------------
    // 已加微信接口
    async addWxFun (idTeap) {
      var that = this
      let params = {
        id: idTeap
      };
      let res = await addWx(params);
      // console.log(res)
      if (res.code == 200) {
        that.getViewedListData()
        that.$message({
          type: "success",
          message: '操作成功',
        });
      } else {
        that.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    // 1、移除主播按钮
    removeBtn (idTeap) {
      this.$confirm("移除主播后将在查看记录中永久删除，是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.removeFun(idTeap);
      });
    },
    // 移除主播方法
    async  removeFun (idTeap) {
      var that = this
      let params = {
        id: idTeap
      };
      let res = await remove(params);
      // console.log(res)
      if (res.code == 200) {
        that.getViewedListData()
        that.$message({
          type: "success",
          message: '移除成功',
        });

      } else {
        that.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    // 主播列表页面数据
    async getViewedListData () {
      var that = this;
      let res = await collect();
      // console.log(res)
      if (res.code == 200) {
        that.viewedData = res.data;
      } else {
        that.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
  }
}
</script>

<style lang="less" scoped>
.manage {
  min-width: 1028px;
  background-color: #fff;
  min-height: calc(100vh - 90px);
  border-radius: 15px;
  overflow: hidden;
  padding: 10px;
}
</style>
<style>
.manage .el-table {
  width: 100%;
}
.manage .el-table thead .cell {
  font-weight: 700;
  color: #000;
}
.operation-box {
  display: flex;
  justify-content: space-around;
  align-items: center;
  flex-flow: wrap;
}
.operation-box .el-button {
  margin-left: 0px;
}
.operation-box .el-button:nth-child(1),
.operation-box .el-button:nth-child(2) {
  margin-bottom: 10px;
}
.operation-box .removeBtn {
  margin-bottom: 0px !important;
}
</style>
<style>
.el-button--primary {
  color: #fff;
  background-color: rgb(152,46,130);
  border-color: rgb(152,46,130);
}
.el-button--primary:focus,
.el-button--primary:hover {
  background: rgb(152, 46, 130);
  border-color: rgb(152, 46, 130);
  color: #fff;
}
.el-loading-spinner .path {
  stroke-dashoffset: 0;
  stroke-width: 2;
  stroke: rgb(152,46,130);
  stroke-linecap: round;
}
</style>