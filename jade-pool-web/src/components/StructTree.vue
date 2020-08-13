<template>
    <div>
        <el-input
                placeholder="输入关键字进行过滤"
                v-model="filterText">
        </el-input>
        <el-tree
                class="filter-tree"
                :data="data"
                :props="defaultProps"
                :default-expanded-keys="defaultExpandedKeys"
                :load="loadNode"
                lazy
                accordion
                :filter-node-method="filterNode"
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
        name: "StructTree",
        watch: {
            filterText(val) {
                this.$refs.tree.filter(val);
            }
        },
        methods: {
            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            },
            async loadNode(node, resolve) {
                const data = node.data;
                switch(data.type) {
                  case 'schema':
                    // eslint-disable-next-line no-case-declarations
                    const arr = [{sourceId: data.sourceId, icon: 'iconfont', type: 'tables-folder', schema: data.name, name: 'Tables', children: []}, {sourceId: data.sourceId, type: 'views-folder', schema: data.name, name: 'Views', children: []}, {sourceId: data.sourceId, type: 'procedures-folder', schema: data.name, name: 'Stored Procedures', children: []}, {sourceId: data.sourceId, type: 'functions-folder', schema: data.name, name: 'Functions', children: []},]
                    return resolve(arr);
                  case 'tables-folder':
                    // eslint-disable-next-line no-case-declarations
                    const tbs = await this.tableAndView(node, 'TABLE');
                    return resolve(tbs);
                  case 'views-folder':
                    // eslint-disable-next-line no-case-declarations
                     const views = await this.tableAndView(node, 'VIEW');
                     return resolve(views);
                  case 'table':
                    // eslint-disable-next-line no-case-declarations
                    const fieldInfo = await this.getFields(data.sourceId, data.schema, data.name);
                    // eslint-disable-next-line no-case-declarations
                    const fields = fieldInfo.columns;
                    // eslint-disable-next-line no-unused-vars,no-case-declarations
                    const target = [];
                    for(var i = 0; i < fields.length; i++) {
                      const obj = fields[i];
                      if(fieldInfo.primaryKeys.includes(obj.name)) {
                        target.push({sourceId: data.sourceId, icon: 'iconfont iconkey', type: 'field', schema: data.schema, name: obj.name, leaf: true });
                      } else {
                        target.push({sourceId: data.sourceId, icon: 'el-icon-coordinate', type: 'field', schema: data.schema, name: obj.name, leaf: true });
                      }
                    }
                    return resolve(target);
                  default:
                    return resolve([]);
                }
            },
            async tableAndView(node, type) {
              const data = node.data;
              // eslint-disable-next-line no-case-declarations
              const res = await this.getTables(data.sourceId, data.schema);
              console.log(res);
              // eslint-disable-next-line no-case-declarations
              const tableList = res.tables;
              // eslint-disable-next-line no-case-declarations
              let target = [];
              for(var i = 0; i < tableList.length; i++ ) {
                const obj = tableList[i];
                if(obj.type === type) {
                  target.push({sourceId: data.sourceId, icon: 'iconfont icontable',type: 'table', schema: data.schema, name: obj.name, children: []});
                }
              }
              return target;
            },
          // eslint-disable-next-line no-unused-vars
            handleNodeClick(data) {
              // console.log(data);
                /*console.log(data.name);
                console.log(data.type);
                switch(data.type) {
                  case 'schema':
                    break;
                  case 'tables-folder':
                    this.getTables(data.sourceId, data.schema);
                    break;
                  case 'views-folder':
                    this.getFields(1, 'proxy', 'source');
                    break;
                  default:
                      break;
                }*/
            },
            handleContextMenu(data) {
              console.log(data);
            },
            getSchema(id) {
              const that = this;
              that.$api.get('/source/schema', { id : id}, (res) => {
                const list = res.data;
                let target = [];
                for(var i = 0; i < list.length; i++) {
                  // const tables = schema.tables;
                  target.push({sourceId: id, icon: 'iconfont icondatabase' , type: 'schema', name: list[i], children: []});
                }
                this.data = target;
              });
            },
            async getTables(id, schema) {
              const that = this;
              const res = await that.$api.syncGet('/source/schema/tables', { id : id, schema: schema});
              if(res.status === 200) {
                return res.data;
              } else {
                return [];
              }
            },
            async getFields(id, schema, table) {
              const that = this;
              const res = await that.$api.syncGet('/source/schema/table/fields', { id : id, schema: schema, table : table});
              console.log(res);
              if(res.status === 200) {
                return res.data;
              } else {
                return [];
              }
            },
        },
        data() {
            return {
                filterText: '',
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
        mounted: function() {
          this.getSchema(1);
        },
    }
</script>

<style scoped>

</style>