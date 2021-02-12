<template>    
    <ul class="nav navbar-nav">
        <li class="nav-item active"><a href="/my-calendar"  class="nav-link">Moj radni kalendar</a></li>
         <li class="nav-item"><a  href="/searchedPatients" class="nav-link">Zapocni savetovanje</a></li>
        <li class="nav-item"><a href="/myPatients" class="nav-link">Moji pacijenti</a></li>
        <li class="nav-item"><a  href="/vacation" class="nav-link">Godisnji odmor i odsustva</a></li>
        <li class="nav-item"><a href="/issue-drug" class="nav-link">Preporuci lekove</a></li>
       
        <b-nav-item-dropdown text="Profil" right>
          <b-dropdown-item href="/pharmacist-profile">Moj profil<router-link ></router-link></b-dropdown-item>
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
