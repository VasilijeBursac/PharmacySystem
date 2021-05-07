
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

            <b-form-group id="description-group" label="Opis:" label-for="description-input" class="text-center">
                <b-form-textarea
                    class="text-center"
                    id="description-input"
                    v-model="pharmacy.description"
                    placeholder="Unesite opis apoteke"
                    rows="4" 
                    max-rows="6"
                    required>
                </b-form-textarea>
            </b-form-group>

            <b-form-group id="price-group" label="Cena savetovanja kod farmaceuta:" label-for="price-input" class="text-center">
                <b-form-input
                    type = "number"
                    min = "0"
                    class="text-center"
                    id="price-input"
                    v-model="pharmacy.pharmacistPrice"
                    placeholder="Unesite cenu savetovanja "
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
                country: '',
                description: '',
                pharmacistPrice: 0
            },
            show: true
        }
    },
    methods: {
          onSubmit(event) {
            event.preventDefault()
                if(this.pharmacy.pharmacistPrice == 0){
                    this.toast('Morate uneti cenu savetovanja kod farmacetua!','Neuspešno', 'danger');
                    return;
                }
                this.$http
                .post("pharmacy/addPharmacy",{
                    name : this.pharmacy.name,
                    address : this.pharmacy.address,
                    city : this.pharmacy.city,
                    country : this.pharmacy.country,
                    description : this.pharmacy.description,
                    pharmacistPrice : this.pharmacy.pharmacistPrice
            })
            .then( () => {

                  this.toast('Uspešno ste dodali novu apoteku!','Uspešno!','success') 
                })                    
                .catch(error => {
                     if(error.response.status == 400)
                        this.toast('Greska prilikom dodavanja apoteke !', 'Neuspešno', 'danger')
                     else this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')
                });    
                
        },
       toast(message, title, variant){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
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
            this.pharmacy.description = ''
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
