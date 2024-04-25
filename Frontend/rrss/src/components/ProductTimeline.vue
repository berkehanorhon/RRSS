<template>
  <div class="product-timeline">
    <div v-for="product in products" :key="product.productId" class="product">
      <h4>{{ product.title }}</h4>
      <img :src="getImage(product)" alt="Product Image" />
      <p>{{ product.description }}</p>
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
      defaultImage: require('@/assets/logo.png')
    };
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
          // Eğer ürünün bir resmi yoksa, varsayılan resmi kullan
          image: require('@/assets/logo.png')
        }));
      })
      .catch(error => {
        console.error("There was an error fetching the products:", error);
      });
    },
    getImage(product) {
      return product.image || this.defaultImage;
    }
  }
};
</script>

<style scoped>
.product-timeline {
  margin-left: 270px; /* Sidebar width + some margin */
}
.product {
  background-color: #fff;
  padding: 20px;
  margin-bottom: 20px;
}
</style>