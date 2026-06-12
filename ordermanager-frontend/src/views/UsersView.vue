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
      <h1 class="mb-4">Usuários</h1>

      <div v-if="errorMessage" class="alert alert-danger">
        {{ errorMessage }}
      </div>

      <div v-if="successMessage" class="alert alert-success">
        {{ successMessage }}
      </div>

      <div class="card shadow mb-4">
        <div class="card-body">
          <h5>Novo Usuário</h5>

          <div class="row g-3">
            <div class="col-md-4">
              <input v-model="name" class="form-control" placeholder="Nome">
            </div>

            <div class="col-md-4">
              <input v-model="email" class="form-control" placeholder="Email">
            </div>

            <div class="col-md-3">
              <input v-model="password" type="password" class="form-control" placeholder="Senha">
            </div>

            <div class="col-md-1">
              <button class="btn btn-primary w-100" @click="createUser">
                +
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
              <th>Nome</th>
              <th>Email</th>
              <th>Ações</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="user in users" :key="user.id">
              <td>{{ user.id }}</td>
              <td>{{ user.name }}</td>
              <td>{{ user.email }}</td>
              <td>
                <button
                    class="btn btn-danger btn-sm"
                    @click="deleteUser(user.id)"
                >
                  Excluir
                </button>
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

const users = ref([])

const name = ref('')
const email = ref('')
const password = ref('')

const errorMessage = ref('')
const successMessage = ref('')

async function loadUsers() {
  try {
    const response = await api.get('/users')
    users.value = response.data
  } catch (error) {
    errorMessage.value = 'Erro ao carregar usuários'
    console.log(error)
  }
}

async function createUser() {
  errorMessage.value = ''
  successMessage.value = ''

  if (!name.value || !email.value || !password.value) {
    errorMessage.value = 'Preencha nome, email e senha'
    return
  }

  try {
    await api.post('/users', {
      name: name.value,
      email: email.value,
      password: password.value
    })

    name.value = ''
    email.value = ''
    password.value = ''

    successMessage.value = 'Usuário criado com sucesso'

    await loadUsers()
  } catch (error) {
    errorMessage.value = 'Erro ao criar usuário'
    console.log(error)
  }
}

async function deleteUser(id) {
  errorMessage.value = ''
  successMessage.value = ''

  const confirmDelete = confirm(`Deseja excluir o usuário #${id}?`)

  if (!confirmDelete) {
    return
  }

  try {
    await api.delete(`/users/${id}`)

    successMessage.value = 'Usuário removido com sucesso'

    await loadUsers()
  } catch (error) {
    errorMessage.value = 'Erro ao remover usuário'
    console.log(error)
  }
}

function logout() {
  localStorage.removeItem('token')
  router.push('/')
}

onMounted(() => {
  loadUsers()
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