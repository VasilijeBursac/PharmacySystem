<template>
    <div class="patient-information">
        <b-row>
            <b-col cols="8">
                <b-form>
                    <b-form-group id="name-group" label="Ime:" label-for="name-input" class="text-left">
                        <b-form-input
                            id="name-input"
                            v-model="form.name"
                            placeholder="Unesite ime"
                            required>
                        </b-form-input>
                    </b-form-group>

                    <b-form-group id="surname-group" label="Prezime:" label-for="surname-input" class="text-left">
                        <b-form-input
                            id="surname-input"
                            v-model="form.surname"
                            placeholder="Unesite prezime"
                            required>
                        </b-form-input>
                    </b-form-group>

                    <b-form-group id="address-group" label="Adresa:" label-for="address-input" class="text-left">
                        <b-form-input
                            id="address-input"
                            v-model="form.address"
                            placeholder="Unesite adresu"
                            required>
                        </b-form-input>
                    </b-form-group>

                    <b-form-group id="city-group" label="Grad:" label-for="city-input" class="text-left">
                        <b-form-input
                            id="city-input"
                            v-model="form.city"
                            placeholder="Unesite grad"
                            required>
                        </b-form-input>
                    </b-form-group>

                    <b-form-group id="country-group" label="Država:" label-for="country-input" class="text-left">
                        <b-form-input
                            id="country-input"
                            v-model="form.country"
                            placeholder="Unesite državu"
                            required>
                        </b-form-input>
                    </b-form-group>

                    <b-form-group id="phone-group" label="Broj telefona:" label-for="phone-input" class="text-left">
                        <b-form-input
                            id="phone-input"
                            v-model="form.phoneNumber"
                            placeholder="Unesite broj telefona"
                            required>
                        </b-form-input>
                    </b-form-group>

                    <div class="buttons text-left">                        
                        <b-button @click="onSubmit" variant="success" class="mr-2">
                            <b-icon-check></b-icon-check>
                            Izmeni informacije</b-button>
                        <b-button @click="onCancel" variant="danger">
                            <b-icon-x></b-icon-x>
                            Otkaži
                        </b-button>
                    </div>
                </b-form>
            </b-col>

            <b-col class="text-right">                
                <ChangePasswordModal />    
            </b-col>
        </b-row>
    </div>
</template>

<script>
import ChangePasswordModal from './ChangePasswordModal.vue';

export default {
    props: ['user'],
    data() {
        return {
            form: {},
            backup: {}
        }
    },
    mounted(){

        this.$http
            .get('/patient/' + this.$store.getters.getUserId)
            .then( res => {
                console.log(res)
                this.form = JSON.parse(JSON.stringify(res.data))
                this.backup = JSON.parse(JSON.stringify(res.data))
            })

    },
    methods: {
        onSubmit(event) {
            event.preventDefault()
            this.$http
                .put('/patient/', 
                    {
                        name: this.form.name, 
                        surname: this.form.surname, 
                        city: this.form.city, 
                        address: this.form.address, 
                        country: this.form.country, 
                        phoneNumber: this.form.phoneNumber, 
                    })
                .then( res => {
                    if(res.status == 200){
                        this.toast()
                    }
                })
        },
        toast(){
            this.$bvToast.toast(`Uspešno ste izmenili svoje informacije!`, {
                title: 'Uspešno!',
                variant: 'success',
                autoHideDelay: 5000
            })
            scroll(0,0)
        },
        onCancel(event) {
            event.preventDefault()
            this.form = JSON.parse(JSON.stringify(this.backup))
        }
    },
    components:{
        ChangePasswordModal
    }
}
</script>

<style scoped>
    .penalty-points{
       text-align: middle;
    }

    .penalty-points > span{
        display: block;
    }
</style>