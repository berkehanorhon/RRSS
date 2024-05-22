<template>
  <div class="container">
    <div v-if="error">
      <p>{{ error }}</p>
    </div>
    <div v-else-if="product" class="product-detail">
      <h1>{{ product.title }}</h1>
      <img :src="getImage(product)" alt="Product Image" />
      <div class="rating">
            <span>({{ product.ratingCount }})</span>
            <star-rating v-for="n in 5" :key="n" :filled="n <= product.averageRating+0.5" />
          </div>
      <p>{{ product.description }}</p>
      <p>Category ID: {{ product.categoryId }}</p>
      <p>Product ID: {{ product.productId }}</p>
      <p>Publish Date: {{ product.publishDate }}</p>
      <p>User ID: {{ product.userId }}</p>
      <div v-if="!error">
        <ReviewForm :productId="product.productId" :userId="product.userId" @review-submitted="fetchProduct" />
      </div>
      <div v-if="product.userId == userId">
      </div>
      <div v-if="product.userId == userId">
        <button @click="editProduct" class="edit-button">Edit Product</button>
      </div>
    </div>
    <p v-if="counter && error">Redirecting in {{ counter }} seconds...</p>
  </div>
</template>

<script>
import axios from 'axios';
import ReviewForm from './ReviewForm.vue';
import StarRating from './StarRating.vue';

export default {
  name: 'ProductDetail',
  components: {
    ReviewForm,
    StarRating,
  },
  data() {
    return {
      product: null,
      error: null,
      reviewData: '',
      productId: this.$route.params.productId,
      userId: localStorage.getItem('userId'),
      defaultImage: require('@/assets/logo.png'),
    };
  },
  methods: {
    fetchProduct() {
      axios.get(`http://localhost:8080/get-product?productId=${this.productId}`)
        .then(response => {
          this.product = response.data;
        })
        .catch(error => {
          this.error = 'An error occurred while fetching the product.';
          console.error(error);
        });
    },
    getImage(product) {
      return product.image || this.defaultImage;
    },
    editProduct() {
      this.$router.push(`/products/${this.productId}/edit`);
    },
  },
  mounted() {
    this.fetchProduct();
  },
};
</script>
  
  <style scoped>
  .container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    border: 5px solid black;
    padding: 20px;
  }
  
  .product-detail {
    max-width: 800px;
    margin: auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 10px;
    background-color: #fff;
  }
  
  .product-detail img {
    max-width: 100%;
    height: auto;
  }
  
  .button-group {
    display: flex;
    justify-content: center; /* Changed from space-between to center */
    margin-top: 20px;
  }
  
  .nav-button, .submit-button {
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

  .edit-button {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    background-color: #007BFF;
    color: #fff;
    cursor: pointer;
    margin-top: 20px;
  }
  </style>