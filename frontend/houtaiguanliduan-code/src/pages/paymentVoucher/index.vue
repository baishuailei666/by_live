<template>
  <div>
    <!-- 面包屑导航区域 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>支付凭证</el-breadcrumb-item>
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
                      @change="getPaymentVoucherListData()">
            </el-input>
            <div class="btnModular">
              <el-button type="primary"
                         icon="el-icon-search"
                         @click="getPaymentVoucherListData()">搜索</el-button>
              <el-button icon="el-icon-refresh-right"
                         @click="resetBtn">重置</el-button>
              <el-button type="primary"
                         icon="el-icon-plus"
                         @click="addVoucherBtn">新增支付凭证</el-button>
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
        <el-table-column prop="aliAppId"
                         label="支付宝appid"
                         align="center">
        </el-table-column>
        <el-table-column prop="aliPrivateKey"
                         label="支付宝私钥"
                         align="center">
          <template slot-scope="scope">
            <div v-if="scope.row.aliPrivateKey">
              <el-popover placement="top"
                          trigger="hover"
                          v-if="scope.row.aliPrivateKey.length>12"
                          :content="scope.row.aliPrivateKey">
                <span slot="reference"
                      style="display: inline-block;white-space: nowrap; width: 80%; overflow: hidden;text-overflow:ellipsis;">{{scope.row.aliPrivateKey}}</span>
              </el-popover>
              <span v-else>{{ scope.row.aliPrivateKey }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="aliPublicKey"
                         label="支付宝公钥"
                         align="center">
          <template slot-scope="scope">
            <div v-if="scope.row.aliPublicKey">
              <el-popover placement="top"
                          trigger="hover"
                          v-if="scope.row.aliPublicKey.length>12"
                          :content="scope.row.aliPublicKey">
                <span slot="reference"
                      style="display: inline-block;white-space: nowrap; width: 80%; overflow: hidden;text-overflow:ellipsis;">{{scope.row.aliPublicKey}}</span>
              </el-popover>
              <span v-else>{{ scope.row.aliPublicKey }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="wxAppId"
                         label="微信appid"
                         align="center">
        </el-table-column>
        <el-table-column prop="wxMchId"
                         label="微信商户号"
                         align="center">
        </el-table-column>
        <el-table-column prop="wxMchKey"
                         label="微信商户秘钥"
                         align="center">
        </el-table-column>
        <el-table-column prop="wxKeyPath"
                         label="微信认证证书"
                         align="center">
        </el-table-column>
        <el-table-column prop="contrary"
                         label="对公公司"
                         align="center">
        </el-table-column>
        <el-table-column prop="contraryBank"
                         label="对公银行"
                         align="center">
        </el-table-column>
        <el-table-column prop="contraryAccount"
                         label="对公账号"
                         align="center">
        </el-table-column>
        <el-table-column prop="ct"
                         label="创建时间"
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
                       size="small"
                       @click="deleteBtn(scope.row.id)">删除</el-button>
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
      <!-- 1、新增支付凭证对话框开始 -->
      <el-dialog title="新增支付凭证"
                 :visible.sync="addVoucherDialogVisible"
                 width="40%"
                 @close="voucherDialogClosed">
        <el-form :model="addVoucherData"
                 :rules="voucherFromRules"
                 ref="voucherFormRef"
                 label-width="140px">
          <el-form-item label="代理商ID"
                        prop="agentUser">
            <el-input v-model="addVoucherData.agentUser"
                      placeholder="请输入代理商ID"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="支付宝AppId"
                        prop="aliAppId">
            <el-input v-model="addVoucherData.aliAppId"
                      placeholder="请输入支付宝AppId"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="支付宝PrivateKey"
                        prop="aliPrivateKey">
            <el-input v-model="addVoucherData.aliPrivateKey"
                      placeholder="请输入支付宝PrivateKey"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="支付宝PubliceKey"
                        prop="aliPublicKey">
            <el-input v-model="addVoucherData.aliPublicKey"
                      placeholder="请输入支付宝PubliceKey"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="微信AppId"
                        prop="wxAppId">
            <el-input v-model="addVoucherData.wxAppId"
                      placeholder="请输入微信AppId"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="微信MchId"
                        prop="wxMchId">
            <el-input v-model="addVoucherData.wxMchId"
                      placeholder="请输入微信MchId"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="微信MchKey"
                        prop="wxMchKey">
            <el-input v-model="addVoucherData.wxMchKey"
                      placeholder="请输入微信MchKey"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="微信支付证书"
                        prop="wxKeyPath">
            <el-input v-model="addVoucherData.wxKeyPath"
                      placeholder="请上传微信支付证书"
                      :disabled="true"
                      clearable></el-input>
            <!-- <el-button plain
                       type="primary"
                       size="mini"
                       @click="getVoucher">
              上传微信支付证书
            </el-button> -->
            <el-upload :action="uploadURL"
                       name="file"
                       accept=".jpeg,.png,.jpg,.bmp,.gif"
                       :on-preview="handlePreview"
                       :on-remove="handleRemove"
                       :on-success="handleSuccess"
                       :limit=1
                       list-type="picture">
              <el-button size="small"
                         type="primary">点击上传</el-button>
              <div slot="tip"
                   class="el-upload__tip">只能上传jpg/png文件，且不超过5MB</div>
            </el-upload>
          </el-form-item>
          <el-form-item label="电子签SecretId"
                        prop="signSecretId">
            <el-input v-model="addVoucherData.signSecretId"
                      placeholder="请输入电子签SecretId"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="电子签SecretKey"
                        prop="signSecretKey">
            <el-input v-model="addVoucherData.signSecretKey"
                      placeholder="请输入电子签SecretKey"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="电子签模板ID"
                        prop="signTemplateId">
            <el-input v-model="addVoucherData.signTemplateId"
                      placeholder="请输入电子签模板ID"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="对公公司"
                        prop="contrary">
            <el-input v-model="addVoucherData.contrary"
                      placeholder="请输入对公公司"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="对公银行"
                        prop="contraryBank">
            <el-input v-model="addVoucherData.contraryBank"
                      placeholder="请输入对公银行"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="对公银行账号"
                        prop="contraryAccount">
            <el-input v-model="addVoucherData.contraryAccount"
                      placeholder="请输入对公银行账号"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="备注"
                        prop="remark">
            <el-input v-model="addVoucherData.remark"
                      placeholder="请输入备注"
                      clearable></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer"
              class="dialog-footer">
          <el-button @click="addVoucherDialogVisible = false">取 消</el-button>
          <el-button type="primary"
                     @click="addVoucherFun">确 定</el-button>
        </span>
      </el-dialog>
      <!-- 图片预览 -->
      <el-dialog title="图片预览"
                 :visible.sync="previewVisible"
                 width="50%">
        <img :src="previewPath"
             alt=""
             class="previewImg">
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import { pay, del, ins } from "./api.js";
export default {
  data () {
    return {
      pageSize: 10,
      total: 0,
      currentPage: 1,
      tableData: null,//表格数据
      searchMobile: "",//根据手机号搜索
      addVoucherDialogVisible: false,//新增支付凭证弹框的显示与隐藏数据
      addVoucherData: {},//支付凭证提交数据
      voucherFromRules: {//数据校验
        agentUser: [{ required: true, message: "请输入对应代理商", trigger: "blur" }],
        // aliAppId: [{ required: true, message: "请输入支付宝APPid", trigger: "blur" }],
        // aliPrivateKey: [{ required: true, message: "请输入支付宝私钥", trigger: "blur" }],
        // aliPublicKey: [{ required: true, message: "请输入支付宝公钥", trigger: "blur" }],
        // wxAppId: [{ required: true, message: "请输入微信APPid", trigger: "blur" }],
        // wxMchId: [{ required: true, message: "请输入微信商户号", trigger: "blur" }],
        // wxMchKey: [{ required: true, message: "请输入微信商户秘钥", trigger: "blur" }],
        // wxKeyPath: [{ required: true, message: "请输入微信支付认证证书", trigger: "blur" }],
        // contrary: [{ required: true, message: "请输入对公公司", trigger: "blur" }],
      },
      uploadURL: `/ZBDH/BACK/common/upload/cert`,//服务器上传图片的地址
      previewVisible: false,//图片预览
      previewPath: "",//预览图片的地址
    }
  },
  created () {
    this.getPaymentVoucherListData()
  },
  methods: {
    // 预览图片的方法
    handlePreview (file) {
      // console.log(file)
      this.previewPath = file.response.msg
      this.previewVisible = true
    },
    //移除图片的方法
    handleRemove () {
      this.addVoucherData.wxKeyPath = ""
    },
    // 监听图片上传成功的事件
    handleSuccess (response) {
      // console.log(response)
      this.addVoucherData.wxKeyPath = response.msg//保存图片的临时路径地址；
      // console.log(this.addFrom)
    },
    // 新增支付凭证按钮
    addVoucherBtn () {
      this.addVoucherDialogVisible = true
    },
    // 新增支付凭证弹框关闭事件
    voucherDialogClosed () {
      this.$refs.voucherFormRef.resetFields();//清空校验结果
      this.addVoucherData = {}
    },
    // 新增支付凭证提交方法
    addVoucherFun () {
      var that = this
      // 1、进行数据的校验
      this.$refs.voucherFormRef.validate(async valid => {
        if (!valid) {
          // 数据校验失败
          return that.$message.error("请检查数据");
        }
        // 数据校验成功
        let params = that.addVoucherData
        let res = await ins(params)
        // console.log(res)
        if (res.code == 200) {
          that.addVoucherDialogVisible = false;//关闭弹框
          that.addVoucherData = {}//清空输入项
          that.getPaymentVoucherListData()//新增成功重新获取列表
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
    // 上传微信支付证书方法
    // async getVoucher () {

    // },
    // 删除按钮
    deleteBtn (diTeap) {
      this.$confirm("删除后，此支付凭证将永久删除，是否继续删除？", "提示", {
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
        that.getPaymentVoucherListData()
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
    // 获取支付凭证列表页面数据
    async getPaymentVoucherListData (currentTep) {
      var that = this;
      if (!currentTep) {
        this.currentPage = 1
      }
      let params = {
        page: this.currentPage,
        size: this.pageSize,
        mobile: this.searchMobile,
      };
      let res = await pay(params);
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
      this.getPaymentVoucherListData();
    },
    // 改变每页有多少条的方法
    handleSizeChange (val) {
      this.pageSize = val;
      this.getPaymentVoucherListData();
    },
    // 改变当前所在页的方法
    handleCurrentChange (val) {
      this.currentPage = val;
      this.getPaymentVoucherListData(val);
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