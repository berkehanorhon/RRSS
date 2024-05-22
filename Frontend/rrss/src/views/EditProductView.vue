<template>
    <div class="container">
      <FormComponent
        title="Edit Product"
        buttonLabel="Edit Product"
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
          { id: 'title', label: 'Product Title', type: 'text' ,text: ''},
          { id: 'description', label: 'Description', type: 'text' ,text: ''},
          { id: 'categoryId', label: 'Category ID', type: 'number' ,text: ''}
        ]
      };
    },
    async created() {
        const response = await axios.get(`http://localhost:8080/get-product?productId=${this.$route.params.productId}`);
        const product = response.data;
        this.inputs.forEach(input => {
        input.text = product[input.id] || '';
        });
    },
    methods: {
        async handleSubmit(formData) {
            try {
            const response = await axios.patch(`http://localhost:8080/update-product`, {
                productId: this.$route.params.productId,
                title: formData.title,
                description: formData.description,
                categoryId: formData.categoryId
            });

            if (response.status === 200) {
                window.location.href = `/products/${this.$route.params.productId}`;
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