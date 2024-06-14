<template>
  <div class="blog-timeline">
    <div class="blog-container">
      <div v-if="blogs.length === 0" class="no-reviews">
        No Blog Post has been created yet!
      </div>
      <div class="blog-row" v-for="(row, index) in chunkedData" :key="index">
        <div v-for="blog in row" :key="blog.id" class="blog">
          <router-link :to="`/blogs/${blog.id}`">
            <h4>{{ blog.postName }}</h4>
            <img :src="getImage(blog)" alt="Blog Image" class="blog-image" />
          </router-link>
          <p>{{ blog.postData }}</p>
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
  name: 'BlogTimeline',
  data() {
    return {
      blogs: [],
      defaultImage: require('@/assets/logo.png'),
      currentPage: 1,
      itemsPerPage: 18,
      windowWidth: 0,
      userId: null,
    };
  },
  async created() {
    this.userId = this.$route.params.userId;
    await this.fetchBlogs();
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
  async fetchBlogs() {
      try {
        const response = await axios.get(`http://localhost:8080/get-users-all-blog-posts?userId=${this.userId}`);
        if (response.status === 200) {
          this.blogs = response.data.map(blog => ({
            id: blog.blogPostId,
            postName: blog.postName,
            postData: blog.postData,
            image: blog.imagePath || this.defaultImage,
          }));
        } else {
          throw new Error();
        }
      } catch (error) {
        console.error('Failed to fetch blogs', error);
      }
    },
    getImage(blog) {
      return blog.imagePath || this.defaultImage;
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
  },
  watch: {
    selectedCategories() {
      this.fetchBlogs();
    }
  }
};
</script>

<style scoped>
.blog-timeline {
  display: flex;
  justify-content: center;
  align-items: center;
  height: auto;
  background-color: #fcfcfc;
  padding: 20px;
}

.blog-container {
  width: 92%;
  max-width: 1500px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #fff;
  height: auto;
  overflow: auto;
}

.blog-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.blog {
  flex: 0 0 200px;
  box-sizing: border-box;
  background-color: #fff;
  padding: 20px;
  margin: 10px;
  border: 1px solid #ccc;
  border-radius: 10px;
}

.blog-image {
  width: 173.5;
  max-height: 173.5px;
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