<template>
    <div class="container">
      <FormComponent
        title="Login"
        buttonLabel="Login"
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
          { id: 'asd', label: 'Remember me', type: 'checkbox' }
        ]
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
                    window.location.href = '/';

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