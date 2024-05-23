<template>
  <div class="blog-timeline">
    <div class="blog-container">
      <div class="blog-row" v-for="(row, index) in chunkedData" :key="index">
        <div v-for="blog in row" :key="blog.blogId" class="blog">
          <div class="blog-image-wrapper">
            <img :src="blog.image" alt="Blog Image" class="blog-image" />
          </div>
          <div class="blog-content">
            <h4>{{ blog.postName }}</h4>
            <p>{{ blog.postData }}</p>
          </div>
          <div class="blog-footer">
            <div class="blog-date">{{ blog.publishDate }}</div>
            <div class="like-wrapper">
              <button class="like-button" @click="likeBlog(blog.blogId)">Like</button>
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
export default {
  name: 'BlogTimeline',
  data() {
    return {
      blogs: [
        { blogId: 1, postName: 'First Blog', postData: 'Description of the first blog.', image: 'https://via.placeholder.com/150', publishDate: 'May 1, 2024', likes: 5 },
        { blogId: 2, postName: 'Second Blog', postData: 'Description of the second blog.', image: 'https://via.placeholder.com/150', publishDate: 'May 2, 2024', likes: 10 },
        { blogId: 3, postName: 'Third Blog', postData: 'Description of the third blog.', image: 'https://via.placeholder.com/150', publishDate: 'May 3, 2024', likes: 15 },
        // Add more mock blogs as needed
      ],
      defaultImage: 'https://via.placeholder.com/150',
      currentPage: 1,
      itemsPerPage: 6,
      windowWidth: 0
    };
  },
  computed: {
    chunkSize() {
      if (this.windowWidth > 1600) {
        return 2;
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
    likeBlog(blogId) {
      const blog = this.blogs.find(blog => blog.blogId === blogId);
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
