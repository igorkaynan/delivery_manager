import { createRouter, createWebHistory } from 'vue-router'

import LoginView from '../views/LoginView.vue'
import DashboardView from '../views/DashboardView.vue'
import UsersView from '../views/UsersView.vue'
import OrdersView from '../views/OrdersView.vue'
import AuditLogsView from '../views/AuditLogsView.vue'
import MonitoringView from '../views/MonitoringView.vue'
import ReportsView from '../views/ReportsView.vue'

const routes = [
    {
        path: '/',
        component: LoginView
    },
    {
        path: '/dashboard',
        component: DashboardView
    },
    {
        path: '/users',
        component: UsersView
    },
    {
        path: '/orders',
        component: OrdersView
    },
    {
        path: '/audit-logs',
        component: AuditLogsView
    },
    {
        path: '/monitoring',
        component: MonitoringView
    },
    {
        path: '/reports',
        component: ReportsView
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router