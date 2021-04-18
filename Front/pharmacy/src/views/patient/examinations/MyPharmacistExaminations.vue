<template>
    <div class="container">
        <h6 class="h6 float-left text-left mt-1">Prestojeći termini</h6>

        <ScheduleModal class="float-right mt-n2 mb-3"/>

        <b-table ref="future" striped hover :busy="isBusy" :items="futureData" :fields="fields">
            <template #table-busy>
				<div class="text-center text-danger my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong> Učitavanje...</strong>
				</div>
			</template>

            <template #cell(akcije)="row">
                <b-button @click="cancel(row)" size="sm" variant="danger" >
                    <b-icon-x></b-icon-x>
                    Otkaži savetovanje
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
import ScheduleModal from './ScheduleModal.vue'

export default {
	data() {
		return {
			futureData: [],
			pastData: [],
			fields:['termin', 'farmaceut', {key:'ocena', sortable:true}, {key:'cena', sortable:true}, 'akcije'],

            isBusy: true
		}
	},
    methods:{
		cancel(row){

            this.isBusy = true

			this.$http
                .get('examination/cancel/' + row.item.id)
                .then( res => {
                    if(res.status == 200){
                        this.toast('Uspešno ste otkazali savetovanje!', 'Uspešno', 'success')
                        this.futureData.splice(row.index, 1)
                        this.$refs.future.refresh()
                        this.isBusy = false
                    }            
                })
                .catch( (error) => {
                    this.isBusy = false
                    
                    if(error.response.status == 400){
                        this.toast('Ne možete otkazati savetovanje do kojeg je ostalo manje od 24h!', 'Neuspešno', 'danger')
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
            .post('examination/future', {type: 'PharmacistExamination'})
            .then( res => {
				let data = []
                res.data.forEach(element => {
					data.push({ 
						termin: new Date(element.term).toLocaleString(), 
						farmaceut: element.employee, 
						ocena: element.employeeRating != 0 ? element.employeeRating : 'Nema ocenu',
						cena: element.price + ' din',
						id: element.examinationId
					})
				});
				this.futureData = data

                this.isBusy = false

                if(data.length == 0){
                    this.toast('Nažalost trenutno ne postoji nijedna apoteka u sistemu.', 'Neuspešno', 'danger')
                }
            })

        this.$http
            .post('examination/examination-history/',{
                type: 'PharmacistExamination'
            })
            .then( res => {
				let data = []
                console.log(res.data)
                res.data.forEach(element => {
					data.push({ 
						termin: new Date(element.term).toLocaleString(), 
						farmaceut: element.employee, 
						ocena: element.employeeRating != 0 ? element.employeeRating : 'Nema ocenu',
						cena: element.price + ' din',
						id: element.examinationId
					})
				});
				this.pastData = data
            })
	},
    components:{
        ScheduleModal
    }
}
</script>