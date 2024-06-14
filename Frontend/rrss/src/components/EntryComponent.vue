<template>
    <div class="entry" @click="handleClick">
      <div class="entry-header">
        <span class="entry-title">{{ entry.title }}</span>
        <span class="entry-date">{{ entry.date }}</span>
      </div>
      <div class="entry-content">
        <p>{{ entry.text }}</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'EntryComponent',
    props: {
      entry: {
        type: Object,
        required: true
      }
    },
    methods: {
      handleClick() {
        this.$emit('view-entry', this.entry.id);
      }
    }
  };
  </script>
  
  <style scoped>
  .entry {
    padding: 15px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #fff;
    margin-bottom: 10px;
    cursor: pointer;
  }
  
  .entry-header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }
  
  .entry-title {
    font-weight: bold;
  }
  
  .entry-date {
    color: #999;
  }
  
  .entry-content p {
    margin: 0;
  }
  </style>
  