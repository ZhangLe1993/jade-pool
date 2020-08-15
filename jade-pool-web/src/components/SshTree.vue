<template>
  <div>
    <el-tree
        class="filter-tree"
        :data="data"
        :props="defaultProps"
        :default-expanded-keys="defaultExpandedKeys"
        ref="tree"
        :highlight-current="true"
        @node-click="handleNodeClick"
        @node-contextmenu="handleContextMenu"
    >
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span>
                <i :class="data.icon"> </i> {{ node.label }}
            </span>
        </span>
    </el-tree>
  </div>
</template>

<script>
export default {
  name: "SshTree",
  props: {
    addTab: Function,
  },
  data() {
    return {
      defaultExpandedKeys: [1],
      data: [],
      defaultProps: {
        children: 'children',
        label: 'name',
        isLeaf: 'leaf',
        icon: 'icon',
      }
    };
  },
  methods: {
    // eslint-disable-next-line no-unused-vars
    handleNodeClick(node) {
      if(node.type === 'NODE') {
        console.log(node);
        this.addTab(node);
      }

    },
    handleContextMenu(data) {
      console.log(data);
    },
  },
  mounted() {
    const that = this;
    that.$api.get('/ssh/tree', { }, (res) => {
      console.log(res.data);
      this.data = res.data;
    });
  }
}
</script>

<style scoped>

</style>