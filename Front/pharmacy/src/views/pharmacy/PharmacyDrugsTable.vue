<template>
    <div class="pharmacy-drugs">
        <PharmacyReserveDrugModal :pharmacyId="pharmacyId" :drug="selectedDrug" />

        <b-table striped hover :busy="isBusy" :items="items | formatRating" :fields="fields"  class="text-middle mt-2">
            <template #table-busy>
				<div class="text-center text-danger my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong> Učitavanje...</strong>
				</div>
			</template>

			<template #cell(actions)="row">
				<b-button size="sm" @click="displayDrugInformations(row.item)">
					Prikaži detaljnije
				</b-button>

                <b-button 
                size="sm" variant="primary" class="ml-2" @click="displayDrugInformations(row.item)">
                    <b-icon icon="question-circle"></b-icon>
					Proveri dostupnost
				</b-button>

                <b-button v-if="loggedUserRole == 'ROLE_PATIENT'"
                    size="sm" variant="success" class="ml-2" @click="selectDrugForReservation(row.item)">
                    <b-icon icon="heart"></b-icon>
                    Rezerviši
                </b-button>

                <b-button v-if="loggedUserRole == 'ROLE_PHARMACY_ADMIN'"
                size="sm" variant="danger" class="ml-2" @click="displayDrugInformations(row.item)">
                    <b-icon icon="x"></b-icon>
					Ukloni
				</b-button>
			</template>
        </b-table>
    </div>
</template>

<script>
export default {
    props: ['pharmacyId'],
    data: function() {
        return{
            loggedUserRole: this.$store.getters.getUserRole,

            items: [],
            fields: [],

            isBusy: true,

            selectedDrug: {}
        } 
    },

    mounted(){
        this.getDrugsInPharmacy()

        this.$root.$on('update-pharmacy-drugs', () => {
            this.getDrugsInPharmacy()
        })

        if (this.loggedUserRole == 'ROLE_PHARMACY_ADMIN')
            this.fields = [
                { key: 'drugName', label: 'Naziv leka', sortable: true}, 
                { key: 'drugCode', label: 'Šifra leka', sortable: true}, 
                { key: 'loyaltyPoints', label: 'Broj Loyalty poena', sortable: true}, 
                { key: 'quantity', label: 'Količina na stanju', sortable: true},
                { key: 'rating', label: 'Ocena', sortable: true},
                { key: 'price', label: 'Cena', sortable: true},
                { key: 'actions', label: 'Akcije'}
            ]
        else
            this.fields = [
                { key: 'drugName', label: 'Naziv leka', sortable: true}, 
                { key: 'loyaltyPoints', label: 'Broj Loyalty poena', sortable: true}, 
                { key: 'rating', label: 'Ocena', sortable: true},
                { key: 'price', label: 'Cena', sortable: true},
                { key: 'actions', label: 'Akcije'}
            ]
    },

    methods:{
        getDrugsInPharmacy() {
            this.$http
                .get('/drugs/byPharmacyId/' + this.pharmacyId)
                .then( res => {
                    this.isBusy = false

                    this.items = res.data
                    console.log(this.items)
                })
                .catch((error) => {
                    this.isBusy = false
                    
                    console.log(error)
                    this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')  
                })
        },

        displayDrugInformations(drug){
            console.log(drug)
            this.$router.push({name: 'DrugInformations', params: { id: drug.drugId, name: drug.drugName }});
        },

        selectDrugForReservation(drug){
            this.$bvModal.show('reserve-drug-modal-' + this.selectedDrug.drugId)
            this.selectedDrug = drug
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

    components:{
        PharmacyReserveDrugModal: () => import('./PharmacyReserveDrugModal.vue')
    }
}
</script>