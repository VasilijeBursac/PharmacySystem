<template>
    <b-modal id="add-dermatologist-modal" scrollable hide-footer title="Dodavanje dermatologa u apoteku" 
        @hide="resetFields">
        <b-form>
            <b-form-group id="dermatologist-group">
                <label for="dermatologist-select">Dermatolog:</label>
                <b-form-select v-model="selectedDermatologist" id="dermatologist-select">
                    <b-form-select-option :value="null" disabled>Izaberite dermatologa</b-form-select-option>
                    <b-form-select-option v-for="dermatologist in dermatologistsNotInPharmacy" :key="dermatologist.id" :value="dermatologist">
                        {{dermatologist.name + " " + dermatologist.surname}}
                    </b-form-select-option>
                </b-form-select>
            </b-form-group>
            
            <b-form-group id="time-start-group" label="Početak radnog vremena:" label-for="time-start-picker" class="text-left">
                <b-form-timepicker v-model="startTime" id="time-start-picker" placeholder="Izaberite vreme početka radnog vremena" locale="en">
                </b-form-timepicker>
            </b-form-group>

            <b-form-group id="time-end-group" label="Kraj radnog vremena:" label-for="time-end-picker" class="text-left">
                <b-form-timepicker v-model="endTime" id="time-end-picker" placeholder="Izaberite vreme kraja radnog vremena" locale="en">
                </b-form-timepicker>
            </b-form-group>


            <div class="buttons mt-5">
                <b-button variant="success" block @click="addDermatologistToPharmacy">
                    Dodaj dermatologa
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
    props: {
        pharmacyId: {
            type: Number,
            default: null
        },
    },
    data: function(){
        return {
            dermatologistsNotInPharmacy: [],

            selectedDermatologist: null,
            startTime: "",
            endTime: "",
        }
    },

    computed: {
        ...mapState(['myPharmacyId']),
    },

    mounted() {
        this.getDermatologistsNotInPharmacy()

        this.$root.$on('update-not-pharmacy-dermatologists', () => {
            this.getDermatologistsNotInPharmacy()
        })
    },

    methods: {
        getDermatologistsNotInPharmacy() {
            this.$http
                .get("/dermatologist/notInPharmacy/" + this.pharmacyId)
                .then( res => {
                    this.isBusy = false

                    this.dermatologistsNotInPharmacy = res.data
                    console.log(this.dermatologistsNotInPharmacy)
                })
                .catch((error) => {
                    this.isBusy = false
                    
                    console.log(error)
                    if (error.response.status == 403 || error.response.status == 401)
                        this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                    // else if (error.response.status == 404)
                    //     this.toast('danger', 'Neuspešno', 'Svi raspoloživi dermatolozi već rade u Vašoj apoteci.')
                    // else 
                    //     this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
                })
        },

        addDermatologistToPharmacy() {
            if (this.selectedDermatologist == null || this.startTime == "" || this.endTime == ""){
                this.toast('danger', 'Neuspešno', 'Morate izabrati dermatologa i uneti početak i kraj radnog vremena!')
                return;
            }

            if (this.formatDateTime(this.startTime) > this.formatDateTime(this.endTime)){
                this.toast('danger', 'Neuspešno', 'Kraj radnog vremena ne može biti pre početka radnog vremena!')
                return;
            }
            
            this.$http
            .post('dermatologist/addToPharmacy/' + this.pharmacyId, {
                dermatologistId: this.selectedDermatologist.id,
                startDate: this.formatDateTime(this.startTime),
                endDate: this.formatDateTime(this.endTime),
            })
            .then( () => {
                this.toast('success', 'Uspešno', 'Uspešno ste dodali dermatologa u apoteku.')
                this.closeModal();
                this.$root.$emit('update-pharmacy-dermatologists')
                this.$root.$emit('update-not-pharmacy-dermatologists')
            })
            .catch( (error) => {
                if (error.response.status == 403 || error.response.status == 401)
                    this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                else if (error.response.status == 400)
                    this.toast('danger', 'Neuspešno', error.response.data)
                else 
                    this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
            })
        },

        formatDateTime(selectedTime) {
            let currentDate = new Date().toISOString().split("T")[0]
            return new Date(currentDate + " " + selectedTime).getTime()
        },

        resetFields(){
            this.selectedDermatologist = null
            this.startTime = ""
            this.endTime = ""
        },

        closeModal(){
            this.$bvModal.hide('add-dermatologist-modal')
        },

        toast(variant, title, message){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
            scroll(0,0)
        }
    },
}
</script>

<style>
    
</style>