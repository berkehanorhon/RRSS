<template>
  <div class="product-timeline">
    <div v-for="product in products" :key="product.id" class="product">
      <h4>{{ product.name }}</h4>
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
      defaultImage: '@/assets/default-image.jpg' // Yedek resim yolu
    };
  },
  mounted() {
    this.fetchProducts();
  },
  methods: {
    fetchProducts() {
      axios.get('https://your-api-url/products')
        .then(response => {
          this.products = response.data.map(product => ({
            ...product,
            image: product.image ? require(`@/assets/${product.image}`) : this.defaultImage
          }));
        })
        .catch(error => {
          console.error("There was an error fetching the products:", error);
        });
    },
    getImage(product) {
      return product.image || require(this.defaultImage);
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
