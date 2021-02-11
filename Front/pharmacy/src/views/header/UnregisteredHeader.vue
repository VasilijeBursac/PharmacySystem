<template>    
    <ul class="nav navbar-nav">
        <li class="nav-item active"><a href="#" class="nav-link">Početna</a></li>
        <li class="nav-item"><a href="#" class="nav-link">Pretraga apoteka</a></li>
        <li class="nav-item"><a href="#" class="nav-link">Pretraga proizvoda</a></li>
        <button class="btn btn-success" v-on:click="showLoginDialog()">Pristup sistemu</button>
    </ul>
</template>

<script>
export default {
   methods: {
        showLoginDialog: function(){       
            this.$router.push('Registration');
        }
    }
}
</script>


<style scoped>
    .nav>li {
        margin-left: 0.5rem;
    }
</style>