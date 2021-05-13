<template>
    <div class="container mt-4">
        <h5 class="h5 float-center text-center">Sve moje ponude</h5>
            <b-form-group id = "filteri" label="Filtriraj ponude po statusu:" class="float-left text-left" v-slot="{ ariaDescribedby }">
                <b-form-checkbox-group
                    id="checkbox-group"
                    v-model="selected"
                    :aria-describedby="ariaDescribedby"
                    name="flavour-2"
                >
                    <b-form-checkbox value="Prihvacena">Prihvacena</b-form-checkbox>
                    <b-form-checkbox value="Odbijena">Odbijena</b-form-checkbox>
                    <b-form-checkbox value="Ceka na odgovor">Ceka na odgovor</b-form-checkbox>
                </b-form-checkbox-group>               
            </b-form-group>
        <b-table   striped hover :items="data" :fields="fields">
        </b-table>
    </div>
</template>

<script>

export default {
    data(){
        return {
            items: [],
			fields: [   
                { label: "Ime apoteke", key: "pharmacyName", sortable: true },
                { label: "Rok isporuke", key: "deliveryDeadline", sortable: true },
                { label: "Ukupna cena", key: "totalPrice", sortable: true },
                { label: "Status", key: "offerStatus", sortable: true },
            ],
            
            selected : []
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
    },
    computed:{
        data(){
            var filteredItems = []
            if(this.selected.length != 0){
                this.selected.forEach(s  => {
                    var newArray = this.items.filter(item => {
                    return item.offerStatus == s
                    })
                    newArray.forEach(n => filteredItems.push(n))                                 
                })
                return filteredItems
            } else return this.items            
            
        }
    },
    mounted(){
        this.$http
            .get('offers/allOffersForSupplier')
            .then( res => {
                if(res.status == 200){
                     let data = []
                        res.data.forEach(element => {
                            data.push({ 
                                'pharmacyName': element.pharmacyName,
                                'deliveryDeadline': new Date(element.deliveryDeadline).toLocaleDateString(),
                                'totalPrice': element.totalPrice,
                                'offerStatus': element.offerStatus
                            })
                        });
                        this.items = data
                }        
            })
            .catch( (error) => {
                if(error.response.status == 400)
                    this.toast('Nemajte jos uvek nijednu poslatu ponudu!','Neuspešno', 'danger')  
                else if(error.response.status != 200)
                    this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
            })
    }
}
</script>
<style>

#filteri {
    margin: 25px 0px
}

</style>