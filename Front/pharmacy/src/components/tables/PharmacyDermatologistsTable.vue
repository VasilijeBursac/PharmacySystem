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
                size="sm" variant="danger" @click="removeDermatologistFromPharmacy(row.item)">
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
    props: {
        pharmacyId: {
            default: null
        },
        isTableOnPharmacyProfile: {
            type: Boolean,
            default: false
        }
    },
    data: function() {
        return{
            loggedUserRole: this.$store.getters.getUserRole,

            data: [],
            fields: [],

            isBusy: true,

            nameFilter: '',
            surnameFilter: '',
            pharmacyNameFilter: '',
			ratingFilter: 5,
        }
    },

    computed: {
        ...mapState(['myPharmacyId']),

        items() {
            return this.nameFilter != '' || this.surnameFilter != '' || this.pharmacyNameFilter != '' || this.ratingFilter != 0 ? 
                    this.data.filter( e => e.name.toLowerCase().includes(this.nameFilter.toLowerCase()) && 
                                    e.surname.toLowerCase().includes(this.surnameFilter.toLowerCase()) && 
                                    e.pharmacies.some(pharmacy => pharmacy.name.toLowerCase().includes(this.pharmacyNameFilter.toLowerCase())) &&
                                    ( e.rating >= 0 && e.rating <= this.ratingFilter)) 
					: this.data
        }
    },
    
    mounted(){
        this.getDermatologists()

        if (this.loggedUserRole == 'ROLE_PHARMACY_ADMIN' && this.myPharmacyId == this.pharmacyId)
            this.fields = [
                { key: 'name', label: 'Ime', sortable: true}, 
                { key: 'surname', label: 'Prezime', sortable: true}, 
                { key: 'email', label: 'Email', sortable: true}, 
                // { key: 'phoneNumber', label: 'Broj telefona', sortable: true},
                { key: 'rating', label: 'Ocena', sortable: true},
                { key: 'price', label: 'Cena pregleda', sortable: true},
                { key: 'displayPharmacies', label: 'Apoteke u kojima radi'},
                { key: 'actions', label: 'Akcije'}
            ]
        else
            this.fields = [
                { key: 'name', label: 'Ime', sortable: true}, 
                { key: 'surname', label: 'Prezime', sortable: true}, 
                { key: 'rating', label: 'Ocena', sortable: true},
                { key: 'price', label: 'Cena pregleda', sortable: true},
                { key: 'displayPharmacies', label: 'Apoteke u kojima radi'},
            ]

        this.$root.$on('update-pharmacy-dermatologists', () => {
            this.getDermatologists()
        })

        this.$root.$on('employee-name', (name) => {
			this.nameFilter = name
		})

        this.$root.$on('employee-surname', (surname) => {
			this.surnameFilter = surname
		})

        this.$root.$on('pharmacy-name', (pharmacyName) => {
			this.pharmacyNameFilter = pharmacyName
		})

		this.$root.$on('employee-rating', (rating) => {
			this.ratingFilter = rating
		})
    },

    methods: {
        getDermatologists() {
            let dermatologistsPath
            if(this.loggedUserRole == 'ROLE_PHARMACY_ADMIN' || this.isTableOnPharmacyProfile)
                dermatologistsPath = '/dermatologist/byPharmacyId/' + this.myPharmacyId
            else
                dermatologistsPath = '/dermatologist'

            this.$http
                .get(dermatologistsPath)
                .then( res => {
                    this.isBusy = false

                    this.data = res.data
                    console.log(this.items)
                })
                .catch((error) => {
                    this.isBusy = false
                    
                    console.log(error)
                    if (error.response.status == 403 || error.response.status == 401) {
                        this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                        window.location.reload()
                    }
                    else if (error.response.status == 404)
                        this.toast('danger', 'Neuspešno', 'Trenutno nema dermatologa u sistemu.')
                    else 
                        this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
                })
        },

        removeDermatologistFromPharmacy(dermatologist){
            this.$http
            .delete('dermatologist/removeFromPharmacy/' + dermatologist.id + "/" + this.pharmacyId)
            .then( () => {
                this.toast('success', 'Uspešno', 'Uspešno ste uklonili dermatologa.')
                this.getDermatologists()
                this.$root.$emit('update-not-pharmacy-dermatologists')
            })
            .catch( (error) => {
                if (error.response.status == 403 || error.response.status == 401)
                    this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                else if (error.response.status == 400)
                    this.toast('danger', 'Neuspešno', 'Nije moguće ukloniti dermatologa. Dermatolog ima zakazane preglede koja treba da održi.')
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