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
      <h1 class="mb-4">Pedidos</h1>

      <div v-if="errorMessage" class="alert alert-danger">
        {{ errorMessage }}
      </div>

      <div v-if="successMessage" class="alert alert-success">
        {{ successMessage }}
      </div>

      <div class="card shadow mb-4">
        <div class="card-body">
          <h5>Novo Pedido</h5>

          <div class="row g-3">
            <div class="col-md-6">
              <input
                  v-model="description"
                  class="form-control"
                  placeholder="Descrição do pedido"
              >
            </div>

            <div class="col-md-4">
              <input
                  v-model="totalValue"
                  type="number"
                  step="0.01"
                  class="form-control"
                  placeholder="Valor total"
              >
            </div>

            <div class="col-md-2">
              <button class="btn btn-primary w-100" @click="createOrder">
                Criar
              </button>
            </div>
          </div>
        </div>
      </div>

      <div class="card shadow">
        <div class="card-body table-responsive">
          <table class="table table-striped align-middle">
            <thead>
            <tr>
              <th>ID</th>
              <th>Descrição</th>
              <th>Valor</th>
              <th>Status</th>
              <th>Criado em</th>
              <th>Ações</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="order in orders" :key="order.id">
              <td>{{ order.id }}</td>
              <td>{{ order.description }}</td>
              <td>R$ {{ order.totalValue }}</td>
              <td>
                <span class="badge bg-secondary">
                  {{ order.status }}
                </span>
              </td>
              <td>{{ order.createdAt }}</td>
              <td>
                <div class="d-flex gap-2">
                  <select
                      class="form-select form-select-sm status-select"
                      v-model="order.status"
                      @change="updateStatus(order)"
                  >
                    <option value="CREATED">CREATED</option>
                    <option value="PROCESSING">PROCESSING</option>
                    <option value="COMPLETED">COMPLETED</option>
                    <option value="CANCELED">CANCELED</option>
                  </select>

                  <button
                      class="btn btn-danger btn-sm"
                      @click="deleteOrder(order.id)"
                  >
                    Excluir
                  </button>
                </div>
              </td>
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

const orders = ref([])
const description = ref('')
const totalValue = ref('')

const errorMessage = ref('')
const successMessage = ref('')

async function loadOrders() {
  try {
    const response = await api.get('/orders')
    orders.value = response.data
  } catch (error) {
    errorMessage.value = 'Erro ao carregar pedidos'
    console.log(error)
  }
}

async function createOrder() {
  errorMessage.value = ''
  successMessage.value = ''

  if (!description.value || !totalValue.value) {
    errorMessage.value = 'Preencha descrição e valor'
    return
  }

  try {
    await api.post('/orders', {
      description: description.value,
      totalValue: Number(totalValue.value)
    })

    description.value = ''
    totalValue.value = ''

    successMessage.value = 'Pedido criado com sucesso'

    await loadOrders()
  } catch (error) {
    errorMessage.value = 'Erro ao criar pedido'
    console.log(error)
  }
}

async function updateStatus(order) {
  errorMessage.value = ''
  successMessage.value = ''

  try {
    await api.patch(`/orders/${order.id}/status`, {
      status: order.status
    })

    successMessage.value = 'Status atualizado com sucesso'

    await loadOrders()
  } catch (error) {
    errorMessage.value = 'Erro ao atualizar status'
    console.log(error)
  }
}

async function deleteOrder(id) {
  errorMessage.value = ''
  successMessage.value = ''

  const confirmDelete = confirm(`Deseja excluir o pedido #${id}?`)

  if (!confirmDelete) {
    return
  }

  try {
    await api.delete(`/orders/${id}`)

    successMessage.value = 'Pedido removido com sucesso'

    await loadOrders()
  } catch (error) {
    errorMessage.value = 'Erro ao remover pedido'
    console.log(error)
  }
}

function logout() {
  localStorage.removeItem('token')
  router.push('/')
}

onMounted(() => {
  loadOrders()
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

.status-select {
  min-width: 150px;
}
</style>