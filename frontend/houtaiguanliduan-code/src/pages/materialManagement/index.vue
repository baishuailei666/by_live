<template>
  <div>
    <!-- 面包屑导航区域 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>素材管理</el-breadcrumb-item>
    </el-breadcrumb> -->
    <!-- 卡片视图区域 -->
    <el-card>
      <div class="button_box">
        <div style="margin-bottom: 30px;"
             v-if="levelTeap==1">
          <el-upload :action="uploadURL0"
                     name="file"
                     :data={type:this.anchor}
                     :on-success="handleSuccess0"
                     :limit=1
                     :show-file-list="false"
                     list-type="picture">
            <el-button size="mini"
                       type="primary">
              <i class="el-icon-plus"></i>
              上传主播数据
            </el-button>
          </el-upload>
        </div>
        <div style="margin-bottom: 30px;"
             v-if="levelTeap==1||levelTeap==2">
          <el-upload :action="uploadURL0"
                     name="file"
                     :data={type:this.resource}
                     :on-success="handleSuccess1"
                     :limit=1
                     :show-file-list="false"
                     list-type="picture">
            <el-button size="mini"
                       type="primary">
              <i class="el-icon-plus"></i>
              上传商户资源
            </el-button>
          </el-upload>
        </div>
        <div style="margin-bottom: 30px;"
             v-if="levelTeap==1">
          <el-button size="mini"
                     type="primary"
                     @click="getVideoBtn">
            <i class="el-icon-plus"></i>
            上传视频数据
          </el-button>
        </div>
      </div>
      <!-- 1、上传视频资源对话框开始 -->
      <el-dialog title="上传视频资源"
                 :visible.sync="addVideoDialogVisible"
                 width="36%"
                 class="video-upload-class"
                 @close="videoDialogClosed">
        <el-form label-width="90px"
                 :model="modAddData"
                 :rules="addFromRules"
                 ref="addFormRef">
          <el-form-item label="视频标题"
                        prop="title">
            <el-input v-model="modAddData.title"
                      placeholder="请输入视频标题"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="视频等级"
                        prop="level">
            <el-select v-model="modAddData.level"
                       placeholder="请选择视频等级"
                       style="width:100%;"
                       clearable>
              <el-option v-for="item in videoLevelOption"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="视频封面"
                        prop="cover">
            <span class="tips-text">(宽高比16:9)</span>
            <input type='file'
                   class="uploadphoto"
                   @change="uploadphoto($event)"
                   ref="inputer"
                   multiple
                   accept="image/png,image/jpeg,image/gif,image/jpg">
            <div style="color:red;">(大小不超过1mb)</div>
          </el-form-item>

          <el-form-item label="视频资源"
                        v-loading="loadingTypeValue"
                        prop="path">
            <el-upload :action="uploadURL1"
                       ref="file"
                       name="file"
                       :before-upload="loadingType"
                       :on-success="handleSuccess2"
                       :limit=1
                       accept=".mp4,.avi,.flv,.mpg,.wmv,.ram"
                       list-type="picture">
              <el-button size="mini"
                         type="primary">
                <i class="el-icon-plus"></i>
                上传视频数据
              </el-button>
            </el-upload>
          </el-form-item>
        </el-form>
        <span slot="footer"
              class="dialog-footer">
          <el-button @click="addVideoDialogVisible = false">取 消</el-button>
          <el-button type="primary"
                     @click="addSubmit">确 定</el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import { videoParam } from "./api.js";
export default {
  data () {
    // var validatePass = (rule, value, callback) => {
    //   if (!this.modAddData.cover) {
    //     callback(new Error('请上传视频封面'));
    //   } else {
    //     callback();
    //   }
    // };
    return {
      anchor: 'anchor',//上传主播数据的type
      resource: 'resource',//上传商户资源数据的type
      uploadURL0: `/ZBDH/BACK/common/upload/excel`,//素材-主播、商户资源上传服务器的地址
      uploadURL1: `/ZBDH/BACK/common/upload/video`,//素材-视频上传服务器的地址
      videoLevelOption: [
        { label: "全部", value: 0 },
        { label: "月卡", value: 1 },
        { label: "季卡", value: 2 },
        { label: "年卡", value: 3 },
      ],
      addVideoDialogVisible: false,//弹框的显示与隐藏
      modAddData: {},//要提交表单的对象
      addFromRules: {//数据校验
        title: [
          { required: true, message: "请输入视频标题", trigger: "blur" },
        ],
        level: [
          { required: true, message: "请选择视频等级", trigger: "blur" },
        ],
        // cover: [
        //   { validator: validatePass, trigger: 'blur', required: true }
        // ],
        path: [
          { required: true, message: "请上传视频资源", trigger: "blur" },
        ],
      },
      levelTeap: 1,//直接超级管理员能有全部上传权限，管理员有上传商户资源权限；
      loadingTypeValue: false
    }
  },
  created () {
    this.levelTeap = localStorage.getItem('level')
  },
  methods: {
    // 上传图片方法
    uploadphoto (e) {
      var that = this
      var file = e.target.files[0];
      var reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function (e) {

        // 读取到的图片base64 数据编码 将此编码字符串传给后台即可
        var imgcode = e.target.result;
        console.log(imgcode);
        that.modAddData.cover = imgcode;
      }
    },
    // 视频列表提交功能
    addSubmit () {
      // 1、进行数据的校验
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) {
          // 数据校验失败
          return this.$message.error("请检查数据");
        }
        // 数据校验成功
        let params = this.modAddData
        let res = await videoParam(params)
        if (res.code == 200) {
          this.$message.success('上传视频数据成功')
          this.addVideoDialogVisible = false
        } else {
          this.$message({
            type: "error",
            message: res.msg
          });
        }
      });
    },
    // 上传视频按钮方法
    getVideoBtn () {
      this.addVideoDialogVisible = true
    },
    // 弹框关闭事件
    videoDialogClosed () {
      this.$refs.addFormRef.resetFields();//清空校验结果
      this.$refs.file.clearFiles()
      this.$refs.file.abort()//终止正在上传的东西；
      this.$refs.inputer.value = ''
      this.modAddData = {}
      this.loadingTypeValue = false;//弹窗关闭要关闭loading;
    },
    // 监听图片上传成功的事件
    handleSuccess0 (response) {
      // console.log(response)
      if (response.code == 200) {
        this.$message.success('导入主播数据成功')
      } else {
        this.$message.error('导入主播数据失败')
      }
    },
    handleSuccess1 (response) {
      if (response.code == 200) {
        this.$message.success('导入商户资源数据成功')
      } else {
        this.$message.error('导入商户资源数据失败')
      }
    },
    // 点击上传时候的效果
    loadingType () {
      this.loadingTypeValue = true;
    },
    handleSuccess2 (response) {
      if (response.code == 200) {
        this.modAddData.path = response.data
        this.loadingTypeValue = false;
        this.$message.success('导入视频成功')
      } else {
        this.$message.error('导入视频数据失败')
      }
    }
  }
}
</script>

<style lang="less" scoped>
.el-card {
  margin-top: 15px;
  height: 400px;
  position: relative;
  .button_box {
    width: 200px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    .el-button--mini {
      width: 180px;
      height: 60px;
      border-radius: 30px;
      font-size: 18px;
    }
  }
}
</style>
<style>
.video-upload-class .el-upload-list--picture .el-upload-list__item {
  padding-left: 10px;
}
.video-upload-class .el-upload-list--picture .el-upload-list__item-thumbnail {
  display: none;
}
.el-upload-list--picture .el-upload-list__item-name i {
  display: none;
}
.tips-text {
  display: inline-block;
  margin-right: 10px;
  /* margin-left: 25px;
  margin-bottom: 10px; */
  color: red;
}
</style>