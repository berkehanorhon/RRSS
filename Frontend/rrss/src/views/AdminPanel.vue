<template>
  <div>
    <div class="section">
      <h2>User Management</h2>
      <UserManagement />
    </div>
    <div class="section">
      <h2>Category Management</h2>
      <CategoryManagement />
    </div>
    <div class="section">
      <h2>Product Management</h2>
      <ProductManagement />
    </div>
    <div class="section">
      <h2>Review Management</h2>
      <ReviewManagement />
    </div>
    <div class="section">
      <h2>Blog Management</h2>
      <BlogManagement />
    </div>
    <div class="section">
      <h2>Forum Management</h2>
      <ForumManagement />
    </div>
     <div class="section">
      <h2>Forum Post Management</h2>
      <ForumPostManagement />
    </div>
  </div>
</template>

<script>
import UserManagement from '@/components/UserManagement.vue';
import ProductManagement from '@/components/ProductManagement.vue';
import ReviewManagement from '@/components/ReviewManagement.vue';
import BlogManagement from '../components/BlogManagement.vue';
import ForumManagement from '../components/ForumManagement.vue';
import ForumPostManagement from '../components/ForumPostManagement.vue';
import CategoryManagement from '../components/CategoryManagement.vue';

export default {
  name: 'AdminPanel',
  components: {
    UserManagement,
    ProductManagement,
    ReviewManagement,
    BlogManagement,
    ForumManagement, 
    ForumPostManagement,
    CategoryManagement
  },
};
</script>

<style scoped>
.section {
  margin-bottom: 20px;
}
</style>
