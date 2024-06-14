<template>
  <div class="container">
    <FormComponent
      title="Add Product"
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
        { id: 'categoryId', label: 'Category ID', type: 'select', options: [], required: true },
        { id: 'title', label: 'Title', type: 'text', required: true },
        { id: 'description', label: 'Description', type: 'text', required: true }
      ]
    };
  },
  async created() {
    try {
      const response = await axios.get('http://localhost:8080/get-all-categories');
      this.inputs.find(input => input.id === 'categoryId').options = response.data.map(category => ({ value: category.categoryId, text: category.categoryName }));
    } catch (error) {
      console.error('Failed to fetch categories:', error);
    }
  },
  methods: {
    async handleSubmit(formData) {
      try {
        const response = await axios.post('http://localhost:8080/add-product', {
          categoryId: formData.categoryId,
          title: formData.title,
          description: formData.description
        });

        if (response.status === 201) {
          alert('Product added successfully');
          this.$router.push('/');
        }
      } catch (error) {
        console.error(error);
        alert('Failed to add product');
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
