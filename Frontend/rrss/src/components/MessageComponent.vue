<template>
  <div
    class="message-wrapper"
    :class="{ 'sent': sentByCurrentUser, 'received': !sentByCurrentUser }"
  >
    <div class="message">
      <p>{{ message.content }}</p>
      <span class="sender" v-if="!sentByCurrentUser">{{ message.sender }}</span>
      <span class="date">{{ formattedDate }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "MessageComponent",
  props: {
    message: Object,
    currentUser: String,
  },
  computed: {
    sentByCurrentUser() {
      return this.message.sender === this.currentUser;
    },
    formattedDate() {
      const date = new Date(this.message.sentDate);
      return date.toLocaleString('en-GB', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
      });
    }
  }
};
</script>

<style scoped>
.message-wrapper {
  display: flex;
  margin-bottom: 10px;
}

.sent {
  justify-content: flex-end;
}

.received {
  justify-content: flex-start;
}

.message {
  padding: 10px;
  max-width: 70%;
  word-wrap: break-word;
  border-radius: 10px;
  position: relative;
}

.sent .message {
  background-color: #dcf8c6;
  align-self: flex-end;
}

.received .message {
  background-color: #e0e0e0;
  align-self: flex-start;
}

.sender {
  font-size: 0.8em;
  color: #666;
  margin-top: 5px;
}

.date {
  font-size: 0.8em;
  color: #999;
  margin-top: 5px;
  display: block;
}
</style>
