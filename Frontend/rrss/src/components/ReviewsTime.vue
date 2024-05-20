<template>
  <div class="review-timeline">
    <div class="review-container">
      <div v-if="reviews.length === 0" class="no-reviews">
        No review has been posted yet!
      </div>
      <div class="review-row" v-for="(row, index) in chunkedData" :key="index">
        <div v-for="review in row" :key="review.reviewId" class="review">
          <router-link :to="`/products/${review.productId}`">
            <p>Click to Go Product!</p>
          </router-link>
          <h4>{{ truncatedReviewData(review.reviewData) }}</h4>
            <p>Publish Date: {{ formatDate(review.publishDate) }}</p>
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
import axios from 'axios';

export default {
  name: 'ReviewTimeline',
  props: ['userId'],
  data() {
    return {
      reviews: [],
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
      return this.reviews.slice(start, end);
    },
    chunkedData() {
      return Array(Math.ceil(this.paginatedData.length / this.chunkSize)).fill().map((_, index) => index * this.chunkSize).map(begin => this.paginatedData.slice(begin, begin + this.chunkSize));
    },
    totalPages() {
      return Math.ceil(this.reviews.length / this.itemsPerPage);
    }
  },
  mounted() {
    console.log(this.userId)
    this.fetchReviews();
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
    fetchReviews() {
      axios.get(`http://127.0.0.1:8080/get-users-reviews?userId=${this.userId}`)
      .then(response => {
        this.reviews = response.data;
      })
      .catch(error => {
        console.error("There was an error fetching the reviews:", error);
      });
    },
    getImage(review) {
      return review.image || this.defaultImage;
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
    },
    formatDate(date) {
      const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' };
      return new Date(date).toLocaleDateString('tr-TR', options).replace('.', '/').replace('.', '/');
    },
    truncatedReviewData(reviewData) {
      if (reviewData.length > 30) {
        return reviewData.substring(0, 30) + '...';
      } else {
        return reviewData;
      }
    }
  },
  watch: {
    selectedCategories() {
      this.fetchReviews();
    }
  }
};
</script>

<style scoped>
.review-timeline {
  display: flex;
  justify-content: center;
  align-items: center;
  height: auto;
  background-color: #ffffff;
  padding: 20px;
}

.review-container {
  width: 92%;
  max-width: 1500px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #fff;
  height: auto;
  overflow: auto;
}

.review-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.review {
  flex: 0 0 200px;
  box-sizing: border-box;
  background-color: #fff;
  padding: 20px;
  margin: 10px;
  border: 1px solid #ccc;
  border-radius: 10px;
}

.review-image {
  width: 173.5;
  max-height: 173.5px;
  object-fit: cover;
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
  margin-top: 10px;
  border: none;
  border-radius: 5px;
  background-color: #007BFF;
  color: #fff;
  cursor: pointer;
}
</style>