<template>
  <div class="product-management">
    <div class="product-list-box">
      <input v-model="searchQuery" placeholder="Search products" class="search-input"/>
      <div class="product-list">
        <ul>
          <li v-for="product in filteredProducts" :key="product.productId" @click="selectProduct(product)" :class="{ selected: product.productId === selectedProduct?.productId }">
            {{ product.title }} (ID: {{ product.productId }})
          </li>
        </ul>
      </div>
    </div>
    <div v-if="selectedProduct" class="actions">
      <button @click="deleteProduct" class="action-button delete-button">Delete Product</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'; // Make sure to install axios using npm install axios

export default {
  name: 'ProductManagement',
  data() {
    return {
      searchQuery: '',
      products: [],
      selectedProduct: null,
    };
  },
  async created() {
    try {
      const response = await axios.get('http://localhost:8080/admin/get-all-products');
      this.products = response.data;
      this.selectedProduct = this.products[0];
    } catch (error) {
      console.error('Failed to fetch products:', error);
    }
  },
  computed: {
    filteredProducts() {
      return this.products.filter(product => product.title.toLowerCase().includes(this.searchQuery.toLowerCase()));
    },
  },
  methods: {
    selectProduct(product) {
      this.selectedProduct = product;
    },
    async deleteProduct() {
      if (this.selectedProduct) {
        try {
          await axios.delete(`http://localhost:8080/admin/delete-product?productId=${this.selectedProduct.productId}`);
          const index = this.products.findIndex(product => product.productId === this.selectedProduct.productId);
          if (index !== -1) {
            this.products.splice(index, 1);
            alert(`Product "${this.selectedProduct.title}" has been deleted`);
            this.selectedProduct = null;
          }
        } catch (error) {
          console.error('Failed to delete product:', error);
        }
      }
    },
  },
};
</script>

<style scoped>
.product-management {
  text-align: center;
  margin: 20px;
}

.product-list-box {
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

.product-list {
  max-height: 300px;
  overflow-y: auto;
  border-top: 1px solid #007BFF;
  padding-top: 10px;
}

.product-list ul {
  list-style: none;
  padding: 0;
}

.product-list li {
  padding: 10px;
  border-bottom: 1px solid #007BFF;
  cursor: pointer;
  transition: background-color 0.3s;
}

.product-list li:hover {
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
</style>
