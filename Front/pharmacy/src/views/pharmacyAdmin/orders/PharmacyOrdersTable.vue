<template>
    <div>
        <AddEditOrderModal :pharmacyId="pharmacyId" :mode="modalMode" :order="selectedOrder" />

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
                    size="sm" variant="secondary" class="" @click="showOrderWithOffers(row.item)">
                    Prikaži narudžbenicu i ponude
                </b-button>

                <b-button v-if="row.item.status != 'Fulfilled'"
                    size="sm" variant="secondary" class="ml-2" @click="editOrder(row.item)">
                    <b-icon icon="pencil-square"></b-icon>
                    Izmeni
                </b-button>

                <b-button v-if="row.item.status != 'Fulfilled'"
                    size="sm" variant="danger" class="ml-2" @click="deleteOrder(row.item)">
                    <b-icon icon="x"></b-icon>
                    Ukloni
                </b-button>
            </template>
        </b-table>
    </div>
</template>

<script>
import AddEditOrderModal from "./AddEditOrderModal.vue"

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

            orderStatusFilter: "",

            modalMode: "",
            selectedOrder: {}
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

        this.$root.$on('show-add-order-modal', () => {
            this.selectedOrder = {}
            this.modalMode = "ADD"
            this.$bvModal.show('add-order-modal')
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

        showOrderWithOffers(order) {
            this.selectedOrder = order
            this.modalMode = "SHOW"
            this.$bvModal.show('add-order-modal')
        },

        editOrder(order) {
            this.selectedOrder = order
            this.modalMode = "EDIT"
            this.$bvModal.show('add-order-modal')
        },

        deleteOrder(order) {
            this.$http
            .delete('orders/' + order.orderId)
            .then( () => {
                this.toast('success', 'Uspešno', 'Uspešno ste obrisali narudžbenicu.')
                this.getOrdersForPharmacy()
            })
            .catch( (error) => {
                if (error.response.status == 403 || error.response.status == 401)
                    this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                else if (error.response.status == 400)
                    this.toast('danger', 'Neuspešno', error.response.data)
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

    components: {
        AddEditOrderModal
    }
}
</script>

<style>

</style>