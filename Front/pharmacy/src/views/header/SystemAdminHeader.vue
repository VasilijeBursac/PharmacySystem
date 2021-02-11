<template>    
    <ul class="nav navbar-nav">
        <li class="nav-item active"><a href="#" class="nav-link"><router-link to="systemAdminsPage">Administratori sistema</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="pharmacyAdminsPage">Administratori apoteka</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="dermatologistsPage">Dermatolozi</router-link></a></li>    
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="suppliersPage">Dobavljaci</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="pharmaciesPage">Apoteke</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link">Godisnji odmori</a></li>
        <li class="nav-item"><a href="#" class="nav-link">Zalbe</a></li>
        <li class="nav-item"><a href="#" class="nav-link">Sifranik lekova</a></li>
        <li class="nav-item"><a href="#" class="nav-link">Loyalty</a></li>
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
      this.$router.push("Registration");
      window.location.reload()
    }
  }

}
</script>
