<template>    
    <ul class="nav navbar-nav">
        <li class="nav-item active"><a href="#" class="nav-link"><router-link to = "/" exact>Početna</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to = "/pharmacies" >Apoteke</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="/drugs">Lekovi</router-link></a></li>

        <button class="btn btn-success ml-3" v-on:click="showLoginDialog()">
            Pristup sistemu
        </button>
    </ul>
</template>

<script>
export default {
    methods: {
        showLoginDialog: function(){       
            this.$router.push('/registration');
        }
    }
}
</script>


<style scoped>
    .nav>li {
        margin-left: 0.5rem;
    }
</style>