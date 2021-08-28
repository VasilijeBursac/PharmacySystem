<template>
    <div class="container mt-4">
        <h5 class="h5 float-center text-center">Sve apoteke na koje sam pretplacen</h5>
        <b-table   striped hover :items="items" :fields="fields">
            <template #cell(Akcije)="row">
                <b-button @click="deleteSubscribedPharmacy(row)" size="sm" >
                    <b-icon-x></b-icon-x>
                    Otkaži pretplatu
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
			fields: [   
                { label: "Ime apoteke", key: "name", sortable: true },
                { label: "Adresa", key: "address", sortable: true },
                { label: "Grad", key: "city", sortable: true },
                { label: "Drzava", key: "country", sortable: true },
                'Akcije'
            ]
        }
    },
    methods:{
        deleteSubscribedPharmacy(row){
            this.$http
                .delete('patient/deleteSubscribedPharmacy/' + row.item.id)
                .then( res => {
                    var newArray = this.items.filter(pharmacy => {
                    return pharmacy.id !== row.item.id
                    })
                    this.items = newArray
                    if(res.status == 200)
                        this.toast('Uspešno ste otkazali pretplatu na akcije i promocije apoteke!', 'Uspešno', 'success')
                })
                .catch(error => {
                    if(error.response.status == 400)
                        this.toast('Greška prilikom otkazivanja pretplate!', 'Neuspešno', 'danger')
                    else this.toast('Desila se greška! Molimo pokušajte kasnije', 'Neuspešno', 'danger')
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
            .get('patient/getSubscribedPharmacies')
            .then( res => {
                if(res.status == 200){
                    this.items = res.data
                }        
            })
            .catch( (error) => {
                if(error.response.status == 404)
                    this.toast('Niste pretplaćeni ni na jednu apoteku!','Neuspešno', 'danger')  
            })
    }
}
</script>
<style>

#filteri {
    margin: 25px 0px
}

</style>