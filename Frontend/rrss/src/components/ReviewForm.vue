<template>
  <div>
    <h3>Add a Review</h3>
    <form @submit.prevent="submitReview">
      <div class="rating">
        <span>Your Rating:</span>
        <star-rating v-for="n in 5" :key="n" :filled="n <= givenStar" @click="givenStar = n" />
      </div>
      <textarea v-model="reviewData" placeholder="Write your review here..."></textarea>
      <div class="button-group">
        <button type="submit" class="submit-button">Submit Review</button>
      </div>
    </form>
    <div v-if="popupVisible" :class="{'popup-success': popupSuccess, 'popup-error': !popupSuccess}">
      <p>{{ popupMessage }}</p>
      <button class="submit-button" @click="popupVisible = false">OK</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import StarRating from './StarRating.vue';

export default {
  name: 'ReviewForm',
  components: {
    StarRating
  },
  props: {
    productId: {
      type: Number,
      required: true,
    },
    userId: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      reviewData: '',
      givenStar: 5,
      popupVisible: false,
      popupMessage: '',
      popupSuccess: false,
    };
  },
  methods: {
    submitReview() {
    axios.post(`http://localhost:8080/add-review?star=${this.givenStar}`, {
      productId: this.productId,
      userId: this.userId,
      reviewData: this.reviewData,
    })
    .then(() => {
      this.$emit('review-submitted');
      this.popupMessage = 'Review submitted successfully!';
      this.popupSuccess = true;
      this.popupVisible = true;
    })
    .catch(error => {
      if (error.response && error.response.status === 409) {
        this.popupMessage = 'You have already reviewed this product!';
      } else {
        this.popupMessage = 'Failed to submit review!';
      }
      this.popupSuccess = false;
      this.popupVisible = true;
    });
  },
  },
};
</script>

<style scoped>
.rating {
  display: flex;
  justify-content: center;
  cursor: pointer;
}

textarea {
  width: 100%;
  height: 100px;
  max-width: 500px;
  max-height: 200px;
  resize: none;
}

.button-group {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.submit-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007BFF;
  color: #fff;
  cursor: pointer;
}

.popup-success {
  background-color: #DFF0D8; /* Light green */
  color: #3C763D; /* Dark green */
}

.popup-error {
  background-color: #F2DEDE; /* Light red */
  color: #A94442; /* Dark red */
}
</style>