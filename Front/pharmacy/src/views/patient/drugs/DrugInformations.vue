<template>
    <b-container class="container mt-4">
        <b-row>
            <b-col>
                <h5 class="h5 float-left text-left mb-4">Informacije o leku : {{this.drugName}}</h5>
            </b-col>
        </b-row>
         <b-row> 
            <b-col>
                <DrugSpecificationCard v-bind:drugId="drugId" />      
            </b-col>
        </b-row>
        <b-row> 
            <b-col>
                <PharmaciesForDrugCard v-bind:drugId="drugId"  id = "pharmaciesForDrugCard"/>      
            </b-col>
        </b-row>
    </b-container>    
</template>

<script>
import DrugSpecificationCard from "../drugs/DrugSpecificationCard.vue";
import PharmaciesForDrugCard from "../drugs/PharmaciesForDrugCard.vue";


export default {
    data: function() {
        return{
            loggedUserRole: this.$store.getters.getUserRole,
            drugId: this.$route.params.id,
            drugName: this.$route.params.name

        } 
    },
    components:{
        DrugSpecificationCard,
        PharmaciesForDrugCard
    }
}
</script>
<style>
    #pharmaciesForDrugCard{
        margin: 20px 0px 0px 0px ;
    }
</style>