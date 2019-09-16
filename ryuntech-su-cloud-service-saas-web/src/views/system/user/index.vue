<template>
  <div class="app-container">
    <el-card>
      <div>
        <el-input v-model="search.username" style="width: 200px;" placeholder="请输入用户名查询" />
        <el-button style="margin-left: 10px;" type="success" icon="el-icon-search" @click="fetchData">查询</el-button>
        <el-button style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleSave">添加</el-button>
      </div>
      <br>
      <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row>
        <el-table-column prop="id" align="center" label="序号" width="60">
          <template scope="scope"><span>{{ scope.$index+(listQuery.page - 1) * listQuery.limit + 1 }} </span></template>
        </el-table-column>
        <el-table-column align="center" label="编号" width="95">
          <template slot-scope="scope">
            {{ scope.row.id }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="用户名" width="150">
          <template slot-scope="scope">
            {{ scope.row.username }}
          </template>
        </el-table-column>
        <el-table-column label="角色" width="150" align="center">
          <template slot-scope="scope">
            <el-tag v-for="role in scope.row.roleList" :key="role.rid" style="margin: 2px;">{{ role.rname }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="手机号" width="150" align="center">
          <template slot-scope="scope">
            {{ scope.row.phone }}
          </template>
        </el-table-column>
        <el-table-column class-name="status-col" label="头像" width="130" align="center">
          <template slot-scope="scope">
            <img :src="scope.row.avatar" width="100" height="100">
          </template>
        </el-table-column>
        <el-table-column align="center" prop="createTime" label="创建时间" width="200">
          <template slot-scope="scope">
            <i class="el-icon-time" />
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleEdit(scope.row.id)">编辑</el-button>
            <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdateUserRoles(scope.$index,scope.row)">修改角色</el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDel(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <save :son-data="form" @sonStatus="status" />

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="listQuery.page"
        :limit.sync="listQuery.limit"
        @pagination="fetchData"
      />
      <!--弹出窗口：修改用户角色-->
      <el-dialog title="修改用户的角色" :visible.sync="editRolesDialogVisible" width="30%">
        <div>
          <el-checkbox v-model="checkAll" :indeterminate="isIndeterminate" @change="handleCheckAllChange">全选</el-checkbox>
          <div style="margin: 15px 0;" />
          <el-checkbox-group v-model="updateUserRolesData.rids">
            <el-checkbox v-for="role in roleOptions" :key="role.id" class="role-checkbox" :label="role.id">
              {{ role.val }}
            </el-checkbox>
          </el-checkbox-group>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="editRolesDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="checkUpdateUserRolesData">确定</el-button>
        </div>
      </el-dialog>
    </el-card>

  </div>
</template>

<script>
import { getList, findById, del, updateUserRoles } from '@/api/system/user'
import { listRoleOptions } from '@/api/system/option'
import Pagination from '@/components/Pagination'
import { root, confirm, pageParamNames } from '@/utils/constants'
import Save from './save'
import { parseTime } from '@/utils/index'

export default {
  components: { Pagination, Save },
  data() {
    return {
      list: null,
      search: {},
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      total: 0,
      dialogVisible: false,
      editRolesDialogVisible: false,
      isIndeterminate: true,
      checkAll: false,
      roleOptions: [],
      roleMap: new Map(),
      updateUserRolesData: {
        idx: null,
        uid: null,
        rids: []
      },
      form: null
    }
  },
  created() {
    this.initData()
    this.fetchData()
  },

  methods: {
    _notify(message, type) {
      this.$message({
        message: message,
        type: type
      })
    },
    initData() {
      // 所有角色选项
      listRoleOptions().then(res => {
        res.data.forEach(obj => {
          console.info(obj.val2)
          if (obj.val2 != root.rval) { // 排除管理员
            this.roleOptions.push(obj)
            this.roleMap.set(obj.id, obj.val)
          }
        })
      })
    },
    fetchData() {
      this.listLoading = true
      getList(this.listQuery, this.search).then(response => {
        this.list = response.data.records
        console.info(response)
        this.total = response.data.total
        this.listLoading = false
      })
    },

    handleSave() {
      this.form = { id: null, createTime: parseTime(new Date()) }
      this.dialogVisible = true
    },
    handleEdit(id) {
      findById(id).then(response => {
        this.form = response.data
      })
    },

    handleUpdateUserRoles(idx, row) {
      // 显示用户的角色
      this.updateUserRolesData = {
        idx: idx,
        uid: row.id,
        rids: row.roleList.map(role => role.rid)
      }
      // 显示弹窗
      this.editRolesDialogVisible = true
    },

    checkUpdateUserRolesData() {
      const noRolesSelected = this.updateUserRolesData && this.updateUserRolesData.rids && this.updateUserRolesData.rids.length == 0
      if (noRolesSelected) {
        this.$confirm('当前没有选中任何角色，会清除该用户已有的角色, 是否继续?', '提示', confirm).then(() => {
          this.invokeUpdateUserRolesApi()
        }).catch(() => {
          this.$message('已取消编辑用户角色')
        })
      } else {
        this.invokeUpdateUserRolesApi()
      }
    },

    // 全选
    handleCheckAllChange(val) {
      const allRids = this.roleOptions.map(role => role.id)
      this.updateUserRolesData.rids = val ? allRids : []
      this.isIndeterminate = false
    },

    invokeUpdateUserRolesApi() {
      updateUserRoles(this.updateUserRolesData).then(res => {
        const newRoles = this.updateUserRolesData.rids.map(rid => {
          const rname = this.roleMap.get(rid)
          if (rname) return { rid, rname }
        })
        this.list[this.updateUserRolesData.idx].roleList = newRoles

        this.editRolesDialogVisible = false
        this.$message.success('更新成功')
      })
    },

    // 子组件的状态Flag，子组件通过`this.$emit('sonStatus', val)`给父组件传值
    // 父组件通过`@sonStatus`的方法`status`监听到子组件传递的值
    status(data) {
      if (data) {
        this.fetchData()
      }
    },

    handleDel(id) {
      this.$confirm('你确定永久删除此账户？, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        del(id).then(response => {
          if (response.tcode === 200) {
            this._notify(response.msg, 'success')
          } else {
            this._notify(response.msg, 'error')
          }
          this.fetchData()
        })
      }).catch(() => {
        this._notify('已取消删除', 'info')
      })
    }
  }
}
</script>
