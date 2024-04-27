<template>
  <div class="container">
    <FormComponent
      title="Register"
      buttonLabel="Register"
      :inputs="inputs"
      :submitForm="handleSubmit"
    />
  </div>
</template>

<script>
import FormComponent from '../components/FormComponent.vue';
import axios from 'axios';
export default {
  components: {
    FormComponent
  },
  data() {
    return {
      inputs: [
        { id: 'username', label: 'Username', type: 'text' },
        { id: 'password', label: 'Password', type: 'password' },
        { id: 'firstName', label: 'First Name', type: 'text' },
        { id: 'lastName', label: 'Last Name', type: 'text' },
        { id: 'birthDate', label: 'Birth Date', type: 'date' },
        { id: 'registrationDate', label: 'Registration Date', type: 'date' },
        { id: 'isAdmin', label: 'Is Admin', type: 'checkbox' },
        { id: 'isModerator', label: 'Is Moderator', type: 'checkbox' },
        { id: 'isMerchant', label: 'Is Merchant', type: 'checkbox' },
        { id: 'email', label: 'Email', type: 'email' },
        { id: 'reputation', label: 'Reputation', type: 'number' },
        { id: 'tokenExp', label: 'Token Expiration', type: 'number' }
      ]
    };
  },
  methods: {
    async handleSubmit(formData) {
            try {
                const response = await axios.post('http://127.0.0.1:8080/auth/register', {
                    username: formData.username,
                    password: formData.password,
                    firstName: formData.firstName,
                    lastName: formData.lastName,
                    birthDate: formData.birthDate,
                    registrationDate: formData.registrationDate,
                    isAdmin: formData.isAdmin,
                    isModerator: formData.isModerator,
                    isMerchant: formData.isMerchant,
                    email: formData.email,
                    reputation: formData.reputation,
                    tokenExp: formData.tokenExp
                });

                if (response.status === 201) {
                    window.location.href = '/login';
                }
            } catch (error) {
                console.error(error);
            }
        }
  }
};
</script>

<style>
.container {
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  padding-top: 20vh;
  height: 100vh;
}

.form-submit-button {
  width: 8%;
  height: 45px;
  padding: 5px 10px;
  background-color: #808080;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 5px;
}

.form-submit-button:hover {
  background-color: #696969;
}
</style>