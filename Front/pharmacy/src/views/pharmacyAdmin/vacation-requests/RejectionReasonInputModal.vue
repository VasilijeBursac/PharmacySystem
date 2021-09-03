<template>
    <b-modal id="rejection-reason-modal" scrollable hide-footer title="Razlog za odbijanje zahteva za odsustvo" 
        @hide="onModalClosing">
        <b-form>
            <b-form-group id="rejection-reason-group">
                <label for="rejection-reason-input">Razlog odbijanja zahteva:</label>
                <b-form-textarea
                    id="rejection-reason-input"
                    class="mb-2 mr-sm-2 mb-sm-0" 
                    placeholder="Unesite razlog odbijanja zahteva"
                    rows="3" 
                    max-rows="5"
                    maxlength="255"
                    no-resize 
                    trim
                    v-model="rejectionReasonMessage" 
                />
            </b-form-group>


            <div class="buttons mt-5">
                <b-button variant="success" block @click="addRejectionReasonMessage">
                    Sačuvaj
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
    data: function(){
        return {
            rejectionReasonMessage: ""
        }
    },

    computed: {
        ...mapState(['myPharmacyId']),
    },

    mounted() {
        
    },

    methods: {
        addRejectionReasonMessage() {
            if (this.rejectionReasonMessage == "") {
                this.toast('danger', 'Neuspešno', 'Morate uneti razlog odbijanja zahteva kako biste odbili zahtev!')
                return;
            }
            
            this.$root.$emit('add-rejection-reason', this.rejectionReasonMessage)
            this.closeModal()
        },

        resetFields(){
            this.rejectionReasonMessage=""
        },

        onModalClosing(){
            this.resetFields()
            this.$root.$emit('rejection-reason-modal-closed')
        },

        closeModal(){
            this.$bvModal.hide('rejection-reason-modal')
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