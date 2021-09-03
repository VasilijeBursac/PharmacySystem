<template>
    <div class="container mt-4">
        <AddPromotionModal :pharmacyId="myPharmacyId" />

        <div class="title-options clearfix">
            <h5 class="h5 float-left mb-4">Akcije i promocije apoteke</h5>
                
            <div class="float-right d-flex">
                <b-button variant="success" class=" mt-n2 mb-3 ml-2" @click="openAddPromotionModal">
                    <b-icon icon="plus-circle"></b-icon>
                    Dodaj promociju
                </b-button>
            </div>
        </div>

        <b-row>
            <b-col>
                <PharmacyPromotionsTable :pharmacyId="myPharmacyId" />
            </b-col>
        </b-row>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import PharmacyPromotionsTable from "./PharmacyPromotionsTable.vue"
import AddPromotionModal from "./AddPromotionModal.vue"

export default {
    computed: {
        ...mapState(['myPharmacyId'])
    },

    methods: {
        openAddPromotionModal() {
            this.$bvModal.show('add-promotion-modal')
        }
    },

    components: {
        PharmacyPromotionsTable,
        AddPromotionModal
    }
}
</script>

<style>

</style>