<template>
  <div class="container">
    <div v-if="blog" class="blog-post">
      <div class="blog-content">
        <h1>{{ blog.postName }}</h1>
        <div class="image-container">
          <img :src="blog.imagePath || defaultImage" alt="Blog Image" class="blog-image" />
        </div>
        <p>{{ blog.postData }}</p>
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
      defaultImage: require('@/assets/logo.png')
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
  background-color: #cccccc;
}

.image-container {
  border: 1px solid transparent; /* Add a border around the image */
  padding: 10px; /* Add some padding to the container */
  display: inline-block; /* Make the container inline-block to wrap around the image */
}
</style>