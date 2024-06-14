<template>
    <div class="container">
      <FormComponent
        title="Edit Profile"
        buttonLabel="Edit Profile"
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
          { id: 'username', label: 'Username', type: 'text' ,text: ''},
          { id: 'password', label: 'Password', type: 'password' ,text: ''},
          { id: 'email', label: 'Email', type: 'email' ,text: ''},
          { id: 'firstName', label: 'First Name', type: 'text' ,text: ''},
          { id: 'lastName', label: 'Last Name', type: 'text' ,text: ''},
          { id: 'birthDate', label: 'Birth Date', type: 'date' ,text: ''}
        ]
      };
    },
    async created() {
        const response = await axios.get(`http://localhost:8080/get-user?userId=${this.$route.params.userId}`);
        const user = response.data;
        this.inputs.forEach(input => {
        input.text = user[input.id] || '';
        });
    },
    methods: {
        async handleSubmit(formData) {
            try {
            const response = await axios.patch(`http://localhost:8080/update-user`, {
                userId: this.$route.params.userId,
                username: formData.username,
                password: formData.password,
                firstName: formData.firstName,
                lastName: formData.lastName,
                birthDate: formData.birthDate,
                email: formData.email
            });

            if (response.status === 200) {
                window.location.href = `/profile/${this.$route.params.userId}`;
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