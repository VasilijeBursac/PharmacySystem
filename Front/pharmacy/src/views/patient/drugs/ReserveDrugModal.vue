<template>
    <div>
        <b-button v-b-modal="'my-modal'" variant="success">
            <b-icon-heart shift-h="-3"></b-icon-heart>
            Rezerviši lek
        </b-button>

        <b-modal id="my-modal" size="lg" hide-footer title="Rezervacija leka">
            <b-form>
                <b-form-group id="drug-group" label="Lek:" label-for="drug-input">
                    <b-form-select v-model="selected" :options="options"></b-form-select>
                </b-form-group>

                <b-form-group id="date-group" label="Rok za preuzimanje:" label-for="date-input">
                    <b-form-datepicker id="date-input" v-model="date" placeholder="Izaberite datum" class="mb-2"></b-form-datepicker>
                </b-form-group>

                <b-button @click="searchDrug()" block variant="success">
                    Pretraži lek
                </b-button>
            </b-form>

            <h6 class="h6 my-3">Izaberite apoteku</h6>
            <b-table striped hover :items="items" :fields="fields" class="mt-3">
                <template #cell(akcije)="row">
                    <b-button @click="reserveDrug(row)" block size="sm" >
                        Rezerviši
                    </b-button>
                </template>
            </b-table>             
        </b-modal>
    </div>    
</template>

<script>
export default {
    data(){
        return {
            items: [],
			fields:['ime', 'grad', {key: 'ocena', sortable: true}, 'akcije'],

            selected: null,
            options: [],            
            date: ''
        }
    },
    methods:{
        searchDrug(){

            if( this.selected == null || this.date == ''){
                this.toast('Morate uneti i naziv leka i rok za preuzimanje kako bi pretražili lek!', 'Neuspešno', 'danger')
                return;
            }

            this.$http
                .get('reservation/search?drug=' + this.selected)
                .then( res => {
                    if(res.status == 200){
                        let data = []
                        res.data.forEach(element => {
                            data.push({ 
                                ime: element.pharmacyName,
                                grad: element.pharmacyCity,
                                ocena: element.pharmacyRating != 0 ? element.pharmacyRating : 'Nema ocenu',
                                id: element.drugInPharmacyId
                            })
                        });
                        this.items = data
                    }            
                })
                .catch( () => {
                    this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
                })
        },
        reserveDrug(row){
            this.$http
                .post('reservation/reserve', {
                    'drugInPharmacyId': row.item.id,
                    'deadline': new Date(this.date + ' 00:00').getTime()
                })
                .then( res => {
                    if(res.status == 200){
                        this.closeModal();
                        this.$router.go(0)
                        this.toast('Uspešno ste rezervisali lek!', 'Uspešno', 'success')
                    }        
                })
                .catch( (error) => {
                    if(error.response.status != 200)
                        this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
                })
        },
        closeModal(){
            this.$bvModal.hide('my-modal')
            this.selected = null
            this.date = ''
            this.items = []
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
            .get('drugs')
            .then( (res) => {
                console.log(res.data)
                if(res.status == 200){
                    this.options = []
                    this.options.push({
                            value: null,
                            text: 'Izaberite lek'
                        })
                    res.data.forEach(drug => {
                        this.options.push({
                            value: drug.id,
                            text: drug.name
                        })
                    });
                }
            })
    }
}
</script>