<template>
  <div class="app-layout">

    <aside class="sidebar bg-dark text-white">
      <h3 class="mt-4 mb-4 text-center">DeliveryManager</h3>

      <div class="d-grid gap-2 px-3">
        <router-link to="/dashboard" class="btn btn-outline-light">Dashboard</router-link>
        <router-link to="/users" class="btn btn-outline-light">Usuários</router-link>
        <router-link to="/orders" class="btn btn-outline-light">Pedidos</router-link>
        <router-link to="/audit-logs" class="btn btn-outline-light">Auditoria</router-link>
        <router-link to="/monitoring" class="btn btn-outline-light">Monitoramento</router-link>
        <router-link to="/reports" class="btn btn-outline-light">Relatórios</router-link>

        <button class="btn btn-danger mt-4" @click="logout">
          Sair
        </button>
      </div>
    </aside>

    <main class="content">
      <h1 class="mb-4">Relatórios Executivos</h1>

      <div class="row">

        <div class="col-md-3 mb-3">
          <div class="card shadow border-0">
            <div class="card-body">
              <h6>Total Usuários</h6>
              <h2>{{ summary.totalUsers }}</h2>
            </div>
          </div>
        </div>

        <div class="col-md-3 mb-3">
          <div class="card shadow border-0">
            <div class="card-body">
              <h6>Total Pedidos</h6>
              <h2>{{ summary.totalOrders }}</h2>
            </div>
          </div>
        </div>

        <div class="col-md-3 mb-3">
          <div class="card shadow border-0">
            <div class="card-body">
              <h6>Concluídos</h6>
              <h2>{{ summary.completedOrders }}</h2>
            </div>
          </div>
        </div>

        <div class="col-md-3 mb-3">
          <div class="card shadow border-0">
            <div class="card-body">
              <h6>PL/SQL</h6>
              <h2>{{ summary.totalOrdersProcedure }}</h2>
            </div>
          </div>
        </div>

      </div>

      <div class="card shadow mt-4">
        <div class="card-body">
          <h4>Resumo Completo</h4>

          <table class="table mt-3">
            <tbody>
            <tr>
              <td>Pedidos Criados</td>
              <td>{{ summary.createdOrders }}</td>
            </tr>

            <tr>
              <td>Pedidos Processando</td>
              <td>{{ summary.processingOrders }}</td>
            </tr>

            <tr>
              <td>Pedidos Concluídos</td>
              <td>{{ summary.completedOrders }}</td>
            </tr>

            <tr>
              <td>Pedidos Cancelados</td>
              <td>{{ summary.canceledOrders }}</td>
            </tr>
            </tbody>
          </table>

        </div>
      </div>

    </main>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '../api/api'

const router = useRouter()

const summary = ref({})

async function loadSummary() {
  const response = await api.get('/reports/summary')
  summary.value = response.data
}

function logout() {
  localStorage.removeItem('token')
  router.push('/')
}

onMounted(() => {
  loadSummary()
})
</script>

<style scoped>
.app-layout {
  display: flex;
  min-height: 100vh;
  background: #f5f6fa;
}

.sidebar {
  width: 260px;
  min-height: 100vh;
  position: fixed;
  left: 0;
  top: 0;
}

.content {
  margin-left: 260px;
  padding: 32px;
  width: calc(100% - 260px);
}

.card {
  border-radius: 16px;
}
</style>