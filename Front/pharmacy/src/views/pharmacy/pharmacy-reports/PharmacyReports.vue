<template>
    <div class="container mt-4">
        <!-- <h4 class="h4 align-middle my-4">Izveštaji o apoteci</h4> -->
        <h5 class="h5 mb-4 text-left">Izveštaji o apoteci</h5>
        <div class="pharmacy-reports-main">
            <b-card no-body>
                <b-tabs pills card align="center" lazy>
                    <b-tab title="Ocena apoteke" active>
                        <ReportPharmacyRating :pharmacyId="pharmacyId" />
                    </b-tab>

                    <b-tab title="Ocene farmaceuta">
                        <PharmacyPharmacistsTable :pharmacyId="pharmacyId" :isReport="true" />    
                    </b-tab>

                    <b-tab title="Ocene dermatologa">
                        <PharmacyDermatologistsTable :pharmacyId="pharmacyId" :isReport="true" />         
                    </b-tab>

                    <b-tab title="Održani pregledi">
                        <ReportFinishedExaminations :pharmacyId="pharmacyId" />  
                    </b-tab>

                    <b-tab title="Potrošnja lekova">   
                    </b-tab>

                    <b-tab title="Prihodi apoteke">   
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return{
            pharmacyId: this.$route.params.id,
            pharmacy: {}
        }
    },

    mounted(){
    },

    components: {
        ReportPharmacyRating: () => import("./ReportPharmacyRating.vue"),
        PharmacyPharmacistsTable: () => import("@/components/tables/PharmacyPharmacistsTable.vue"),
        PharmacyDermatologistsTable: () => import("@/components/tables/PharmacyDermatologistsTable.vue"),
        ReportFinishedExaminations: () => import("./ReportFinishedExaminations.vue"),
    }
}
</script>