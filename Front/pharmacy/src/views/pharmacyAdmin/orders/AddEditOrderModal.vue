<template>
    <b-modal id="add-order-modal" size="lg" scrollable hide-footer title="Kreiranje narudžbenice" @hide="resetFields">
        <b-form>
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

            <p class="mt-3">Sadržaj narudžbenice: </p>
            <b-table striped hover :items="drugsInOrder" :fields="fields"  class="text-middle mt-0">
                <template #cell(actions)="row">
                    <b-button size="sm" variant="danger" @click="removeDrugFromOrder(row)">
                        Ukloni
                    </b-button>
                </template>
            </b-table>


            <div class="buttons mt-5">
                <b-button variant="success" block @click="addNewOrder">
                    Dodaj narudžbenicu
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
    props: ["pharmacyId"],
    data: function(){
        return {
            drugs: [],

            deadlineDate: null,
            selectedDrug: null,
            quantity: "",

            drugsInOrder: [],
            fields: [
                {key:'drugName', label: 'Naziv leka', sortable:true}, 
				{key:'quantity', label: 'Količina', sortable:true},
                {key:'actions', label: 'Ukloni'} 
            ],
        }
    },

    computed: {
        ...mapState(['myPharmacyId'])
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

        resetDrugInputFields() {
            this.selectedDrug = null
            this.quantity = ""
        },

        resetFields(){
            this.resetDrugInputFields()
            this.deadlineDate = null
            this.drugsInOrder = []
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
    }
}
</script>

<style>

</style>