<template>
  <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link> |
    <router-link v-if="!isLoggedIn" to="/login">Login</router-link>
    <router-link v-if="isLoggedIn" to="/login" @click="logout">Logout</router-link> |
    <router-link to="/register">Register</router-link>
    <button class="user-icon-button" @click="navigateUser">
      <img src="https://img.icons8.com/ios-filled/50/000000/user-male-circle.png" alt="User" />
    </button>
  </nav>
  <router-view/>
</template>

<script>
export default {
  data() {
    return {
      isLoggedIn: !!localStorage.getItem('token')
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
        //this.$router.push('/login');
        this.$router.push('/profile');
      }
    },
    logout(event) {
      event.preventDefault();
      localStorage.removeItem('token');
      localStorage.removeItem('userId');
      window.location.href = '/';
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

.user-icon-button {
  background: none;
  border: none;
  cursor: pointer;
}

.user-icon-button img {
  height: 32px;
}
</style>