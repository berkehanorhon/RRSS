<template>
    <div>
        <h1>Register</h1>
        <form @submit.prevent="register">
            <label for="username">Username:</label>
            <input type="text" id="username" v-model="username" required>

            <label for="password">Password:</label>
            <input type="password" id="password" v-model="password" required>

            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" v-model="firstName" required>

            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" v-model="lastName" required>

            <label for="birthDate">Birth Date:</label>
            <input type="date" id="birthDate" v-model="birthDate" required>

            <label for="registrationDate">Registration Date:</label>
            <input type="date" id="registrationDate" v-model="registrationDate" required>

            <label for="isAdmin">Is Admin:</label>
            <input type="checkbox" id="isAdmin" v-model="isAdmin">

            <label for="isModerator">Is Moderator:</label>
            <input type="checkbox" id="isModerator" v-model="isModerator">

            <label for="isMerchant">Is Merchant:</label>
            <input type="checkbox" id="isMerchant" v-model="isMerchant">

            <label for="email">Email:</label>
            <input type="email" id="email" v-model="email" required>

            <label for="reputation">Reputation:</label>
            <input type="number" id="reputation" v-model="reputation" required>

            <label for="tokenExp">Token Expiration:</label>
            <input type="number" id="tokenExp" v-model="tokenExp" required>

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
            password: '',
            firstName: '',
            lastName: '',
            birthDate: '',
            registrationDate: '',
            isAdmin: 0,
            isModerator: 0,
            isMerchant: 0,
            email: '',
            reputation: 0,
            tokenExp: 0
        };
    },
    methods: {
        async register() {
            try {
                const response = await axios.post('http://127.0.0.1:8080/auth/register', {
                    username: this.username,
                    password: this.password,
                    firstName: this.firstName,
                    lastName: this.lastName,
                    birthDate: this.birthDate,
                    registrationDate: this.registrationDate,
                    isAdmin: this.isAdmin,
                    isModerator: this.isModerator,
                    isMerchant: this.isMerchant,
                    email: this.email,
                    reputation: this.reputation,
                    tokenExp: this.tokenExp
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