<template>
    <div class="drugs-table">
        <b-table  striped hover :busy="isBusy" :items="items" :fields="fields">
			<template #table-busy>
				<div class="text-center text-danger my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong> Učitavanje...</strong>
				</div>
			</template>

			<template #cell(akcije)="row">
				<b-button size="sm" @click="displayDrugInformations(row)">
					Prikaži detaljnije
				</b-button>
			</template>
        </b-table>
    </div>
</template>

<script>
export default {
	data() {
		return {
			data: [],
			fields:['ime', 'tip', 'ocenaIspis', 'akcije'],
			nameFilter: '',
			ratingFilter: 5,

			isBusy: true
		}
	},
	methods:{
		toast(message, title, variant){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        },
        displayDrugInformations(row){
                this.$router.push({name: 'DrugInformations', params: { id: row.item.id, name: row.item.ime }});
            }
        },
	computed:{
		items(){
			return this.nameFilter != '' || this.cityFilter != '' || this.ratingFilter != 0 ? 
						this.data.filter( e => e.ime.toLowerCase().includes(this.nameFilter.toLowerCase()) && 
										( e.ocena >= 0 && e.ocena <= this.ratingFilter)) 
					: this.data
		}
	},
	mounted(){

		this.$http
			.get('drugs/')
			.then( res => {
				if(res.status == 200){
					let data = []
                        res.data.forEach(element => {
                            data.push({ 
                                ime: element.name, 
                                tip: element.type,
                                ocena: element.rating,
								ocenaIspis: element.rating == 0 ? 'Nema ocenu' : element.rating,
                                id: element.id
                            })
                        });
                        this.data = data

						this.isBusy = false
				}                        
			})
			.catch( (error) => {
				this.isBusy = false
				
				if(error.response.status != 404)
                    this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger') 
                else this.toast('Trenutno ne postoji nijedan lek u sistemu!','Neuspešno', 'danger') 
			})

		this.$root.$on('drug-name', (name) => {
			this.nameFilter = name
		})

		this.$root.$on('drug-rating', (rating) => {
			this.ratingFilter = rating
		})
	}
}
</script>