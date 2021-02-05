import Vue from "vue";
import axios from "axios";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)

Vue.prototype.$http = axios
Vue.config.productionTip = false;

new Vue({
	router,
	store,
	render: h => h(App),
	created(){
		axios.defaults.baseURL = 'http://localhost:9001';
		axios.defaults.headers.common['Authorization'] = 'Bearer ' + this.$store.getters.getJWT;
	}
}).$mount("#app");
