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
      <button class="page-button" @click="previousPage" :disabled="currentPage === 1">Previous</button>
      <span class="page-text">{{ currentPage }} / {{ totalPages }}</span>
      <button class="page-button" @click="nextPage" :disabled="currentPage === totalPages">Next</button>
      

    </div>
  </div>
</template>

<script>
//import axios from 'axios';
import productsData from '../mocks/Products.json'; // Import the JSON file
export default {
  name: 'ProductTimeline',
  data() {
    return {
      products: [],
      defaultImage: require('@/assets/logo.png'),
      currentPage: 1,
      itemsPerPage: 18,
      windowWidth: 0
    };
  },
  computed: {
    chunkSize() {
      if (this.windowWidth > 1600) {
        return 6;
      } else if (this.windowWidth > 800) {
        return 4;
      }
        else if (this.windowWidth > 600) {
        return 2;
      } else {
        return 1;
      }
    },
    paginatedData() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.products.slice(start, end);
    },
    chunkedData() {
      return Array(Math.ceil(this.paginatedData.length / this.chunkSize)).fill().map((_, index) => index * this.chunkSize).map(begin => this.paginatedData.slice(begin, begin + this.chunkSize));
    },
    totalPages() {
      return Math.ceil(this.products.length / this.itemsPerPage);
    }
  },
  mounted() {
    this.fetchProducts();
    this.windowWidth = window.innerWidth;
    window.addEventListener('resize', () => {
      this.windowWidth = window.innerWidth;
    });
  },
  beforeUnmount() {
    window.removeEventListener('resize', () => {
      this.windowWidth = window.innerWidth;
    });
  },
  methods: {
    // fetchProducts() {
    //   axios.get('http://127.0.0.1:8080/get-all-products?categoryId=-1')
    //   .then(response => {
    //     this.products = response.data.map(product => ({
    //       ...product,
    //       image: require('@/assets/logo.png')
    //     }));
    //   })
    //   .catch(error => {
    //     console.error("There was an error fetching the products:", error);
    //   });
    // },
      fetchProducts() {
        // Replace axios call with local JSON data
        this.products = productsData.map(product => ({
          ...product,
          image: require('@/assets/logo.png')
        }));
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
  height: auto; /* Reduced from 100vh */
  background-color: #ffffcc; /* Light yellow */
  padding: 20px;
}

.product-container {
  width: 92%;
  max-width: 1500px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #fff;
  height: auto; /* Changed from 1000px */
  overflow: auto;
}

.product-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.product {
  flex: 0 0 200px; /* Sabit genişlik */
  box-sizing: border-box;
  background-color: #fff;
  padding: 20px;
  margin: 10px; /* Added margin */
  border: 1px solid #ccc;
  border-radius: 10px;
}

.product-image {
  width: 173.5; /* Added this line */
  max-height: 173.5px; /* Added this line */
  object-fit: cover; /* Added this line */
}

.pagination {
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}

.page-text {
  margin-left: 10px;
  margin-right: 10px;
}

.page-button {
  padding: 10px 20px;
  margin-top: 10px; /* Add margin top to create space */
  border: none;
  border-radius: 5px;
  background-color: #007BFF;
  color: #fff;
  cursor: pointer;
}
/* Not necessary at the moment, could be necessary in the future */
/* @media (max-width: 1600px) {
  .product {
    flex: 0 0 200px;
  }
}

@media (max-width: 600px) {
  .product {
    flex: 0 0 200px;
  }
}

@media (max-width: 300px) {
  .product {
    flex: 0 0 200px;
  }
} */
</style>