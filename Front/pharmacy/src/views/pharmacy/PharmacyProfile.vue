<template>
    <b-container class="container mt-4">
        <b-row>
             <b-col cols="8">
                 <h5 class="h5 float-left text-left mb-4">Profil apoteke</h5>
             </b-col>

            <b-col>
                <b-button variant="success" class="float-right mt-n2 mb-3 ml-2"
                 @click="$router.push({name: 'PharmacyReports', params: { id: pharmacyId }})">
                    <b-icon-graph-up shift-h="-3"></b-icon-graph-up>
                    Prikaži izveštaje
                </b-button>

                <b-button variant="success" class="float-right mt-n2 mb-3" 
                @click="$router.push({name: 'EditPharmacyInfo', params: { id: pharmacyId }})">
                    <b-icon-pencil-square shift-h="-3"></b-icon-pencil-square>
                    Izmeni informacije
                </b-button>
             </b-col>
        </b-row>
    
        <b-row>
             <b-col>
                <PharmacyInfoCard v-bind:pharmacyId="pharmacyId" />
            </b-col>
        </b-row>
        
    </b-container>    
</template>

<script>
import PharmacyInfoCard from "@/views/pharmacy/PharmacyInfoCard.vue";

export default {
    data: function() {
        return{
            loggedUserRole: this.$store.getters.getUserRole,
            pharmacyId: this.$route.params.id,
            pharmacy: {}
        } 
    },
    mounted(){
        this.$http
            .get('/pharmacy/' + this.pharmacyId)
            .then( res => {
                this.pharmacy = JSON.parse(JSON.stringify(res.data))
            })
    },
    methods:{
       
    },
    components:{
        PharmacyInfoCard
    }
}
</script>