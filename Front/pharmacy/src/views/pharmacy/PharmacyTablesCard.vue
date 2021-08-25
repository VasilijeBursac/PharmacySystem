<template>
    <div class="pharmacy-tables-card">
        <b-card class="overflow-hidden mt-3 mb-5">
            <b-row>
                <b-col>
                    <h5 class="h5 text-left">Lekovi u apoteci</h5>
                    <PharmacyDrugsTable :pharmacyId="pharmacyId" />
                </b-col>
            </b-row>

            <b-row>
                <b-col>
                    <h5 class="h5 mt-5 text-left">Farmaceuti u apoteci</h5>
                    <PharmacyPharmacistsTable :pharmacyId="pharmacyId" />
                </b-col>
            </b-row>

            <b-row>
                <b-col>
                    <h5 class="h5 mt-3 text-left">Dermatolozi u apoteci</h5>
                    <PharmacyDermatologistsTable :pharmacyId="pharmacyId" />
                </b-col>
            </b-row>
        </b-card>
    </div>
</template>

<script>
import PharmacyDrugsTable from "@/views/pharmacy/PharmacyDrugsTable.vue";
import PharmacyPharmacistsTable from "@/views/pharmacy/PharmacyPharmacistsTable.vue"
import PharmacyDermatologistsTable from "@/views/pharmacy/PharmacyDermatologistsTable.vue"

export default {
    props: ['pharmacyId'],
    data: function() {
        return{
        } 
    },
    mounted(){

    },
    methods:{

    },
    components:{
        PharmacyDrugsTable,
        PharmacyPharmacistsTable,
        PharmacyDermatologistsTable
    }
}
</script>