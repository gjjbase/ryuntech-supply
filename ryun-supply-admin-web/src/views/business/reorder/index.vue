<template>
  <div class="app-container">
    <el-card>
      <div>
        <el-input style="width: 200px;" v-model="search.username" placeholder="请输入用户名查询"></el-input>
        <el-button style="margin-left: 10px;" type="success" icon="el-icon-search" @click="fetchData">查询</el-button>
        <el-button style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleSave">添加</el-button>
      </div>
      <br/>
      <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row>
        <el-table-column align="center" label="订单编号" width="95">
          <template slot-scope="scope">
            {{ scope.row.orderId }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="客户编号" width="150">
          <template slot-scope="scope">
            {{ scope.row.memberId }}
          </template>
        </el-table-column>
        <el-table-column label="申请时间" width="200" align="center">
          <template slot-scope="scope">
            <i class="el-icon-time"/>
            <span>{{ scope.row.orderTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="合伙人编号" width="150" align="center">
          <template slot-scope="scope">
            {{ scope.row.partnerId }}
          </template>
        </el-table-column>
        <el-table-column class-name="status-col" label="推荐人编号" width="130" align="center">
          <template slot-scope="scope">
            {{ scope.row.orderMemo }}
          </template>
        </el-table-column>
        <el-table-column class-name="status-col" label="订单申请金额" width="130" align="center">
          <template slot-scope="scope">
            {{ scope.row.orderPayAmount }}
          </template>
        </el-table-column>
        <el-table-column align="center" prop="createTime" label="实际到账金额" width="130">
          <template slot-scope="scope">
            {{ scope.row.orderFactPayAmount }}
          </template>
        </el-table-column>
        <el-table-column align="center" prop="createTime" label="到账时间" width="200">
          <template slot-scope="scope">
            <i class="el-icon-time"/>
            <span>{{ scope.row.orderPayTime }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="createTime" label="订单状态" width="100">
          <template slot-scope="scope">
            {{ scope.row.orderStatus }}
          </template>
        </el-table-column>
        <el-table-column align="center" prop="createTime" label="佣金状态" width="100">
          <template slot-scope="scope">
            {{ scope.row.paymentStatus }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="handleEdit(scope.row.id)" size="mini" icon="el-icon-edit">编辑</el-button>
            <el-button type="danger" @click="handleDel(scope.row.id)" icon="el-icon-delete" size="mini">删除</el-button>
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
  import {getList, findById, del} from '@/api/reorder/reorder'
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
      handleEdit(id) {
        findById(id).then(response => {
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
