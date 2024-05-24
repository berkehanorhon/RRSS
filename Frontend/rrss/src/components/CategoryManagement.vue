<template>
  <div class="category-management">
    <div class="category-list-box">
      <input v-model="searchQuery" placeholder="Search categories" class="search-input"/>
      <div class="category-list">
        <ul>
          <li v-for="category in filteredCategories" :key="category.categoryId" @click="selectCategory(category)" :class="{ selected: category === selectedCategory }">
            {{ category.categoryName }} (ID: {{ category.categoryId }}) - {{ category.description }}
          </li>
        </ul>
      </div>
    </div>
    <div class="actions">
      <button @click="deleteCategory" v-if="selectedCategory" class="action-button delete-button">Delete Category</button>
      <button @click="goToAddCategory" class="action-button add-button">Add Category</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'; // Make sure to install axios using npm install axios

export default {
  name: 'CategoryManagement',
  data() {
    return {
      searchQuery: '',
      categories: [],
      selectedCategory: null,
    };
  },
  async created() {
    try {
      const response = await axios.get('http://localhost:8080/get-all-categories');
      this.categories = response.data;
      this.selectedCategory = this.categories[0];
    } catch (error) {
      console.error('Failed to fetch categories:', error);
    }
  },
  computed: {
    filteredCategories() {
      return this.categories.filter(category => category.categoryName.toLowerCase().includes(this.searchQuery.toLowerCase()));
    },
  },
  methods: {
    selectCategory(category) {
      this.selectedCategory = category;
    },
    async deleteCategory() {
      if (this.selectedCategory) {
        try {
          await axios.delete(`http://localhost:8080/admin/delete-category?categoryId=${this.selectedCategory.categoryId}`);
          const index = this.categories.findIndex(category => category.categoryId === this.selectedCategory.categoryId);
          if (index !== -1) {
            this.categories.splice(index, 1);
            alert(`Category "${this.selectedCategory.categoryName}" has been deleted`);
            this.selectedCategory = null;
          }
        } catch (error) {
          console.error('Failed to delete category:', error);
        }
      }
    },
    goToAddCategory() {
      this.$router.push('/add-category');
    },
  },
};
</script>

<style scoped>
.category-management {
  text-align: center;
  margin: 20px;
}

.category-list-box {
  border: 1px solid #007BFF;
  border-radius: 10px;
  padding: 20px;
  margin: 20px auto;
  max-width: 500px;
  box-shadow: 0 4px 8px rgba(0, 123, 255, 0.2);
}

.search-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #007BFF;
  border-radius: 5px;
  margin-bottom: 20px;
}

.category-list {
  max-height: 300px;
  overflow-y: auto;
  border-top: 1px solid #007BFF;
  padding-top: 10px;
}

.category-list ul {
  list-style: none;
  padding: 0;
}

.category-list li {
  padding: 10px;
  border-bottom: 1px solid #007BFF;
  cursor: pointer;
  transition: background-color 0.3s;
}

.category-list li:hover {
  background-color: #f0f8ff;
}

.selected {
  font-weight: bold;
  background-color: #e0e0e0;
}

.actions {
  margin-top: 20px;
}

.action-button {
  background-color: #007BFF;
  color: white;
  border: none;
  padding: 10px 20px;
  margin: 5px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.action-button:hover {
  background-color: #0056b3;
}

.delete-button {
  background-color: #dc3545;
}

.delete-button:hover {
  background-color: #c82333;
}

.add-button {
  background-color: #28a745;
}

.add-button:hover {
  background-color: #218838;
}
</style>
