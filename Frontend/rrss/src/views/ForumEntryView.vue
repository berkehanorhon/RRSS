<template>
    <div class="forum-entry">
      <div class="entry-box">
        <h1>{{ entry.title }}</h1>
        <div class="entry-details">
          <p><strong>User ID:</strong> {{ entry.userId }}</p>
          <p>{{ entry.text }}</p>
          <p class="entry-date">{{ entry.date }}</p>
          <button @click="likeEntry">Like ({{ entry.likes }})</button>
        </div>
      </div>
      <div class="replies">
        <h2>Replies</h2>
        <div v-for="reply in entry.replies" :key="reply.id" class="reply-box">
          <p><strong>User ID:</strong> {{ reply.userId }}</p>
          <p>{{ reply.text }}</p>
          <p class="reply-date">{{ reply.date }}</p>
        </div>
        
        <form @submit.prevent="submitReply">
          <h3>Add a Reply</h3>
          <FormComponent
            :title="'Reply to ' + entry.title"
            :buttonLabel="'Submit Reply'"
            :inputs="replyInputs"
            :submitForm="submitReplyForm"
          />
        </form>
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
        userId: 'YourUserId', 
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
        ]
      };
    },
    created() {
      this.fetchEntry();
    },
    methods: {
      fetchEntry() {
        this.entry = this.entriesData[this.categoryId].find(entry => entry.id === parseInt(this.entryId));
      },
      likeEntry() {
        this.entry.likes += 1;
      },
      submitReplyForm(formData) {
        const reply = {
          id: this.entry.replies.length + 1, 
          userId: this.userId, 
          text: formData.replyText,
          date: new Date().toISOString() 
        };
        this.entry.replies.push(reply); 
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
  