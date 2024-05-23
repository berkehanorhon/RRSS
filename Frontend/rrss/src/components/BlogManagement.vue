<template>
  <div class="blog-management">
    <div class="blog-list-box">
      <input v-model="searchQuery" placeholder="Search blogs" class="search-input"/>
      <div class="blog-list">
        <ul>
          <li v-for="blog in filteredBlogs" :key="blog.id" @click="selectBlog(blog)" :class="{ selected: blog === selectedBlog }">
            {{ blog.postName }} (ID: {{ blog.blogPostId }})
          </li>
        </ul>
      </div>
    </div>
    <div v-if="selectedBlog" class="actions">
      <button @click="deleteBlog" class="action-button delete-button">Delete Blog</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'; // Make sure to install axios using npm install axios

export default {
  name: 'BlogManagement',
  data() {
    return {
      searchQuery: '',
      blogs: [],
      selectedBlog: null,
    };
  },
  async created() {
    try {
      const response = await axios.get('http://localhost:8080/admin/get-all-blog-posts');
      this.blogs = response.data;
      this.selectedBlog = this.blogs[0];
    } catch (error) {
      console.error('Failed to fetch blogs:', error);
    }
  },
  computed: {
    filteredBlogs() {
      return this.blogs.filter(blog => blog.postName && blog.postName.toLowerCase().includes(this.searchQuery.toLowerCase()));
    },
  },
  methods: {
    selectBlog(blog) {
      this.selectedBlog = blog;
    },
    async deleteBlog() {
      if (this.selectedBlog) {
        try {
          await axios.delete(`http://localhost:8080/admin/delete-blog-post?blogPostId=${this.selectedBlog.blogPostId}`);
          const index = this.blogs.findIndex(blog => blog.blogPostId === this.selectedBlog.blogPostId);
          if (index !== -1) {
            this.blogs.splice(index, 1);
            alert(`Blog "${this.selectedBlog.postName}" has been deleted`);
            this.selectedBlog = null;
          }
        } catch (error) {
          console.error('Failed to delete blog:', error);
        }
      }
    },
  },
};
</script>

<style scoped>
.blog-management {
  text-align: center;
  margin: 20px;
}

.blog-list-box {
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

.blog-list {
  max-height: 300px;
  overflow-y: auto;
  border-top: 1px solid #007BFF;
  padding-top: 10px;
}

.blog-list ul {
  list-style: none;
  padding: 0;
}

.blog-list li {
  padding: 10px;
  border-bottom: 1px solid #007BFF;
  cursor: pointer;
  transition: background-color 0.3s;
}

.blog-list li:hover {
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

.delete-button {
  background-color: #dc3545;
}

.delete-button:hover {
  background-color: #c82333;
}
</style>
