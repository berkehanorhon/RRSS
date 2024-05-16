<template>
  <div class="profile-page">
    <ProfileHeader :user="user" />
    <div class="blogs-reviews-bookmarks-container">
      <div class="left-column">
        <h2>Blog Posts</h2>
        <BlogPosts :posts="blogPosts" />
      </div>
      <div class="spacer"></div> <!-- Boşluk için bir div -->
      <div class="middle-column">
        <h2>Reviews</h2>
        <ReviewsTime :reviews="reviews" />
      </div>
      <div class="spacer"></div> <!-- Boşluk için bir div -->
      <div class="right-column">
        <h2>Bookmarked</h2>
        <BookmarkedProducts :products="bookmarkedProducts" />
      </div>
    </div>
  </div>
</template>

<script>
import ProfileHeader from '../components/ProfileHeader.vue';
import BlogPosts from '../components/BlogPosts.vue';
import ReviewsTime from '../components/ReviewsTime.vue';
import BookmarkedProducts from '../components/BookmarkedProducts.vue';

export default {
  name: 'ProfilePage',
  data() {
    return {
      user: {
        username: 'john_doe',
        photo: 'https://via.placeholder.com/150',
        about: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      },
      blogPosts: [
        { title: 'Post 1', content: 'Content of post 1' },
        { title: 'Post 2', content: 'Content of post 2' },
        // Add more posts as needed
      ],
      reviews: [
        { title: 'Review 1', content: 'Content of review 1' },
        { title: 'Review 2', content: 'Content of review 2' },
        // Add more reviews as needed
      ],
      bookmarkedProducts: [
        { name: 'Product 1', description: 'Description of product 1' },
        { name: 'Product 2', description: 'Description of product 2' },
        // Add more products as needed
      ],
    };
  },
  components: {
    ProfileHeader,
    BlogPosts,
    ReviewsTime,
    BookmarkedProducts,
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
  margin-top: 20px;
}

.left-column,
.middle-column,
.right-column {
  flex: 1;
}

.spacer {
  flex: 0 0 20px; /* Boşluğun genişliğini buradan ayarlayabilirsiniz */
}

</style>
