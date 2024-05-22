<template>
  <div class="home-container">
    <sidebar></sidebar>
    <product-timeline></product-timeline>
  </div>
</template>

<script>
import ProductTimeline from '../components/ProductTimeline.vue';
import CategoryBar from '../components/CategoryBar.vue';

export default {
  name: 'HomeView',
  components: {
    'sidebar': CategoryBar,
    'product-timeline': ProductTimeline
  }
}
</script>

<style scoped>
.home-container {
  display: flex;
  width: 100%;
  height: calc(100vh - 60px); /* Navbar'ın yüksekliği çıkarıldı */
  border: 2px solid rgb(105, 102, 102);
}

.sidebar {
  flex: 0 0 160px;
  padding: 20px;
  overflow-y: auto;
  margin-right: 2px; /* Sağdan 20px boşluk ekler */
}

.product-timeline {
  flex: 1; /* Kalan tüm alanı kaplar */
  display: flex;
  flex-direction: column;
  align-items: center; /* Çocuk elemanları yatay olarak ortalar */
  background-color: #ffffff;
  padding: 20px;
  overflow-y: auto; /* İçerik taşarsa kaydırmayı etkinleştir */
}
</style>
