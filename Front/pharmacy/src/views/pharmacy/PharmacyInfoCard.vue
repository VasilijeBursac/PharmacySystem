<template>
    <div class="pharmacy-info-card">
        <b-card class="overflow-hidden">
            <b-row>
                <b-col cols="6">
                    <div class="pharmacy-info">
                        <h5 class="h5 text-left font-weight-bold">{{pharmacy.name}}</h5>
                        <h6 class="h6 text-left font-italic mt-n1">{{pharmacy.address}}, {{pharmacy.city}}, {{pharmacy.country}}</h6>
                        <p class="h6 text-left mt-4 mb-3">{{pharmacy.description}}</p>

                        <b-button v-if="!subscribed" block @click = "subscribeToPromotions">
                            <b-icon icon="bell-fill" aria-hidden="true"></b-icon> Pretplati se na akcije i promocije apoteke
                        </b-button> 
                        <p id = "subscription" v-if="subscribed" class = "h6 text-left mt-4 mb-3">Pretplaceni ste na akcije i promocije apoteke !</p>          
                    </div>
                </b-col>            

                <b-col cols="2">
                    <div class="pharmacy-rating">
                    <p class="h6">{{ pharmacy.rating != "0.0" ? pharmacy.rating : "Nema ocena" }} <b-icon icon="star-fill"></b-icon></p>
                    </div>
                </b-col>   
                <b-col class="mr-4">
                    <LocationMap v-bind:pharmacyId="pharmacyId"/>
                </b-col>
            </b-row>
        </b-card>
    </div>        
</template>

<script>
import LocationMap from "@/views/pharmacy/LocationMap.vue";

export default {
    props: ['pharmacyId'],
    data: function() {
        return{
            loggedUserRole: this.$store.getters.getUserRole,
            pharmacy: {},
            subscribed : false
        } 
    },
    mounted(){
        this.$http
            .get('/pharmacy/' + this.pharmacyId)
            .then( res => {
                this.pharmacy = JSON.parse(JSON.stringify(res.data))
            })

        this.$http
            .get('patient/checkForSubsrciption/' + this.pharmacyId)
            .then( () => {
                this.subscribed = true
            })
    },
    methods:{
       subscribeToPromotions(){
          this.$http
            .post('patient/addPharmacyForPromotions/' + this.pharmacyId)
            .then( res => {
                this.subscribed = true
                if(res.status == 200)
                    this.toast('Uspesno ste se pretplatili na akcije i promocije!!','Uspesno', 'succes')    
            })
            .catch( error => {
                if(error.response.status == 400)
                    this.toast('Greska prikom prijavljivanja na akcije i promocije!','Neuspešno', 'danger')
                else this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
            })
       },
        toast(message, title, variant){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        }
    },
    components:{
        LocationMap
    }
}
</script>
<style >
#subscription{
    color: green;
}
</style>