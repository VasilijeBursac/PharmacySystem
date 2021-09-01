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
                    <PharmacyPharmacistsTable :pharmacyId="pharmacyId" :isTableOnPharmacyProfile="true" />
                </b-col>
            </b-row>

            <b-row>
                <b-col>
                    <h5 class="h5 mt-3 text-left">Dermatolozi u apoteci</h5>
                    <PharmacyDermatologistsTable :pharmacyId="pharmacyId" />
                </b-col>
            </b-row>

            <b-row>
                <b-col>
                    <h5 class="h5 mt-5 text-left">Slobodni termini pregleda kod dermatologa</h5>
                    <PharmacyDermatologistsExaminationsTable :pharmacyId="pharmacyId" />
                </b-col>
            </b-row>
        </b-card>
    </div>
</template>

<script>
import PharmacyDrugsTable from "@/components/tables/PharmacyDrugsTable.vue";
import PharmacyPharmacistsTable from "@/components/tables/PharmacyPharmacistsTable.vue"
import PharmacyDermatologistsTable from "@/components/tables/PharmacyDermatologistsTable.vue"
import PharmacyDermatologistsExaminationsTable from "@/components/tables/PharmacyDermatologistsExaminationsTable.vue"

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
        PharmacyDermatologistsTable,
        PharmacyDermatologistsExaminationsTable
    }
}
</script>