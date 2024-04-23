<template>
    <div>
        <h1>Register</h1>
        <form @submit.prevent="register">
            <label for="username">Username:</label>
            <input type="text" id="username" v-model="username" required>

            <label for="email">Email:</label>
            <input type="email" id="email" v-model="email" required>

            <label for="password">Password:</label>
            <input type="password" id="password" v-model="password" required>

            <label for="passwordConfirm">Confirm Password:</label>
            <input type="password" id="passwordConfirm" v-model="passwordConfirm" required>

            <p v-if="passwordMismatch" class="error">Passwords do not match!</p>

            <button type="submit">Register</button>
        </form>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            username: '',
            email: '',
            password: '',
            passwordConfirm: ''
        };
    },
    computed: {
        passwordMismatch() {
            return this.password && this.passwordConfirm && this.password !== this.passwordConfirm;
        }
    },
    methods: {
        async register() {
            try {
                const response = await axios.post('http://127.0.0.1:8000/auth/register', {
                    username: this.username,
                    email: this.email,
                    password: this.password
                });

                if (response.status === 200) {
                    localStorage.setItem('token', response.data.token);
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
    justify-content: flex-start;
    padding-top: 20vh;
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
button:disabled {
    background-color: #999;
    cursor: not-allowed;
}
.error {
    color: red;
}
</style>