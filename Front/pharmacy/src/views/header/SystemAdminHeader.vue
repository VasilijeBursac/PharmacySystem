<template>    
    <ul class="nav navbar-nav">
        <li class="nav-item active"><a href="#" class="nav-link"><router-link to="systemAdminsPage">Administratori sistema</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="pharmacyAdminsPage">Administratori apoteka</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="dermatologistsPage">Dermatolozi</router-link></a></li>    
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="suppliersPage">Dobavljači</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="pharmaciesPage">Apoteke</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="vacation-requests">Godišnji odmori</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="complaints">Žalbe</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="drugsPage">Šifarnik lekova</router-link></a></li>
        <li class="nav-item" style="align-right"><a href="#" class="nav-link" @click="logout">Odjava</a></li>
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
