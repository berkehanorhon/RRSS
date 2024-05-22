import FromComponentTest from '@/views/FromComponentTest.vue'
import { createRouter, createWebHistory } from 'vue-router'
import BlogPost from '../components/BlogPost.vue'
import BlogPosts from '../components/BlogPosts.vue'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import MerchantPanel from '../views/MerchantPanel.vue'
import ProfilePage from '../views/ProfilePage.vue'
import RegisterView from '../views/RegisterView.vue'
import EditProfileView from '../views/EditProfileView.vue'
import EditProductView from '../views/EditProductView.vue'

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
    path: '/products/:productId/edit',
    name: 'EditProductView',
    component: EditProductView
  },
  {
    path: '/test/',
    name: 'FormTest',
    component: FromComponentTest
  },
  {
    path: '/profile/:userId',
    name: 'ProfilePage',
    component: ProfilePage
  },
  {
    path: '/profile/:userId/edit',
    name: 'EditProfileView',
    component: EditProfileView
  },
  {
    path: '/merchantpanel',
    name: 'MerchantPanel',
    component: MerchantPanel
  },
  {
    path: '/blogs',
    name: 'BlogPosts',
    component: BlogPosts
  },
  {
    path: '/blogs/:blogId',
    name: 'BlogPost',
    component: BlogPost,
    props: true
  },
]

const router = createRouter({
  history: createWebHistory(),
  //history: createWebHashHistory(),
  routes
})

export default router
