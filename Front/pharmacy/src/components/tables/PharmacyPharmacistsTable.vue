<template>
    <div class="pharmacy-pharmacists">
        <b-table striped hover :busy="isBusy" :items="items | formatRating | formatPrice" :fields="fields" class="text-middle mt-0">
			<template #table-busy>
				<div class="text-center text-danger my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong> Učitavanje...</strong>
				</div>
			</template>
            
            <template #cell(actions)="row">
				<b-button v-if="loggedUserRole == 'ROLE_PHARMACY_ADMIN' && myPharmacyId == pharmacyId"
                size="sm" variant="danger" @click="removePharmacistFromPharmacy(row.item)">
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
                                    e.pharmacyName.toLowerCase().includes(this.pharmacyNameFilter.toLowerCase()) &&
                                    ( e.rating >= 0 && e.rating <= this.ratingFilter)) 
					: this.data
        }
    },
    
    mounted(){
        this.getPharmacists()

        if (this.loggedUserRole == 'ROLE_PHARMACY_ADMIN' && this.myPharmacyId == this.pharmacyId)
            this.fields = [
                { key: 'name', label: 'Ime', sortable: true}, 
                { key: 'surname', label: 'Prezime', sortable: true}, 
                { key: 'email', label: 'Email', sortable: true}, 
                // { key: 'phoneNumber', label: 'Broj telefona', sortable: true},
                { key: 'rating', label: 'Ocena', sortable: true},
                { key: 'price', label: 'Cena pregleda', sortable: true},
                { key: 'actions', label: 'Akcije'}
            ]
        else
            this.fields = [
                { key: 'name', label: 'Ime', sortable: true}, 
                { key: 'surname', label: 'Prezime', sortable: true},
                { key: 'rating', label: 'Ocena', sortable: true},
                { key: 'price', label: 'Cena pregleda', sortable: true},
                { key: 'pharmacyName', label: 'Apoteka u kojoj radi', sortable: true}
            ]

        this.$root.$on('update-pharmacy-pharmacists', () => {
            this.getPharmacists()
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
        getPharmacists() {
            let pharmacistsPath
            if(this.loggedUserRole == 'ROLE_PHARMACY_ADMIN' || this.isTableOnPharmacyProfile)
                pharmacistsPath = '/pharmacist/byPharmacyId/' + this.pharmacyId
            else
                pharmacistsPath = '/pharmacist'

            this.$http
                .get(pharmacistsPath)
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
                        this.toast('danger', 'Neuspešno', 'Trenutno nema farmaceuta u sistemu.')
                    else 
                        this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
                })
        },

        removePharmacistFromPharmacy(pharmacist) {
            this.$http
            .delete('pharmacist/removeFromPharmacy/' + pharmacist.id)
            .then( () => {
                this.toast('success', 'Uspešno', 'Uspešno ste uklonili farmaceuta.')
                this.getPharmacists()
            })
            .catch( (error) => {
                if (error.response.status == 403 || error.response.status == 401)
                    this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                else if (error.response.status == 400)
                    this.toast('danger', 'Neuspešno', 'Nije moguće ukloniti farmaceuta. Farmaceut ima zakazana savetovanja koja treba da održi.')
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