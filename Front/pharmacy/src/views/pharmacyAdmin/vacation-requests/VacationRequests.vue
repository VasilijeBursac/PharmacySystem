<template>
    <div class="container mt-4">
        <div class="title-options clearfix">
            <h5 class="h5 float-left mb-4">
                {{ "Zahtevi za godišnje odmore i odsustva " + (this.loggedUserRole == 'ROLE_PHARMACY_ADMIN' ? "farmaceuta u apoteci" : "dermatologa") }} 
            </h5>
        </div>

        <b-row>
            <b-col>
                <PharmacyVacationRequestsTable :pharmacyId="myPharmacyId" />
            </b-col>
        </b-row>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import PharmacyVacationRequestsTable from "./PharmacyVacationRequestsTable.vue"

export default {
    data: function() {
        return {
            loggedUserRole: this.$store.getters.getUserRole
        }
    },

    computed: {
        ...mapState(['myPharmacyId'])
    },

    mounted() {
    },

    methods: {
    },

    components: {
        PharmacyVacationRequestsTable,
    }
}
</script>

<style>

</style>