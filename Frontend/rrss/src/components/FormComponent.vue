<template>
  <form @submit.prevent="submit">
    <h1>{{ title }}</h1>
    <div v-for="(input, index) in inputs" :key="index">
      <label v-if="input.type !== 'checkbox'" :for="input.id" :style="labelStyle">{{ input.label }}</label>
      <input v-if="input.type !== 'select' && input.type !== 'checkbox' && input.type !== 'textarea'" :type="input.type" :id="input.id" :style="inputStyle" v-model="formData[input.id]" :required="input.type !== 'checkbox'" :placeholder="input.text">
      <select v-else-if="input.type === 'select'" :id="input.id" :style="inputStyle" v-model="formData[input.id]">
        <option v-for="(option, index) in input.options" :key="index" :value="option.value">{{ option.text }}</option>
      </select>
      <textarea v-else-if="input.type === 'textarea'" :id="input.id" :style="inputStyle" v-model="formData[input.id]" :required="true" :placeholder="input.text"></textarea>
      <input v-else-if="input.type === 'checkbox'" :type="input.type" :id="input.id" :style="checkboxStyle" v-model="formData[input.id]">
      <label v-if="input.type === 'checkbox'" :for="input.id" :style="checkboxlabelStyle">{{ input.label }}</label>
    </div>
    <div>
      <button class="form-submit-button" type="submit">{{ buttonLabel }}</button>
    </div>
  </form>
</template>

<script>
export default {
  computed: {
    labelStyle() {
      return {
        display: 'block',
        marginBottom: '5px'
      };
    },
    checkboxlabelStyle() {
      return {
        marginLeft: '5px'
      };
    },
    inputStyle() {
      return {
        padding: '5px',
        marginBottom: '10px'
      };
    },
    checkboxStyle() {
      return {
        padding: '5px',
        marginBottom: '15px',
      };
    },
  },
  props: {
    title: {
      type: String,
      required: true
    },
    buttonLabel: {
      type: String,
      required: true
    },
    inputs: {
      type: Array,
      required: true
    },
    submitForm: {
      type: Function,
      required: true
    }
  },
  data() {
    return {
      formData: {}
    };
  },
  methods: {
    async submit() {
      this.submitForm(this.formData);
    }
  }
};
</script>

