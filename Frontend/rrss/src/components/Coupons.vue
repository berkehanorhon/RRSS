<template>
  <div class="coupon-timeline">
    <div class="coupon-container">
      <div v-if="coupons.length === 0" class="no-coupons">
        No coupon list has been created yet!
      </div>
      <div class="coupon-row" v-for="(row, index) in chunkedData" :key="index">
        <div v-for="coupon in row" :key="coupon.id" class="coupon">
            <h4>{{ coupon.couponName }}</h4>
            <img :src="getCouponImage(coupon)" alt="Coupon Image" class="coupon-image" />
          <p>Coupon Description: {{ coupon.couponText }}</p>
          <p>Coupon Code: <strong>{{ coupon.couponData }}</strong></p>
          <p>Creation Date:</p>
          <p>{{ formatDate(coupon.creationDate) }}</p>
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
  name: 'CouponTimeline',
  data() {
    return {
      coupons: [],
      defaultImage: require('@/assets/logo.png'),
      currentPage: 1,
      itemsPerPage: 18,
      windowWidth: 0
    };
  },
  created() {
    this.fetchCoupons();
  },
  computed: {
    chunkSize() {
      if (this.windowWidth > 1600) {
        return 6;
      } else if (this.windowWidth > 800) {
        return 4;
      } else if (this.windowWidth > 600) {
        return 2;
      } else {
        return 1;
      }
    },
    paginatedData() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.coupons.slice(start, end);
    },
    chunkedData() {
      return Array(Math.ceil(this.paginatedData.length / this.chunkSize)).fill().map((_, index) => index * this.chunkSize).map(begin => this.paginatedData.slice(begin, begin + this.chunkSize));
    },
    totalPages() {
      return Math.ceil(this.coupons.length / this.itemsPerPage);
    }
  },
  mounted() {
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
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' };
      return new Date(dateString).toLocaleDateString(undefined, options);
    },
    async fetchCoupons() {
      try {
        const response = await axios.get('http://localhost:8080/user-get-coupon');
        this.coupons = response.data;
      } catch (error) {
        console.error(error);
      }
    },
    getCouponImage(coupon) {
      return coupon.couponImage || this.defaultImage;
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
  }
};
</script>

<style scoped>
.coupon-timeline {
  display: flex;
  justify-content: center;
  align-items: center;
  height: auto;
  background-color: #136f79;
  padding: 20px;
  margin: 100px auto;
  max-width: 90%;
}

.coupon-container {
  width: 100%; /* Change this line */
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #136f79;
  height: auto;
  overflow: auto;
  text-align: center; /* Center align the content */
}


.coupon-row {
  display: flex;
  justify-content: space-evenly;
  margin-bottom: 20px;
}

.coupon {
  flex: 0 0 180px; /* Adjusted width */
  box-sizing: border-box;
  background-color: #fff;
  padding: 20px;
  margin: 0px;
  border: 1px solid #ccc;
  border-radius: 10px;
  margin-right: 1px;
}

.coupon-image {
  width: 100%; /* Make the image fill the container */
  max-height: 150px; /* Reduce the image height */
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
