<template>
    <div class="container">
        <h4 class="h4 align-middle mt-4">Slobodni termini kod dermatologa</h4>
        <h5 class="h5 align-middle my-3 text-success">Apoteka Jankovic</h5>
        <b-table ref="table" striped hover :items="items" :fields="fields">
            <template #cell(akcije)="row">
                <b-button @click="schedule(row)" size="sm" >
                    Zakaži pregled
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
			fields:['termin', 'dermatolog', {key:'ocena', sortable:true}, {key:'cena', sortable:true}, 'akcije'],
		}
	},
	methods:{
		schedule(row){
			this.$http
            .get('examination/schedule/' + row.item.id)
            .then( res => {
				if(res.status == 200){
					this.toast()
					this.data.splice(row.index, 1)
					this.$refs.table.refresh()
				}
					
            })
		},
		toast(){
            this.$bvToast.toast(`Uspešno ste zakazali pregled!`, {
                title: 'Uspešno!',
                variant: 'success',
                autoHideDelay: 5000
            })
        }
	},
	computed:{
		items(){
			return this.data
		}
	},
	mounted(){
		this.$http
            .post('pharmacy/1/dermatologist-examinations', {
				type: 'DermatologistExamination'
			})
            .then( res => {
				let data = []
                res.data.forEach(element => {
					data.push({ 
						termin: new Date(element.term).toLocaleString(), 
						dermatolog: element.employee, 
						ocena: element.employeeRating != 0 ? element.employeeRating : 'Nema ocenu',
						cena: element.price,
						id: element.examinationId
						})
				});
				this.data = data
            })
	}
}
</script>