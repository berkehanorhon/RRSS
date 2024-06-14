<template>
  <div class="forum-entry">
    <div class="entry-box">
      <h1>{{ entry.title }}</h1>
      <div class="entry-details">
        <p><strong>User ID:</strong> {{ entry.userId }}</p>
        <p>{{ entry.text }}</p>
        <p class="entry-date">{{ entry.date }}</p>
        <div class="like-section">
          <button v-if="isLoggedIn" @click="toggleLike">
            {{ liked ? 'Dislike' : 'Like' }}
          </button>
          <span class="like-count">({{ entry.likes }})</span>
        </div>
      </div>
    </div>
    <div class="replies">
      <h2>Replies</h2>
      <div v-for="reply in entry.replies" :key="reply.id" class="reply-box">
        <p><strong>User ID:</strong> {{ reply.userId }}</p>
        <p>{{ reply.text }}</p>
        <p class="reply-date">{{ reply.date }}</p>
      </div>
      
      <form v-if="isLoggedIn" @submit.prevent="submitReplyForm">
        <h3>Add a Reply</h3>
        <FormComponent
          :title="'Reply to ' + entry.title"
          :buttonLabel="'Submit Reply'"
          :inputs="replyInputs"
          :submitForm="handleSubmit"
        />
      </form>
      <p v-else>Please log in to add a reply.</p>
    </div>
  </div>
</template>

<script>
import FormComponent from '@/components/FormComponent.vue';

export default {
  name: 'ForumEntryView',
  components: {
    FormComponent
  },
  props: {
    categoryId: {
      type: String,
      required: true
    },
    entryId: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      userId: localStorage.getItem('userId') || null,
      isLoggedIn: !!localStorage.getItem('token'),
      liked: false,
      entriesData: {
        1: [
          {
            id: 1,
            userId: 'User1',
            title: 'Entry 1 in Category 1',
            text: 'Text for entry 1 in category 1',
            date: '2024-05-20',
            likes: 0,
            replies: [
              { id: 1, userId: 'User2', text: 'Reply 1 to entry 1', date: '2024-05-20' }
            ]
          },
          {
            id: 2,
            userId: 'User3',
            title: 'Entry 2 in Category 1',
            text: 'Text for entry 2 in category 1',
            date: '2024-05-21',
            likes: 0,
            replies: [
              { id: 2, userId: 'User4', text: 'Reply 1 to entry 2', date: '2024-05-21' }
            ]
          }
        ],
        2: [
          {
            id: 3,
            userId: 'User5',
            title: 'Entry 1 in Category 2',
            text: 'Text for entry 1 in category 2',
            date: '2024-05-22',
            likes: 0,
            replies: [
              { id: 3, userId: 'User6', text: 'Reply 1 to entry 1', date: '2024-05-22' }
            ]
          },
          {
            id: 4,
            userId: 'User7',
            title: 'Entry 2 in Category 2',
            text: 'Text for entry 2 in category 2',
            date: '2024-05-23',
            likes: 0,
            replies: [
              { id: 4, userId: 'User8', text: 'Reply 1 to entry 2', date: '2024-05-23' }
            ]
          }
        ]
      },
      entry: {},
      replyInputs: [
        { type: 'textarea', id: 'replyText', label: 'Reply Text:', required: true }
      ],
      replyFormData: {
        replyText: ''
      }
    };
  },
  created() {
    this.fetchEntry();
  },
  methods: {
    fetchEntry() {
      this.entry = this.entriesData[this.categoryId].find(entry => entry.id === parseInt(this.entryId));
      const likedEntries = JSON.parse(localStorage.getItem('likedEntries') || '[]');
      this.liked = likedEntries.includes(this.entry.id);
    },
    toggleLike() {
      const likedEntries = JSON.parse(localStorage.getItem('likedEntries') || '[]');
      if (this.liked) {
        this.entry.likes -= 1;
        this.liked = false;
        const index = likedEntries.indexOf(this.entry.id);
        if (index > -1) likedEntries.splice(index, 1);
      } else {
        this.entry.likes += 1;
        this.liked = true;
        likedEntries.push(this.entry.id);
      }
      localStorage.setItem('likedEntries', JSON.stringify(likedEntries));
    },
    handleSubmit(formData) {
      const reply = {
        id: this.entry.replies.length + 1,
        userId: this.userId,
        text: formData.replyText,
        date: new Date().toISOString()
      };
      this.entry.replies.push(reply);
      this.replyFormData.replyText = '';
    },
    submitReplyForm() {
      this.handleSubmit(this.replyFormData);
    }
  }
};
</script>

<style scoped>
.forum-entry {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
  background-color: #f5f5f5;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.entry-box {
  padding: 20px;
  margin-bottom: 20px;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.entry-details {
  margin-top: 10px;
}

.entry-date,
.reply-date {
  color: #999;
  font-size: 0.9em;
}

.like-section {
  display: flex;
  align-items: center;
}

.like-section button {
  margin-right: 10px;
}

.replies {
  margin-top: 30px;
}

.reply-box {
  padding: 10px;
  margin-top: 10px;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 5px;
}
</style>
