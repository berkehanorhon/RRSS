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
  data() {
    return {
      currentUser: "John", 
      newMessage: "",
      messages: [],
      user1: { id: 1, name: "John" },
      user2: { id: 2, name: "Jane" }, 
    };
  },
  created() {
    
    const rawMessages = [
      {
        receiverUserId: 2,
        messages: [
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
            message: "Selamlar Efenim6",
          },
          {
            senderId: 2,
            receiverId: 1,
            sentDate: "2024-05-19T19:34:48.349+00:00",
            message: "Selamlar Efenim666",
          },
          {
            senderId: 1,
            receiverId: 2,
            sentDate: "2024-05-19T19:35:48.349+00:00",
            message: "test ilk",
          },
          {
            senderId: 2,
            receiverId: 1,
            sentDate: "2024-05-19T20:07:28.318+00:00",
            message: "Selamlar Efenim666",
          },
          {
            senderId: 2,
            receiverId: 1,
            sentDate: "2024-05-20T15:24:34.521+00:00",
            message: "As efenim",
          },
          {
            senderId: 2,
            receiverId: 1,
            sentDate: "2024-05-20T15:24:46.156+00:00",
            message: "As efenim2",
          },
          {
            senderId: 1,
            receiverId: 2,
            sentDate: "2024-05-20T15:25:00.427+00:00",
            message: "Test son",
          },
        ],
      },
    ];

    
    const formattedMessages = rawMessages[0].messages.map((msg) => ({
      content: msg.message,
      sender: msg.senderId === this.user1.id ? this.user1.name : this.user2.name,
      sentDate: msg.sentDate,
    }));

    this.messages = formattedMessages;
  },
  methods: {
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
  display: flex;
  flex-direction: column;
  height: 100vh;
  padding: 20px;
  align-items: center; 
}

.chat-container {
  flex-grow: 1;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 600px; 
}

.input-container {
  display: flex;
  align-items: center;
  width: 100%;
  max-width: 600px; 
}

.input-container input {
  flex: 1;
  padding: 10px;
  font-size: 16px;
}

.input-container button {
  padding: 10px 20px;
  font-size: 16px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.input-container button:hover {
  background-color: #0056b3;
}
</style>
