<template>
    <div>
        <b-button v-b-modal="'my-modal'" variant="success">
            <b-icon-calendar-check shift-h="-3"></b-icon-calendar-check>
            Zakaži konsultovanje
        </b-button>

        <b-modal id="my-modal" size="lg" hide-footer title="Zakazivanje savetovanja kod farmaceuta" @hidden="closeModal(true)">
            <div v-if="showPharmacy" class="pharmacy-selection">
                <b-form>
                    <b-form-group id="date-group" label="Datum:" label-for="date-input">
                        <b-form-datepicker id="date-input" v-model="date" placeholder="Izaberite datum" class="mb-2"></b-form-datepicker>
                        <b-form-timepicker v-model="time" placeholder="Izaberite vreme" locale="en"></b-form-timepicker>
                    </b-form-group>

                    <b-button @click="findPharmacies()" block variant="success">
                        Pretraži apoteke
                    </b-button>
                </b-form>

                <b-table striped hover :items="pharmacyItems" :fields="pharmacyFields" class="mt-3">
                    <template #cell(akcije)="row">
                        <b-button @click="showPharmacistsDialog(row)" size="sm" >
                            Pogledaj
                        </b-button>
                    </template>
                </b-table>            
            </div>       

            <div v-if="showPharmacist" class="pharmacist-selection">
                <div class="pharmacist-title">
                    <b-button @click="closeModal" class="p-0" size="sm" variant="bg-transparent default">
                        <b-icon-chevron-left font-scale="1.5"></b-icon-chevron-left>
                    </b-button>
                    <h6 class="h6 mt-1 ml-2">Slobodni farmaceuti</h6>
                </div>                
                <b-table striped hover :items="pharmacistItems" :fields="pharmacistFields" class="mt-3">
                    <template #cell(akcije)="row">
                        <b-overlay
                            :show="busy"
                            rounded
                            opacity="0.6"
                            spinner-small
                            spinner-variant="primary"
                            class="d-inline-block">
                            <b-button @click="scheduleExamination(row)" variant="success" size="sm" block>
                                Zakaži
                            </b-button>
                        </b-overlay>
                    </template>
                </b-table>     
            </div>
            

        </b-modal>
    </div>    
</template>

<script>
export default {
    data(){
        return {
            pharmacyItems: [],
			pharmacyFields:['ime', 'grad', {key: 'ocena', sortable: true}, {key: 'cena', sortable: true}, 'akcije'],

            pharmacistItems: [],
			pharmacistFields:['ime', 'prezime', {key: 'ocena', sortable: true}, 'akcije'],

            showPharmacy: true,
            showPharmacist: false,

            date: '',
            time: '',

            busy: false
        }
    },
    methods:{
        showPharmacistsDialog(row){
            this.showPharmacy = false
            this.showPharmacist = true

            this.$http
                .post('pharmacy/examinations', {
                    id: row.item.id,
                    type: 'PharmacistExamination',
                    date: new Date(this.date + ' ' + this.time).getTime()
                })
                .then( res => {
                    if(res.status == 200){  
                        let data = []
                         res.data.forEach(element => {
                            data.push({ 
                                ime: element.employee.split(' ')[0], 
                                prezime: element.employee.split(' ')[1], 
                                ocena: element.employeeRating != 0 ? element.employeeRating : 'Nema ocenu',
                                id: element.examinationId
                            })
                        });
                        this.pharmacistItems = data
                    }            
                })

        },
        scheduleExamination(row){
            this.busy = true

            this.$http
                .get('examination/schedule/' + row.item.id)
                .then( res => {
                    if(res.status == 200){
                        this.toast('Uspešno ste zakazali konsultovanje kod farmaceuta!', 'Uspešno', 'success')
                        this.$bvModal.hide('my-modal')
                        this.closeModal(true)

                        setTimeout( () => {
                            this.$router.go(0)
                        }, 250)
                    }                        
                })
		},
        closeModal(close = false){
            this.showPharmacy = true
            this.showPharmacist = false
            this.busy = false
            if(close == true){
                this.pharmacyItems = []
                this.pharmacistItems = []
                this.date = ''
                this.time = ''
            }           
        },
        findPharmacies(){            
            if(this.date == '' || this.time == ''){
                this.toast('Niste popunili datum savetovanja! Molimo Vas popunite i datum i vreme.', 'Neuspešno', 'danger')
                return;
            }

            this.$http
                .post('pharmacy/search-examinations', {
                    date: new Date(this.date + ' ' + this.time).getTime(),
                    type: 'PharmacistExamination'
                })
                .then( res => {
                    if(res.status == 200){  
                        let data = []
                         res.data.forEach(element => {
                            data.push({ 
                                ime: element.name, 
                                grad: element.address, 
                                ocena: element.rating != 0 ? element.rating : 'Nema ocenu',
                                cena: element.pharmacistPrice + ' din',
                                id: element.id
                            })
                        });
                        this.pharmacyItems = data

                        if(data.length == 0){
                            this.toast('Nažalost ne postoji nijedan slobodan farmaceut za dati termin. Molimo pokušajte drugi termin.', 'Neuspešno', 'danger')
                        }
                    }            
                })
                .catch( (error) => {
                    if(error.response.status != 200)
                        this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
                })
        },
        toast(message, title, variant){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        }
    },
}
</script>

<style scoped>

    .pharmacist-title{
        display: flex;
    }

</style>