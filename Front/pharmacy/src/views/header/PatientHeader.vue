<template>    
    <ul class="nav navbar-nav">      
        <li class="nav-item active"><a href="#" class="nav-link"><router-link to="/pharmacies">Apoteke</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="/drugs">Lekovi</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="/pharmacists">Farmaceuti</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="/dermatologists">Dermatolozi</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="/myexaminations">Moji pregledi</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="/eRecipes">Moji eRecepti</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="/mydrugs">Moji lekovi</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="/patient-subsribed-pharmacies">Pretplaćene apoteke</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="/ratings">Ocene</router-link></a></li>
        <b-nav-item-dropdown text="Profil" right>
          <b-dropdown-item href="/patient-profile">Moj profil</b-dropdown-item>
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


