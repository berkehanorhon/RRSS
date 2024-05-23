<template>
  <div class="conversations-list">
    <h1>Conversations</h1>
    <ul>
      <router-link v-for="(conversation, index) in conversations" :key="index" :to="'/messenger/' + conversation.id">
        <li>
          <div class="conversation-container">
            <img class="user-image" :src="defaultImage" alt="User Image">
            <div class="message-info">
              <p class="username"><strong>{{ conversation.username }}</strong></p>
              <p class="message">Last Message: {{ conversation.last_message }}</p>
            </div>
            <p class="sent-time">Sent Time: {{ conversation.sent_time }}</p>
          </div>
        </li>
      </router-link>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'ConversationsListView',
  data() {
    return {
      defaultImage: require('@/assets/logo.png'),
      conversations: []
    };
  },
  created() {
    this.fetchConversations();
  },
  methods: {
    fetchConversations() {
      axios.get('http://localhost:8080/get-message-box')
        .then(response => {
          const dataArray = response.data;
          dataArray.forEach(data => {
            for (let key in data) {
              this.conversations.push({
                id: key,
                username: data[key].username,
                last_message: data[key].lastMessage,
                sent_time: data[key].lastMessageTime
              });
            }
          });
        })
        .catch(error => {
          console.error(error);
        });
    }
  }
};
</script>
  
  <style scoped>
  .conversations-list {
    margin: 20px;
    font-family: Arial, sans-serif;
    max-width: 60%;
    margin: 0 auto;
  }
  
  h1 {
    color: #333;
    font-size: 24px;
    margin-bottom: 20px;
  }
  
  ul {
    list-style-type: none;
    padding: 0;
  }
  
  li {
    margin-bottom: 20px;
    border: 1px solid #ccc;
    padding: 10px;
    border-radius: 5px;
    box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24); 
    transition: all 0.3s cubic-bezier(.25,.8,.25,1); 
    background-color: #f9f9f9; 
  }
  
  li:hover {
    box-shadow: 0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23);
    background-color: #e9e9e9;
  }
  
  p {
    margin: 0 0 10px 0;
    color: #333;
  }
  
  p:last-child {
    margin-bottom: 0;
  }
  .username {
  font-weight: bold;
  font-size: 30px; /* Adjust this value as needed */
}
  </style>