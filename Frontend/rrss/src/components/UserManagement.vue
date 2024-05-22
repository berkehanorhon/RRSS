<template>
  <div class="user-management">
    <div class="user-list-box">
      <input v-model="searchQuery" placeholder="Search users" class="search-input"/>
      <div class="user-list">
        <ul>
          <li v-for="user in filteredUsers" :key="user.user_id" @click="selectUser(user)" :class="{ selected: user.user_id === selectedUser?.user_id }">
            {{ user.username }} (ID: {{ user.user_id }}) - 
            {{ user.isAdmin ? 'Admin' : '' }} 
            {{ user.isMerchant ? 'Merchant' : '' }} 
            {{ user.isModerator ? 'Moderator' : '' }}
            {{ !(user.isAdmin || user.isMerchant || user.isModerator) ? ' User' : '' }}
          </li>
        </ul>
      </div>
    </div>
    <div v-if="selectedUser" class="actions">
      <button @click="toggleAdmin" class="action-button">{{ selectedUser.isAdmin ? 'Unset as Admin' : 'Set as Admin' }}</button>
      <button @click="toggleMerchant" class="action-button">{{ selectedUser.isMerchant ? 'Unset as Merchant' : 'Set as Merchant' }}</button>
      <button @click="toggleModerator" class="action-button">{{ selectedUser.isModerator ? 'Unset as Moderator' : 'Set as Moderator' }}</button>
      <button @click="banUser" class="action-button ban-button">Ban User</button>
    </div>
  </div>
</template>

<script>
import usersData from '../mocks/Users.json'; // Import the JSON file

export default {
  name: 'UserManagement',
  data() {
    return {
      searchQuery: '',
      users: usersData,
      selectedUser: usersData[0],
    };
  },
  computed: {
    filteredUsers() {
      return this.users.filter(user => user.username.toLowerCase().includes(this.searchQuery.toLowerCase()));
    },
  },
  methods: {
    selectUser(user) {
      this.selectedUser = user;
    },
    toggleAdmin() {
      if (this.selectedUser) {
        this.selectedUser.isAdmin = !this.selectedUser.isAdmin;
        this.updateUserData(this.selectedUser.user_id, { isAdmin: this.selectedUser.isAdmin });
      }
    },
    toggleMerchant() {
      if (this.selectedUser) {
        this.selectedUser.isMerchant = !this.selectedUser.isMerchant;
        this.updateUserData(this.selectedUser.user_id, { isMerchant: this.selectedUser.isMerchant });
      }
    },
    toggleModerator() {
      if (this.selectedUser) {
        this.selectedUser.isModerator = !this.selectedUser.isModerator;
        this.updateUserData(this.selectedUser.user_id, { isModerator: this.selectedUser.isModerator });
      }
    },
    banUser() {
      if (this.selectedUser) {
        alert(`User ${this.selectedUser.username} has been banned`);
        // Ban user işlemi burada uygulanacak
      }
    },
    updateUserData(userId, updatedData) {
      // Mock verilerinizi güncelleyin
      this.users = this.users.map(user => {
        if (user.user_id === userId) {
          return { ...user, ...updatedData };
        }
        return user;
      });
      // Gerçek uygulamada, burada bir API çağrısı yaparak sunucudaki verileri güncelleyebilirsiniz.
    },
  },
};
</script>

<style scoped>
.user-management {
  text-align: center;
  margin: 20px;
}

.user-list-box {
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

.user-list {
  max-height: 300px;
  overflow-y: auto;
  border-top: 1px solid #007BFF;
  padding-top: 10px;
}

.user-list ul {
  list-style: none;
  padding: 0;
}

.user-list li {
  padding: 10px;
  border-bottom: 1px solid #007BFF;
  cursor: pointer;
  transition: background-color 0.3s;
}

.user-list li:hover {
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

.ban-button {
  background-color: #dc3545;
}

.ban-button:hover {
  background-color: #c82333;
}
</style>
