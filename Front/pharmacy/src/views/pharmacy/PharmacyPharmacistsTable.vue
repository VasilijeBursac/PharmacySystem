<template>
    <div class="pharmacy-pharmacists">
        <b-table striped hover :busy="isBusy" :items="items | formatRating" :fields="fields" class="text-middle mt-2">
			<template #table-busy>
				<div class="text-center text-danger my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong> Učitavanje...</strong>
				</div>
			</template>
            
            <template #cell(actions)="row">
				<b-button v-if="loggedUserRole == 'ROLE_PHARMACY_ADMIN'"
                size="sm" variant="danger" @click="displayDrugInformations(row.item)">
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

            isBusy: true
        }
    },
    
    mounted(){
        this.getPharmacistsInPharmacy()

        if (this.loggedUserRole == 'ROLE_PHARMACY_ADMIN')
            this.fields = [
                { key: 'name', label: 'Ime', sortable: true}, 
                { key: 'surname', label: 'Prezime', sortable: true}, 
                { key: 'email', label: 'Email', sortable: true}, 
                { key: 'phoneNumber', label: 'Broj telefona', sortable: true},
                { key: 'rating', label: 'Ocena', sortable: true},
                { key: 'price', label: 'Cena pregleda', sortable: true},
                { key: 'actions', label: 'Akcije'}
            ]
        else
            this.fields = [
                { key: 'name', label: 'Ime', sortable: true}, 
                { key: 'surname', label: 'Prezime', sortable: true}, 
                { key: 'rating', label: 'Ocena', sortable: true},
                { key: 'price', label: 'Cena pregleda', sortable: true}
            ]
    },

    methods: {
        getPharmacistsInPharmacy() {
            this.$http
                .get('/pharmacist/byPharmacyId/' + this.pharmacyId)
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

        toast(variant, title, message){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
            scroll(0,0)
        },
    }
}
</script>

<style>

</style>