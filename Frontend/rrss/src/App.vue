<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link> |
      <router-link v-if="!isLoggedIn" to="/login">Login</router-link>
      <router-link v-if="isLoggedIn" @click="logout">Logout</router-link> |
      <router-link to="/register">Register</router-link>
      <button class="user-icon-button" @click="navigateUser">
        <img src="https://img.icons8.com/ios-filled/50/000000/user-male-circle.png" alt="User" />
      </button>
    </nav>
    <div class="router-container">
      <router-view/>
    </div>
  </div>
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
        this.$router.push('/');
      } else {
        this.$router.push('/login');
      }
    },
    logout(event) {
      event.preventDefault();
      localStorage.removeItem('token');
      this.$router.push('/login');
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  display: flex;
  flex-direction: column;
  height: 100vh; /* Sayfanın tam boyunu kaplar */
}

nav {
  width: 100%;
  padding: 20px 0;
  background-color: rgb(227, 230, 142);
  border-bottom: 1px solid #ccc;
  display: flex;
  justify-content: space-evenly; /* Elemanlar arasında eşit boşluk bırakır */
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1000;
}

.router-container {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center; /* Dikey olarak merkeze hizalar */
  padding-top: 20px; /* Navbar yüksekliği */
  width: 100%;
}

body, html {
  margin: 0;
  padding: 0;
  height: 100%;
  background-color: #f0f0f0;
}

nav a, .user-icon-button {
  font-weight: bold;
  color: #2c3e50;
  margin: 0 15px; /* Artırılmış boşluk */
  font-size: 18px; /* Büyütülmüş font boyutu */
  display: inline-flex; /* Dikey hizalamayı düzeltmek için */
  align-items: center; /* Dikey hizalamayı düzeltmek için */
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
