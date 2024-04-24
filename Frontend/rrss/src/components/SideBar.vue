<template>
    <div class="sidebar">
      <h3>Kategoriler</h3>
      <ul>
        <li v-for="category in categories" :key="category.id">{{ category.name }}</li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    name: 'SideBar',
    data() {
      return {
        categories: [
          { id: 1, name: 'Elektronik' },
          { id: 2, name: 'Kitap' },
          { id: 3, name: 'Giyim' }
        ]
      };
    }
  };
  </script>
  
  <style scoped>
  .sidebar {
    width: 250px;
    background-color: #540e6f;
    padding: 20px;
  }
  </style>
  