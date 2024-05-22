<template>
  <div class="profile-page">
    <ProfileHeader :user="user" />
    <div class="blogs-reviews-bookmarks-container">
      <div class="column">
        <h2>Blog Posts</h2>
        <BlogPosts :userId="userId" />
      </div>
      <div class="column">
        <h2>Reviews</h2>
        <ReviewsTime :userId="userId" />
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
        userId: this.$route.params.userId
      },
      userId: this.$route.params.userId
    };
  },
  components: {
    ProfileHeader,
    BlogPosts,
    ReviewsTime,
    BookmarkedProducts,
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