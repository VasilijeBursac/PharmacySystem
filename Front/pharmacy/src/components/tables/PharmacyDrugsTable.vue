<template>
    <div class="pharmacy-drugs">
        <PharmacyReserveDrugModal :pharmacyId="pharmacyId" :drug="selectedDrug" />

        <b-table striped hover :busy="isBusy" :items="items | formatRating | formatPrice" :fields="fields"  class="text-middle mt-0">
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

                <!-- <b-button 
                size="sm" variant="primary" class="ml-2" @click="displayDrugInformations(row.item)">
                    <b-icon icon="question-circle"></b-icon>
					Proveri dostupnost
				</b-button> -->

                <b-button v-if="loggedUserRole == 'ROLE_PATIENT'"
                    size="sm" variant="success" class="ml-2" @click="selectDrugForReservation(row.item)">
                    <b-icon icon="heart"></b-icon>
                    Rezerviši
                </b-button>

                <b-button v-if="loggedUserRole == 'ROLE_PHARMACY_ADMIN' && myPharmacyId == pharmacyId"
                size="sm" variant="danger" class="ml-2" @click="removeDrugFromPharmacy(row.item)">
                    <b-icon icon="x"></b-icon>
					Ukloni
				</b-button>
			</template>
        </b-table>
    </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
    props: ['pharmacyId'],
    data: function() {
        return{
            loggedUserRole: this.$store.getters.getUserRole,

            data: [],
            fields: [],

            isBusy: true,

            selectedDrug: {},

            nameFilter: '',
			ratingFilter: 5,
        } 
    },

    computed:{
        ...mapState(['myPharmacyId']),
        
		items(){
			return this.nameFilter != '' || this.ratingFilter != 0 ? 
						this.data.filter( e => e.drugName.toLowerCase().includes(this.nameFilter.toLowerCase()) && 
										( e.rating >= 0 && e.rating <= this.ratingFilter)) 
					: this.data
		}
	},

    mounted(){
        this.getDrugsInPharmacy()

        this.$root.$on('update-pharmacy-drugs', () => {
            this.getDrugsInPharmacy()
        })

        this.$root.$on('drug-name', (name) => {
			this.nameFilter = name
		})

		this.$root.$on('drug-rating', (rating) => {
			this.ratingFilter = rating
		})

        if (this.loggedUserRole == 'ROLE_PHARMACY_ADMIN')
            this.fields = [
                { key: 'drugName', label: 'Naziv leka', sortable: true}, 
                { key: 'drugCode', label: 'Šifra leka', sortable: true}, 
                { key: 'loyaltyPoints', label: 'Loyalty poena', sortable: true}, 
                { key: 'quantity', label: 'Količina na stanju', sortable: true},
                { key: 'rating', label: 'Ocena', sortable: true},
                { key: 'price', label: 'Cena', sortable: true},
                { key: 'actions', label: 'Akcije'}
            ]
        else
            this.fields = [
                { key: 'drugName', label: 'Naziv leka', sortable: true}, 
                { key: 'loyaltyPoints', label: 'Loyalty poena', sortable: true}, 
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

                    this.data = res.data
                    console.log(this.items)
                })
                .catch((error) => {
                    this.isBusy = false
                    
                    console.log(error)
                    if (error.response.status == 403 || error.response.status == 401)
                        this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                    else if (error.response.status == 404)
                        this.toast('danger', 'Neuspešno', 'Trenutno nema lekova u sistemu.')
                    else {
                        this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
                        window.location.reload() 
                    } 
                })
        },

        removeDrugFromPharmacy(drug){
            this.$http
                .delete('/drugInPharmacy/removeFromPharmacy/' + drug.drugId + '/' + this.pharmacyId)
                .then( res => {
                    console.log(res)
                    this.toast('success', 'Uspešno', 'Uspešno ste uklonili lek iz apoteke!')
                    this.getDrugsInPharmacy()
                })
                .catch((error) => {
                    console.log(error)

                    if (error.response.status == 403)
                        this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                    else if (error.response.status == 400)
                        this.toast('danger', 'Neuspešno', 'Nije moguće ukloniti lek. Lek je rezervisan, a korisnik ga još nije preuzeo.')
                    else 
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
        }
    },

    components:{
        PharmacyReserveDrugModal: () => import('../../views/pharmacy/PharmacyReserveDrugModal.vue')
    }
}
</script>