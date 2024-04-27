<template>
  <div class="container">
    <FormComponent
      title="Test"
      buttonLabel="TestButton"
      :inputs="inputs"
      :submitForm="handleSubmit"
    />
  </div>
</template>

<script>
import FormComponent from '../components/FormComponent.vue';

export default {
  components: {
    FormComponent
  },
  data() {
    return {
      inputs: [
        { id: 'username', label: 'Username', type: 'text' },
        { id: 'password', label: 'Password', type: 'password' },
        { id: 'bca', label: 'BCA', type: 'text' },
        { id: 'asd', label: 'Remember me', type: 'checkbox' }
      ]
    };
  },
  methods: {
    async handleSubmit(formData) {
      console.log(1);
      console.log(formData);
      console.log(2);
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