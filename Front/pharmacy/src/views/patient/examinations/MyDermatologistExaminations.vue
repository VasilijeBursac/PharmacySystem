<template>
    <div class="container">
        <h6 class="h6 text-left mb-3">Prestojeći termini</h6>        

        <b-table ref="future" striped hover :busy="tableBusy" :items="futureData" :fields="fields">
            <template #table-busy>
				<div class="text-center text-danger my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong> Učitavanje...</strong>
				</div>
			</template>

            <template #cell(akcije)="row">
                <b-button @click="cancel(row)" size="sm" variant="danger" >
                    <b-icon-x></b-icon-x>
                    Otkaži pregled
                </b-button>
            </template>
        </b-table>

        <h6 class="h6 text-left mt-5 mb-3">Završeni termini</h6>
        <b-table ref="past" striped hover :items="pastData" :fields="fields">
            <template #cell(akcije)="row">
                <b-button @click="schedule(row)" size="sm" >
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
			futureData: [],
			pastData: [],
			fields:['termin', 'dermatolog', {key:'ocena', sortable:true}, {key:'cena', sortable:true}, 'akcije'],

            showModal: false,

            tableBusy: true,
		}
	},
    methods:{
		cancel(row){

            this.tableBusy = true

			this.$http
                .get('examination/cancel/' + row.item.id)
                .then( res => {
                    if(res.status == 200){
                        this.toast('Uspešno ste otkazali pregled!', 'Uspešno', 'success')
                        this.futureData.splice(row.index, 1)
                        this.$refs.future.refresh()
                        this.tableBusy = false
                    }            
                })
                .catch( (error) => {
                    this.tableBusy = false

                    if(error.response.status == 400){
                        this.toast('Ne možete otkazati pregled do kojeg je ostalo manje od 24h!', 'Neuspešno', 'danger')
                    }else this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
                })
		},
		toast(message, title, variant){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        }
	},
    mounted(){
		this.$http
            .post('examination/future', {type: 'DermatologistExamination'})
            .then( res => {
               // console.log(res.data)
				let data = []
                res.data.forEach(element => {
					data.push({ 
						termin: new Date(element.term).toLocaleString(), 
						dermatolog: element.employee, 
						ocena: element.employeeRating != 0 ? element.employeeRating : 'Nema ocenu',
						cena: element.price + ' din',
						id: element.examinationId
					})
				});
				this.futureData = data
                this.tableBusy = false

                if(data.length == 0){
                    this.toast('Nažalost trenutno ne postoji nijedna apoteka u sistemu.', 'Neuspešno', 'danger')
                }
            })

        this.$http
            .post('examination/examination-history/',{
                type: 'DermatologistExamination'
            })
            .then( res => {
				let data = []
                console.log(res.data)
                res.data.forEach(element => {
					data.push({ 
						termin: new Date(element.term).toLocaleString(), 
						dermatolog: element.employee, 
						ocena: element.employeeRating != 0 ? element.employeeRating : 'Nema ocenu',
						cena: element.price + ' din',
						id: element.examinationId
					})
				});
				this.pastData = data
            })
	}
}
</script>