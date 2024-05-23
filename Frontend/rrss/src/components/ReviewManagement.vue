<template>
  <div class="review-management">
    <div class="review-list-box">
      <input v-model="searchQuery" placeholder="Search reviews" class="search-input"/>
      <div class="review-list">
        <ul>
          <li v-for="review in filteredReviews" :key="review.reviewId" @click="selectReview(review)" :class="{ selected: review === selectedReview }">
            {{ review.reviewData }} (ID: {{ review.reviewId }})
          </li>
        </ul>
      </div>
    </div>
    <div v-if="selectedReview" class="actions">
      <button @click="deleteReview" class="action-button ban-button">Delete Review</button>
    </div>
  </div>
</template>

<script>
import reviewsData from '../mocks/Reviews.json'; // Import the JSON file

export default {
  name: 'ReviewManagement',
  data() {
    const reviews = reviewsData;
    return {
      searchQuery: '',
      reviews: reviews,
      selectedReview: reviews[0],
    };
  },
  computed: {
    filteredReviews() {
      return this.reviews.filter(review => review.reviewData.toLowerCase().includes(this.searchQuery.toLowerCase()));
    },
  },
  methods: {
    selectReview(review) {
      this.selectedReview = review;
    },
    deleteReview() {
      if (this.selectedReview) {
        this.reviews = this.reviews.filter(review => review !== this.selectedReview);
        alert(`Review "${this.selectedReview.reviewData}" has been deleted`);
        this.selectedReview = null;
      }
    },
  },
};
</script>

<style scoped>
.review-management {
  text-align: center;
  margin: 20px;
}

.review-list-box {
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

.review-list {
  max-height: 300px;
  overflow-y: auto;
  border-top: 1px solid #007BFF;
  padding-top: 10px;
}

.review-list ul {
  list-style: none;
  padding: 0;
}

.review-list li {
  padding: 10px;
  border-bottom: 1px solid #007BFF;
  cursor: pointer;
  transition: background-color 0.3s;
}

.review-list li:hover {
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

.ban-button {
  background-color: #dc3545;
}

.ban-button:hover {
  background-color: #c82333;
}
</style>
