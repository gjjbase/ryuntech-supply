<template>
  <div class="dashboard-container">
    <el-card>
      <span>
        <img src="../../assets/logo_1.png" alt="Coverage Status" style="width: 50%;">
      </span>
      <el-collapse v-model="activeNames">
        <el-collapse-item title="完整的微服务架构" name="1">
          <div>基于 Netflix Zuul</div>
          <div>基于 Spring Security OAuth2</div>
          <div>基于 Spring Boot 2.1.5.RELEASE</div>
          <div>基于 Spring Cloud Greenwich.SR1</div>
        </el-collapse-item>
        <el-collapse-item title="极简的项目设计" name="2">
          <div>Spring Cloud微服务项目</div>
          <div>没有复杂的代码封装，开箱即用，方便扩展</div>
        </el-collapse-item>
        <el-collapse-item title="前后端分离式开发" name="3">
          <div>基于 vue-admin-template</div>
          <div>使用Vue框架，快速前后端分离式开发模式</div>
        </el-collapse-item>
        <el-collapse-item title="完整的开发文档" name="4">
          <div>Spring Cloud + Vue分布式微服务</div>
          <div>提供该项目开发过程的完整文档</div>
        </el-collapse-item>
        <el-collapse-item title="食用说明（必看）" name="5">
          <div>本项目由 睿云科技 开发</div>
        </el-collapse-item>
      </el-collapse>
    </el-card>
  </div>
</template>

<script>

  export default {
    name: 'Dashboard',
    data() {
      return {
        activeNames: ['1', '2', '3', '4', '5']
      }
    }
  }
</script>

<style lang="scss">
  .dashboard-container {
    text-align: center;
    padding: 20px;
  }

  .el-collapse-item__header {
    display: block !important;
  }
</style>
