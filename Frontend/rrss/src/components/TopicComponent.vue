<template>
    <div class="topic" @click="handleClick">
      <img src="@/assets/profile_icon.png" alt="Forum Icon" class="topic-icon" />
      <div class="topic-content">
        <h2>{{ topic.title }}</h2>
        <p>{{ topic.content }}</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'TopicComponent',
    props: {
      topic: {
        type: Object,
        required: true
      }
    },
    methods: {
      handleClick() {
        this.$emit('view-topic', this.topic);
      }
    }
  };
  </script>
  
  <style scoped>
  .topic {
    display: flex;
    align-items: flex-start;
    padding: 15px;
    border: 1px solid #ccc;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
    gap: 15px;
    background-color: #fff;
  }
  
  .topic:hover {
    background-color: #bebcbc;
  }
  
  .topic-icon {
    width: 50px;
    height: 50px;
  }
  
  .topic-content {
    flex-grow: 1;
  }
  
  .topic h2 {
    margin: 0;
    font-size: 1.2em;
  }
  
  .topic p {
    margin: 5px 0 0 0;
  }
  </style>
  