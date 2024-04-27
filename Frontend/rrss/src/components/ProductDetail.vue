<template>
    <div class="container">
      <div v-if="error">
        <p>{{ error }}</p>
      </div>
      <div v-else-if="product" class="product-detail">
        <h1>{{ product.title }}</h1>
        <img :src="getImage(product)" alt="Product Image" />
        <p>{{ product.description }}</p>
        <p>Category ID: {{ product.categoryId }}</p>
        <p>Product ID: {{ product.productId }}</p>
        <p>Publish Date: {{ product.publishDate }}</p>
        <p>User ID: {{ product.userId }}</p>
        <div v-if="!error">
          <h3>Add a Review</h3>
          <form @submit.prevent="submitReview">
            <textarea v-model="reviewData" placeholder="Write your review here..."></textarea>
            <div class="button-group">
              <button type="submit" class="submit-button">Submit Review</button>
              <button @click="previousPage" class="nav-button" :disabled="currentPage === 1">Previous</button>
              <button @click="nextPage" class="nav-button" :disabled="currentPage === totalPages">Next</button>
            </div>
          </form>
        </div>
      </div>
      <p v-if="counter && error">Redirecting in {{ counter }} seconds...</p>
    </div>
  </template>
  
  <script>
  // Your script remains the same
  </script>
  
  <style scoped>
  .container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #ffffcc; /* Light yellow */
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
    justify-content: space-between;
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
  </style>