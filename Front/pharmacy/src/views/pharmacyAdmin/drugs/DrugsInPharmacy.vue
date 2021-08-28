<template>
    <div class="container mt-4">
        <ImportDrugToPharmacyModal />

        <div class="title-options clearfix">
            <h5 class="h5 float-left mb-4">Lekovi u apoteci</h5>
                
            <div class="float-right d-flex">
                <b-link to="/drugs">Svi lekovi</b-link>

                <b-button variant="success" class=" mt-n2 mb-3 ml-2" 
                    @click="openDrugImportModal">
                    <b-icon icon="plus-circle"></b-icon>
                    Dodaj lek
                </b-button>
            </div>
        </div>

        <b-row>
            <b-col cols="3">                
                <DrugsFilters />
            </b-col>
            <b-col>
                <PharmacyDrugsTable :pharmacyId="myPharmacyId" />
            </b-col>
        </b-row>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import DrugsFilters from "@/components/filters/DrugsFilters.vue"
import PharmacyDrugsTable from "@/components/tables/PharmacyDrugsTable.vue"
import ImportDrugToPharmacyModal from "./ImportDrugToPharmacyModal.vue"

export default {
    computed: {
        ...mapState(['myPharmacyId'])
    },

    methods: {
        openDrugImportModal() {
            this.$bvModal.show('import-drug-modal')
        }
    },

    components:{
        DrugsFilters,
        PharmacyDrugsTable,
        ImportDrugToPharmacyModal
    }
}
</script>

<style>

</style>