<template>
    <b-modal id="add-order-modal" size="lg" scrollable hide-footer 
        :title="mode=='SHOW' ? 'Prikaz narudžbenice i ponuda' : 'Dodaj narudžbenicu'" 
        @hide="resetFields" @show="setDataForTables">
        <b-form>
            <div v-if="mode=='SHOW'" class="order-info mb-3">
                <p>Status narudžbenice:</p>
                <p>{{ order.status }}</p>

                <p class="mt-2">Rok za slanje ponuda:</p>
                <p>{{ order.deadline }}</p>

                <hr>
            </div>

            <div v-else-if="mode!='SHOW' || isEditingAllowed" class="order-forms mb-3">
                <b-form-group id="deadline-date-group">
                    <label for="deadline-date-picker">Rok za slanje ponuda:</label>

                    <b-form-datepicker id="deadline-date-picker" v-model="deadlineDate" 
                    placeholder="Izaberite datum kraja roka za slanje ponuda" ></b-form-datepicker>
                </b-form-group>

                <p>Dodavanje leka u narudžbenicu: </p>
                <b-form inline @submit.stop.prevent>
                    <b-form-select v-model="selectedDrug">
                        <b-form-select-option :value="null" disabled>Izaberite lek</b-form-select-option>
                        <b-form-select-option v-for="drug in drugs" :key="drug.id" :value="drug">
                            {{drug.name}}
                        </b-form-select-option>
                    </b-form-select>
                    <b-form-input v-model="quantity" type="number" placeholder="Količina" min="1" class="ml-2" trim></b-form-input>
                    <b-button type="submit" class="ml-2" @click="addDrugToOrder">
                        Dodaj
                    </b-button>
                </b-form>

                <hr>
            </div>
            
            <div class="order-drugs">
                <p class="">Sadržaj narudžbenice: </p>
                <b-table ref="drugInOrderTable" striped hover :items="drugsInOrder" 
                :fields="mode!='SHOW' ? fieldsOrder : fieldsOrderReadOnly" class="text-middle mt-0">
                    <template #cell(actions)="row">
                        <b-button v-if="mode!='SHOW'" size="sm" variant="danger" @click="removeDrugFromOrder(row)">
                            Ukloni
                        </b-button>
                    </template>
                </b-table>
            </div>
            
            <div v-if="mode!='ADD'" class="order-offers">
                <hr>
                <p class="mt-4">Ponude dobavljača: </p>
                <p v-if="offers.length === 0">Trenutno nema ponuda dobavljača za ovu narudžbenicu.</p>
                <b-table v-else striped hover :items="offers | formatDate | formatPrice" :fields="fieldsOffer"  class="text-middle mt-0">
                    <template #cell(actions)="row">
                        <b-button size="sm" variant="success" @click="acceptOffer(row.item)">
                            Prihvati ponudu
                        </b-button>
                    </template>
                </b-table>
            </div>

            <div class="buttons mt-5">
                <b-button v-if="mode!='SHOW' || isEditingAllowed" variant="success" block @click="addNewOrder">
                    {{ mode=='ADD' ? 'Dodaj narudžbenicu' : 'Izmeni narudžbenicu'}}
                </b-button>

                <b-button variant="danger" block @click="closeModal">
                    Zatvori
                </b-button>
            </div>
        </b-form>
    </b-modal>
</template>

<script>
import { mapState } from 'vuex';

export default {
    props: {
        pharmacyId: {
            type: Number,
            default: null
        },
        mode: {
            type: String,
            default: ""
        },
        order: {
            type: Object,
            default: function () {
                return {}
            }
        }
    },
    data: function(){
        return {
            drugs: [],

            deadlineDate: null,
            selectedDrug: null,
            quantity: "",

            drugsInOrder: [],
            fieldsOrder: [
                {key:'drugName', label: 'Naziv leka', sortable:true}, 
				{key:'quantity', label: 'Količina', sortable:true},
                {key:'actions', label: 'Ukloni'} 
            ],
            fieldsOrderReadOnly: [
                {key:'drugName', label: 'Naziv leka', sortable:true}, 
				{key:'quantity', label: 'Količina', sortable:true},
            ],

            offers: [],
            fieldsOffer: [
                {key:'supplierFullName', label: 'Dobavljač', sortable:true}, 
				{key:'deliveryDeadline', label: 'Rok za dostavu', sortable:true},
                {key:'totalPrice', label: 'Cena', sortable:true}, 
				{key:'offerStatus', label: 'Status ponude', sortable:true},
                {key:'actions', label: 'Akcije'}
            ]
        }
    },

    computed: {
        ...mapState(['myPharmacyId']),

        isEditingAllowed(){
            return this.mode=="EDIT" && this.offers.length !=0
        }
    },

    watch: {
        order: function() {
            this.setDataForTables()
        },

        mode: function() {
            this.setDataForTables()
        }
    },

    mounted() {
        this.getAllDrugs()
    },

    methods: {
        getAllDrugs() {
            this.$http
			.get('drugs/')
			.then( res => {
				if(res.status == 200)
                    console.log(res.data)
					this.drugs = res.data                      
			})
			.catch( (error) => {
				if(error.response.status == 404)
                    this.toast('danger', 'Neuspešno', 'Trenutno ne postoji nijedan lek u sistemu!')
                else 
                    this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.') 
			})
        },

        addDrugToOrder() {
            if (this.quantity == "" || this.selectedDrug == null) {
                this.toast('danger', 'Neuspešno', 'Morate uneti lek i količinu!')
                return;
            }

            this.drugsInOrder.push({drugId: this.selectedDrug.id, drugName: this.selectedDrug.name, quantity: this.quantity})
            this.resetDrugInputFields()
        },

        removeDrugFromOrder(row) {
            this.drugsInOrder.splice(row.index, 1)
        },

        addNewOrder() {
            if(this.deadlineDate == null){
                this.toast('danger', 'Neuspešno', 'Morate uneti rok za slanje ponuda!')
                return;
            }
            if(new Date(this.deadlineDate).getTime() < new Date().getTime()){
                this.toast('danger', 'Neuspešno', 'Rok za slanje ponuda ne može biti pre današnjeg datuma!')
                return;
            }
            if(this.drugsInOrder.length == 0){
                this.toast('danger', 'Neuspešno', 'Morate uneti barem jedan lek u narudžbenicu!')
                return;
            }
            
            this.$http
            .post('orders/', {
                deadline: this.deadlineDate,
                drugsInOrder: this.drugsInOrder
            })
            .then( () => {
                this.toast('success', 'Uspešno', 'Uspešno ste kreirali narudžbenicu.')
                this.closeModal();
                this.$root.$emit('update-pharmacy-orders')
            })
            .catch( (error) => {
                if (error.response.status == 403 || error.response.status == 401)
                    this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                else 
                    this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
            })
        },

        getAllOffersForOrder(){
            console.log(this.order)
            this.$http
			.get('offers/byOrderId/' + this.order.orderId)
			.then( res => {
				if(res.status == 200)
                    console.log(res.data)
					this.offers = res.data                      
			})
			.catch( (error) => {
				if(error.response.status == 404)
                    this.toast('danger', 'Neuspešno', 'Trenutno nema ponuda dobavljača za ovu narudžbenicu.')
                // else 
                //     this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.') 
			})
        },

        acceptOffer(offer) {
            this.$http
            .post('offers/acceptOffer/' + offer.offerId, {
            })
            .then( () => {
                this.toast('success', 'Uspešno', 'Uspešno ste odabrali ponudu za narudžbenicu.')
                this.closeModal();
                this.$root.$emit('update-pharmacy-orders')
            })
            .catch( (error) => {
                console.log(error)
                if (error.response.status == 403 || error.response.status == 401)
                    this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                else if (error.response.status == 400)
                    this.toast('danger', 'Neuspešno', error.response.data)
                else 
                    this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
            })
        },

        resetDrugInputFields() {
            this.selectedDrug = null
            this.quantity = ""
        },

        resetFields(){
            this.resetDrugInputFields()
            this.deadlineDate = null
            this.drugsInOrder = []
        },

        setDataForTables(){
            if(this.mode == "ADD")
                this.drugsInOrder = []
            else{
                this.drugsInOrder = [...this.order.drugsInOrder]
                this.getAllOffersForOrder()
            }   
        },

        closeModal(){
            this.$bvModal.hide('add-order-modal')
        },

        toast(variant, title, message){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
            scroll(0,0)
        }
    },

    filters: {
        formatDate: function(items) {
            items.forEach((item, index) => {
                let formatedDate = new Date(item.deliveryDeadline).toLocaleDateString()
                items[index] = {...items[index], deliveryDeadline: formatedDate}
            })
            return items
        },

        formatPrice: function(items) {
            items.forEach((item, index) => {
                let formatedPrice = item.totalPrice == -1 ? "Nije uneta" : item.totalPrice + " din"
                items[index] = {...items[index], totalPrice: formatedPrice}
            })
            return items
        }
    }
}
</script>

<style>
    .order-info p, .order-offers p.info{
        margin: 0;
    }
</style>