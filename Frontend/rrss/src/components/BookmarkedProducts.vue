<template>
  <div class="bookmarkList-timeline">
    <div class="bookmarkList-container">
      <div v-if="bookmarkLists.length === 0" class="no-reviews">
        No bookmark list has been created yet!
      </div>
      <div class="bookmarkList-row" v-for="(row, index) in chunkedData" :key="index">
        <div v-for="bookmarkList in row" :key="bookmarkList.bookmarkListId" class="bookmarkList">
          <router-link :to="`/bookmarklists/${bookmarkList.bookmarkListId}`">
            <h4>{{ bookmarkList.title }}</h4>
            <img :src="getImage(bookmarkList)" alt="BookmarkList Image" class="bookmarkList-image" />
          </router-link>
          <p>Publish Date: {{ formatDate(bookmarkList.creationDate) }}</p>
          <button v-if="this.$route.params.userId == currentUserId" class="delete-button" @click="deleteBookmarkList(bookmarkList.bookmarkListId)">Delete</button>
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
  name: 'BookmarkListTimeline',
  props: ['userId'],
  data() {
    return {
      bookmarkLists: [
      ],
      defaultImage: require('@/assets/logo.png'),
      currentPage: 1,
      itemsPerPage: 18,
      windowWidth: 0
    };
  },
  computed: {
    currentUserId() {
      return localStorage.getItem('userId');
    },
    chunkSize() {
      if (this.windowWidth > 1600) {
        return 6;
      } else if (this.windowWidth > 800) {
        return 4;
      }
        else if (this.windowWidth > 600) {
        return 2;
      } else {
        return 1;
      }
    },
    paginatedData() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.bookmarkLists.slice(start, end);
    },
    chunkedData() {
      return Array(Math.ceil(this.paginatedData.length / this.chunkSize)).fill().map((_, index) => index * this.chunkSize).map(begin => this.paginatedData.slice(begin, begin + this.chunkSize));
    },
    totalPages() {
      return Math.ceil(this.bookmarkLists.length / this.itemsPerPage);
    }
  },
  mounted() {
    this.fetchBookmarkLists();
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
    deleteBookmarkList(bookmarkListId) {
      axios.delete(`http://127.0.0.1:8080/bookmark/delete`, {
        data: {
          bookmarkListId: bookmarkListId
        }
      })
      .then(() => {
        this.fetchBookmarkLists();
      })
      .catch(error => {
        console.error("There was an error deleting the bookmark list:", error);
      });
    },
    fetchBookmarkLists() {
      axios.get(`http://127.0.0.1:8080/bookmark/get-users-lists?userId=${this.userId}`)
      .then(response => {
        this.bookmarkLists = response.data.map(bookmarkList => ({
          ...bookmarkList,
          image: require('@/assets/logo.png')
        }));
      })
      .catch(error => {
        console.error("There was an error fetching the bookmarkLists:", error);
      });
    },
    getImage(bookmarkList) {
      return bookmarkList.image || this.defaultImage;
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
    formatDate(date) {
      const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' };
      return new Date(date).toLocaleDateString('tr-TR', options).replace('.', '/').replace('.', '/');
    }
  },
  watch: {
    selectedCategories() {
      this.fetchBookmarkLists();
    }
  }
};
</script>

<style scoped>
.bookmarkList-timeline {
  display: flex;
  justify-content: center;
  align-items: center;
  height: auto;
  background-color: #fdfdfd;
  padding: 20px;
}

.bookmarkList-container {
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

.bookmarkList-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.bookmarkList {
  flex: 0 0 200px;
  box-sizing: border-box;
  background-color: #fff;
  padding: 20px;
  margin: 10px;
  border: 1px solid #ccc;
  border-radius: 10px;
}

.bookmarkList-image {
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
.delete-button {
  padding: 10px;
  margin-top: 10px;
  border: none;
  border-radius: 5px;
  background-color: #FF0000;
  color: #fff;
  cursor: pointer;
}
</style>