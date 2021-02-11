<template>
    <div class="pharmacy-table">
        <b-table ref="pharmacyTable" striped hover :items="items" :fields="fields">
			<template #cell(akcije)="">
				<b-button size="sm" >
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
			fields:['ime', 'adresa', 'ocenaIspis', 'akcije'],
			nameFilter: '',
			cityFilter: '',
			ratingFilter: 5,
		}
	},
	methods:{
		toast(message, title, variant){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        }
	},
	computed:{
		items(){
			return this.nameFilter != '' || this.cityFilter != '' || this.ratingFilter != 0 ? 
						this.data.filter( e => e.ime.toLowerCase().includes(this.nameFilter.toLowerCase()) && 
										( e.ocena >= 0 && e.ocena <= this.ratingFilter) && 
										e.grad.toLowerCase().includes(this.cityFilter.toLowerCase())) 
					: this.data
		}
	},
	mounted(){

		this.$http
			.get('pharmacy/allPharmacies')
			.then( res => {
				if(res.status == 200){
					let data = []
                         res.data.forEach(element => {
                            data.push({ 
                                ime: element.name, 
                                grad: element.city,
								adresa: element.address, 
                                ocena: element.rating,
								ocenaIspis: element.rating == 0 ? 'Nema ocenu' : element.rating,
                                id: element.id
                            })
                        });
                        this.data = data

                        if(data.length == 0){
                            this.toast('Nažalost trenutno ne postoji nijedna apoteka u sistemu.', 'Neuspešno', 'danger')
                        }
				}                        
			})
			.catch( (error) => {
				if(error.response.status != 200)
					this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
			})

		this.$root.$on('pharmacy-name', (name) => {
			this.nameFilter = name
		})

		this.$root.$on('pharmacy-city', (city) => {
			this.cityFilter = city
		})

		this.$root.$on('pharmacy-rating', (rating) => {
			this.ratingFilter = rating
		})
	}
}
</script>