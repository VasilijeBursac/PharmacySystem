
<template>
    <div class="register">
        <b-form id = "form" @submit="onSubmit" @reset="onReset" v-if="show">
            

            <b-form-group id="name-group" label="Ime:" label-for="name-input" class="text-center">
                <b-form-input
                    class="text-center"
                    id="name-input"
                    v-model="pharmacy.name"
                    placeholder="Unesite ime"
                    required>
                </b-form-input>
            </b-form-group>

            <b-form-group id="address-group" label="Adresa:" label-for="address-input" class="text-center">
                <b-form-input
                    class="text-center"
                    id="address-input"
                    v-model="pharmacy.address"
                    placeholder="Unesite adresu apoteke"
                    required>
                </b-form-input>
            </b-form-group>

            <b-form-group id="city-group" label="Grad:" label-for="city-input" class="text-center">
                <b-form-input
                    class="text-center"
                    id="city-input"
                    v-model="pharmacy.city"
                    placeholder="Unesite grad "
                    required>
                </b-form-input>
            </b-form-group>

            <b-form-group id="country-group" label="Drzava:" label-for="country-input" class="text-center">
                <b-form-input
                    class="text-center"
                    id="country-input"
                    v-model="pharmacy.country"
                    placeholder="Unesite drzavu "
                    required>
                </b-form-input>
            </b-form-group>

            <div class="buttons text-center">                        
                <b-button type="submit" variant="success" class="mr-2">
                    <b-icon-check></b-icon-check>
                    Dodaj</b-button>
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
            pharmacy: {
                name: '',
                address: '',
                city: '',
                country: ''
            },
            show: true
        }
    },
    methods: {
          onSubmit(event) {
            event.preventDefault()
           
                this.$http
                .post("pharmacy/addPharmacy",{

                    name : this.pharmacy.name,
                    address : this.pharmacy.address,
                    city : this.pharmacy.city,
                    country : this.pharmacy.country,

            })
            .then( () => {
                  this.toast()  
                   
                })                    
                .catch(function (error) {
                    if(error.response.status === 500) {
                        alert('Greska u sistemu');               
                    } else if(error.response.status === 401) {
                        alert('Nemate pravo pristupa !');               
                    }
                });    
                
        },
        toast(){
            this.$bvToast.toast(`Uspešno ste dodali novu apoteku!`, {
                title: 'Uspešno!',
                variant: 'success',
                autoHideDelay: 5000
            })
        },
        onReset(event) {
            event.preventDefault()
            // Reset our form values

            this.pharmacy.name = ''
            this.pharmacy.address = ''
            this.pharmacy.city = ''
             this.pharmacy.country = ''

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
