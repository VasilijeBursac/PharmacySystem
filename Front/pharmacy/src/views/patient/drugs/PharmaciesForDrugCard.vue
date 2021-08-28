<template>
    <div class="pharmaciesForDrugModal">
        <b-card class="overflow-hidden">
            <h6 class="h6 float-left text-left mb-4">Apoteke u kojima je lek dostupan</h6>
            <b-table striped hover :busy="isBusy" :items="items" :fields="fields">
                <template #table-busy>
                    <div class="text-center text-danger my-2">
                        <b-spinner class="align-middle"></b-spinner>
                        <strong> Učitavanje...</strong>
                    </div>
                </template>

                <template #cell(akcije)="row">
                    <b-button size="sm" @click="displayPharmacyProfile(row.item)">
                        Prikaži detaljnije
                    </b-button>
                </template>
            </b-table>
        </b-card>
    </div>
</template>

<script>
export default {
    props: ['drugId'],
    data(){
        return{
            items: [],
			fields:['ime', 'adresa', 'cena', 'akcije'],
            isBusy: false
        }
    },
    methods: {
        toast(message, title, variant){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        },

        displayPharmacyProfile(pharmacy) {
            this.$router.push({name: 'PharmacyProfile', params: { id: pharmacy.pharmacyId }});
        }
    },
    mounted(){
        this.$http
        .get('drugInPharmacy/pharmaciesForDrug/' + this.drugId) 
        .then( res => {
            if(res.status == 200){
                let data = []
                    res.data.forEach(element => {
                        data.push({ 
                            ime: element.pharmacyName, 
                            adresa: element.address, 
                            cena: element.drugPrice,
                            pharmacyId: element.pharmacyId
                        })
                    });
                    this.items = data

                    this.isBusy = false
            }                        
        })
        .catch( (error) => {
            this.isBusy = false
            
            if(error.response.status == 404)
                this.toast('Trenutno ni u jendoj apoteci nema leka na stanju!', 'Neuspešno', 'danger')
            else this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
        })
    }
}
</script>