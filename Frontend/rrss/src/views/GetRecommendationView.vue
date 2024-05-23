<template>
    <div class="home-container">
      <sidebar></sidebar>
      <product-timeline></product-timeline>
    </div>
  </template>
  
  <script>
  import ProductTimeline from '../components/ProductTimelineRecommendation.vue';
  
  export default {
    name: 'HomeView',
    components: {
      'product-timeline': ProductTimeline
    }
  }
  </script>
  
  <style scoped>
  .home-container {
    display: flex;
    width: 100%;
    height: calc(100vh - 60px);
    border: 2px solid rgb(105, 102, 102);
  }
  
  .sidebar {
    flex: 0 0 160px;
    padding: 20px;
    overflow-y: auto;
    margin-right: 2px;
  }
  
  .product-timeline {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: #ffffff;
    padding: 20px;
    overflow-y: auto;
  }
  </style>
  