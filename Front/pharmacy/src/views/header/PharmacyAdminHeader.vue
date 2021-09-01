<template>    
    <ul class="nav navbar-nav">      
        <li class="nav-item active">
            <a href="#" class="nav-link">
                <router-link :to = "'/pharmacy/' + pharmacyAdmin.pharmacyId">
                    Profil apoteke
                </router-link>
            </a>
        </li>

        <li class="nav-item">
            <a href="#" class="nav-link">
                <router-link to = "/pharmacy-drugs">
                    Lekovi
                </router-link>
            </a>
        </li>

        <li class="nav-item">
            <a href="#" class="nav-link">
                <router-link to="/pharmacy-orders">
                    Narudžbenice
                </router-link>
            </a>
        </li>

        <li class="nav-item">
            <a href="#" class="nav-link">
                <router-link to="/promotions">
                    Promocije
                </router-link>
            </a>
        </li>

        <li class="nav-item">
            <a href="#" class="nav-link">
                <router-link to="/pharmacists">
                    Farmaceuti
                </router-link>
            </a>
        </li>
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
                this.$store.dispatch('setMyPharmacyId', this.pharmacyAdmin.pharmacyId)
            })
    },
    methods:{
        logout : function(){
            localStorage.setItem("UserRole","ROLE_UNREGISTERED")
            localStorage.removeItem("UserId")
            localStorage.removeItem("JWT")

            this.$router.push("/registration");
            this.$store.dispatch('setMyPharmacyId', null)

            this.$axios
            .post("auth/logout")
            .then()
            .catch((error) => {
            this.errorMessage = error.message;
            this.$notify({
                type: "error",
                title: "Error",
                text: "Doslo je do greške prilikom logout-a!",
                closeOnClick: true,
            });
            });
            window.location.reload()
        }
    }
}
</script>