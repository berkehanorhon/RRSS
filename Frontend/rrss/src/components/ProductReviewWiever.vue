<template>
    <div class="reviews-container">
      <h2>Reviews</h2>
      <div v-for="review in reviews" :key="review.reviewId" class="review">
        <p>
          <router-link :to="`/profile/${review.userId}`">{{ review.username }}</router-link>
        </p>
        <p>Publish Date: {{ formatDate(review.publishDate) }}</p>
        <p>Review: {{ review.reviewData }}</p>
        <div class="star-rating">
          <StarRating v-for="n in 5" :key="n" :filled="n <= review.starRating" />
        </div>
        <button v-if="review.userId === userId" @click="deleteReview(review.reviewId)" class="delete-button">Delete</button>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import StarRating from './StarRating.vue';
  
  export default {
    name: 'ProductReviews',
    components: {
      StarRating
    },
    props: {
      productId: {
        type: Number,
        required: true,
      },
    },
    data() {
      return {
        reviews: [],
        userId: Number(localStorage.getItem('userId')),
      };
    },
    methods: {
        async deleteReview(reviewId) {
            try {
                const response = await axios.delete(`http://localhost:8080/delete-review?reviewId=${reviewId}`);
                if (response.status === 200) {
                alert('Review successfully deleted');
                location.reload();
                } else {
                throw new Error();
                }
            } catch (error) {
                alert('Failed to delete review');
            }
            },
      formatDate(dateString) {
        const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' };
        return new Date(dateString).toLocaleDateString(undefined, options);
      },
      async fetchReviews() {
        try {
          const response = await axios.get(`http://localhost:8080/get-products-reviews?productId=${this.productId}`);
          this.reviews = await Promise.all(response.data.map(async review => {
            const starRatingResponse = await axios.get(`http://localhost:8080/get-review-given-star?userId=${review.userId}&productId=${this.productId}`);
            const userResponse = await axios.get(`http://localhost:8080/get-user?userId=${review.userId}`);
            return { ...review, starRating: starRatingResponse.data, username: userResponse.data.username };
          }));
        } catch (error) {
          console.error(error);
        }
      },
    },
    mounted() {
      this.fetchReviews();
    },
  };
  </script>
  
<style scoped>
.delete-button {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 5px;
}
.reviews-container {
  margin-top: 20px;
  background-color: #f9f9f9;
  border-radius: 5px;
  padding: 15px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);
  overflow: auto;
  width: 30%;
  margin-left: auto;
  margin-right: auto;
}

h2 {
  color: #333;
  margin-bottom: 15px;
}

.review {
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.review p {
  margin: 0 0 10px;
  line-height: 1.5;
}

.review p:last-child {
  margin-bottom: 0;
}

.star-rating {
  display: flex;
  justify-content: flex-end;
}
</style>