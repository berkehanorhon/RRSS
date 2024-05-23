<template>
  <div class="profile-header">
    <div class="user-info">
      <img :src="user.photo" alt="User Photo" class="user-photo" />
      <div class="user-details">
        <h2>
          {{ user.username }}
          <span v-if="user.isAdmin" class="admin-label">(Admin)</span>
          <span v-else-if="user.isModerator" class="moderator-label">(Moderator)</span>
          <span v-else-if="user.is_merchant" class="merchant-label">(Merchant)</span>
        </h2>
        <p>Email: {{ user.email }}</p>
        <p>First Name: {{ user.firstName }}</p>
        <p>Last Name: {{ user.lastName }}</p>
        <div class="actions">
          <router-link v-if="this.$route.params.userId != currentUserId" :to="`/messenger/${this.$route.params.userId}`" class="action-button">Send Message</router-link>
          <router-link v-if="this.$route.params.userId == currentUserId" to="/coupons" class="action-button">My Coupons</router-link>
          <router-link v-if="this.$route.params.userId == currentUserId & user.isAdmin" to="/admin-panel" class="action-button">Admin Panel</router-link>
          <router-link v-if="this.$route.params.userId == currentUserId" :to="`/profile/${this.$route.params.userId}/edit`" class="action-button">Edit Profile</router-link>
          <router-link v-if="this.$route.params.userId == currentUserId" :to="`/createbookmarklist`" class="action-button">Create Bookmark List</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  computed: {
    currentUserId() {
      return localStorage.getItem('userId');
    }
  },
  props: {
    user: {
      type: Object,
      required: true,
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
  margin-top: 10px;
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

.admin-label {
  color: red;
}

.moderator-label {
  color: blue;
}

.merchant-label {
  color: green;
}
</style>