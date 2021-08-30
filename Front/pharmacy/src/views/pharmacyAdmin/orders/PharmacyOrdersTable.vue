<template>
    <div>
        <b-table striped hover :busy="isBusy" :items="items | formatDate | formatOrderStatus" 
        :fields="fields" class="text-middle mt-0">
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

            data: [],
            fields: [
                {key:'pharmacyAdminFullName', label: 'Autor narudžbenice', sortable:true}, 
				{key:'deadline', label: 'Rok za ponude', sortable:true},
                {key:'status', label: 'Status narudžbenice', sortable:true},
                {key: 'actions', label: 'Akcije'} 
            ],

            isBusy: true,

            orderStatusFilter: ""
        } 
    },

    computed: {
        items(){
			return this.orderStatusFilter !== "" ? 
						this.data.filter( e => e.status === this.orderStatusFilter)
					: this.data
		}
    },

    mounted(){
        this.getOrdersForPharmacy()

        this.$root.$on('update-pharmacy-orders', () => {
            this.getOrdersForPharmacy()
        })

        this.$root.$on('filter-orders', (orderStatus) => {
            this.orderStatusFilter = orderStatus
        })
    },

    methods:{
        getOrdersForPharmacy() {
            this.$http
                .get('/orders/byPharmacyId/' + this.pharmacyId)
                .then( res => {
                    this.isBusy = false

                    this.data = res.data
                    console.log(this.items)
                })
                .catch((error) => {
                    this.isBusy = false
                    
                    console.log(error)
                    if (error.response.status == 403 || error.response.status == 401)
                        this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                    else if (error.response.status == 404)
                        this.toast('danger', 'Neuspešno', 'Trenutno nema narudžbenica za ovu apoteku.')
                    else {
                        this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
                        window.location.reload()
                    } 
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
                if(typeof(item.deadline) == "number"){
                    let formatedDate = new Date(item.deadline).toLocaleDateString()
                    items[index] = {...items[index], deadline: formatedDate}
                }
            })
            return items
        },

        formatOrderStatus: function(items) {
            items.forEach((item, index) => {
                let formatedStatus

                if (item.status === "Waiting")
                    formatedStatus = "Čeka ponude"
                else if (item.status === "Fulfilled")
                    formatedStatus = "Obrađena"
                else
                    formatedStatus = item.status

                items[index] = {...items[index], 
                status: formatedStatus}
            })
            return items
        }
    },
}
</script>

<style>

</style>