<template>
	<div id="app">
		<Header />
		<router-view :key="$route.fullPath" />
	</div>
</template>

<script>
import Header from "@/views/header/Header.vue";

export default {
	name: "App",
	components: {
		Header
	}
};
</script>

<style>
#app {
	font-family: 'Segoe UI', Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-align: center;
	color: #2c3e50;
}
</style>
