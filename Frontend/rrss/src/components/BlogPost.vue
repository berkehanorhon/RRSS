<template>
  <div class="container">
    <div v-if="blog" class="blog-post">
      <div class="blog-content">
        <h1>
          <router-link :to="`/profile/${blog.userId}`">{{ blog.postName }}</router-link>
        </h1>
        <div class="image-container">
          <img :src="blog.imagePath || defaultImage" alt="Blog Image" class="blog-image" />
        </div>
        <p>{{ blog.postData }}</p>
        <div class="like-section">
          <button @click="toggleLike">
            <span v-if="liked">👎</span>
            <span v-else>👍</span>
            {{ liked ? 'Unlike' : 'Like' }}
          </button>
          <span>{{ likeCount }} likes</span>
        </div>
        <button v-if="blog.userId === localUserId" @click="deleteBlog" class="delete-button">Delete Blog Post</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'BlogPost',
  data() {
    return {
      blog: null,
      defaultImage: require('@/assets/logo.png'),
      likeCount: 0, // Initial like count
      liked: false,  // User's like status
      localUserId: Number(localStorage.getItem('userId'))
    };
  },
  async created() {
    await this.fetchBlog();
  },
  methods: {
    async fetchBlog() {
      try {
        const response = await axios.get(`http://localhost:8080/get-blog-post?blogPostId=${this.$route.params.blogId}`);
        if (response.status === 200) {
          this.blog = {
            id: response.data.blogPostId,
            postName: response.data.postName,
            postData: response.data.postData,
            image: response.data.imagePath || this.defaultImage,
            userId: response.data.userId
          };
          // Initialize likeCount from the blog data if available
          if (this.blog && this.blog.likes) {
            this.likeCount = this.blog.likes;
          }
        } else {
          throw new Error();
        }
      } catch (error) {
        console.error('Failed to fetch blog', error);
      }
    },
    toggleLike() {
      if (this.liked) {
        this.likeCount--;
      } else {
        this.likeCount++;
      }
      this.liked = !this.liked;
    },
    async deleteBlog() {
      try {
        const response = await axios.delete(`http://localhost:8080/delete-blog-post?blogPostId=${this.blog.id}`);
        if (response.status === 200) {
          alert('Blog post deleted successfully');
          this.$router.push('/');
        } else {
          throw new Error();
        }
      } catch (error) {
        console.error('Failed to delete blog', error);
      }
    }
  }
};
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: -40px auto 0 auto;
}

.blog-post {
  margin-top: 1em;
}

.blog-content {
  border: 1px solid #ccc; /* Add a border around the content */
  padding: 10px; /* Add some padding to the content */
  background-color: #e3dada;
}

.image-container {
  border: 1px solid transparent; /* Add a border around the image */
  padding: 10px; /* Add some padding to the container */
  display: inline-block; /* Make the container inline-block to wrap around the image */
}
.delete-button {
  margin-top: 10px;
  background-color: red;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
}
.like-section {
  margin-top: 10px; /* Add some space above the like section */
  display: flex;
  align-items: center;
}

.like-section button {
  margin-right: 10px; /* Add some space between the button and the like count */
  background: none; /* Remove default button background */
  border: none; /* Remove default button border */
  cursor: pointer; /* Change cursor to pointer */
  font-size: 1em; /* Set font size */
  display: flex;
  align-items: center;
}

.like-section button span {
  margin-right: 5px; /* Add space between the emoji and text */
}
</style>
