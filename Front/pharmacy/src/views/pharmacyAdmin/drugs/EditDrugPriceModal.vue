<template>
    <b-modal id="edit-drug-price-modal" scrollable hide-footer title="Izmena cene leka" 
        @hide="resetFields">
        <b-form>
            <b-form-group id="drug-price-group">
                <label for="price-input">Cena leka:</label>
                <b-form-input v-model="drugPrice" id="price-input" placeholder="Unesite novu cenu leka"
                type="number" min="0">
                </b-form-input>
            </b-form-group>
            
            <b-form-group id="date-start-group" label="Početak važenja cene:" label-for="date-start-picker" class="text-left">
                <b-form-datepicker v-model="startDate" id="date-start-picker" placeholder="Izaberite datum početka važenja cene">
                </b-form-datepicker>
            </b-form-group>

            <b-form-group id="date-end-group" label="Kraj važenja cene:" label-for="date-end-picker" class="text-left">
                <b-form-datepicker v-model="endDate" id="date-end-picker" placeholder="Izaberite datum kraja važenja cene">
                </b-form-datepicker>
            </b-form-group>


            <div class="buttons mt-5">
                <b-button variant="success" block @click="changeDrugPrice">
                    Izmeni cenu
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
            default: null
        },
        drug: {
            type: Object,
            default: function () {
                return {}
            }
        }
    },
    data: function(){
        return {
            drugPrice: "",

            startDate: "",
            endDate: "",
        }
    },

    computed: {
        ...mapState(['myPharmacyId']),
    },

    mounted() {
    },

    methods: {
        changeDrugPrice() {
            if (this.drugPrice == "" || this.startDate == "" || this.endDate == ""){
                this.toast('danger', 'Neuspešno', 'Morate uneti cenu leka i početak i kraj važenja cene!')
                return;
            }

            if (new Date(this.endDate).getTime() < new Date().getTime()){
                this.toast('danger', 'Neuspešno', 'Datum kraja važenja cene ne može biti pre današnjeg datuma!')
                return;
            }

            if (new Date(this.startDate).getTime() > new Date(this.endDate).getTime()){
                this.toast('danger', 'Neuspešno', 'Datum kraja važenja cene ne može biti pre datuma početka važenja cene!')
                return;
            }
            
            this.$http
            .put('drugInPharmacy/editPrice', {
                drugInPharmacyId: {pharmaciId: this.pharmacyId, drugId: this.drug.drugId},
                priceValidDateRange: {startDate: this.startDate, endDate: this.endDate},
                price: this.drugPrice
            })
            .then( () => {
                this.toast('success', 'Uspešno', 'Uspešno ste izmenili cenu leka!')
                this.closeModal();
                this.$root.$emit('update-pharmacy-drugs')
            })
            .catch( (error) => {
                if (error.response.status == 403 || error.response.status == 401)
                    this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                else if (error.response.status == 400)
                    this.toast('danger', 'Neuspešno', 'Neispravan zahtev.')
                else 
                    this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
            })
        },

        resetFields(){
            this.drugPrice = ""
            this.startDate = ""
            this.endDate = ""
        },

        closeModal(){
            this.$bvModal.hide('edit-drug-price-modal')
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
}
</script>

<style>
    
</style>