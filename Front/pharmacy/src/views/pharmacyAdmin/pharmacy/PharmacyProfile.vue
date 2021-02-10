<template>
    <b-container class="container mt-4">
      
        <b-row>
             <b-col cols="6">
                 <h5 class="h5 float-left text-left mb-4">Profil apoteke</h5>
             </b-col>

            <b-col>
                <b-button variant="success" class="float-right mt-n2 mb-3">
                    <b-icon-pencil-square shift-h="-3"></b-icon-pencil-square>
                    Izmeni informacije
                </b-button>
             </b-col>

             <b-col>
                <b-button variant="success" class="float-right mt-n2 mb-3">
                    <b-icon-graph-up shift-h="-3"></b-icon-graph-up>
                    Prikaži izveštaje za apoteku
                </b-button>
             </b-col>
        </b-row>
    
        <b-row>
            <b-col>
                <div class="pharmacy-info">
                <b-card v-bind:title="pharmacy.name" >
                    <h6 class="h6 text-left mb-3">{{pharmacy.name}}</h6>
                </b-card>
            </div>
            </b-col>
        </b-row>
        
    </b-container>    
</template>

<script>
export default {
    data: function() {
        return{
            pharmacyId: this.$route.params.id,
            pharmacy: {}
        } 
    },
    mounted(){
        this.$http
            .get('/pharmacy/' + this.pharmacyId)
            .then( res => {
                this.pharmacy = JSON.parse(JSON.stringify(res.data))
                console.log(this.pharmacy)
            })
    },
    methods:{
       
    }
}
</script>