<template>
    <div>
        <h1>Login</h1>
        <form @submit.prevent="login">
            <label for="username">Username:</label>
            <input type="text" id="username" v-model="username" required>
            <label for="password">Password:</label>
            <input type="password" id="password" v-model="password" required>
            <button type="submit">Login</button>
        </form>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            username: '',
            password: ''
        };
    },
    methods: {
        async login() {
            try {
                const response = await axios.post('http://127.0.0.1:8000/auth/login', {
                    username: this.username,
                    password: this.password
                });

                if (response.status === 200) {
                    localStorage.setItem('token', response.data.token);
                    window.location.href = '/'; // Ana sayfaya yönlendir ve sayfayı yeniden yükle
                }
            } catch (error) {
                console.error(error);
            }
        }
    }
};
</script>

<style scoped>
div {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: flex-start; /* Değiştirilen satır */
    padding-top: 20vh; /* Eklenen satır */
    height: auto;
    background-color: #f0f0f0;
}
h1 {
    margin-bottom: 20px;
}
label {
    display: block;
    margin-bottom: 5px;
}
input {
    width: 100%;
    padding: 5px;
    margin-bottom: 10px;
}
button {
    display: block;
    width: 100%;
    height: 50px;
    margin: 20px auto;
    padding: 5px 10px;
    background-color: #808080;
    color: white;
    border: none;
    cursor: pointer;
}
button:hover {
    background-color: #696969;
}
</style>