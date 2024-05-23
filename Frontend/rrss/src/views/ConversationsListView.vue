<template>
    <div class="conversations-list">
      <h1>Conversations</h1>
      <ul>
        <router-link v-for="(conversation, index) in conversations" :key="index" :to="'/messenger/' + conversation.id">
          <li>
            <div>
              <p>{{ conversation.username }}</p>
              <p>Last Message: {{ conversation.last_message }}</p>
              <p>Sent Time: {{ conversation.sent_time }}</p>
            </div>
          </li>
        </router-link>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    name: 'ConversationsListView',
    data() {
      return {
        conversations: [
          {
            id: 1,
            username: 'user1',
            last_message: 'hey bro how u doin?',
            sent_time: '19.05.2024 15:28'
          },
          {
            id: 2,
            username: 'user2',
            last_message: 'Just finished my work, you?',
            sent_time: '19.05.2024 15:30'
          },
          {
            id: 3,
            username: 'user3',
            last_message: 'Getting ready for the meeting.',
            sent_time: '19.05.2024 15:35'
          },
          {
            id: 4,
            username: 'user4',
            last_message: 'Can we talk later?',
            sent_time: '19.05.2024 15:40'
          },
          {
            id: 5,
            username: 'user5',
            last_message: 'Sure, no problem.',
            sent_time: '19.05.2024 15:45'
          }
        ]
      };
    }
  };
  </script>
  
  <style scoped>
  .conversations-list {
    margin: 20px;
  }
  
  ul {
    list-style-type: none;
    padding: 0;
  }
  
  li {
    margin-bottom: 20px;
    border: 1px solid #ccc;
    padding: 10px;
  }
  
  li:hover {
    background-color: #f0f0f0;
  }
  </style>
  