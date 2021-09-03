<template>
    <div class="container mt-4">
        <AddDermatologistToPharmacyModal :pharmacyId="myPharmacyId" />

        <div class="title-options clearfix">
            <h5 class="h5 float-left mb-4">
                {{ loggedUserRole == "ROLE_PHARMACY_ADMIN" ? 'Dermatolozi u apoteci' : 'Dermatolozi'}}
            </h5>
                
            <div class="float-right d-flex">
                <b-button v-if="loggedUserRole == 'ROLE_PHARMACY_ADMIN'" variant="success" class=" mt-n2 mb-3 ml-2" 
                    @click="openAddDermatologistToPharmacyModal">
                    <b-icon icon="plus-circle"></b-icon>
                    Dodaj dermatologa
                </b-button>
            </div>
        </div>

        <b-row>
            <b-col cols="3">                
                <EmployeeFilters />
            </b-col>
            <b-col>
                <PharmacyDermatologistsTable :pharmacyId="myPharmacyId" />
            </b-col>
        </b-row>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import EmployeeFilters from "@/components/filters/EmployeeFilters.vue"
import PharmacyDermatologistsTable from "@/components/tables/PharmacyDermatologistsTable.vue"
import AddDermatologistToPharmacyModal from "./AddDermatologistToPharmacyModal.vue"

export default {
    data: function () {
        return {
            loggedUserRole: this.$store.getters.getUserRole
        }
    },

    computed: {
        ...mapState(['myPharmacyId'])
    },

    methods: {
        openAddDermatologistToPharmacyModal() {
            this.$bvModal.show('add-dermatologist-modal')
        }
    },

    components:{
        EmployeeFilters,
        PharmacyDermatologistsTable,
        AddDermatologistToPharmacyModal
    }
}
</script>

<style>

</style>