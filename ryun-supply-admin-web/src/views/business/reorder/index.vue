<template>
  <div class="app-container">
    <el-card>
      <div>
        <el-input style="width: 200px;" v-model="search.orderid" placeholder="请输入订单号查询"></el-input>
        <el-button style="margin-left: 10px;" type="success" icon="el-icon-search" @click="fetchData">查询</el-button>
        <el-button style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleSave">添加</el-button>
      </div>
      <br/>

      <el-table  border v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row>
        <el-table-column prop="id" align="center" label="序号" width="60">
          <template scope="scope"><span>{{scope.$index+(listQuery.page - 1) * listQuery.limit + 1}} </span></template>
        </el-table-column>
        <el-table-column align="center" label="订单编号" width="180">
          <template slot-scope="scope">
            {{ scope.row.orderid }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="客户名称" width="150">
          <template slot-scope="scope">
            {{ scope.row.memberName }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="公司名称" width="150">
          <template slot-scope="scope">
            {{ scope.row.companyName }}
          </template>
        </el-table-column>
        <el-table-column label="申请时间" width="200" align="center">
          <template slot-scope="scope">
            <i class="el-icon-time"/>
            <span>{{ scope.row.orderTime }}</span>
          </template>
        </el-table-column>
        <!--<el-table-column label="合伙人编号" width="150" align="center">
          <template slot-scope="scope">
            {{ scope.row.partnerId }}
          </template>
        </el-table-column>-->
        <el-table-column class-name="status-col" label="城市" width="120" align="center">
          <template slot-scope="scope">
            {{ scope.row.city }}
          </template>
        </el-table-column>
        <el-table-column class-name="status-col" label="贷款申请金额" width="130" align="center">
          <template slot-scope="scope">
            {{ scope.row.orderPayAmount }}
          </template>
        </el-table-column>
        <el-table-column align="center" prop="createTime" label="实际到账金额" width="130">
          <template slot-scope="scope">
            {{ scope.row.orderFactPayAmount }}
          </template>
        </el-table-column>
        <el-table-column align="center" prop="createTime" label="推荐人" width="200">
          <template slot-scope="scope">
            {{ scope.row.partnerId }}
          </template>
        </el-table-column>
        <el-table-column align="center" prop="createTime" label="贷款状态" width="100">
          <template slot-scope="scope">
            <!-- "订单状态:01=待处理(合伙人推荐成功),
           02=资质不符合(橙势审核不符),
           03=资金方审核(提交银行渠道)
           04=已放款(银行渠道已放款，交易完成)
           05=已拒款(交易失败) 06=已删除"-->
            <span v-if="scope.row.orderStatus==='01'">待处理</span>
            <span v-if="scope.row.orderStatus==='02'">已拒款</span>
            <span v-if="scope.row.orderStatus==='03'">待处理</span>
            <span v-if="scope.row.orderStatus==='04'">已放款</span>
            <span v-if="scope.row.orderStatus==='05'">已拒款</span>
            <span v-if="scope.row.orderStatus==='06'">已删除</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="handleEdit(scope.row.orderid)" size="mini" icon="el-icon-edit">编辑</el-button>
            <el-button type="primary" @click="handleLoan(scope.row.orderid)"  v-if="scope.row.orderStatus==='01'" size="mini" icon="el-icon-view">放款</el-button>
            <el-button type="danger" @click="handleRefuse(scope.row.orderid)"   v-if="scope.row.orderStatus==='01'" size="mini" icon="el-icon-view">拒款</el-button>
            <el-button type="danger"  size="mini" v-if="scope.row.orderStatus==='05'"  @click="handleDel(scope.row.orderid)" icon="el-icon-delete">删除</el-button>

<!--            <el-button type="danger" @click="handleDel(scope.row.orderId)" icon="el-icon-delete" size="mini">删除</el-button>-->
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
  import {getList, findById, del,loan,refuse} from '@/api/business/reorder'
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
        this.form = {orderId: null, createTime: parseTime(new Date())}
        this.dialogVisible = true;
      },
      handleEdit(orderId) {
        findById(orderId).then(response => {
          this.form = response.data;
        })
      },
        handleRefuse(orderId) {
            this.$confirm('你确定拒绝此订单？, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                refuse(orderId).then(response => {
                    console.info("response"+response)
                    if (response.tcode === 200) {
                        this._notify(response.msg, '操作成功')
                    } else {
                        this._notify(response.msg, '操作失败')
                    }
                    this.fetchData();
                })
            }).catch(function (err) {
                console.info(err);
                this._notify('已取消', 'info');
            });
       },
       handleLoan(orderId) {


           this.$prompt('请输入放款金额(单位W)', '提示', {
               confirmButtonText: '确定',
               cancelButtonText: '取消',
               inputPattern: /^\d+(\.\d{0,2})?$/,
               inputErrorMessage: '金额不正确'
           }).then(({ value }) => {
               loan(orderId,amt).then(response => {
                   console.info("response"+response)
                   if (response.tcode === 200) {
                         this.$message({
                          type: 'success',
                          message: '操作成功,放款金额是: ' + value
                      });
                   } else {
                       this._notify(response.msg, '操作失败')
                   }
                   this.fetchData();
               })
           }).catch(() => {
               this.$message({
                   type: 'info',
                   message: '取消输入'
               });
           });
           /*this.$confirm('你确定对订单放款？, 是否继续?', '提示', {
               confirmButtonText: '确定',
               cancelButtonText: '取消',
               type: 'warning'
           }).then(() => {
               loan(orderId).then(response => {
                   console.info("response"+response)
                   if (response.tcode === 200) {
                       this._notify(response.msg, '操作成功')
                   } else {
                       this._notify(response.msg, '操作失败')
                   }
                   this.fetchData();
               })
           }).catch(function (err) {
               console.info(err);
               this._notify('已取消', 'info')
           });*/
      },
      handleView(orderId) {
        findById(orderId).then(response => {
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

      handleDel(orderId) {
        this.$confirm('你确定永久删除此订单？, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          del(orderId).then(response => {
              console.info("response"+response)
              if (response.tcode === 200) {
                  this._notify(response.msg, '操作成功')
              } else {
                  this._notify(response.msg, '操作失败')
              }
            this.fetchData();
          })
        }).catch(function (err) {
            console.info(err);
            this._notify('已取消', 'info')
        });
      }
    }
  }
</script>
