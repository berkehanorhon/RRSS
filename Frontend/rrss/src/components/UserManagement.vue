<template>
  <div class="user-management">
    <div class="user-list-box">
      <input v-model="searchQuery" placeholder="Search users" class="search-input"/>
      <div class="user-list">
        <ul>
          <li v-for="user in filteredUsers" :key="user.userId" @click="selectUser(user)" :class="{ selected: user.userId === selectedUser?.userId }">
            {{ user.username }} (ID: {{ user.userId }}) - 
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
    <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
    <div v-if="successMessage" class="success-message">{{ successMessage }}</div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'UserManagement',
  data() {
    return {
      searchQuery: '',
      users: [],
      selectedUser: null,
      errorMessage: '',
      successMessage: '',
    };
  },
  created() {
    this.loadUsers();
  },
  computed: {
    filteredUsers() {
      return this.users.filter(user => user.username.toLowerCase().includes(this.searchQuery.toLowerCase()));
    },
  },
  methods: {
    async loadUsers() {
      try {
        const response = await axios.get('http://localhost:8080/admin/get-all-users');
        this.users = response.data;
      } catch (error) {
        console.error('Error loading users:', error);
        this.errorMessage = 'Error loading users.';
      }
    },
    selectUser(user) {
      this.selectedUser = user;
    },
    async toggleAdmin() {
      if (this.selectedUser) {
        try {
          const updatedStatus = !this.selectedUser.isAdmin;
          const response = await this.updateUserData(this.selectedUser.userId, { setAdmin: updatedStatus });
          if (response.status === 200) {
            this.selectedUser.isAdmin = updatedStatus;
            this.successMessage = `User ${this.selectedUser.username} is now ${updatedStatus ? 'an Admin' : 'not an Admin'}`;
          }
        } catch (error) {
          this.errorMessage = 'Error updating Admin status.';
        }
      }
    },
    async toggleMerchant() {
      if (this.selectedUser) {
        try {
          const updatedStatus = !this.selectedUser.isMerchant;
          const response = await this.updateUserData(this.selectedUser.userId, { setMerchant: updatedStatus });
          if (response.status === 200) {
            this.selectedUser.isMerchant = updatedStatus;
            this.successMessage = `User ${this.selectedUser.username} is now ${updatedStatus ? 'a Merchant' : 'not a Merchant'}`;
          }
        } catch (error) {
          this.errorMessage = 'Error updating Merchant status.';
        }
      }
    },
    async toggleModerator() {
      if (this.selectedUser) {
        try {
          const updatedStatus = !this.selectedUser.isModerator;
          const response = await this.updateUserData(this.selectedUser.userId, { setModerator: updatedStatus });
          if (response.status === 200) {
            this.selectedUser.isModerator = updatedStatus;
            this.successMessage = `User ${this.selectedUser.username} is now ${updatedStatus ? 'a Moderator' : 'not a Moderator'}`;
          }
        } catch (error) {
          this.errorMessage = 'Error updating Moderator status.';
        }
      }
    },
    async banUser() {
      if (this.selectedUser) {
        try {
          await axios.post('http://localhost:8080/admin/ban-user', { userId: this.selectedUser.userId });
          this.successMessage = `User ${this.selectedUser.username} has been banned`;
          this.errorMessage = '';
        } catch (error) {
          this.errorMessage = 'Error banning user.';
        }
      }
    },
    async updateUserData(userId, updatedData) {
      try {
        const response = await axios.patch('http://localhost:8080/admin/update-user', { userId, ...updatedData });
        if (response.status === 200) {
          this.users = this.users.map(user => {
            if (user.userId === userId) {
              return { ...user, ...updatedData };
            }
            return user;
          });
          this.selectedUser = this.users.find(user => user.userId === userId);
        } else {
          this.errorMessage = 'Error updating user data.';
        }
        return response; // return the response object
      } catch (error) {
        this.errorMessage = 'Error updating user data.';
        throw error;
      }
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

.error-message {
  color: red;
  margin-top: 10px;
}

.success-message {
  color: green;
  margin-top: 10px;
}
</style>
