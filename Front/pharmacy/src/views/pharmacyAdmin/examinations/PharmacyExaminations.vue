<template>
    <div class="container mt-4">
        <div class="title-options clearfix">
            <AddExaminationTermModal :pharmacyId="myPharmacyId" />
            <h5 class="h5 float-left mb-4">Slobodni termini kod dermatologa</h5>
            
            <div class="float-right d-flex">
                <b-button variant="success" class=" mt-n2 mb-3 ml-2" @click="openAddExaminationTermModal">
                    <b-icon icon="plus-circle"></b-icon>
                    Dodaj slobodni termin
                </b-button>
            </div>
        </div>

        <b-row>
            <b-col>
                <PharmacyDermatologistsExaminationsTable :pharmacyId="myPharmacyId" />
            </b-col>
        </b-row>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import PharmacyDermatologistsExaminationsTable from "@/components/tables/PharmacyDermatologistsExaminationsTable.vue"
import AddExaminationTermModal from "./AddExaminationTermModal.vue"

export default {
    data: function() {
        return {
        }
    },

    computed: {
        ...mapState(['myPharmacyId'])
    },

    mounted() {
        
    },

    methods: {
        openAddExaminationTermModal() {
            this.$bvModal.show('add-examination-term-modal')
        }
    },

    components: {
        PharmacyDermatologistsExaminationsTable,
        AddExaminationTermModal
    }
}
</script>

<style>

</style>