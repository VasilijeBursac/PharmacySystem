<template>
    <div>
        <b-button v-b-modal="'my-modal'" variant="success">
            <b-icon-star shift-h="-3"></b-icon-star>
            Dodaj ocenu
        </b-button>

        <b-modal id="my-modal" hide-footer title="Ocenjivanje">
            <b-form>
                <b-form-group id="rating-subject-group" label="Predmet ocene:" label-for="rating-subject-input">
                    <b-form-select v-model="subjectSelected" :options="subjectOptions"></b-form-select>
                </b-form-group>

                <b-form-group v-if="subjectSelected == 'dermatologist'" id="dermatologist-group" label="Dermatolozi kod kojih ste bili:" label-for="dermatologist-input">
                    <b-form-select v-model="dermatologistSelected" :options="dermatologists"></b-form-select>
                </b-form-group>

                <b-form-group v-if="subjectSelected == 'pharmacist'" id="pharmacist-group" label="Farmaceuti kod kojih ste bili:" label-for="pharmacist-input">
                    <b-form-select v-model="pharmacistSelected" :options="pharmacists"></b-form-select>
                </b-form-group>

                <b-form-group v-if="subjectSelected == 'drug'" id="drug-group" label="Lekovi koje ste koristili:" label-for="drug-input">
                    <b-form-select v-model="selected" :options="options"></b-form-select>
                </b-form-group>

                <b-form-group v-if="subjectSelected == 'pharmacy'" id="pharmacy-group" label="Apoteke koje ste posetili:" label-for="pharmacy-input">
                    <b-form-select v-model="pharmacySelected" :options="pharmacies"></b-form-select>
                </b-form-group>

                <b-form-group v-if="subjectSelected != null" id="rating-group" label="Izaberite ocenu:" label-for="rating-input">
                    <b-form-rating v-model="rating"></b-form-rating>
                </b-form-group>

                <b-button @click="rate()" block variant="success">
                    Dodaj ocenu
                </b-button>
            </b-form>      
        </b-modal>
    </div>    
</template>

<script>
export default {
    data(){
        return{
            subjectOptions : [
                {
                    value: null,
                    text: 'Izaberite predmet ocene'
                },
                {
                    value: 'dermatologist',
                    text: 'Dermatolog'
                },
                {
                    value: 'pharmacist',
                    text: 'Farmaceut'
                },
                {
                    value: 'drug',
                    text: 'Lek'
                },
                {
                    value: 'pharmacy',
                    text: 'Apoteka'
                }
            ],

            subjectSelected: null,
            dermatologists: [],
            dermatologistSelected: null,
            pharmacists: [],
            pharmacistSelected: null,
            drugs: [],
            drugSelected: null,
            pharmacies: [],
            pharmacySelected: null,

            rating: 4
        }
    },
    methods:{
        rate(){
             this.$http
                .post('rating/rate',{
                    'dermatologistId': this.dermatologistSelected,
                    'pharmacistId': this.pharmacistSelected,
                    'drugId': this.drugSelected,
                    'pharmacyId': this.pharmacySelected,                    
                    'rating': this.rating
                })
                .then( (res) => {
                    if(res.status == 200){
                        this.closeModal();
                        this.$router.go(0)
                        this.toast('Uspešno ste dodali ocenu!', 'Uspešno', 'success')
                    }        
                })
                .catch( (error) => {
                    if(error.response.status == 400){
                        this.toast('Izabrani predmet ocenjivanja ste već ocenili! Molimo izaberite drugi.', 'Neuspešno', 'danger')
                    }else this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
                })
        },
        closeModal(){
            this.$bvModal.hide('my-modal')
            /*this.selected = null
            this.date = ''
            this.items = []*/
        },
        toast(message, title, variant){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        },
        getDermatologist(){
            this.$http
                .get('examination/patient-employees?type=DermatologistExamination')
                .then( (res) => {
                    console.log(res.data)
                    if(res.status == 200){
                        this.dermatologists = []
                        this.dermatologists.push({
                                value: null,
                                text: 'Izaberite dermatologa'
                            })
                        res.data.forEach(user => {
                            this.dermatologists.push({
                                value: user.id,
                                text: user.firstName + ' ' + user.lastName
                            })
                        });
                    }
                })
        },
        getPharmacist(){
            this.$http
                .get('examination/patient-employees?type=PharmacistExamination')
                .then( (res) => {
                    if(res.status == 200){
                        this.pharmacists = []
                        this.pharmacists.push({
                                value: null,
                                text: 'Izaberite farmaceuta'
                            })
                        res.data.forEach(user => {
                            this.pharmacists.push({
                                value: user.id,
                                text: user.firstName + ' ' + user.lastName
                            })
                        });
                    }
                })
        },
        getPharmacies(){
            this.$http
                .get('pharmacy/patient-pharmacies')
                .then( (res) => {
                    if(res.status == 200){
                        this.pharmacies = []
                        this.pharmacies.push({
                                value: null,
                                text: 'Izaberite apoteku'
                            })
                        res.data.forEach(pharmacy => {
                            this.pharmacies.push({
                                value: pharmacy.id,
                                text: pharmacy.name
                            })
                        });
                    }
                })
        }
    },
    watch:{
        subjectSelected: function(){
            this.dermatologistSelected = null
            this.pharmacistSelected = null
            this.pharmacySelected = null
            this.drugSelected = null

            switch(this.subjectSelected){
                case 'dermatologist':
                    this.getDermatologist()
                    break
                case 'pharmacist':
                    this.getPharmacist()
                    break
                case 'pharmacy':
                    this.getPharmacies()
                    break
            }
        }
    }
}
</script>