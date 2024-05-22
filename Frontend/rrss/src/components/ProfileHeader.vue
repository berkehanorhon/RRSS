<template>
  <div class="profile-header">
    <div class="user-info">
      <img :src="user.photo" alt="User Photo" class="user-photo" />
      <div class="user-details">
        <h2>{{ user.username }}</h2>
        <p>Email: {{ user.email }}</p>
        <p>First Name: {{ user.firstName }}</p>
        <p>Last Name: {{ user.lastName }}</p>
        <button @click="sendMessage" class="action-button">Send Message</button>
        <router-link :to="`/profile/${this.$route.params.userId}/edit`" class="action-button">Edit Profile</router-link>
        <router-link :to="`/createbookmarklist`" class="action-button">Create Bookmark List</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    user: {
      type: Object,
      required: true,
    },
  },
  methods: {
    sendMessage() {
      // Implement send message functionality here
      alert('Message sent!');
    },
  },
};
</script>

<style scoped>
.profile-header {
  display: flex;
  justify-content: center;
  align-items: center;
}

.actions {
  display: flex;
}

.user-info {
  display: flex;
  align-items: center;
  margin-left: 20px;
}

.user-photo {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  margin-right: 20px;
}

.user-details {
  text-align: center;
}

.action-button {
  padding: 10px 20px;
  margin-right: 10px;
  border-radius: 5px;
  text-decoration: none;
  color: #fff;
  background-color: #2196f3;
}
</style>