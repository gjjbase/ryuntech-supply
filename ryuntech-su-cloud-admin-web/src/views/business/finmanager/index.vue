<template>
  <div class="app-container">
    <el-card>
      <div>
        <el-input style="width: 200px;" v-model="search.order.memberName" placeholder="客户姓名"></el-input>
        <el-input style="width: 200px;" v-model="search.order.partnerId" placeholder="推荐人"></el-input>
        <el-select v-model="search.paymentStatus" placeholder="请选择佣金状态">
<!--          结算状态(03-可结算，01已结算，02结算中)-->
          <el-option label="结算中" value="02"></el-option>
          <el-option label="已结算" value="01"></el-option>
        </el-select>
        <el-button style="margin-left: 10px;" type="success" icon="el-icon-search" @click="fetchData">查询</el-button>
      </div>
      <br/>

      <el-table  border v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row>
        <el-table-column prop="id" label="序号" width="60">
          <template scope="scope"><span>{{scope.$index+(listQuery.page - 1) * listQuery.limit + 1}} </span></template>
        </el-table-column>
        <el-table-column align="center"  prop="paymentSystemId"  label="编号" width="180">
          <template slot-scope="scope">
            {{ scope.row.paymentSystemId }}
          </template>
        </el-table-column>
        <el-table-column align="center" prop="memberName" label="客户名称" width="150">
          <template slot-scope="scope" v-if="scope.row.order!=null">
            {{ scope.row.order.memberName }}
          </template>
        </el-table-column>
        <!--<el-table-column align="center" label="公司名称" width="150">
          <template slot-scope="scope">
            {{ scope.row.order.companyName }}
          </template>
        </el-table-column>-->
        <el-table-column label="推荐人" width="200" prop="partnerId" align="center">
          <template slot-scope="scope"  v-if="scope.row.order!=null">
            <span>{{ scope.row.order.partnerId }}</span>
          </template>
        </el-table-column>
        <!--<el-table-column label="年龄" width="100" prop="realName" align="center">
          <template slot-scope="scope">
            &lt;!&ndash;{{ scope.row.order.memberName }}&ndash;&gt;
          </template>
        </el-table-column>-->
        <el-table-column class-name="status-col" prop="orderPayAmount" label="贷款需求" width="130" align="center">
          <template slot-scope="scope" v-if="scope.row.order!=null">
            {{ scope.row.order.orderPayAmount }}
          </template>
        </el-table-column>
        <el-table-column align="center" prop="orderFactPayAmount" label="放款金额" width="130">
          <template slot-scope="scope" v-if="scope.row.order!=null">
            {{ scope.row.order.orderFactPayAmount }}W
          </template>
        </el-table-column>
        <el-table-column align="center" prop="paymentFee" label="佣金" width="150">
          <template slot-scope="scope" v-if="scope.row.order!=null">
            <span>{{ scope.row.paymentFee }}RMB</span>
          </template>
        </el-table-column>
        <el-table-column label="申请结算时间" prop="paymentTime" width="200" align="center">
          <template slot-scope="scope">
            <i class="el-icon-time"/>
            <span>{{ scope.row.paymentTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="结算完成时间" prop="payedTime" width="200" align="center">
          <template slot-scope="scope">
            <i class="el-icon-time"/>
            <span>{{ scope.row.payedTime }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" prop="paymentStatus" label="佣金状态" width="100">
          <template slot-scope="scope">
<!--            佣金结算状态(00-未结算，01已结算，02结算中，03可结算)-->
            <span v-if="scope.row.paymentStatus==='00'">未结算</span>
            <span v-if="scope.row.paymentStatus==='01'">已结算</span>
            <span v-if="scope.row.paymentStatus==='02'">结算中</span>
            <span v-if="scope.row.paymentStatus==='03'">可结算</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button v-if="scope.row.paymentStatus==='02'"  type="primary" @click="handleUp(scope.row.paymentSystemId)" size="mini"  >确认结算</el-button>
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
  import {getList, findById, updateById,settlement} from '@/api/business/finmanager'
  import Pagination from '@/components/Pagination'
  import Save from './save'

  export default {
    components: {Pagination, Save},
    data() {
      return {
        list: null,
        search: {
            order: {
                memberName:"",
                partnerId:""
            }
        },
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
      } ,
      handleEdit(paymentSystemId) {
         updateById(paymentSystemId).then(response => {
             if (response.tcode === 200) {
                 this._notify(response.msg, 'success')
             } else {
                 this._notify(response.msg, 'error')
             }
             this.fetchData();
        }).catch(() => {
             this._notify('已取消', 'info')
         });
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

      handleDel(id) {
        this.$confirm('你确定永久删除此账户, 是否继续?', '提示', {
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
            this.fetchData();
          })
        }).catch(() => {
          this._notify('已取消删除', 'info')
        });
      } ,
      handleUp(paymentSystemId) {

          this.$prompt('请输入结算佣金金额(单位元)', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              inputPattern: /^\d+(\.\d{0,2})?$/,
              inputErrorMessage: '金额不正确'
          }).then(({ value }) => {
              settlement(paymentSystemId,value).then(response => {
                  console.info("response"+response)
                  if (response.tcode === 200) {
                      this.$message({
                          type: 'success',
                          message: '操作成功,结算金额是: ' + value+"元"
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

      }
    }
  }
</script>
