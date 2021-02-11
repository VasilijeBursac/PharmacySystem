<template>
  <div class="loginClass">
    <b-form
      id="form"
      @submit="login"
      @reset="onReset"
      v-if="show"
      class="text-center"
    >
      <b-form-group
        id="email-group"
        label="Email:"
        label-for="email-input"
        class="text-center"
      >
        <b-form-input
          id="email-input"
          class="text-center"
          v-model="form.email"
          placeholder="Unesite email"
          required
        >
        </b-form-input>
      </b-form-group>

      <b-form-group
        id="password-group"
        label="Lozinka:"
        label-for="password-input"
        class="text-center"
      >
        <b-form-input
          type="password"
          class="text-center"
          id="password-input"
          v-model="form.password"
          placeholder="Unesite lozinku"
          required
        >
        </b-form-input>
      </b-form-group>
      <div class="buttons text-center">
        <b-button type="submit" variant="success" class="mr-2">
          <b-icon-check></b-icon-check>
          Uloguj se
        </b-button>
        <b-button type="reset" variant="danger">
          <b-icon-x></b-icon-x>
          Otkaži
        </b-button>
      </div>
    </b-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        email: "",
        password: "",
      },
      show: true,
    };
  },

    methods: {
        
        login(event) {
           event.preventDefault()
            this.$http
                .post('auth/login',{
                    email : this.form.email,
                    password : this.form.password
                })
                .then( (response) => {
                    this.toast()                           
                    this.$store.commit('setUserRole',response.data.role);
                    this.$store.commit('setUserId',response.data.userId);
                    this.$store.commit('setJWT',response.data.accessToken);
                    localStorage.setItem("UserRole",response.data.role);
                    localStorage.setItem("UserId",response.data.userId);
                    localStorage.setItem("JWT",response.data.accessToken);
                    
                    if (response.data.role === "ROLE_PATIENT") {
                        this.$router.push("patient-profile");
                    }
                    if (response.data.role === "ROLE_SYSTEM_ADMIN") {
                        this.$router.push("systemAdminsPage");
                    }
                    if (response.data.role === "ROLE_PHARMACY_ADMIN") {
                        this.$router.push("patient-profile");
                    }
                    if (response.data.role === "ROLE_DERMATOLOGIST") {
                        this.$router.push("my-calendar");
                    }
                    if (response.data.role === "ROLE_PHARMACIST") {
                        this.$router.push("my-calendar");
                    }
                    if (response.data.role === "ROLE_SUPPLIER") {
                        this.$router.push("patient-profile");  
                    }
                   
                    window.location.reload()
                 
                })                    
                .catch(function (error) {
                    if(error.response.status === 401) {
                        alert('Ne postoji korisnik sa unetim podacima');
                    }
                });
        },  
        toast(){
            this.$bvToast.toast(`Uspešno ste se ulogovali!`, {
                title: 'Uspešno!',
                variant: 'success',
                autoHideDelay: 5000
            })
        },
        onReset(event) {
            event.preventDefault()
            // Reset our form values
            this.form.email = ''
            this.form.password = ''   
            // Trick to reset/clear native browser form validation state
            this.show = false
            this.$nextTick(() => {
                this.show = true
            })
        }

    },
  components: {},
};
</script>

<style scoped>
#form {
  width: 40%;
  margin: auto;
}
</style>
