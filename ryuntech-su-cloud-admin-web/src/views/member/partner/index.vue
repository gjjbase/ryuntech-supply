<template>
  <div class="app-container">
    <el-card>
      <div>
        <el-input style="width: 200px;" v-model="search.partnerId" placeholder="合伙人编号"></el-input>
        <el-input style="width: 200px;" v-model="search.partnerName" placeholder="合伙人姓名"></el-input>
        <el-select v-model="search.status" placeholder="请选择状态">
          <el-option label="未认证" value="0"></el-option>
          <el-option label="已认证" value="1"></el-option>
        </el-select>
        <el-button style="margin-left: 10px;" type="success" icon="el-icon-search" @click="fetchData">查询</el-button>
        <el-button style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleSave">添加</el-button>
      </div>
      <br/>
      <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row>
        <el-table-column prop="id" align="center" label="序号" width="60">
          <template scope="scope"><span>{{scope.$index+(listQuery.page - 1) * listQuery.limit + 1}} </span></template>
        </el-table-column>
        <el-table-column align="center" label="合伙人编号" width="200">
          <template slot-scope="scope">
            {{ scope.row.partnerId }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="姓名" width="120">
          <template slot-scope="scope">
            {{ scope.row.partnerName }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="公司名称" width="200">
          <template slot-scope="scope">
            {{ scope.row.companyName }}
          </template>
        </el-table-column>
        <el-table-column label="注册手机" width="200" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.registerMobile }}</span>
          </template>
        </el-table-column>
        <el-table-column label="审核状态" width="120" align="center">
          <template slot-scope="scope">
           <!-- 状态 00待认证 01 已认证 02已拒绝-->
            <span v-if="scope.row.status==='00'">待审核</span>
            <span v-if="scope.row.status==='01'">已审核</span>
            <span v-if="scope.row.status==='02'">已拒绝</span>
          </template>
        </el-table-column>


        <el-table-column label="审核人" width="120" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.reviewerName }}</span>
          </template>
        </el-table-column>
        <el-table-column class-name="status-col" label="证件号码" width="200" align="center">
          <template slot-scope="scope">
            {{ scope.row.idcartNumber }}
          </template>
        </el-table-column>
        <el-table-column align="center" prop="createTime" label="创建时间" width="200">
          <template slot-scope="scope">
            <i class="el-icon-time"/>
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="handleEdit(scope.row.partnerId)" size="mini" icon="el-icon-edit">编辑</el-button>
            <el-button v-if="scope.row.status==='00'" type="primary" @click="handleExa(scope.row.partnerId)" size="mini" icon="el-icon-edit">审核</el-button>
            <el-button type="danger" @click="handleDel(scope.row.partnerId)" icon="el-icon-delete" size="mini">删除</el-button>
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
    import {getList, findById, updateById,del} from '@/api/member/partner'
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
          limit: 10,
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
        handleExa(id) {
            this.$confirm('确定审核通过？, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'info'
            }).then(() => {
                updateById(id).then(response => {
                    // this.form = response.data;
                    if (response.tcode === 200) {
                        this._notify(response.msg, 'success')
                    } else {
                        this._notify(response.msg, 'error')
                    }
                })
                this.fetchData();
            }).catch(() => {
                this._notify('已取消审核', 'info')
            });

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
            if (response.tcode === 200) {
              this._notify("删除成功", 'success')
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
