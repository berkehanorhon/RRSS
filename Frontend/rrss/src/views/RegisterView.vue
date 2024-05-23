<template>
  <div class="container">
    <FormComponent
      title="Register"
      buttonLabel="Register"
      :inputs="inputs"
      :submitForm="handleSubmit"
    />
    <div v-if="popupVisible" :class="{'popup-success': popupSuccess, 'popup-error': !popupSuccess}">
      <p>{{ popupMessage }}</p>
      <button class="submit-button" @click="popupVisible = false">OK</button>
    </div>
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
        { id: 'username', label: 'Username', type: 'text' ,text: ''},
        { id: 'password', label: 'Password', type: 'password' ,text: ''},
        { id: 'email', label: 'Email', type: 'email' ,text: ''},
        { id: 'firstName', label: 'First Name', type: 'text' ,text: ''},
        { id: 'lastName', label: 'Last Name', type: 'text' ,text: ''},
        { id: 'birthDate', label: 'Birth Date', type: 'date' ,text: ''}
      ],
      popupVisible: false,
      popupMessage: '',
      popupSuccess: false,
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
          isAdmin: formData.isAdmin,
          isModerator: formData.isModerator,
          isMerchant: formData.isMerchant,
          email: formData.email,
          reputation: formData.reputation,
          tokenExp: formData.tokenExp
        });

        if (response.status === 201) {
          this.popupMessage = 'Your registration is successful, redirecting to the login page...';
          this.popupSuccess = true;
          this.popupVisible = true;
          setTimeout(() => {
            window.location.href = '/login';
          }, 3000);
        } else {
          throw new Error('Registration failed');
        }
      } catch (error) {
        console.error(error);
        this.popupMessage = error.response && error.response.data.message ? error.response.data.message : 'An error occurred!';
        this.popupSuccess = false;
        this.popupVisible = true;
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

.popup-success {
  background-color: #DFF0D8;
  color: #3C763D;
  width: 20%;
  margin: 0 auto;
}

.popup-error {
  background-color: #F2DEDE;
  color: #A94442;
  width: 20%;
  margin: 0 auto;
}
</style>