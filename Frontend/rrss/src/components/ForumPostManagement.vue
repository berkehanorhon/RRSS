<template>
  <div class="forum-management">
    <div class="forum-list-box">
      <input v-model="searchQuery" placeholder="Search forums" class="search-input" />
      <div class="forum-list">
        <ul>
          <li v-for="forum in filteredForums" :key="forum.forumPostId" @click="selectForum(forum)" :class="{ selected: forum.forumPostId === selectedForum?.forumPostId }">
            {{ forum.postName }} (ID: {{ forum.forumPostId }}) - {{ forum.postData }}
          </li>
        </ul>
      </div>
    </div>
    <div class="actions">
      <button @click="deleteForum" v-if="selectedForum" class="action-button delete-button">Delete Forum</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'ForumPostManagement',
  data() {
    return {
      searchQuery: '',
      forums: [],
      selectedForum: null,
    };
  },
  async created() {
    try {
      const response = await axios.get('http://localhost:8080/admin/get-all-forum-posts');
      this.forums = response.data;
      this.selectedForum = this.forums[0];
    } catch (error) {
      console.error('Failed to fetch forums:', error);
    }
  },
  computed: {
    filteredForums() {
      return this.forums.filter(forum => forum.postName.toLowerCase().includes(this.searchQuery.toLowerCase()));
    },
  },
  methods: {
    selectForum(forum) {
      this.selectedForum = forum;
    },
    async deleteForum() {
      if (this.selectedForum) {
        try {
          await axios.delete(`http://localhost:8080/admin/delete-forum-post?forumPostId=${this.selectedForum.forumPostId}`);
          const index = this.forums.findIndex(forum => forum.forumPostId === this.selectedForum.forumPostId);
          if (index !== -1) {
            this.forums.splice(index, 1);
            alert(`Forum "${this.selectedForum.postName}" has been deleted`);
            this.selectedForum = null;
          }
        } catch (error) {
          console.error('Failed to delete forum:', error);
        }
      }
    },
  },
};
</script>

<style scoped>
.forum-management {
  text-align: center;
  margin: 20px;
}

.forum-list-box {
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

.forum-list {
  max-height: 300px;
  overflow-y: auto;
  border-top: 1px solid #007BFF;
  padding-top: 10px;
}

.forum-list ul {
  list-style: none;
  padding: 0;
}

.forum-list li {
  padding: 10px;
  border-bottom: 1px solid #007BFF;
  cursor: pointer;
  transition: background-color 0.3s;
}

.forum-list li:hover {
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

.add-button {
  background-color: #28a745;
}

.add-button:hover {
  background-color: #218838;
}
</style>
