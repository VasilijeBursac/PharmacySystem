<template>
    <div class="container mt-4">
        <div class="title-options clearfix">
            <h5 class="h5 float-left mb-4">Zahtevi za godišnje odmore i odsustva</h5>
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