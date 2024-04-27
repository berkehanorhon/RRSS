<template>
  <div class="product-timeline">
    <div class="product-container">
      <div class="product-row" v-for="(row, index) in chunkedData" :key="index">
        <div v-for="product in row" :key="product.productId" class="product">
          <router-link :to="`/products/${product.productId}`">
            <h4>{{ product.title }}</h4>
            <img :src="getImage(product)" alt="Product Image" class="product-image" />
          </router-link>
          <p>{{ product.description }}</p>
        </div>
      </div>
    </div>
    <div class="pagination">
      <button @click="previousPage" class="nav-button" :disabled="currentPage === 1">Previous</button>
      <span>Page {{ currentPage }} of {{ totalPages }}</span>
      <button @click="nextPage" class="nav-button" :disabled="currentPage === totalPages">Next</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'ProductTimeline',
  data() {
    return {
      products: [],
      defaultImage: require('@/assets/logo.png'),
      currentPage: 1,
      itemsPerPage: 9
    };
  },
  computed: {
    paginatedData() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.products.slice(start, end);
    },
    chunkedData() {
      const chunkSize = 3;
      return Array(Math.ceil(this.paginatedData.length / chunkSize)).fill().map((_, index) => index * chunkSize).map(begin => this.paginatedData.slice(begin, begin + chunkSize));
    },
    totalPages() {
      return Math.ceil(this.products.length / this.itemsPerPage);
    }
  },
  mounted() {
    this.fetchProducts();
  },
  methods: {
    fetchProducts() {
      axios.get('http://127.0.0.1:8080/get-all-products')
      .then(response => {
        this.products = response.data.map(product => ({
          ...product,
          image: require('@/assets/logo.png')
        }));
      })
      .catch(error => {
        console.error("There was an error fetching the products:", error);
      });
    },
    getImage(product) {
      return product.image || this.defaultImage;
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    previousPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    }
  },
  watch: {
    selectedCategories() {
      this.fetchProducts();
    }
  }
};
</script>

<style scoped>
.product-timeline {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh; /* Reduced from 100vh */
  background-color: #ffffcc; /* Light yellow */
  border: 5px solid black;
  padding: 20px;
}

.product-container {
  max-width: 600px; /* Reduced from 800px */
  margin: auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #fff;
}

.product-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.product {
  flex-basis: calc(30% - 20px); /* Reduced from 33% */
  box-sizing: border-box; /* Added this line */
  background-color: #fff;
  padding: 20px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
}

.product-image {
  width: 100%; /* Added this line */
  height: 80px; /* Added this line */
  object-fit: cover; /* Added this line */
}

.pagination {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}

.nav-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007BFF;
  color: #fff;
  cursor: pointer;
}

.nav-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>