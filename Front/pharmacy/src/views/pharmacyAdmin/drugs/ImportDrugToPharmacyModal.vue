<template>
    <b-modal id="import-drug-modal" hide-footer title="Dodavanje leka u apoteku" @hide="resetFields">
        <b-form @submit.stop.prevent>
            <b-form-group id="drug-group">
                <label for="drug-select">Lek:</label>

                <b-form-select id="drug-select" v-model="selectedDrug" >
                    <b-form-select-option :value="null" disabled>Izaberite lek</b-form-select-option>
                    <b-form-select-option v-for="drug in drugs" :key="drug.id" :value="drug">
                        {{drug.name}}
                    </b-form-select-option>
                </b-form-select>
            </b-form-group>


            <b-form-group id="quantity-group">
                <label for="quantity-input">Količina:</label>

                <b-form-input id="quantity-default" placeholder="Unesite količinu" type="number"
                min="0" 
                v-model="quantity">
                </b-form-input>
            </b-form-group>


            <div class="buttons mt-5">
                <b-button variant="success" block @click="importDrugToPharmacy">
                    Dodaj lek
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
    props: ['pharmacyId'],
    data: function() {
        return {
            drugs: [],
            selectedDrug: null,
            quantity: ""
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

        importDrugToPharmacy() {
            if (this.quantity == "" || this.selectedDrug == null) {
                this.toast('danger', 'Neuspešno', 'Morate uneti lek i količinu!')
                return;
            }

            console.log(this.pharmacyId)

            this.$http
			.post('/drugInPharmacy/addToPharmacy', {
                pharmacyId: this.pharmacyId,
                drugId: this.selectedDrug.id,
                quantity: this.quantity
            })
			.then( res => {
                console.log(res.data)
                this.toast('success', 'Uspešno', 'Uspešno ste dodali lek u apoteku.') 
                this.closeModal();
                this.$root.$emit('update-pharmacy-drugs')
			})
			.catch( (error) => {
                if (error.response.status == 403 || error.response.status == 401)
                    this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                else if(error.response.status == 404)
                    this.toast('danger', 'Neuspešno', 'Trenutno ne postoji nijedan lek u sistemu!')
                else 
                    this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
			})
        },

        resetFields(){
            this.selectedDrug = null
            this.quantity = ""
        },

        closeModal(){
            this.$bvModal.hide('import-drug-modal')
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