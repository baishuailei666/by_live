<template>
  <div class="anchor-details-box">
    <div class="anchor-details-left">
      <div class="anchor-details-left-top">
        <div class="details-header">
          <img :src="anchorDetailsData.img"
               alt=""
               srcset="">
          <div class="shop-name">
            <i :class="anchorDetailsData==1?'el-icon-male':'el-icon-female'"></i>
            <span>{{anchorDetailsData.nickname}}</span>
          </div>
          <div class="douyin-name">{{'抖音号：'+anchorDetailsData.authorId}}</div>
          <div class="link">
            <span class="link-title">链接跳转：</span>
            <a :href="anchorDetailsData.url"
               target="_blank"
               class="link-detial">{{anchorDetailsData.url}}</a>
            <!-- <span class="link-detial">{{anchorDetailsData.url}}</span> -->
            <!-- <el-button icon="el-icon-document-copy"
                       style="vertical-align: middle;"
                       size="mini"
                       type="text"
                       @click="copyTextBtn(anchorDetailsData.url)">复制</el-button> -->
          </div>
        </div>
        <ul class="details-data">
          <li>
            <div class="details-value">{{anchorDetailsData.fans}}</div>
            <div class="details-key">粉丝数</div>
          </li>
          <li>
            <div class="details-value">{{anchorDetailsData.goods}}</div>
            <div class="details-key">推广商品</div>
          </li>
          <li>
            <div class="details-value">{{anchorDetailsData.sales}}</div>
            <div class="details-key">橱窗销量</div>
          </li>
        </ul>
      </div>
      <div class="anchor-details-left-bottom"
           style="overflow:hidden;">
        <div class="category-title">主推类目</div>
        <!-- 下面是个echarts图 -->
        <div class="category-data">
          <div v-if="this.seriesData.length>0"
               id="echartBingtu">
          </div>
          <div v-else
               class="noneData">
            暂无数据
          </div>
        </div>
      </div>
    </div>
    <div class="anchor-details-right">
      <div class="anchor-details-right-top">
        <div class="synopsis">主播简介</div>
        <div class="cooperation">{{anchorDetailsData.introduce}}</div>
      </div>
      <div class="anchor-details-right-bottom">
        <div class="commerce-data-title">带货数据</div>
        <ul class="commerce-data-content">
          <li>
            <div class="data-value">{{anchorDetailsData.live}}</div>
            <div class="data-key">直播带货场次</div>
          </li>
          <li>
            <div class="data-value">{{anchorDetailsData.liveDay}}</div>
            <div class="data-key">直播带货天数</div>
          </li>
          <li>
            <div class="data-value">{{anchorDetailsData.view}}</div>
            <div class="data-key">直播间观看人数</div>
          </li>
          <li>
            <div class="data-value">{{anchorDetailsData.avgDur}}</div>
            <div class="data-key">平均观看时长</div>
          </li>
          <li>
            <div class="data-value">{{anchorDetailsData.rate}}</div>
            <div class="data-key">转化指数</div>
          </li>
          <li>
            <div class="data-value">{{anchorDetailsData.avgGmv}}</div>
            <div class="data-key">场均销售额</div>
          </li>
          <li>
            <div class="data-value">{{anchorDetailsData.price}}</div>
            <div class="data-key">主推价格区间</div>
          </li>
          <li>
            <div class="data-value">{{anchorDetailsData.score}}</div>
            <div class="data-key">带货口碑</div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
const echarts = require("echarts");
import { info } from "./api.js";
export default {
  data () {
    return {
      anchorDetailsData: {},//主播详细信息
      seriesData: [//高耗能行业用电量-饼图数据
        // { value: 96, name: '智能家具' },
        // { value: 120, name: '家用电器' },
        // { value: 23, name: '男装女装' },
      ],
    }
  },
  created () {
    let idTeap = this.$route.query.getIdTeap
    this.getAnchorDetailsData(idTeap)
  },
  mounted () {

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
    // 获取主播详细信息数据的方法
    async getAnchorDetailsData (teap) {
      let params = {
        id: teap
      }
      let res = await info(params)
      if (res.code == 200) {
        // console.log(res)
        this.anchorDetailsData = res.data
        for (let i = 0; i < this.anchorDetailsData.cateList.length; i++) {
          let obj = {}
          obj.name = this.anchorDetailsData.cateList[i].key;
          obj.value = this.anchorDetailsData.cateList[i].val;
          this.seriesData.push(obj)
        }
        this.$nextTick(() => {
          this.echartsFun()
        })


      } else {
        this.$message({
          type: "error",
          message: res.msg,
        });
      }
    },
    echartsFun () {
      // var that = this;
      var chartDom = document.getElementById('echartBingtu');
      let myChart = echarts.init(chartDom);
      let option = {
        tooltip: {
          trigger: 'item',
          position: ['30%', '50%']
        },
        color: ['#59ADFF', '#F7B500', '#5AD8A6'],
        legend: {
          bottom: '10%',
          left: 'center',
          orient: 'vertical',
          textStyle: {
            color: "rgba(0,0,0,.65)", // 文本颜色
            fontSize: 10
          },
          icon: 'circle',
          itemWidth: 10,
          itemHieght: 10,
          formatter: function (name) {
            // 显示名称+百分比
            // console.log(name)
            var val = "";
            if (name.length > 4) {
              val = name.substr(0, 8);
            } else {
              val = name
            }
            return `${val} `;
          },
        },
        series: [
          {
            // name: '管道燃气特许经营许可评估',
            type: 'pie',
            radius: ['30%', '50%'],
            center: ['50%', '40%'],
            avoidLabelOverlap: false,// 防止标签重叠，默认开启
            label: {
              normal: {
                show: false,//让饼状图上的文字消失；
                position: 'inner',  // 设置标签位置，默认在饼状图外 可选值：'outer' ¦ 'inner（饼状图上）'
                // formatter: '{a} {b} : {c}个 ({d}%)'   设置标签显示内容 ，默认显示{b}
                // {a}指series.name  {b}指series.data的name
                // {c}指series.data的value  {d}%指这一部分占总数的百分比
                formatter: '{c}',
                color: '#fff'
              }
            },
            emphasis: {
              label: {
                show: false
              }
            },
            labelLine: {
              show: false
            },
            data: this.seriesData
          }
        ]
      };
      myChart.setOption(option, true);
      window.addEventListener("resize", function () {
        setTimeout(() => {
          myChart && myChart.resize();
        }, 400);
      })
    }
  }
}
</script>

<style lang="less" scoped>
.anchor-details-box {
  box-sizing: border-box;
  background-color: #fff;
  border-radius: 15px;
  min-width: 1260px;
  min-height: calc(100vh - 95px);
  padding-top: 30px;
  .anchor-details-left {
    box-sizing: border-box;
    display: inline-block;
    width: 290px;
    height: 540px;
    box-shadow: 2px 2px 10px 2px rgba(152, 46, 130, 0.2);
    margin-left: 30px;
    margin-right: 20px;
    .anchor-details-left-top {
      padding-top: 20px;
      text-align: center;
      margin-bottom: 16px;
      .details-header {
        margin-bottom: 16px;
        img {
          width: 50px;
          height: 50px;
          border-radius: 50%;
        }
        .shop-name {
          font-size: 16px;
          line-height: 20px;
          font-weight: 500;
          .el-icon-male {
            color: rgb(100, 192, 239);
          }
          .el-icon-female {
            color: rgb(241, 43, 138);
          }
        }
        .douyin-name {
          font-size: 14px;
          line-height: 20px;
        }
        .link {
          font-size: 12px;
          line-height: 18px;
          .link-title {
            display: inline-block;
          }
          .link-detial {
            vertical-align: middle;
            color: #409eff;
            display: inline-block;
            width: 120px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            margin: 0 auto;
          }
        }
      }
      .details-data {
        display: flex;
        justify-content: space-around;
        align-items: center;
        line-height: 22px;
        .details-value {
          font-weight: 600;
          color: rgb(152, 46, 130);
        }
        .details-key {
          font-size: 14px;
        }
      }
    }
    .anchor-details-left-bottom {
      width: 290px;
      box-sizing: border-box;
      padding: 0px 15px;
      font-weight: 600;
      .category-title {
        margin-bottom: 14px;
      }
      .category-data {
        width: 260px;
        box-sizing: border-box;
        height: 280px;
        // border: 1px solid #f40;
        #echartBingtu {
          width: 260px;
          height: 280px;
        }
        .noneData {
          font-weight: 500;
          text-align: center;
          line-height: 200px;
        }
      }
    }
  }
  .anchor-details-right {
    display: inline-block;
    width: 70%;
    height: 540px;
    box-shadow: 2px 2px 10px 2px rgba(0, 0, 0, 0.2);
    vertical-align: top;
    .anchor-details-right-top {
      box-sizing: border-box;
      width: 100%;
      height: 80px;
      padding-left: 30px;
      border-bottom: 1px solid #e5e5e5;
      padding-top: 10px;
      line-height: 30px;
      .synopsis {
        font-size: 16px;
        font-weight: 600;
      }
      .cooperation {
        font-size: 13px;
        line-height: 20px;
        color: #aaa;
      }
    }
    .anchor-details-right-bottom {
      box-sizing: border-box;
      width: 100%;
      height: 400px;
      padding: 20px 30px 0px;
      // border: 1px solid #f40;
      .commerce-data-title {
        font-size: 16px;
        font-weight: 600;
      }
      .commerce-data-content {
        width: 100%;
        display: flex;
        justify-content: space-around;
        align-items: center;
        flex-wrap: wrap;
        text-align: center;
        padding-top: 40px;
        li {
          width: 25%;
          padding: 20px 0px;
          line-height: 36px;
          .data-value {
            font-weight: 600;
            font-size: 24px;
            color: rgb(152, 46, 130);
          }
          .data-key {
            font-size: 14px;
            color: #888;
          }
        }
      }
    }
  }
}
</style>