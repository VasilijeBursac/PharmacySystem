import Vue from "vue";
import axios from "axios";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import Notifications from 'vue-notification';
import velocity      from 'velocity-animate'

Vue.use(Notifications, { velocity })

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)

Vue.prototype.$http = axios
Vue.config.productionTip = false;


Vue.filter('formatRating', function (items) {
	items.forEach((item, index) => {
		items[index] = {...items[index], rating: item.rating == 0 ? 'Nema ocenu' : item.rating}
	})

	return items
})

Vue.filter('formatPrice', function (items) {
	items.forEach((item, index) => {
		let formatedPrice = item.price == -1 ? "Nije uneta" : item.price + " din"
		items[index] = {...items[index], price: formatedPrice}
	})

	return items
})


new Vue({
	router,
	store,
	render: h => h(App),
	created(){
		var userId = localStorage.getItem("UserId") 
		
		if( userId == undefined ){
			localStorage.setItem("UserRole","ROLE_UNREGISTERED")
			localStorage.removeItem("UserId")
			localStorage.removeItem("JWT")
		}

		// axios.defaults.baseURL = 'https://team54-pharmacy-backend.herokuapp.com/';
		axios.defaults.baseURL = 'http://localhost:9001/';
		axios.defaults.headers.common['Authorization'] = 'Bearer ' + this.$store.getters.getJWT;

		
	}
}).$mount("#app");
