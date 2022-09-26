<template>
  <div style="height: 100%">
    <el-menu style="height: 100%"
             class="el-menu-vertical-demo"
             :router="true"
             :unique-opened="true"
             background-color="#002248"
             text-color="#fff"
             active-text-color="#fff"
             :default-active="$route.path">
      <div v-for="(item, index) in menus"
           :key="item.rightUrl">
        <el-menu-item class="lineHeight"
                      :index="'/'+ oneChildObj.rightUrl"
                      v-if="onlyChild(item)">
          <i :class="item.icon"></i><span>{{ oneChildObj.name }}</span>
        </el-menu-item>
        <el-submenu v-else
                    class="lineHeight"
                    :index="index + ''">
          <template slot="title">
            <i :class="item.icon"></i>
            <span>{{ item.name }}</span>
          </template>
          <el-menu-item-group>
            <el-menu-item :index="'/' + items.rightUrl"
                          v-for="items in item.childRights"
                          :key="items.rightUrl">{{ items.name }}</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </div>
    </el-menu>
  </div>
</template>

<script>
export default {
  data () {
    return {
      menus: [],
      arr: [],
    };
  },
  created () {
    let menus = localStorage.getItem("menus");
    this.menus = JSON.parse(menus);
  },
  methods: {

    onlyChild (parent) {
      const { childRights } = parent;
      const allChild = childRights.filter(item => {
        this.oneChildObj = item;
        return true;
      })
      if (allChild.length === 1) {
        return true;
      }
      return false;
    }
  },
};
</script>

<style lang="less" scoped>
/deep/.el-submenu__title {
  font-size: 18px;
}
/deep/.el-menu-item {
  font-size: 18px;
}
.is-active {
  background-color: #0396f8 !important;
}
.el-submenu .el-menu-item {
  min-width: 100%;
}
.lineHeight {
  span {
    vertical-align: top;
  }
}
</style>