<template>
  <div class="forum-management">
    <div class="forum-list-box">
      <input v-model="searchQuery" placeholder="Search forums" class="search-input" />
      <div class="forum-list">
        <ul>
          <li v-for="forum in filteredForums" :key="forum.id" @click="selectForum(forum)" :class="{ selected: forum.id === selectedForum?.id }">
            {{ forum.forumName }} (ID: {{ forum.id }}) - {{ forum.forumData }}
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
import forumsData from '../mocks/ForumPosts.json'; // Import the JSON file

export default {
  name: 'ForumPostManagement',
  data() {
    const forums = Object.entries(forumsData).map(([id, forum]) => ({ id, ...forum }));
    return {
      searchQuery: '',
      forums: forums,
      selectedForum: forums[0],
    };
  },
  computed: {
    filteredForums() {
      return this.forums.filter(forum => forum.forumName.toLowerCase().includes(this.searchQuery.toLowerCase()));
    },
  },
  methods: {
    selectForum(forum) {
      this.selectedForum = forum;
    },
    deleteForum() {
      if (this.selectedForum) {
        this.forums = this.forums.filter(forum => forum.id !== this.selectedForum.id);
        alert(`Forum "${this.selectedForum.forumName}" has been deleted`);
        this.selectedForum = null;
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
