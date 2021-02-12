<template>
    <div class="container">
        <h4 class="h4 align-middle my-4">Izmena informacija o apoteci</h4>
        <div class="profile-main">
            <b-card no-body>
                <b-tabs pills card align="center" lazy>
                    <b-tab title="Osnovne informacije" active>
                        <div class="pharmacy-information">
                            <b-row>
                                <b-col cols="8">
                                    <b-form>
                                        <b-form-group id="name-group" label="Naziv apoteke:" label-for="name-input" class="text-left">
                                            <b-form-input
                                                id="name-input"
                                                v-model="form.name"
                                                placeholder="Unesite naziv"
                                                trim
                                                required>
                                            </b-form-input>
                                        </b-form-group>

                                        <b-form-group id="description-group" label="Opis:" label-for="description-input" class="text-left">
                                            <b-form-textarea
                                                id="description-input"
                                                v-model="form.description"
                                                placeholder="Unesite opis"
                                                required
                                                trim
                                                maxlength="255"
                                                max-rows="3"
                                                no-resize>
                                            </b-form-textarea>
                                        </b-form-group>

                                        <b-form-group id="address-group" label="Adresa:" label-for="address-input" class="text-left">
                                            <b-form-input
                                                id="address-input"
                                                v-model="form.address"
                                                placeholder="Unesite adresu"
                                                trim
                                                required>
                                            </b-form-input>
                                        </b-form-group>

                                        <b-form-group id="city-group" label="Grad:" label-for="city-input" class="text-left">
                                            <b-form-input
                                                id="city-input"
                                                v-model="form.city"
                                                placeholder="Unesite grad"
                                                trim
                                                required>
                                            </b-form-input>
                                        </b-form-group>

                                        <b-form-group id="country-group" label="Država:" label-for="country-input" class="text-left">
                                            <b-form-input
                                                id="country-input"
                                                v-model="form.country"
                                                placeholder="Unesite državu"
                                                trim
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
                            </b-row>
                        </div>
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
    </div>
</template>


<script>
export default {
    data: function(){
        return{
            pharmacyId: this.$route.params.id,
            form: {},
            backup: {}
        }
    },
    mounted(){
        this.$http
            .get('/pharmacy/' + this.pharmacyId)
            .then( res => {
                this.form = JSON.parse(JSON.stringify(res.data))
                this.backup = JSON.parse(JSON.stringify(res.data))
            })
    },
    methods: {
        onSubmit(event) {
            event.preventDefault()
            this.$http
                .put('pharmacy/', 
                    {
                        id: this.pharmacyId,
                        name: this.form.name, 
                        description: this.form.description, 
                        address: this.form.address, 
                        city: this.form.city, 
                        country: this.form.country, 
                        
                    })
                .then( res => {
                    if(res.status == 200){
                        this.toast('success', 'Uspešno!', `Uspešno ste izmenili informacije apoteke!`)
                        //window.location.reload()
                        this.$router.push({name: 'PharmacyProfile', params: { id: this.pharmacyId }})
                    }
                })
                .catch(() => 
                        this.toast('danger', 'Neuspešno!', 'Greška pri izmeni informacija apoteke!')
                    )
        },

        toast(variant, title, message){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
            scroll(0,0)
        },
        
        onCancel(event) {
            event.preventDefault()
            this.form = JSON.parse(JSON.stringify(this.backup))
        }
    }
}
</script>
