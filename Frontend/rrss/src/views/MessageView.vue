<template>
  <div class="message-view">
    <h1>{{ user1.name }} and {{ user2.name }}'s Conversation</h1>
    <div class="chat-container">
      <MessageComponent
        v-for="(message, index) in messages"
        :key="index"
        :message="message"
        :currentUser="currentUser"
      />
    </div>
    <div class="input-container">
      <input
        type="text"
        v-model="newMessage"
        @keydown.enter="sendMessage"
        placeholder="Type a message..."
      />
      <button @click="sendMessage">Send</button>
    </div>
    <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
  </div>
</template>

<script>
import MessageComponent from "../components/MessageComponent.vue";
import axios from "axios";

export default {
  name: "MessageView",
  components: {
    MessageComponent,
  },
  props: {
    userId: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      currentUser: "",
      newMessage: "",
      messages: [],
      errorMessage: "",
      user1: { id: null, name: "" },
      user2: { id: null, name: "" },
    };
  },
  created() {
    this.loadMessages();
  },
  methods: {
    async loadMessages() {
      try {
        const response = await axios.post(`http://localhost:8080/get-conversation-with-user?userId=${this.userId}`);
        const data = response.data;
        
        const users = data.users;
        this.user1.id = parseInt(this.userId);
        this.user1.name = users[this.userId];
        this.user2.id = this.user1.id === 1 ? 2 : 1; // Assuming the current user is always "John" with id 1
        this.user2.name = users[this.user2.id.toString()];
        this.currentUser = this.user2.name;

        const rawMessages = data.messages || [];

        const formattedMessages = rawMessages.map((msg) => ({
          content: msg.message,
          sender: msg.senderId === this.user2.id ? this.user2.name : this.user1.name,
          sentDate: msg.sentDate,
        }));

        this.messages = formattedMessages;
      } catch (error) {
        console.error("Error loading messages:", error);
      }
    },
    async sendMessage() {
      if (this.newMessage.trim() !== "") {
        try {
          const response = await axios.post("http://localhost:8080/send-message", {
            receiverId: this.user1.id,
            message: this.newMessage
          });

          if (response.status === 200) {
            this.messages.push({
              content: this.newMessage,
              sender: this.currentUser,
              sentDate: new Date().toISOString(),
            });
            this.newMessage = "";
            this.errorMessage = "";
          } else {
            this.errorMessage = "Error sending message.";
          }
        } catch (error) {
          this.errorMessage = "Error sending message.";
        }
      }
    },
  },
};
</script>

<style scoped>
.message-view {
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.chat-container {
  height: calc(100vh - 150px);
  overflow-y: auto;
  padding-bottom: 10px;
}

.input-container {
  display: flex;
  margin-top: 10px;
}

.input-container input {
  flex-grow: 1;
  padding: 10px;
  font-size: 16px;
  box-sizing: border-box;
}

.input-container button {
  padding: 10px;
  font-size: 16px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  box-sizing: border-box;
}

.input-container button:hover {
  background-color: #0056b3;
}

.error-message {
  color: red;
  margin-top: 10px;
}
</style>
