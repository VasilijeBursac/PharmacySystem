<template>
    <b-modal id="import-drug-modal" hide-footer title="Dodavanje leka u apoteku">
        <b-form>
            <b-form-group id="drug-group">
                <label for="drug-select">Lek:</label>

                <b-form-select id="drug-select" v-model="selectedDrug" >
                    <b-form-select-option v-for="drug in drugsForImport" :key="drug.id">
                        <!-- {{ drug.name }} -->
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
    data: function() {
        return {
            drugsForImport: [],
            selectedDrug: {},
            quantity: 1
        }
    },

    computed: {
        ...mapState(['myPharmacyId'])
    },

    mounted() {
        this.getDrugsForImport()
    },

    methods: {
        getDrugsForImport() {
            this.$http
            .get('/drugInPharmacy/removeFromPharmacy/' + this.myPharmacyId)
            .then( res => {
                console.log(res)
                this.toast('success', 'Uspešno', 'Uspešno ste uklonili lek iz apoteke!')
                this.getDrugsInPharmacy()
            })
            .catch((error) => {
                console.log(error)

                if (error.response.status == 403)
                    this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                else if (error.response.status == 400)
                    this.toast('danger', 'Neuspešno', 'Nije moguće ukloniti lek. Lek je rezervisan, a korisnik ga još nije preuzeo.')
                else 
                    this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')  
            })
        },

        importDrugToPharmacy() {
            this.$root.$emit('update-pharmacy-drugs')
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