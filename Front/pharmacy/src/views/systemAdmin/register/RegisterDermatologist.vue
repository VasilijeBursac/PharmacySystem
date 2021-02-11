
<template>
    <div class="register">
        <b-form id = "form" @submit="onSubmit" @reset="onReset" v-if="show">
            <b-form-group id="email-group" label="Email:" label-for="email-input" class="text-center">
                <b-form-input
                    class="text-center"
                    id="email-input"
                    v-model="user.email"
                    placeholder="Unesite email"
                    required>
                </b-form-input>
            </b-form-group>

            <b-form-group id="password-group" label="Lozinka:" label-for="password-input" class="text-center">
                <b-form-input
                    class="text-center"
                    type = "password"
                    id="password-input"
                    v-model="user.password"
                    placeholder="Unesite lozinku"
                    required>
                </b-form-input>
            </b-form-group>

            <b-form-group id="passwordCheck-group" label="Ponovljena lozinka" label-for="address-input" class="text-center">
                <b-form-input
                    class="text-center"
                    type = "password"
                    id="passwordCheck-input"
                    v-model="user.passwordCheck"
                    placeholder="Ponovite lozinku"
                    required>
                </b-form-input>   
            </b-form-group>
            <label v-if="passwordError" style="color : red">Lozinke se ne podudaraju !</label> 
            <b-form-group id="name-group" label="Ime:" label-for="name-input" class="text-center">
            <b-form-input
                class="text-center"
                id="name-input"
                v-model="user.name"
                placeholder="Unesite ime"
                required>
            </b-form-input>
            </b-form-group>

            <b-form-group id="surname-group" label="Prezime:" label-for="surname-input" class="text-center">
                <b-form-input
                    class="text-center"
                    id="surname-input"
                    v-model="user.surname"
                    placeholder="Unesite prezime"
                    required>
                </b-form-input>
            </b-form-group>

            <b-form-group id="address-group" label="Adresa:" label-for="address-input" class="text-center">
                <b-form-input
                    class="text-center"
                    id="address-input"
                    v-model="user.address"
                    placeholder="Unesite adresu prebivalista"
                    required>
                </b-form-input>
            </b-form-group>

            <b-form-group id="city-group" label="Grad:" label-for="city-input" class="text-center">
                <b-form-input
                    class="text-center"
                    id="city-input"
                    v-model="user.city"
                    placeholder="Unesite grad "
                    required>
                </b-form-input>
            </b-form-group>

            <b-form-group id="country-group" label="Drzava:" label-for="country-input" class="text-center">
                <b-form-input
                    class="text-center"
                    id="country-input"
                    v-model="user.country"
                    placeholder="Unesite drzavu "
                    required>
                </b-form-input>
            </b-form-group>

            <b-form-group id="phone-group" label="Telefon:" label-for="phone-input" class="text-center">
                <b-form-input
                    class="text-center"
                    id="phone-input"
                    v-model="user.phone"
                    placeholder="Unesite broj telefona "
                    required>
                </b-form-input>
            </b-form-group>

             <b-form-group id="price-group" label="Cena:" label-for="price-input" class="text-center">
                <b-form-input
                    type = "number"
                    min = "0"
                    class="text-center"
                    id="price-input"
                    v-model="user.price"
                    placeholder="Unesite cenu pregleda "
                    required>
                </b-form-input>
            </b-form-group>



            <div class="buttons text-center">                        
                <b-button type="submit" variant="success" class="mr-2">
                    <b-icon-check></b-icon-check>
                    Registruj se</b-button>
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
            user: {
                email: '',
                password: '',
                passwordCheck: '',
                name: '',
                surname: '',
                address: '',
                city: '',
                country: '',
                phone: '',
                price : 0
            },
            passwordError : false,
            show: true
        }
    },
    methods: {
          onSubmit(event) {
            event.preventDefault()
            this.passwordError = false;
            if(this.user.passwordCheck !== this.user.password){
                this.passwordError = true;
            }
            if(!this.passwordError){
                this.$http
                .post("auth/signupDermatologist",{
                    email : this.user.email,
                    password : this.user.password,
                    firstName : this.user.name,
                    lastName : this.user.surname,
                    address : this.user.address,
                    city : this.user.city,
                    country : this.user.country,
                    phoneNumber : this.user.phone,
                    price : this.user.price
            })
            .then( () => {
                  this.toast()  
                   
                })                    
                .catch(function (error) {
                    if(error.response.status === 500) {
                    alert('Vec postoji korisnik sa unetim imejlom');               
                    }
                });    
            }     
        },
        toast(){
            this.$bvToast.toast(`Uspešno ste registrovali novog dermatologa!`, {
                title: 'Uspešno!',
                variant: 'success',
                autoHideDelay: 5000
            })
        },
        onReset(event) {
            event.preventDefault()
            // Reset our form values
            this.user.email = ''
            this.user.password = ''
            this.user.passwordCheck = ''
            this.user.name = ''
            this.user.surname = ''
            this.user.address = ''
            this.user.city = ''
            this.user.phone = ''
            this.passwordError = false
            this.user.price = 0
            // Trick to reset/clear native browser form validation state
            this.show = false
            this.$nextTick(() => {
                this.show = true
            })
        }
    },
    components:{
      
    }
}
</script>
<style scoped>
    #form {
        width : 40%;
        margin : auto
    }
</style>
