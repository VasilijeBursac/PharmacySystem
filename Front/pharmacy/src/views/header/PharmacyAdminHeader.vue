<template>    
    <ul class="nav navbar-nav">      
        <li class="nav-item active"><a href="#" class="nav-link"><router-link to="/pharmacies">Profil apoteke</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link">Lekovi</a></li>
        <li class="nav-item"><a href="#" class="nav-link">Narudžbenice</a></li>
        <li class="nav-item"><a href="#" class="nav-link">Akcije</a></li>
        <li class="nav-item"><a href="#" class="nav-link">Farmaceuti</a></li>
        <li class="nav-item"><a href="#" class="nav-link">Dermatolozi</a></li>
        <li class="nav-item"><a href="#" class="nav-link">Slobodni termini</a></li>
        <li class="nav-item"><a href="#" class="nav-link">Odsustva</a></li>
        <b-nav-item-dropdown v-bind:text = "pharmacyAdmin.name + ' ' + pharmacyAdmin.surname" right>
          <b-dropdown-item href="#"><router-link to="/pharmacy-admin-profile">Moj profil</router-link></b-dropdown-item>
          <b-dropdown-item href="#" @click="logout">Odjava</b-dropdown-item>
        </b-nav-item-dropdown>
    </ul>
</template>

<script>
export default {
    data: function() {
        return{
            pharmacyAdmin: {}
        } 
    },
    mounted(){
        this.$http
            .get('/pharmacyAdmin/' + this.$store.getters.getUserId)
            .then( res => {
                this.pharmacyAdmin = JSON.parse(JSON.stringify(res.data))
            })
    },
    methods:{
        logout : function(){
        localStorage.setItem("UserRole","ROLE_UNREGISTERED")
        localStorage.removeItem("UserId")
        localStorage.removeItem("JWT")
        this.$router.push("Registration");
         this.$axios
        .post("auth/logout")
        .then()
        .catch((error) => {
          this.errorMessage = error.message;
          this.$notify({
            type: "error",
            title: "Error",
            text: "Doslo je do greske prilikom logout-a!",
            closeOnClick: true,
          });
        });
        window.location.reload()
        }
    }
}
</script>