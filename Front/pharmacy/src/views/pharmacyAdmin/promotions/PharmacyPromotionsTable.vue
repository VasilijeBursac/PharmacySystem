<template>
    <div>
        <b-table striped hover :busy="isBusy" :items="items | formatDate" :fields="fields"  class="text-middle mt-0">
            <template #table-busy>
				<div class="text-center text-danger my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong> Učitavanje...</strong>
				</div>
			</template>

            <template #cell(actions)="row">
                <b-button
                    size="sm" variant="danger" class="ml-2" @click="deletePromotion(row.item)">
                    <b-icon icon="x"></b-icon>
                    Ukloni
                </b-button>
            </template>
        </b-table>
    </div>
</template>

<script>
export default {
    props: ['pharmacyId'],
    data: function() {
        return{
            loggedUserRole: this.$store.getters.getUserRole,

            items: [],
            fields: [
                {key:'dateInterval.startDate', label: 'Početak promocije', sortable:true}, 
				{key:'dateInterval.endDate', label: 'Završetak promocije', sortable:true},
                {key:'description', label: 'Opis promocije', sortable:true},
                { key: 'actions', label: 'Akcije'} 
            ],

            isBusy: true,
        } 
    },

    mounted(){
        this.getPromotionsForPharmacy()

        this.$root.$on('update-pharmacy-promotions', () => {
            this.getPromotionsForPharmacy()
        })
    },

    methods:{
        getPromotionsForPharmacy() {
            this.$http
                .get('/promotion/byPharmacyId/' + this.pharmacyId)
                .then( res => {
                    this.isBusy = false

                    this.items = res.data
                    console.log(this.items)
                })
                .catch((error) => {
                    this.isBusy = false
                    
                    console.log(error)
                    if (error.response.status == 403 || error.response.status == 401)
                        this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                    else if (error.response.status == 404)
                        this.toast('danger', 'Neuspešno', 'Trenutno nema promocija za ovu apoteku.')
                    else 
                        this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')  
                }) 
        },

        deletePromotion(promotion) {
            this.$http
                .delete('/promotion/' + promotion.id)
                .then( () => {
                    this.getPromotionsForPharmacy()
                    this.toast('success', 'Uspešno', 'Uspešno ste uklonili promociju.')
                })
                .catch((error) => {
                    console.log(error)
                    if (error.response.status == 403 || error.response.status == 401)
                        this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                    else 
                        this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')  
                }) 
        },

        toast(variant, title, message){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        }
    },

    filters: {
        formatDate: function(items) {
            items.forEach((item, index) => {
                let formatedStartDate = new Date(item.dateInterval.startDate).toLocaleDateString()
                let formatedEndDate = new Date(item.dateInterval.endDate).toLocaleDateString()
                items[index] = {...items[index], 
                dateInterval: {startDate: formatedStartDate, 
                endDate: formatedEndDate}}
            })
            return items
        }
    },
}
</script>

<style>

</style>