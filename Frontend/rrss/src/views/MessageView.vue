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
  </div>
</template>

<script>
import MessageComponent from "../components/MessageComponent.vue";

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
      currentUser: "John", 
      newMessage: "",
      messages: [],
      user1: { id: 1, name: "John" },
      user2: { id: null, name: "User" }, 
      allMessages: {
        "1": [
          {
            senderId: 2,
            receiverId: 1,
            sentDate: "2024-05-19T19:33:47.270+00:00",
            message: "Selamlar Efenim6",
          },
          {
            senderId: 2,
            receiverId: 1,
            sentDate: "2024-05-19T19:34:01.520+00:00",
            message: "Selamlar Efenim6"
          }
        ],
        "3": [
          {
            senderId: 3,
            receiverId: 1,
            sentDate: "2024-05-19T19:33:47.270+00:00",
            message: "Selamlar Efenim6"
          },
          {
            senderId: 1,
            receiverId: 3,
            sentDate: "2024-05-19T19:33:47.270+00:00",
            message: "Selamlar Efenfsddfsfsdim6"
          },
          {
            senderId: 3,
            receiverId: 1,
            sentDate: "2024-05-19T19:34:01.520+00:00",
            message: "Selamlar Efenim6"
          }
        ]
      }
    };
  },
  created() {
    this.setUser2();
    this.loadMessages();
  },
  methods: {
    setUser2() {
      this.user2.id = parseInt(this.userId);
      this.user2.name = this.userId === "2" ? "Jane" : "Unknown User";
    },
    loadMessages() {
      const rawMessages = this.allMessages[this.userId] || [];

      const formattedMessages = rawMessages.map((msg) => ({
        content: msg.message,
        sender: msg.senderId === this.user1.id ? this.user1.name : this.user2.name,
        sentDate: msg.sentDate,
      }));

      this.messages = formattedMessages;
    },
    sendMessage() {
      if (this.newMessage.trim() !== "") {
        this.messages.push({
          content: this.newMessage,
          sender: this.currentUser,
          sentDate: new Date().toISOString(),
        });
        this.newMessage = "";
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
  display: block;
  margin-top: 10px;
}

.input-container input {
  width: calc(100% - 100px); 
  padding: 10px;
  font-size: 16px;
  box-sizing: border-box; 
}

.input-container button {
  width: 100px;
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
</style>
