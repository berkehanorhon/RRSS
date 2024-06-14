<template>
    <div class="forum-category-entries">
      <h1>{{ categoryName }}</h1>
      <div class="entries">
        <EntryComponent
          v-for="entry in entries"
          :key="entry.id"
          :entry="entry"
          @view-entry="viewEntry"
        />
      </div>
    </div>
  </template>
  
  <script>
  import EntryComponent from '@/components/EntryComponent.vue';
  
  export default {
    name: 'ForumCategoryEntriesView',
    components: {
      EntryComponent
    },
    props: {
      categoryId: {
        type: String,
        required: true
      }
    },
    data() {
      return {
        categories: {
          1: 'Category 1',
          2: 'Category 2'
        },
        entriesData: {
          1: [
            { id: 1, title: 'Entry 1 in Category 1', text: 'Text for entry 1 in category 1', date: '2024-05-20' },
            { id: 2, title: 'Entry 2 in Category 1', text: 'Text for entry 2 in category 1', date: '2024-05-21' }
          ],
          2: [
            { id: 3, title: 'Entry 1 in Category 2', text: 'Text for entry 1 in category 2', date: '2024-05-22' },
            { id: 4, title: 'Entry 2 in Category 2', text: 'Text for entry 2 in category 2', date: '2024-05-23' }
          ]
        },
        categoryName: '',
        entries: []
      };
    },
    created() {
      this.fetchEntries();
    },
    methods: {
      fetchEntries() {
        this.categoryName = this.categories[this.categoryId];
        this.entries = this.entriesData[this.categoryId];
      },
      viewEntry(entryId) {
        this.$router.push(`/forum/categories/${this.categoryId}/${entryId}`);
      }
    }
  };
  </script>
  
  <style scoped>
  .entries {
    display: flex;
    flex-direction: column;
    gap: 20px;
    max-width: 800px;
    width: 100%;
    margin: 0 auto;
  }
  </style>
  