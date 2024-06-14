<template>
    <font-awesome-icon :icon="['fas', 'star']" :class="{ 'fas': filled, 'far': !filled }" />
  </template>
  
  <script>
  import { library } from '@fortawesome/fontawesome-svg-core'
  import { faStar as fasFaStar, faStar as farFaStar } from '@fortawesome/free-solid-svg-icons'
  import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
  
  library.add(fasFaStar, farFaStar)
  
  export default {
    name: 'StarRating',
    components: {
      FontAwesomeIcon
    },
    props: {
      filled: {
        type: Boolean,
        default: false
      }
    }
  };
  </script>
  
  <style scoped>
  .fas {
    color: gold;
  }
  
  .far {
    color: #ccc;
  }
  </style>