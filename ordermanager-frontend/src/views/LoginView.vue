<template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-4">
        <div class="card shadow">
          <div class="card-body">

            <h3 class="text-center mb-4">DeliveryManager</h3>

            <div v-if="errorMessage" class="alert alert-danger">
              {{ errorMessage }}
            </div>

            <input
                v-model="email"
                class="form-control mb-3"
                placeholder="Email"
            >

            <input
                v-model="password"
                type="password"
                class="form-control mb-3"
                placeholder="Senha"
            >

            <button class="btn btn-primary w-100" @click="login">
              Entrar
            </button>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../api/api'

const router = useRouter()

const email = ref('teste@email.com')
const password = ref('123456')
const errorMessage = ref('')

async function login() {
  errorMessage.value = ''

  try {
    const response = await api.post('/auth/login', {
      email: email.value.trim(),
      password: password.value.trim()
    })

    localStorage.setItem('token', response.data.token)

    router.push('/dashboard')
  } catch (error) {
    console.log(error)
    errorMessage.value = 'Login inválido'
  }
}
</script>