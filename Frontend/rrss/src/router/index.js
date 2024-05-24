import EditProfileView from '../views/EditProfileView.vue';
import AdminPanel from '../views/AdminPanel.vue'
import BlogTimeline from '../components/BlogTimeline.vue'
import AddForum from '../views/AddForum.vue'
import CreateBookmarkList from '../views/CreateBookmarkList.vue';
import ForumCategoryView from '@/views/ForumCategoryView.vue';
import ProfilePage from '../views/ProfilePage.vue';
import { createRouter, createWebHistory } from 'vue-router';
import MerchantPanel from '../views/MerchantPanel.vue';
import EditProductView from '../views/EditProductView.vue';
import MessageView from '@/views/MessageView.vue';
import LoginView from '../views/LoginView.vue'
import ConversationsListView from '../views/ConversationsListView.vue';
import RegisterView from '../views/RegisterView.vue'
import HomeView from '../views/HomeView.vue'
import ProductTimelineBookmarks from '../components/ProductTimelineBookmarks.vue';
import ForumView from '@/views/ForumView.vue';
import ForumEntryView from '@/views/ForumEntryView.vue';
import ForumCategoryEntriesView from '@/views/ForumCategoryEntriesView.vue';
import Coupons from '../components/Coupons.vue'
import BlogPost from '@/components/BlogPost.vue';
import AddCategory from '@/views/AddCategory.vue';
import AddProduct from '@/views/AddProduct.vue';
import AddBlogPost from '@/views/AddBlogPost.vue';
// import BlogPosts from '@/components/BlogPosts.vue';

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
    path: '/admin-panel',
    name: 'AdminPanel',
    component: AdminPanel
  },
  {
    path: '/add-forum',
    name: 'AddForum',
    component: AddForum
  },
  {
    path: '/add-product',
    name: 'AddProduct',
    component: AddProduct
  },
  {
    path: '/add-blogpost',
    name: 'AddBlogPost',
    component: AddBlogPost
  },
  {
    path: '/add-category',
    name: 'AddCategory',
    component: AddCategory
  },
  {
    path: '/products/:productId',
    name: 'ProductDetail',
    component: () => import('@/components/ProductDetail.vue')
  },
  {
    path: '/get-recommendation',
    name: 'GetReceommendation',
    component: () => import('@/views/GetRecommendationView.vue')
  },
  {
    path: '/products/:productId/edit',
    name: 'EditProductView',
    component: EditProductView
  },
  {
    path: '/messenger',
    name: 'ConversationsList',
    component: ConversationsListView
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
  },
  {
    path: '/profile/:userId',
    name: 'ProfilePage',
    component: ProfilePage
  },{
    path: '/coupons',
    name: 'coupons',
    component: Coupons
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
    path: '/bookmarklists/:bookmarkListId',
    name: 'BookmarkList',
    component: ProductTimelineBookmarks
  },
  {
    path: '/createbookmarklist',
    name: 'CreateBookmarkList',
    component: CreateBookmarkList
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component: BlogTimeline
  },
  {
    path: '/blogs/:blogId',
    name: 'BlogPost',
    component: BlogPost,
    props: true
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
