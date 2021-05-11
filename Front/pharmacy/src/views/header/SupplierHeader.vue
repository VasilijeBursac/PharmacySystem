<template>    
    <ul class="nav navbar-nav">
        <li class="nav-item active"><a href="#" class="nav-link"><router-link to="orders">Narudzbenice</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="supplier-drugs">Moji lekovi</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="supplier-offers">Moje Ponude</router-link></a></li>
        <b-nav-item-dropdown text="Profil" right>
          <b-dropdown-item href="/supplier-profile">Moj profil</b-dropdown-item>
          <b-dropdown-item href="#" @click="logout" >Odjava</b-dropdown-item>
        </b-nav-item-dropdown>
    </ul>
</template>

<script>
export default {
  methods:{
    logout : function(){
      localStorage.setItem("UserRole","ROLE_UNREGISTERED")
      localStorage.removeItem("UserId")
      localStorage.removeItem("JWT")
      this.$router.push('/registration');
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

