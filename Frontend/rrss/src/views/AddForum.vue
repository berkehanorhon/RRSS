<template>
  <div class="container">
    <FormComponent
      title="Add Forum"
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
        { id: 'forumName', label: 'Forum Name', type: 'text', required: true },
        { id: 'forumDescription', label: 'Forum Description', type: 'text', required: true }
      ]
    };
  },
  methods: {
    async handleSubmit(formData) {
      try {
        const response = await axios.post('http://localhost:8080/add-forum', {
          forumName: formData.forumName,
          forumDescription: formData.forumDescription
        });

        if (response.status === 201) {
          alert('Forum added successfully');
          this.$router.push('/forums');
        }
      } catch (error) {
        console.error(error);
        alert('Failed to add forum');
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
