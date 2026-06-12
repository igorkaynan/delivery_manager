<template>
  <div class="app-layout">

    <aside class="sidebar bg-dark text-white">
      <h3 class="mt-4 mb-4 text-center">
        DeliveryManager
      </h3>

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
      <h1 class="mb-4">
        Dashboard
      </h1>

      <div v-if="errorMessage" class="alert alert-danger">
        {{ errorMessage }}
      </div>

      <div class="row mb-4">

        <div class="col-md-3 mb-3">
          <div class="card shadow border-0 dashboard-card">
            <div class="card-body">
              <h6>Total Usuários</h6>
              <h2>{{ totalUsers }}</h2>
            </div>
          </div>
        </div>

        <div class="col-md-3 mb-3">
          <div class="card shadow border-0 dashboard-card">
            <div class="card-body">
              <h6>Total Pedidos</h6>
              <h2>{{ totalOrders }}</h2>
            </div>
          </div>
        </div>

        <div class="col-md-3 mb-3">
          <div class="card shadow border-0 dashboard-card">
            <div class="card-body">
              <h6>Total via PL/SQL</h6>
              <h2>{{ totalOrdersProcedure }}</h2>
            </div>
          </div>
        </div>

        <div class="col-md-3 mb-3">
          <div class="card shadow border-0 dashboard-card">
            <div class="card-body">
              <h6>Processando</h6>
              <h2>{{ processingOrders }}</h2>
            </div>
          </div>
        </div>

      </div>

      <div class="card shadow border-0 chart-card">
        <div class="card-body">
          <h4 class="mb-4">
            Pedidos por Status
          </h4>

          <div class="chart-wrapper">
            <Doughnut
                v-if="hasChartData"
                :data="chartData"
                :options="chartOptions"
            />

            <div v-else class="empty-chart">
              Nenhum pedido disponível para exibir no gráfico.
            </div>
          </div>
        </div>
      </div>

    </main>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Doughnut } from 'vue-chartjs'
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend
} from 'chart.js'
import api from '../api/api'

ChartJS.register(ArcElement, Tooltip, Legend)

const router = useRouter()

const totalUsers = ref(0)
const totalOrders = ref(0)
const totalOrdersProcedure = ref(0)

const createdOrders = ref(0)
const processingOrders = ref(0)
const completedOrders = ref(0)
const canceledOrders = ref(0)

const chartData = ref(null)
const hasChartData = ref(false)
const errorMessage = ref('')

const chartOptions = {
  responsive: true,
  maintainAspectRatio: false
}

async function loadDashboard() {

  try {
    const usersResponse = await api.get('/users')
    console.log('USERS OK', usersResponse.data)

    const ordersResponse = await api.get('/orders')
    console.log('ORDERS OK', ordersResponse.data)

    const reportResponse = await api.get('/reports/total-orders')
    console.log('REPORT OK', reportResponse.data)

    const orders = ordersResponse.data

    totalUsers.value = usersResponse.data.length
    totalOrders.value = orders.length
    totalOrdersProcedure.value = reportResponse.data.totalOrders

    createdOrders.value = orders.filter(order => order.status === 'CREATED').length
    processingOrders.value = orders.filter(order => order.status === 'PROCESSING').length
    completedOrders.value = orders.filter(order => order.status === 'COMPLETED').length
    canceledOrders.value = orders.filter(order => order.status === 'CANCELED').length

    hasChartData.value =
        createdOrders.value > 0 ||
        processingOrders.value > 0 ||
        completedOrders.value > 0 ||
        canceledOrders.value > 0

    chartData.value = {
      labels: ['Criados', 'Processando', 'Concluídos', 'Cancelados'],
      datasets: [
        {
          data: [
            createdOrders.value,
            processingOrders.value,
            completedOrders.value,
            canceledOrders.value
          ]
        }
      ]
    }

  } catch (error) {

    console.log('ERRO DASHBOARD')
    console.log(error)

    if (error.response) {
      console.log(error.response.status)
      console.log(error.response.data)
    }

    errorMessage.value = 'Erro ao carregar dashboard.'
  }
}

function logout() {
  localStorage.removeItem('token')
  router.push('/')
}

onMounted(() => {
  loadDashboard()
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
  min-height: 100vh;
}

.dashboard-card {
  border-radius: 16px;
  min-height: 120px;
}

.chart-card {
  border-radius: 16px;
}

.chart-wrapper {
  height: 320px;
  max-height: 320px;
  position: relative;
}

.empty-chart {
  height: 320px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #6c757d;
  font-size: 18px;
  border: 1px dashed #ced4da;
  border-radius: 16px;
  background: #fafafa;
}
</style>