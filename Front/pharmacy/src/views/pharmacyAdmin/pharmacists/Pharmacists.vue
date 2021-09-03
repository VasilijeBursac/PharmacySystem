<template>
    <div class="container mt-4">
        <RegisterPharmacistModal :pharmacyId="myPharmacyId" />

        <div class="title-options clearfix">
            <h5 class="h5 float-left mb-4">
                {{ loggedUserRole == "ROLE_PHARMACY_ADMIN" ? 'Farmaceuti u apoteci' : 'Farmaceuti'}}
            </h5>
                
            <div class="float-right d-flex">
                <b-button v-if="loggedUserRole == 'ROLE_PHARMACY_ADMIN'" variant="success" class=" mt-n2 mb-3 ml-2" 
                    @click="openRegisterPharmacistModal">
                    <b-icon icon="plus-circle"></b-icon>
                    Dodaj farmaceuta
                </b-button>
            </div>
        </div>

        <b-row>
            <b-col cols="3">                
                <EmployeeFilters />
            </b-col>
            <b-col>
                <PharmacyPharmacistsTable :pharmacyId="myPharmacyId" />
            </b-col>
        </b-row>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import EmployeeFilters from "@/components/filters/EmployeeFilters.vue"
import PharmacyPharmacistsTable from "@/components/tables/PharmacyPharmacistsTable.vue"
import RegisterPharmacistModal from "./RegisterPharmacistModal.vue"

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
        openRegisterPharmacistModal() {
            this.$bvModal.show('register-pharmacist-modal')
        }
    },

    components:{
        EmployeeFilters,
        PharmacyPharmacistsTable,
        RegisterPharmacistModal
    }
}
</script>

<style>

</style>