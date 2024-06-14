<template>
  <div class="container">
    <div class="bookmark-container">
      <select v-model="selectedBookmarkListId" class="select-dropdown">
        <option v-for="bookmark in bookmarkList" :key="bookmark.bookmarkListId" :value="bookmark.bookmarkListId">
          {{ bookmark.title }}
        </option>
      </select>
      <button @click="addToBookmarkList" class="submit-button">Add to The BookmarkList</button>
      <p :class="bookmarkMessageClass">{{ bookmarkMessage }}</p>
    </div>
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
      <p>Product Description: {{ product.description }}</p>
      <p>Category: {{ categoryName }}</p>
      <p>Publish Date: {{ formatDate(product.publishDate) }}</p>
      <p>User: <router-link :to="`/profile/${product.userId}`"><strong>{{ username }}</strong></router-link></p>
      <div v-if="!error">
        <ReviewForm :productId="product.productId" :userId="product.userId" @review-submitted="fetchProduct" />
      </div>
      <div v-if="product.userId == userId">
        <button @click="editProduct" class="edit-button">Edit Product</button>
        <button @click="deleteProduct" class="delete-button">Delete Product</button>
      </div>
    </div>
    <p v-if="counter && error">Redirecting in {{ counter }} seconds...</p>
    <ProductReviews :productId="productId" />
  </div>
</template>

<script>
import axios from 'axios';
import ReviewForm from './ReviewForm.vue';
import StarRating from './StarRating.vue';
import ProductReviews from './ProductReviewWiever.vue';
export default {
  name: 'ProductDetail',
  components: {
    ReviewForm,
    StarRating,
    ProductReviews
  },
  data() {
    return {
      product: null,
      error: null,
      reviewData: '',
      productId: this.$route.params.productId,
      userId: localStorage.getItem('userId'),
      defaultImage: require('@/assets/logo.png'),
      bookmarkList: [],
      selectedBookmarkListId: null,
      bookmarkMessage: '',
      categoryName: '',
      username: '',
    };
  },
  computed: {
    bookmarkMessageClass() {
      if (this.bookmarkMessage === 'Bookmarked successfully') {
        return 'text-green';
      } else if (this.bookmarkMessage === 'Product is already bookmarked') {
        return 'text-red';
      }
      return '';
    },
  },
  methods: {
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' };
      return new Date(dateString).toLocaleDateString(undefined, options);
    },
    fetchCategoryName(categoryId) {
      axios.get(`http://localhost:8080/get-category?categoryId=${categoryId}`)
        .then(response => {
          this.categoryName = response.data.categoryName;
        })
        .catch(error => {
          console.error(error);
        });
    },
    addToBookmarkList() {
    axios.post(`http://localhost:8080/bookmark/list/add?productId=${this.productId}&bookmarkListId=${this.selectedBookmarkListId}`, {
      bookmarkListId: this.selectedBookmarkListId,
      productId: this.productId,
    })
      .then(response => {
        if (response.status === 201) {
          this.bookmarkMessage = 'Bookmarked successfully';
        }
        console.log(response);
      })
      .catch(error => {
        if (error.response && error.response.status === 409) {
          this.bookmarkMessage = 'Product is already bookmarked';
        }
        console.error(error);
      });
  },
    fetchBookmarkList() {
      axios.get('http://localhost:8080/bookmark/get-users-lists?userId=-1')
        .then(response => {
          this.bookmarkList = response.data;
        })
        .catch(error => {
          console.error(error);
        });
    },
    fetchProduct() {
    axios.get(`http://localhost:8080/get-product?productId=${this.productId}`)
      .then(response => {
        this.product = response.data;
        this.fetchCategoryName(this.product.categoryId);
        this.fetchUsername(this.product.userId);
      })
      .catch(error => {
        this.error = 'An error occurred while fetching the product.';
        console.error(error);
      });
  },
  fetchUsername(userId) {
      axios.get(`http://localhost:8080/get-user?userId=${userId}`)
        .then(response => {
          this.username = response.data.username;
        })
        .catch(error => {
          console.error(error);
        });
    },
    
    getImage(product) {
      return product.image || this.defaultImage;
    },
    editProduct() {
      this.$router.push(`/products/${this.productId}/edit`);
    },
    deleteProduct() {
    axios.delete(`http://localhost:8080/delete-product?productId=${this.productId}`)
      .then(response => {
        if (response.status === 200) {
          this.$router.push('/');
        }
      })
      .catch(error => {
        console.error(error);
      });
  },
  },
  mounted() {
    this.fetchProduct();
    this.fetchBookmarkList();
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
  .submit-button {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    background-color: #007BFF;
    color: #fff;
    cursor: pointer;
  }
  .text-red {
    color: red;
  }
  .select-dropdown {
    padding: 10px;
    border: none;
    border-radius: 5px;
    background-color: #fff;
    color: #000;
    cursor: pointer;
  }
  .product-detail img {
    max-width: 100%;
    height: auto;
  }
  .bookmark-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.text-green {
  color: green;
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
  
  .delete-button {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    background-color: #FF0000; /* Red */
    color: #fff;
    cursor: pointer;
    margin-top: 20px;
    margin-left: 10px;
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