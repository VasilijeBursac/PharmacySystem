<template>
    <div class="pharmacy-table">
        <b-table striped hover :items="items" :fields="fields">
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
			data: [
				{ime: 'Apoteka', adresa: 'Ivo Lole Ribara', grad: 'Novi Sad', ocena: 4.5},
				{ime: 'Apoteka', adresa: 'Ivo Lole Ribara 2', grad: 'Beograd', ocena: 4.5},
				{ime: 'Apoteka', adresa: 'Ivo Lole Ribara', grad: 'Nis', ocena: 4.5},
				{ime: 'Apoteka', adresa: 'Ivo Lole Ribara', grad: 'Novi Sad', ocena: 4}
			],
			fields:['ime', 'adresa', 'ocena', 'akcije'],
			nameFilter: '',
			cityFilter: '',
			ratingFilter: 5
		}
	},
	computed:{
		items(){
			return this.nameFilter || this.cityFilter || this.ratingFilter != 0 ? 
						this.data.filter( e => e.ime.toLowerCase().includes(this.nameFilter.toLowerCase()) && 
										( e.ocena >= 0 && e.ocena <= this.ratingFilter) && 
										e.grad.toLowerCase().includes(this.cityFilter.toLowerCase())) 
					: this.data
		}
	},
	mounted(){
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