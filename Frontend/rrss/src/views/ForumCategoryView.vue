<template>
    <div class="forum-category-view">
      <h1>Forum Categories</h1>
      <ul>
        <li v-for="category in categories" :key="category.id">
          <router-link :to="`/forum/categories/${category.id}`">{{ category.name }}</router-link>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    name: 'ForumCategoryView',
    data() {
      return {
        categories: [
          { id: 1, name: 'Category 1' },
          { id: 2, name: 'Category 2' },
        ]
      };
    }
  };
  </script>
  
  <style scoped>
  
  </style>
  