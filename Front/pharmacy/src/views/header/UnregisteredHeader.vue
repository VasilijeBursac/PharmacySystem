<template>    
    <ul class="nav navbar-nav">
        <li class="nav-item active"><a href="#" class="nav-link"><router-link to = "/" exact>Početna</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to = "/pharmacies" >Pretraga apoteka</router-link></a></li>
                <li class="nav-item"><a href="#" class="nav-link"><router-link to="/all-drugs">Pretraga lekova</router-link></a></li>
        <button class="btn btn-success" v-on:click="showLoginDialog()">Pristup sistemu</button>
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