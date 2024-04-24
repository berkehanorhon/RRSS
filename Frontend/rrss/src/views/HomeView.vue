<template>
  <div class="home-container">
    <sidebar></sidebar>
    <product-timeline></product-timeline>
  </div>
</template>

<script>
import ProductTimeline from '../components/ProductTimeline.vue';
import Sidebar from '../components/SideBar.vue';

export default {
  name: 'HomeView',
  components: {
    'sidebar': Sidebar,
    'product-timeline': ProductTimeline
  }
}
</script>

<style scoped>
.home-container {
  display: flex;
  width: 100%;
  height: calc(100vh - 60px); /* Navbar'ın yüksekliği çıkarıldı */
  margin-top: 60px; /* Navbar altına yerleştir */
}

.sidebar {
  flex: 0 0 160px; /* Sidebar için sabit genişlik ayarlanmıştır */
  background-color: rgb(227, 230, 142);
  padding: 20px;
  overflow-y: auto; /* Uzun sidebar içeriği için kaydırma çubuğu */
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
