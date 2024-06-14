<template>
    <div class="container">
      <FormComponent
        title="Add Category"
        buttonLabel="Add"
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
          { id: 'categoryName', label: 'Category Name', type: 'text', required: true },
          { id: 'description', label: 'Description', type: 'text', required: true }
        ]
      };
    },
    methods: {
      async handleSubmit(formData) {
        try {
          const response = await axios.post('http://localhost:8080/add-category', {
            categoryName: formData.categoryName,
            description: formData.description
          });
  
          if (response.status === 201) {
            alert('Category added successfully');
            this.$router.push('/admin-panel');
          }
        } catch (error) {
          console.error(error);
          alert('Failed to add category');
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