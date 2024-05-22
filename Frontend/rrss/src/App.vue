<template>
  <nav>
    <router-link to="/blogtimeline">Home</router-link> |
    <router-link to="/about">About</router-link> |
    <router-link v-if="!isLoggedIn" to="/login">Login</router-link>
    <router-link v-if="isLoggedIn" to="/login" @click="logout">Logout</router-link> |
    <router-link :to="isLoggedIn ? '/' : '/register'">Register</router-link>
    <button class="user-icon-button" @click="navigateUser">
      <span class="balance" v-if="isLoggedIn">Balance: {{ user_balance }}</span>
      <img src="https://img.icons8.com/ios-filled/50/000000/user-male-circle.png" alt="User" />
    </button>
  </nav>
  <router-view/>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      isLoggedIn: !!localStorage.getItem('token'),
      user_balance: null,
    };
  },
  methods: {
    navigateUser() {
      if (this.isLoggedIn) {
      let userId = localStorage.getItem('userId');
      if (userId) {
        this.$router.push(`/profile/${userId}`);
      } else {
        this.$router.push('/');
      }
    } else {
        this.$router.push('/login');
      }
    },
    logout(event) {
      event.preventDefault();
      localStorage.removeItem('token');
      localStorage.removeItem('userId');
      this.user_balance = null;
      window.location.href = '/';
    }
  },
  async created() { // created hook'unu kullanarak user_balance'ı doldurun
    if (this.isLoggedIn) {
      try {
        const response = await axios.get('http://localhost:8080/get-balance');
        this.user_balance = response.data;
      } catch (error) {
        console.error(error);
      }
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 10px;
  border-bottom: 1px solid black;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

body {
    background-color: #f0f0f0;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}

nav a.router-link-exact-active:not(:first-child) {
  color: #2c3e50;
}

.user-icon-button {
  background: none;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
}

.user-icon-button img {
  height: 32px;
}

.balance {
  font-weight: bold;
  color: #42b983;
  margin-right: 10px;
}
</style>