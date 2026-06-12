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
      <h1 class="mb-4">Monitoramento</h1>

      <div class="row">
        <div class="col-md-3 mb-4" v-for="service in services" :key="service.name">
          <div class="card shadow border-0">
            <div class="card-body">
              <h6>{{ service.name }}</h6>
              <h3>
                <span class="badge" :class="service.status === 'UP' ? 'bg-success' : 'bg-danger'">
                  {{ service.status }}
                </span>
              </h3>
            </div>
          </div>
        </div>
      </div>

      <div class="card shadow border-0 mt-3">
        <div class="card-body">
          <h4>Status detalhado</h4>
          <pre class="bg-dark text-success p-3 rounded mt-3">{{ healthJson }}</pre>
        </div>
      </div>
    </main>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()

const services = ref([])
const healthJson = ref('Carregando...')

async function loadHealth() {
  try {
    const response = await axios.get('http://localhost:8080/actuator/health')
    const data = response.data

    services.value = [
      { name: 'Spring Boot API', status: data.status },
      { name: 'Oracle Database', status: data.components.db.status },
      { name: 'MongoDB', status: data.components.mongo.status },
      { name: 'RabbitMQ', status: data.components.rabbit.status },
      { name: 'Disk Space', status: data.components.diskSpace.status },
      { name: 'Readiness', status: data.components.readinessState.status }
    ]

    healthJson.value = JSON.stringify(data, null, 2)
  } catch (error) {
    services.value = [
      { name: 'Spring Boot API', status: 'DOWN' }
    ]

    healthJson.value = 'API offline ou Actuator indisponível'
  }
}

function logout() {
  localStorage.removeItem('token')
  router.push('/')
}

onMounted(() => {
  loadHealth()
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

pre {
  max-height: 360px;
  overflow: auto;
}
</style>