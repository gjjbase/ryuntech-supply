<template>
  <div class="app-container">
    <el-card>
      <div>
        <el-input style="width: 200px;" v-model="search.realName" placeholder="请输入名称查询"></el-input>
        <el-button style="margin-left: 10px;" type="success" icon="el-icon-search" @click="fetchData">查询</el-button>
        <el-button style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleSave">添加</el-button>
      </div>
      <br/>
      <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row>
        <el-table-column align="center" label="编号" width="150">
          <template slot-scope="scope">
            {{ scope.row.userId }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="真实姓名" width="95">
          <template slot-scope="scope">
            {{ scope.row.realName }}
          </template>
        </el-table-column>
        <el-table-column align="center" prop="createTime" label="创建时间" width="200">
          <template slot-scope="scope">
            <i class="el-icon-time"/>
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="手机号" width="150" align="center">
          <template slot-scope="scope">
            {{ scope.row.mobile }}
          </template>
        </el-table-column>
        <el-table-column class-name="status-col" label="地址" width="130" align="center">
          <template slot-scope="scope">
            {{ scope.row.address }}
          </template>
        </el-table-column>

        <el-table-column class-name="status-col" label="身份证" width="130" align="center">
          <template slot-scope="scope">
            {{ scope.row.idNumber }}
          </template>
        </el-table-column>

        <el-table-column class-name="status-col" label="公司名" width="130" align="center">
          <template slot-scope="scope">
            {{ scope.row.companyName }}
          </template>
        </el-table-column>

        <el-table-column class-name="status-col" label="职业" width="130" align="center">
          <template slot-scope="scope">
            {{ scope.row.occupation }}
          </template>
        </el-table-column>

        <el-table-column class-name="status-col" label="营业执照编号" width="130" align="center">
          <template slot-scope="scope">
            {{ scope.row.bussinessLicense }}
          </template>
        </el-table-column>

        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="handleEdit(scope.row.userId)" size="mini" icon="el-icon-edit">编辑</el-button>
            <el-button type="danger" @click="handleDel(scope.row.userId)" icon="el-icon-delete" size="mini">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <save :sonData="form" @sonStatus="status"></save>

      <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"
                  @pagination="fetchData"></pagination>
    </el-card>
  </div>
</template>

<script>
  import {getList, findById, del} from '@/api/member/finance'
  import Pagination from '@/components/Pagination'
  import Save from './save'
  import {parseTime} from '@/utils/index'

  export default {
    components: {Pagination, Save},
    data() {
      return {
        list: null,
        search: {},
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 3,
          importance: undefined,
          title: undefined,
          type: undefined,
          sort: '+id'
        },
        total: 0,
        dialogVisible: false,
        form: null,
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      _notify(message, type) {
        this.$message({
          message: message,
          type: type
        })
      },
      fetchData() {
        this.listLoading = true
        getList(this.listQuery, this.search).then(response => {
          this.list = response.data.records
          this.total = response.data.total
          this.listLoading = false
        })
      },
      handleSave() {
        this.form = {id: null, createTime: parseTime(new Date())}
        this.dialogVisible = true;
      },
      handleEdit(userId) {
        findById(userId).then(response => {
          this.form = response.data;
        })
      },

      //子组件的状态Flag，子组件通过`this.$emit('sonStatus', val)`给父组件传值
      //父组件通过`@sonStatus`的方法`status`监听到子组件传递的值
      status(data) {
        if (data) {
          this.fetchData();
        }
      },

      handleDel(id) {
        this.$confirm('你确定永久删除此账户？, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          del(id).then(response => {
            if (response.code === 200) {
              this._notify(response.msg, 'success')
            } else {
              this._notify(response.msg, 'error')
            }
            this.fetchData();
          })
        }).catch(() => {
          this._notify('已取消删除', 'info')
        });
      }
    }
  }
</script>
