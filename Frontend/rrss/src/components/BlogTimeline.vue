<template>
  <div class="blog-timeline">
    <div class="blog-container">
      <div class="blog-row" v-for="(row, index) in chunkedData" :key="index">
        <div v-for="blog in row" :key="blog.blogPostId" class="blog" @click="navigateToBlog(blog.blogPostId)">
          <div class="blog-image-wrapper">
            <img :src="getImagePath(blog.imagePath)" alt="Blog Image" class="blog-image" />
          </div>
          <div class="blog-content">
            <h4>{{ blog.postName }}</h4>
            <p>{{ blog.postData }}</p>
          </div>
          <div class="blog-footer">
            <div class="blog-date">{{ formatDate(blog.publishDate) }}</div>
            <div class="like-wrapper">
              <button class="like-button" @click.stop="likeBlog(blog.blogPostId)">Like</button>
              <div class="like-count">{{ blog.likes }}</div>
            </div>
          </div>
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
import { mapGetters } from 'vuex';

export default {
  name: 'BlogTimeline',
  data() {
    return {
      blogs: [],
      defaultImage: require('@/assets/logo.png'),
      currentPage: 1,
      itemsPerPage: 6,
      windowWidth: 0
    };
  },
  computed: {
    ...mapGetters(['isLoggedIn']),
    chunkSize() {
      if (this.windowWidth > 1600) {
        return 1;
      } else if (this.windowWidth > 800) {
        return 1;
      } else {
        return 1;
      }
    },
    paginatedData() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.blogs.slice(start, end);
    },
    chunkedData() {
      return Array(Math.ceil(this.paginatedData.length / this.chunkSize)).fill().map((_, index) => index * this.chunkSize).map(begin => this.paginatedData.slice(begin, begin + this.chunkSize));
    },
    totalPages() {
      return Math.ceil(this.blogs.length / this.itemsPerPage);
    }
  },
  mounted() {
    this.windowWidth = window.innerWidth;
    window.addEventListener('resize', this.updateWindowWidth);
    this.fetchBlogs();
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.updateWindowWidth);
  },
  methods: {
    updateWindowWidth() {
      this.windowWidth = window.innerWidth;
    },
    async fetchBlogs() {
      try {
        const response = await axios.get('http://localhost:8080/get-all-blog-posts');
        this.blogs = response.data.map(blog => ({ ...blog, likes: 0 }));
      } catch (error) {
        console.error('Error fetching blogs:', error);
      }
    },
    getImagePath() {
      return this.defaultImage;
    },
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Date(dateString).toLocaleDateString(undefined, options);
    },
    likeBlog(blogPostId) {
      const blog = this.blogs.find(blog => blog.blogPostId === blogPostId);
      if (blog) {
        blog.likes++;
      }
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
    navigateToBlog(blogPostId) {
      this.$router.push(`/blogs/${blogPostId}`);
    }
  }
};
</script>

<style scoped>
.blog-timeline {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background-color: #f8f9fa;
}

.blog-container {
  width: 100%;
  max-width: 1200px;
  margin: auto;
  background-color: #fff;
}

.blog-row {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  border-bottom: 1px solid #ddd;
  padding: 10px 0;
}

.blog {
  display: flex;
  flex-direction: row;
  width: 100%;
  cursor: pointer;
}

.blog-image-wrapper {
  flex-shrink: 0;
  margin-right: 10px;
}

.blog-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
}

.blog-content {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.blog-footer {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-end;
}

.blog-date {
  align-self: flex-start;
  font-size: 12px;
  color: #666;
}

.like-wrapper {
  display: flex;
  align-items: center;
  margin-top: 10px;
}

.like-button {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 5px;
}

.like-count {
  font-size: 14px;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}

.page-button {
  margin: 0 10px;
}

.page-text {
  font-size: 16px;
}
</style>
