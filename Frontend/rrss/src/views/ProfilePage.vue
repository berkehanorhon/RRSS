<template>
  <div class="profile-page">
    <ProfileHeader :user="user" />
    <div class="blogs-reviews-bookmarks-container">
      <div class="column">
        <h2>Blog Posts</h2>
        <BlogPosts :userId="userId" />
      </div>
      <div v-if="is_merchant !== null" class="column">
        <h2 v-if="user.is_merchant">Products</h2>
        <h2 v-else>Reviews</h2>
        <ProductTimelineProfile v-if="user.is_merchant" :userId="userId" />
        <ReviewsTime v-else :userId="userId" />
      </div>
      <div class="column">
        <h2>Bookmarked</h2>
        <BookmarkedProducts :userId="userId" />
      </div>
    </div>
  </div>
</template>

<script>
import ProfileHeader from '../components/ProfileHeader.vue';
import BlogPosts from '../components/BlogPosts.vue';
import ReviewsTime from '../components/ReviewsTime.vue';
import BookmarkedProducts from '../components/BookmarkedProducts.vue';
import ProductTimelineProfile from '../components/ProductTimelineProfile.vue';
import axios from 'axios';

export default {
  name: 'ProfilePage',
  data() {
    return {
      user: {
        username: '',
        email: '',
        firstName: '',
        lastName: '',
        photo: require('@/assets/logo.png'),
        userId: this.$route.params.userId,
        is_merchant: false
      },
      userId: this.$route.params.userId,
      products: []
    };
  },
  components: {
    ProfileHeader,
    BlogPosts,
    ReviewsTime,
    BookmarkedProducts,
    ProductTimelineProfile
  },
  created() {
    this.fetchUserData();
  },
  methods: {
    fetchUserData() {
      axios.get(`http://127.0.0.1:8080/get-user?userId=${this.userId}`)
        .then(response => {
          this.user.username = response.data.username;
          this.user.email = response.data.email;
          this.user.firstName = response.data.firstName;
          this.user.lastName = response.data.lastName;
          this.user.photo = response.data.avatarImagePath || this.user.photo;
          this.user.is_merchant = response.data.isMerchant;
        })
        .catch(error => {
          console.error("There was an error fetching the user data:", error);
        });
    },
  },
};
</script>

<style>
.profile-page {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.profile-header {
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f2f2f2;
  height: 150px;
  width: 100%;
  border-radius: 10px;
  padding: 20px;
}

.blogs-reviews-bookmarks-container {
  display: flex;
  flex-direction: column;
  margin-top: 20px;
}

.column {
  width: 100%;
}

</style>