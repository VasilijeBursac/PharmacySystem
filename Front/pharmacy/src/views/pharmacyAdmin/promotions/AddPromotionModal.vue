<template>
    <b-modal id="add-promotion-modal" hide-footer title="Kreiranje promocije" @hide="resetFields">
        <b-form>
            <b-form-group id="description-group">
                <label for="description-input">Opis promocije:</label>

                <b-form-input 
                    id="description-input" 
                    placeholder="Unesite opis promocije" 
                    type="text"
                    required
                    trim
                    v-model="description"
                    autocomplete="off">
                </b-form-input>
            </b-form-group>


            <b-form-group id="start-date-group">
                <label for="start-date-picker">Početak promocije:</label>

                <b-form-datepicker id="start-date-picker" v-model="startDate" 
                placeholder="Izaberite datum početka promocije" ></b-form-datepicker>
            </b-form-group>

            <b-form-group id="end-date-group">
                <label for="end-date-picker">Kraj promocije:</label>

                <b-form-datepicker id="end-date-picker" v-model="endDate" 
                placeholder="Izaberite datum kraja promocije" ></b-form-datepicker>
            </b-form-group>


            <div class="buttons mt-5">
                <b-button variant="success" block @click="addNewPromotion">
                    Dodaj promociju
                </b-button>

                <b-button variant="danger" block @click="closeModal">
                    Zatvori
                </b-button>
            </div>
        </b-form>
    </b-modal>
</template>

<script>
import { mapState } from 'vuex';

export default {
    props: ["pharmacyId"],
    data: function(){
        return {
            startDate: null,
            endDate: null,
            description: ""
        }
    },

    computed: {
        ...mapState(['myPharmacyId'])
    },

    mounted() {
        
    },

    methods: {
        addNewPromotion() {
            if(new Date(this.endDate).getTime() < new Date(this.startDate).getTime()){
                this.toast('danger', 'Neuspešno', 'Datum kraja promocije ne može biti pre datuma početka promocije!')
                return;
            }
            if(new Date(this.startDate).getTime() < new Date().getTime()){
                this.toast('danger', 'Neuspešno', 'Datum početka promocije ne može biti pre današnjeg datuma!')
                return;
            }
            
            this.$http
            .post('promotion/', {
                description: this.description,
                dateInterval: {
                    startDate: this.startDate,
                    endDate: this.endDate
                },
                pharmacyId: this.pharmacyId,
            })
            .then( () => {
                this.toast('success', 'Uspešno', 'Uspešno ste dodali novu promociju. Svi pretplaćeni korisnici će biti obavešteni email-om.')
                this.closeModal();
                this.$root.$emit('update-pharmacy-promotions')
                    
            })
            .catch( (error) => {
                if (error.response.status == 403 || error.response.status == 401)
                    this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                else 
                    this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
            })
        },

        resetFields(){
            this.description = ""
            this.startDate = null
            this.endDate = null
        },

        closeModal(){
            this.$bvModal.hide('add-promotion-modal')
        },

        toast(variant, title, message){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
            scroll(0,0)
        }
    }
}
</script>

<style>

</style>