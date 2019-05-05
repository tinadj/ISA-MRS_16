import Vue from 'vue'
import App from './App.vue'
import router from './router'

import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)

// Ikonice
import { library } from '@fortawesome/fontawesome-svg-core'
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

require('../src/styles/global.css')
library.add(faUserSecret)
Vue.component('font-awesome-icon', FontAwesomeIcon)

// Mape
import YmapPlugin from 'vue-yandex-maps'

const options = { // podesavanje jezika
  lang: 'en_US',
  version: '2.1'
}

Vue.use(YmapPlugin, options)

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
