<template>
  <div class="square">
    <header>
      <div class="select">
        <el-button size="small"
                   :type="anchorTypeValue==''?'primary':''"
                   @click="selectAnchorType('')">全部</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='食品饮料'?'primary':''"
                   @click="selectAnchorType('食品饮料')">食品饮料</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='智能家居'?'primary':''"
                   @click="selectAnchorType('智能家居')">智能家居</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='母婴宠物'?'primary':''"
                   @click="selectAnchorType('母婴宠物')">母婴宠物</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='服饰内衣'?'primary':''"
                   @click="selectAnchorType('服饰内衣')">服饰内衣</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='生鲜'?'primary':''"
                   @click="selectAnchorType('生鲜')">生鲜</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='个护家清'?'primary':''"
                   @click="selectAnchorType('个护家清')">个护家清</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='美妆'?'primary':''"
                   @click="selectAnchorType('美妆')">美妆</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='玩具乐器'?'primary':''"
                   @click="selectAnchorType('玩具乐器')">玩具乐器</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='运动户外'?'primary':''"
                   @click="selectAnchorType('运动户外')">运动户外</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='礼品文创'?'primary':''"
                   @click="selectAnchorType('礼品文创')">礼品文创</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='钟表配饰'?'primary':''"
                   @click="selectAnchorType('钟表配饰')">钟表配饰</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='3C数码家电'?'primary':''"
                   @click="selectAnchorType('3C数码家电')">3C数码家电</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='鞋靴箱包'?'primary':''"
                   @click="selectAnchorType('鞋靴箱包')">鞋靴箱包</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='珠宝文玩'?'primary':''"
                   @click="selectAnchorType('珠宝文玩')">珠宝文玩</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='农资绿植'?'primary':''"
                   @click="selectAnchorType('农资绿植')">农资绿植</el-button>
        <el-button size="small"
                   :type="anchorTypeValue=='图书音像'?'primary':''"
                   @click="selectAnchorType('图书音像')">图书音像</el-button>
      </div>
    </header>
    <ul class="list"
        v-if="findAnchorData"
        v-loading="loading">
      <li v-for="item in findAnchorData"
          :key="item.id">
        <div class="box"
             @click="itemFun(item.id)">
          <img :src="item.img"
               class="avatar">
          <p class="name">{{item.nickname}}</p>
          <p class="fans">{{'粉丝：'+item.fans}}</p>
          <div class="classfiy">
            <div class="item"
                 v-for="(fiyItem,index) in item.category"
                 :key="index">
              {{fiyItem}}
            </div>
          </div>
          <div class="footer">
            <div class="item"><span>{{item.goods}}</span>
              <p>推广商品</p>
            </div>
            <div class="item"><span>{{item.sales}}</span>
              <p>橱窗销量</p>
            </div>
          </div>
        </div>
      </li>
    </ul>
    <div v-else
         style="height:200px;line-height:200px;text-align:center;">暂无数据</div>
    <!-- 下面是分页功能elementUI自带的-------------------------------------------------------------------------------------- -->
    <div class="page-box"
         v-if="findAnchorData"
         style="padding: 10px 0px;">
      <el-pagination background
                     @current-change="handleCurrentChange"
                     :current-page="currentPage"
                     layout="prev, pager, next"
                     :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { list, info } from "./api.js";
export default {
  data () {
    return {
      anchorTypeValue: '',//主播类型切换
      total: 0,//总条数
      currentPage: 1,//当前页
      loading: true,
      // servicePower: false,//用于判断是否开通服务权限
      findAnchorData: {},//主播列表数据
    }
  },
  created () {
    this.getFindAnchorListData()
  },
  methods: {
    // 主播类型切换
    selectAnchorType (anchorTypeTeap) {
      this.anchorTypeValue = anchorTypeTeap
      this.getFindAnchorListData()
    },
    // 主播列表页面数据
    async getFindAnchorListData (currentTep) {
      var that = this;
      if (!currentTep) {
        this.currentPage = 1
      }
      let params = {
        page: this.currentPage,
        category: this.anchorTypeValue
      };
      let res = await list(params);
      // console.log(res)
      if (res.code == 200) {
        that.loading = false
        that.findAnchorData = res.data;
        that.total = res.total;
      } else {
        that.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    // 每个主播的点击触发事件
    async itemFun (idTeap) {
      let params = {
        id: idTeap
      };
      let res = await info(params);
      if (res.code == 200) {
        this.$router.push({ path: '/anchor-details', query: { getIdTeap: idTeap } })
      } else {
        if (!this.servicePower) {
          this.$confirm("只对开通了权益的商家开放，开通后即可查看！", "提示", {
            confirmButtonText: "立即开通",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            this.$router.push('/open-service/open-card')//点击立即开通跳转到“开通服务”页面
          });
        }
      }
    },
    // 改变当前所在页的方法
    handleCurrentChange (val) {
      this.currentPage = val;
      this.loading = true
      this.getFindAnchorListData(val);
    },
  }
}
</script>

<style lang="less" scoped>
.square {
  min-width: 1100px;
  header {
    background-color: #fff;
    border-radius: 10px;
    .select {
      padding: 6px 26px 26px;
      .el-button {
        margin: 10px 10px 0 0;
      }
      .el-button:focus,
      .el-button:hover {
        color: rgb(152, 46, 130);
        border-color: #ffdebd;
        background-color: #fff4e9;
      }
      .el-button--primary:focus,
      .el-button--primary:hover {
        background: rgb(152, 46, 130);
        border-color: rgb(152, 46, 130);
        color: #fff;
      }
      .el-button--primary {
        color: #fff;
        background-color: rgb(152, 46, 130);
        border-color: rgb(152, 46, 130);
      }
    }
  }
  .list {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    min-height: 70vh;
    li {
      width: 20%;
      padding: 30px 30px 0 0;
      -webkit-user-select: none;
      -moz-user-select: none;
      -ms-user-select: none;
      user-select: none;
      .box {
        cursor: pointer;
        height: 300px;
        background: #fff;
        text-align: center;
        padding-top: 20px;
        transition: all 0.5s ease 0s;
        img {
          width: 100%;
          height: 100%;
          width: 80px;
          height: 80px;
          border-radius: 50%;
          overflow: hidden;
          margin: 0 auto 24px;
        }
        .name {
          line-height: 1;
          font-size: 18px;
        }
        .fans {
          line-height: 1;
          font-size: 13px;
          margin-top: 6px;
        }
        .classfiy {
          min-height: 68px;
          display: flex;
          flex-wrap: wrap;
          justify-content: center;
          .item {
            padding: 0 6px;
            height: 24px;
            line-height: 24px;
            background: #fff4e9;
            border-radius: 4px;
            font-size: 12px;
            color: rgb(152, 46, 130);
            margin: 12px 4px 0;
          }
        }
        .footer {
          display: flex;
          align-items: center;
          justify-content: space-around;
          padding: 10px 20px 0;
          .item {
            text-align: center;
            span {
              font-size: 16px;
            }
            p {
              font-size: 12px;
              line-height: 20px;
            }
          }
        }
      }
      .box:hover {
        box-shadow: 2px 2px 10px 2px rgba(152, 46, 130, 0.1);
        margin-top: -6px;
      }
    }
  }
  .page-box {
    justify-content: center;
    display: flex;
    .el-pager {
      li {
        background-color: #fff !important;
      }
    }
  }
}
</style>
<style>
.page-box .el-pagination.is-background .el-pager li {
  margin: 0 5px;
  background-color: #fff;
  color: #606266;
  min-width: 30px;
  border-radius: 2px;
}
.el-loading-spinner .path {
  stroke-dashoffset: 0;
  stroke-width: 2;
  stroke: rgb(152, 46, 130);
  stroke-linecap: round;
}
</style>