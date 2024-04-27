<template>
  <div class="sidebar">
    <h3>Kategoriler</h3>
    <ul class="category-list">
      <li v-for="category in categories" :key="category.categoryId">
        <input type="checkbox" :id="category.categoryId" :value="category.categoryId" v-model="checkedCategories">
        <label :for="category.categoryId" :title="category.description">{{ category.categoryName }}</label>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'SideBar',
  data() {
    return {
      categories: [],
      checkedCategories: []
    };
  },
  created() {
    axios.get('http://localhost:8080/get-all-categories')
      .then(response => {
        this.categories = response.data;
      });
  }
};
</script>

<style scoped>
.sidebar {
  width: 250px;
  background-color: #540e6f;
  padding: 20px;
}

.category-list {
  list-style-type: none;
  padding-left: 0;
}
</style>