<template>
  <div class="app-container">
    <!--查询-->
    <el-row>
      <el-input v-model="search.rname" style="width:200px;" placeholder="角色名" />
      <span style="margin-right: 15px;" />
      <el-input v-model="search.rval" style="width:200px;" placeholder="角色值" />
      <span style="margin-right: 15px;" />
      <el-tooltip class="item" content="搜索" placement="top">
        <el-button icon="el-icon-search" circle @click="fetchData()" />
      </el-tooltip>
    </el-row>
    <div style="margin-bottom: 30px;" />
    <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleCreate">{{ textMap.create }}</el-button>
    <div style="margin-bottom: 30px;" />
    <!--列表-->
    <el-table
      v-loading.body="tableLoading"
      style="width: 100%"
      :data="tableData"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column prop="id" label="序号" width="60">
        <template scope="scope"><span>{{ scope.$index+(listQuery.page - 1) * listQuery.limit + 1 }} </span></template>
      </el-table-column>
      <el-table-column prop="rid" label="角色id" />
      <el-table-column prop="rname" label="角色名" />
      <el-table-column prop="rdesc" label="角色描述" />
      <el-table-column prop="rval" label="角色值" />
      <el-table-column prop="created" label="创建时间">
        <template v-if="scope.row.created!=null" slot-scope="scope">
          <span>{{ parseTime(scope.row.created) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="updated" label="更新时间">
        <template v-if="scope.row.updated!=null" slot-scope="scope">
          <span>{{ parseTime(scope.row.updated) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-tooltip content="编辑" placement="top">
            <el-button size="medium" type="info" icon="el-icon-edit" circle plain @click="handleUpdate(scope.$index,scope.row)" />
          </el-tooltip>
          <el-tooltip v-if="!hasAdminRole(scope.row)" content="修改权限" placement="top">
            <el-button size="medium" type="warning" icon="el-icon-view" circle plain @click="handleUpdateRolePerms(scope.$index,scope.row)" />
          </el-tooltip>
          <el-tooltip v-if="!hasAdminRole(scope.row)" content="删除" placement="top">
            <el-button size="medium" type="danger" icon="el-icon-delete" circle plain @click="handleDelete(scope.$index,scope.row)" />
          </el-tooltip>
          <el-popover v-else trigger="hover" placement="top" style="display: inline-block;">
            <el-alert type="warning" :closable="false" title="权限说明">
              <div>为保证管理员在系统中的最高权限</div>
              <div>不允许编辑管理员自身的权限</div>
              <div>不允许删除管理员角色</div>
            </el-alert>
            <div slot="reference">
              <el-tag style="margin-left: 10px;" type="info">权限说明</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-bottom: 30px;" />
    <!--分页-->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="fetchData"
    />
    <!--弹出窗口：编辑角色-->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="30%">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="150px">
        <el-form-item label="角色名" prop="rname">
          <el-input v-model="temp.rname" />
        </el-form-item>
        <el-form-item v-if="dialogStatus=='create'" label="角色值" prop="rval">
          <el-input v-model="temp.rval" />
        </el-form-item>
        <el-form-item label="角色描述" prop="rdesc">
          <el-input v-model="temp.rdesc" type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="请输入" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">创建</el-button>
        <el-button v-else type="primary" @click="updateData">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import roleApi from '@/api/system/role'
import { parseTime, resetTemp } from '@/utils/index'
import { pageParamNames, confirm, root } from '@/utils/constants'
import Pagination from '@/components/Pagination'

export default {
  name: 'RoleManage',
  components: { Pagination },
  data() {
    return {

      tableLoading: false,
      tableData: [],
      search: {},
      listQuery: {
        page: 1,
        limit: 10,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      total: 0,
      form: null,
      dialogFormVisible: false,
      dialogStatus: '',
      temp: {
        idx: null, // 表格的下标
        rid: null,
        rname: null,
        rdesc: null,
        rval: null,
        created: null,
        updated: null
      },
      textMap: {
        update: '编辑角色',
        create: '新增角色'
      },
      rules: {
        rname: [{ required: true, message: '必填', trigger: 'blur' }],
        rval: [{ required: true, message: '必填', trigger: 'blur' }]
      }
    }
  },

  created() {
    this.fetchData()
  },

  /* watch:{
      //延时查询
      'tableQuery.rname': debounce( function(){
        this.fetchData()
      },500),
      'tableQuery.rval': debounce( function(){
        this.fetchData()
      },500),
    },//watch*/

  methods: {
    parseTime,
    hasAdminRole(row) {
      return row && row.rval == root.rval
    },
    // 查询
    fetchData() {
      this.tableLoading = true
      roleApi.queryRole(this.listQuery, this.search).then(res => {
        /*  this.list = response.data.records
            this.total = response.data.total*/
        this.tableData = res.data.records
        this.total = res.data.total
        this.tableLoading = false
        // 设置后台返回的分页参数
        // pageParamNames.forEach(name=>this.$set(this.tablePage,name,res.data.page[name]))
      })
    },

    // 更新
    handleUpdate(idx, row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.idx = idx
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => this.$refs['dataForm'].clearValidate())
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (!valid) return
        const tempData = Object.assign({}, this.temp)// copy obj
        roleApi.updateRole(tempData).then(res => {
          tempData.updated = res.data.updated
          this.tableData.splice(tempData.idx, 1, tempData)
          this.dialogFormVisible = false
          this.$message.success('更新角色信息成功')
        })
      })
    },

    // 更新用户的角色
    handleUpdateRolePerms(idx, row) {
      console.info('handleUpdateRolePerms')
      this.$router.push(
        { path: '/saas/role_manage/' + row.rid + '/assign_perm' }
      )
    },

    // 删除
    handleDelete(idx, row) {
      this.$confirm('您确定要永久删除该用户？', '提示', confirm).then(() => {
        roleApi.deleteRole({ rid: row.rid }).then(res => {
          this.tableData.splice(idx, 1)
          this.dialogFormVisible = false
          this.$message.success('删除角色成功')
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },

    // 新增
    handleCreate() {
      resetTemp(this.temp)
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => this.$refs['dataForm'].clearValidate())
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (!valid) return
        roleApi.addRole(this.temp).then((res) => {
          this.temp.rid = res.data.rid// 后台传回来新增记录的id
          this.temp.created = res.data.created// 后台传回来新增记录的时间
          this.tableData.unshift(Object.assign({}, this.temp))
          ++this.total
          this.dialogFormVisible = false
          this.message.success('添加角色成功')
        })
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>
