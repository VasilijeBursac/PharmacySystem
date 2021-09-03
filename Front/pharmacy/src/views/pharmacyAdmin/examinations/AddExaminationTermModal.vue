<template>
    <b-modal id="add-examination-term-modal" scrollable hide-footer title="Dodavanje slobodnih termina dermatologa" 
        @hide="resetFields">
        <b-form>
            <b-form-group id="dermatologist-group">
                <label for="dermatologist-select">Dermatolog:</label>
                <b-form-select v-model="selectedDermatologist" id="dermatologist-select">
                    <b-form-select-option :value="null" disabled>Izaberite dermatologa</b-form-select-option>
                    <b-form-select-option v-for="dermatologist in dermatologistsInPharmacy" :key="dermatologist.id" :value="dermatologist">
                        {{dermatologist.name + " " + dermatologist.surname}}
                    </b-form-select-option>
                </b-form-select>
            </b-form-group>

            <b-form-group id="price-group">
                <label for="price-input">Cena pregleda:</label>
                <b-form-input v-model="price" id="price-input" placeholder="Unesite cenu pregleda"
                type="number" min="0">
                </b-form-input>
            </b-form-group>
            
            <b-form-group id="date-start-group" label="Datum pregleda:" label-for="date-start-picker" class="text-left">
                <b-form-datepicker v-model="startDate" id="date-start-picker" placeholder="Izaberite datum početka pregleda">
                </b-form-datepicker>
            </b-form-group>

            <b-form-group id="time-start-group" label="Vreme pregleda:" label-for="time-start-picker" class="text-left">
                <b-form-timepicker v-model="startTime" id="time-start-picker" placeholder="Izaberite vreme početka pregleda" locale="en">
                </b-form-timepicker>
            </b-form-group>

            <b-form-group id="duration-group">
                <label for="duration-input">Trajanje pregleda:</label>
                <b-form-input v-model="duration" id="duration-input" placeholder="Unesite trajanje pregleda"
                type="number" min="0">
                </b-form-input>
            </b-form-group>


            <div class="buttons mt-5">
                <b-button variant="success" block @click="addExaminationTerm">
                    Dodaj termin
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
            default: null
        },
    },
    data: function(){
        return {
            dermatologistsInPharmacy: [],

            selectedDermatologist: null,
            price: "",
            startDate: "",
            startTime: "",
            duration: 30,
        }
    },

    computed: {
        ...mapState(['myPharmacyId']),
    },

    mounted() {
        this.getDermatologists()
    },

    methods: {
        getDermatologists() {
            this.$http
                .get('/dermatologist/byPharmacyId/' + this.pharmacyId)
                .then( res => {
                    this.isBusy = false

                    this.dermatologistsInPharmacy = res.data
                    console.log(this.dermatologistsInPharmacy)
                })
                .catch((error) => {
                    this.isBusy = false
                    
                    console.log(error)
                    if (error.response.status == 403 || error.response.status == 401) {
                        this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                        // window.location.reload()
                    }
                    else if (error.response.status == 404)
                        this.toast('danger', 'Neuspešno', 'Trenutno nema dermatologa u sistemu.')
                    else 
                        this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
                })
        },

        addExaminationTerm() {
            if (this.selectedDermatologist == null || this.price == "" || this.startDate == "" || this.startTime == "" || this.duration == ""){
                this.toast('danger', 'Neuspešno', 'Morate popuniti sva polja!')
                return;
            }

            if (new Date(this.startDate + ' ' + this.startTime).getTime() < new Date().getTime()){
                this.toast('danger', 'Neuspešno', 'Termin pregleda ne može biti pre trenutnog datuma i vremena!')
                return;
            }

            
            this.$http
            .post('/examination/addDermatologistTerm', {
                employeeId: this.selectedDermatologist.id,
                termStart: new Date(this.startDate + ' ' + this.startTime),
                duration: this.duration,
                price: this.price
            })
            .then( () => {
                this.toast('success', 'Uspešno', 'Uspešno ste dodali slobodan termin pregleda za dermatologa!')
                this.closeModal();
                this.$root.$emit('update-pharmacy-dermatologists-examinations')
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
        

        resetFields(){
            this.selectedDermatologist = null
            this.price = ""
            this.startDate = ""
            this.startTime = ""
            this.duration = 30
        },

        closeModal(){
            this.$bvModal.hide('add-examination-term-modal')
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