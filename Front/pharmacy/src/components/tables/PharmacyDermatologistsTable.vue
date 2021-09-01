<template>
    <div class="pharmacy-dermatologists">
        <b-table striped hover :busy="isBusy" :items="items | formatRating | formatPrice" :fields="fields" class="text-middle mt-0">
			<template #table-busy>
				<div class="text-center text-danger my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong> Učitavanje...</strong>
				</div>
			</template>
            
            <template #cell(displayPharmacies)="row">
                <b-dropdown text="Prikaži apoteke" size="sm" >
                    <b-dropdown-item v-for="pharmacy in row.item.pharmacies" :key="pharmacy.id" 
                    :to="{name: 'PharmacyProfile', params: { id: pharmacy.id }}" class="my-0">
                        {{ pharmacy.name }}
                    </b-dropdown-item>
                </b-dropdown>
			</template>

            <template #cell(actions)="row">
                <b-button v-if="loggedUserRole == 'ROLE_PHARMACY_ADMIN' && myPharmacyId == pharmacyId"
                size="sm" variant="danger" @click="displayDrugInformations(row.item)">
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

            items: [],
            fields: [],

            isBusy: true
        }
    },

    computed: {
        ...mapState(['myPharmacyId'])
    },
    
    mounted(){
        this.getDermatologistsInPharmacy()

        if (this.loggedUserRole == 'ROLE_PHARMACY_ADMIN' && this.myPharmacyId == this.pharmacyId)
            this.fields = [
                { key: 'name', label: 'Ime', sortable: true}, 
                { key: 'surname', label: 'Prezime', sortable: true}, 
                { key: 'email', label: 'Email', sortable: true}, 
                { key: 'phoneNumber', label: 'Broj telefona', sortable: true},
                { key: 'rating', label: 'Ocena', sortable: true},
                { key: 'price', label: 'Cena pregleda', sortable: true},
                { key: 'displayPharmacies', label: 'Apoteke u kojima je zaposlen'},
                { key: 'actions', label: 'Akcije'}
            ]
        else
            this.fields = [
                { key: 'name', label: 'Ime', sortable: true}, 
                { key: 'surname', label: 'Prezime', sortable: true}, 
                { key: 'rating', label: 'Ocena', sortable: true},
                { key: 'price', label: 'Cena pregleda', sortable: true},
                { key: 'displayPharmacies', label: 'Apoteke u kojima je zaposlen'},
            ]
    },

    methods: {
        getDermatologistsInPharmacy() {
            this.$http
                .get('/dermatologist/byPharmacyId/' + this.pharmacyId)
                .then( res => {
                    this.isBusy = false

                    this.items = res.data
                    console.log(this.items)
                })
                .catch((error) => {
                    this.isBusy = false
                    
                    console.log(error)
                    if (error.response.status == 403 || error.response.status == 401)
                        this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                    else if (error.response.status == 404)
                        this.toast('danger', 'Neuspešno', 'Trenutno nema dermatologa u sistemu.')
                    else 
                        this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
                })
        },

        toast(variant, title, message){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        },
    }
}
</script>

<style>

</style>