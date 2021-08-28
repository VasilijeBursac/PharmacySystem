import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import Notifications from 'vue-notification';
import velocity      from 'velocity-animate'

Vue.use(Vuex);
Vue.use(Notifications, { velocity })
Vue.prototype.$axios = axios


export default new Vuex.Store({
	state: {
		userRole: localStorage.getItem("UserRole"),
		userId: localStorage.getItem("UserId"),
		JWT: localStorage.getItem("JWT"),

		myPharmacyId: null
	},

	mutations: {
		setUserRole(state,newRole) {
			state.userRole = newRole;
		},
		setUserId(state,newUserId) {
			state.userId = newUserId;
		},
		setJWT(state,newJWT) {
			state.JWT = newJWT;
			axios.defaults.headers.common['Authorization'] = 'Bearer ' + newJWT;
		},

		SET_MY_PHARMACY_ID(state, newPharmacyId) {
			state.myPharmacyId = newPharmacyId;
		}
	},
	actions: {
		setMyPharmacyId({commit}, myPharmacyId) {
			commit("SET_MY_PHARMACY_ID", myPharmacyId)
		}
	},
	modules: {},
	getters:{
		getUserRole: state => state.userRole,
		getUserId: state => state.userId,
		getJWT: state => state.JWT,

		getMyPharmacyId: state => state.myPharmacyId
	}
});
