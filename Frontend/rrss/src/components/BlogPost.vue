<template>
  <div class="container">
    <div v-if="blog" class="blog-post">
      <div class="blog-content">
        <h1>{{ blog.postName }}</h1>
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
      </div>
    </div>
  </div>
</template>

<script>
import blogsData from '../mocks/Blogs.json'; // Import the JSON file

export default {
  name: 'BlogPost',
  props: ['blogId'],
  data() {
    return {
      blog: null,
      defaultImage: require('@/assets/logo.png'),
      likeCount: 0, // Initial like count
      liked: false  // User's like status
    };
  },
  created() {
    this.fetchBlog();
  },
  methods: {
    fetchBlog() {
        console.log('Fetching blog with ID:', this.blogId);
        this.blog = blogsData[this.blogId];
        console.log('Fetched blog:', this.blog);
        // Initialize likeCount from the blog data if available
        if (this.blog && this.blog.likes) {
          this.likeCount = this.blog.likes;
        }
    },
    toggleLike() {
      if (this.liked) {
        this.likeCount--;
      } else {
        this.likeCount++;
      }
      this.liked = !this.liked;
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
