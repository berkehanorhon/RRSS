import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import RegisterView from '../views/RegisterView.vue';
import FromComponentTest from '@/views/FromComponentTest.vue';
import MessageView from '@/views/MessageView.vue';
import ForumView from '@/views/ForumView.vue';
import ForumCategoryEntriesView from '@/views/ForumCategoryEntriesView.vue';
import ForumCategoryView from '@/views/ForumCategoryView.vue';
import ForumEntryView from '@/views/ForumEntryView.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    component: () => import('../views/AboutView.vue')
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
    path: '/messenger/:userId',
    name: 'Messenger',
    component: MessageView,
    props: true
  },
  {
    path: '/forum',
    name: 'Forum',
    component: ForumView
  },
  {
    path: '/forum/categories',
    name: 'ForumCategories',
    component: ForumCategoryView
  },
  {
    path: '/forum/categories/:categoryId',
    name: 'ForumCategoryEntries',
    component: ForumCategoryEntriesView,
    props: true
  },
  {
    path: '/forum/categories/:categoryId/:entryId',
    name: 'ForumEntry',
    component: ForumEntryView,
    props: true
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
