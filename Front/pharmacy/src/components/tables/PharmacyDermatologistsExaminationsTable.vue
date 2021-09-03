<template>
    <div class="pharmacy-dermatologists-examinations">
        <b-table ref="table" striped hover :busy="isBusy" 
        :items="items"
        :fields="loggedUserRole == 'ROLE_PATIENT' ? fieldsPatient : fields" 
        class="text-middle mt-0">
            <template #table-busy>
				<div class="text-center text-danger my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong> Učitavanje...</strong>
				</div>
			</template>

            <template #cell(actions)="row">
                <b-button v-if="loggedUserRole == 'ROLE_PATIENT'" 
                variant="success" size="sm" @click="schedule(row)">
					<b-icon icon="calendar2-week"></b-icon>
                    Zakaži pregled
                </b-button>
            </template>
        </b-table>
    </div>    
</template>

<script>
import { mapState } from 'vuex';

export default {
    props: ['pharmacyId'],
	data() {
		return {
            loggedUserRole: this.$store.getters.getUserRole,

			data: [],
            fields: [
                {key:'termin', label: 'Termin', sortable:true},
                {key:'trajanje', label: 'Trajanje', sortable:true}, 
				{key:'dermatolog', label: 'Dermatolog',sortable:true},  	
                {key:'ocena', label: 'Ocena', sortable:true}, 
				{key:'cena sa popustom', label: 'Cena pregleda', sortable:true}
            ],
			fieldsPatient: [
				{key:'termin', label: 'Termin', sortable:true},
                {key:'trajanje', label: 'Trajanje', sortable:true}, 
				{key:'dermatolog', label: 'Dermatolog',sortable:true},  	
                {key:'ocena', label: 'Ocena', sortable:true}, 
				{key:'cena sa popustom', label: 'Cena pregleda sa popustom', sortable:true},
                {key:'actions', label: 'Akcije'}
			],

            isBusy: true,
		}
	},

    computed:{
        ...mapState(['myPharmacyId']),

		items(){
			return this.data
		}
	},

    mounted(){
		this.getAvailableDermatologistsExaminations()

        this.$root.$on('update-pharmacy-dermatologists-examinations', () => {
            this.getAvailableDermatologistsExaminations()
        })
	},

	methods:{
        getAvailableDermatologistsExaminations() {
            this.$http
            .post('pharmacy/' + this.pharmacyId + '/dermatologist-examinations', {
				type: 'DermatologistExamination'
			})
            .then( res => {
                this.isBusy = false

				let data = []
                res.data.forEach(element => {
					data.push({ 
						termin: new Date(element.term).toLocaleString(),
                        trajanje: element.duration, 
						dermatolog: element.employee, 
						ocena: element.employeeRating != 0 ? element.employeeRating : 'Nema ocenu',
						'cena sa popustom': element.price,
						id: element.examinationId
						})
				});
				this.data = data
            })
            .catch((error) => {
                console.log(error)
                this.isBusy = false

                if (error.response.status == 403 || error.response.status == 401)
                    this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                else if (error.response.status == 404)
                    this.toast('danger', 'Neuspešno', 'Trenutno nema slobodnih termina kod dermatologa.')
                else 
                    this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')  
            })
        },

		schedule(row){
			this.$http
            .get('examination/schedule/' + row.item.id)
            .then( res => {
				if(res.status == 200){
                    this.toast('success', 'Uspešno!', `Uspešno ste zakazali pregled!`)
					this.data.splice(row.index, 1)
					this.$refs.table.refresh()
				}
            })
            .catch((error) => {
                console.log(error)

                if (error.response.status == 403)
                    this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
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
        }
	},
	
	
}
</script>