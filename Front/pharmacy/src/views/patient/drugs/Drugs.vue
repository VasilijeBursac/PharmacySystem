<template>
    <div class="container mt-4">
        <h5 class="h5 float-left text-left">Rezervisani lekovi</h5>

        <ReserveDrugModal class="float-right mt-n2 mb-3"/>

        <b-table ref="drug" striped hover :busy="tableBusy" :items="items" :fields="fields">
            <template #table-busy>
                <div class="text-center text-danger my-2">
                <b-spinner class="align-middle"></b-spinner>
                    <strong> Učitavanje...</strong>
                </div>
            </template>

            <template #cell(akcije)="row">
                <b-button v-if="row.item.action != 'ACTION_NONE'" @click="cancel(row)" size="sm" variant="danger" >
                    <b-icon-x></b-icon-x>
                    Otkaži rezervaciju
                </b-button>
            </template>
        </b-table>
    </div>
</template>

<script>

export default {
    data(){
        return {
            items: [],
			fields:['lek', 'apoteka', 'rok za preuzimanje', 'status', 'akcije'],

            tableBusy: true
        }
    },
    methods:{
        cancel(row){
            this.tableBusy = true

            this.$http
                .get('reservation/cancel/' + row.item.id)
                .then( res => {
                    if(res.status == 200){
                        this.toast('Uspešno ste otkazali rezervaciju leka!', 'Uspešno', 'success')
                        
                        this.tableBusy = false
                        
                        setTimeout( () => {
                            this.$router.go(0)
                        }, 250)                        
                    }
                })
                .catch( (error) => {
                    this.tableBusy = false
                    if(error.response.status == 400){
                        this.toast('Ne možete otkazati rezervaciju leka do čijeg roka za preuzimanje je ostalo manje od 24h!', 'Neuspešno', 'danger')
                    }else this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
                })
        },
        toast(message, title, variant){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 7000
            })
        }
    },
    mounted(){
        this.$http
            .get('reservation/all')
            .then( res => {
                if(res.status == 200){
                    let data = []
                        res.data.forEach(element => {
                            data.push({ 
                                'lek': element.drugName,
                                'apoteka': element.pharmacyName,
                                'rok za preuzimanje': new Date(element.deadline).toLocaleString(),
                                'status': element.status == 'Canceled' ? 'Otkazano' : 'Rezervisano',
                                '_rowVariant': element.status == 'Canceled' ? 'danger' : 'default',
                                'action': element.status == 'Canceled' ? 'ACTION_NONE' : 'OK',
                                'id': element.id
                            })
                        });
                        this.items = data

                        this.tableBusy = false

                        if(data.length == 0){
                            this.toast('Nažalost trenutno ne postoji nijedna apoteka u sistemu.', 'Neuspešno', 'danger')
                        }
                }        
            })
            .catch( (error) => {
                this.tableBusy = false

                if(error.response.status != 200)
                    this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
            })
    },
    components:{
        ReserveDrugModal: () => import('./ReserveDrugModal.vue')
    }
}
</script>