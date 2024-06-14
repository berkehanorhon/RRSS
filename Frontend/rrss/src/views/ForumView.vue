<template>
    <div class="forum">
      <h1>Forum</h1>
      <div class="topics-container">
        <div class="topics">
          <TopicComponent
            v-for="topic in topics"
            :key="topic.id"
            :topic="topic"
            @view-topic="viewTopic"
          />
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import TopicComponent from '@/components/TopicComponent.vue';
  
  export default {
    name: 'ForumView',
    components: {
      TopicComponent
    },
    data() {
      return {
        topics: [
          { id: 1, title: 'First Topic', content: 'This is the first topic content' },
          { id: 2, title: 'Second Topic', content: 'This is the second topic content' },
          
        ]
      };
    },
    methods: {
      viewTopic(topic) {
        console.log('Viewing topic:', topic);
      }
    }
  };
  </script>
  
  <style scoped>
  .forum {
    padding: 20px;
    text-align: center;
  }
  
  .topics-container {
    display: flex;
    justify-content: center;
  }
  
  .topics {
    display: flex;
    flex-direction: column;
    gap: 20px;
    max-width: 800px;
    width: 100%;
  }
  </style>
  