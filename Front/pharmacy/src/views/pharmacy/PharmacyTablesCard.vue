<template>
    <div class="pharmacy-tables-card">
        <b-card class="overflow-hidden mt-3">
            <h5 class="h5 text-left">Lekovi u apoteci</h5>
            <b-row>
                <b-col>
                    <PharmacyDrugsTable v-bind:pharmacyId="pharmacyId" />
                </b-col>
            </b-row>
        </b-card>
    </div>
</template>

<script>
import PharmacyDrugsTable from "@/views/pharmacy/PharmacyDrugsTable.vue";

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
        PharmacyDrugsTable
    }
}
</script>