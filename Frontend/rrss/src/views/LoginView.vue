<template>
  <div class="container">
    <FormComponent
      title="Login"
      buttonLabel="Login"
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
        { id: 'username', label: 'Username', type: 'text' , text: ''},
        { id: 'password', label: 'Password', type: 'password' , text: ''},
        { id: 'asd', label: 'Remember me', type: 'checkbox' , text: ''}
      ],
      popupVisible: false,
      popupMessage: '',
      popupSuccess: false,
    };
  },
  methods: {
    async handleSubmit(formData) {
      try {
        const response = await axios.post('http://127.0.0.1:8080/auth/login', {
          username: formData.username,
          password: formData.password
        });

        if (response.status === 200) {
          console.log(response.data.token);
          localStorage.setItem('token', response.data.token);
          localStorage.setItem('userId', response.data.userId);
          window.location.href = '/';
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
  background-color: #DFF0D8; /* Light green */
  color: #3C763D; /* Dark green */
}

.popup-error {
  background-color: #F2DEDE; /* Light red */
  color: #A94442; /* Dark red */
  width: 20%;
  margin: 0 auto;
}
</style>