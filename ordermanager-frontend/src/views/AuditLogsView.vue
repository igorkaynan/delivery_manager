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
      <h1 class="mb-4">Auditoria do Sistema</h1>

      <div class="card shadow">
        <div class="card-body table-responsive">
          <table class="table table-striped align-middle">
            <thead>
            <tr>
              <th>Ação</th>
              <th>Descrição</th>
              <th>Data</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="log in logs" :key="log.id">
              <td>
                <span class="badge bg-primary">
                  {{ log.action }}
                </span>
              </td>
              <td>{{ log.description }}</td>
              <td>{{ log.createdAt }}</td>
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
const logs = ref([])

async function loadLogs() {
  const response = await api.get('/audit-logs')
  logs.value = response.data
}

function logout() {
  localStorage.removeItem('token')
  router.push('/')
}

onMounted(() => {
  loadLogs()
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

.card {
  border-radius: 16px;
}
</style>