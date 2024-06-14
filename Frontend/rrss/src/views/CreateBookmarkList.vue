<template>
  <div class="container">
    <FormComponent
      title="Bookmark List"
      buttonLabel="Create List"
      :inputs="inputs"
      :submitForm="addBookmarkList"
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
        { id: 'title', label: 'Title', type: 'text' , text: ''},
      ]
    };
  },
  methods: {
    async addBookmarkList(formData) {
      try {
        const response = await axios.post('http://127.0.0.1:8080/bookmark/create', {
          title: formData.title
        });

        if (response.status === 201) {
          alert('Bookmark list created successfully');
          this.$router.push('/');
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