<template>
  <form @submit.prevent="submit">
    <h1>{{ title }}</h1>
    <div v-for="(input, index) in inputs" :key="index">
      <label v-if="input.type !== 'checkbox'" :for="input.id" :style="labelStyle">{{ input.label }}</label>
      <input :type="input.type" :id="input.id" :style="input.type === 'checkbox' ? checkboxStyle : inputStyle" v-model="formData[input.id]" :required="input.type !== 'checkbox'">
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

