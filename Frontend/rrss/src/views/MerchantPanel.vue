<template>
  <div class="merchant-panel">
    <MerchantProducts :products="products" />
    <div class="selected-product-reviews">
      <h3>Selected Product Reviews</h3>
      <ul>
        <li v-for="review in selectedProduct.reviews" :key="review.id">
          <p>{{ review.text }}</p>
        </li>
      </ul>
    </div>
    <div class="product-actions">
      <button @click="addProduct">Add Product</button>
      <button @click="deleteProduct">Delete Product</button>
      <button @click="updateProduct">Update Product</button>
      <button @click="analyzeRatings">Analyze Ratings</button>
    </div>
  </div>
</template>

<script>
import MerchantProducts from '../components/MerchantProducts.vue';

export default {
  components: {
    MerchantProducts,
  },
  data() {
    return {
      products: [
        { id: 1, name: 'Product 1', description: 'Description 1', image: '../assets/default-image.jpeg', reviews: [] },
        { id: 2, name: 'Product 2', description: 'Description 2', image: 'product2.jpg', reviews: [] },
      ],
      selectedProduct: { id: 1, name: 'Product 1', description: 'Description 1', image: 'product1.jpg', reviews: [] },
    };
  },
  methods: {
    addProduct() {
      // Add product functionality
    },
    deleteProduct() {
      // Delete product functionality
    },
    updateProduct() {
      // Update product functionality
    },
    analyzeRatings() {
      // Analyze ratings functionality
    },
  },
};
</script>

<style>
.merchant-panel {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.product-actions {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.product-actions button {
  padding: 15px 30px;
  margin: 0 10px; /* Aralarında boşluk bırakmak için */
  border-radius: 8px;
  background-color: #2196f3;
  color: #fff;
  font-size: 16px; /* Daha büyük yazı boyutu */
  cursor: pointer;
}

.product-actions button:hover {
  background-color: #0e77d0; /* Hover efekti için */
}
</style>