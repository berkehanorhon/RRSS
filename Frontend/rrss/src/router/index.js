import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import FromComponentTest from '@/views/FromComponentTest.vue'
import MessageView from '@/views/MessageView.vue'
import ForumView from '@/views/ForumView.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/products/:productId',
    name: 'ProductDetail',
    component: () => import('@/components/ProductDetail.vue')
  },
  {
    path: '/test/',
    name: 'FormTest',
    component: FromComponentTest
  },
  {
    path: '/messenger/',
    name: 'Messenger',
    component: MessageView
  },
  {
    path: '/forum',
    name: 'Forum',
    component: ForumView
  }
]

const router = createRouter({
  history: createWebHistory(),
  //history: createWebHashHistory(),
  routes
})

export default router
