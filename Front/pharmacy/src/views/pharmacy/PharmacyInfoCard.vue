<template>
    <div class="pharmacy-info-card">
        <b-card class="overflow-hidden">
            <b-row>
                <b-col cols="6">
                    <div class="pharmacy-info">
                        <h5 class="h5 text-left font-weight-bold text-success">
                            {{pharmacy.name}}
                        </h5>
                        <h6 class="h6 text-left font-italic mt-n1">
                            {{pharmacy.address}}, {{pharmacy.city}}, {{pharmacy.country}}
                        </h6>
                        <p class="h6 text-left mt-4 mb-3">
                            {{pharmacy.description}}
                        </p>


                        <b-button
                            v-if="loggedUserRole == 'ROLE_PATIENT'"  
                            block 
                            :variant="subscribed ? 'success' : 'secondary'" 
                            @click = "changePharmacySubscriptionStatus"
                            >
                            <b-icon icon="bell-fill" aria-hidden="true"></b-icon>
                            {{ subscriptionStatusButtonText }}
                        </b-button>      
                    </div>
                </b-col>            

                <b-col cols="2">
                    <div class="pharmacy-rating">
                        <p class="h6">
                            {{ pharmacy.rating != "0.0" ? pharmacy.rating : "Nema ocenu" }} <b-icon icon="star-fill"></b-icon>
                        </p>
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

    computed: {
        subscriptionStatusButtonText() {
            if (!this.subscribed)
                return "Pretplati se na akcije i promocije apoteke"
            else
                return "Pretplaćeni ste na akcije i promocije apoteke"
        }
    },

    mounted(){
        this.$http
            .get('/pharmacy/' + this.pharmacyId)
            .then( res => {
                this.pharmacy = JSON.parse(JSON.stringify(res.data))
            })
        
        this.getPharmacySubscriptionStatus()
    },

    methods:{
        getPharmacySubscriptionStatus() {
            if (this.loggedUserRole == "ROLE_PATIENT")
                this.$http
                    .get('patient/checkForSubsrciption/' + this.pharmacyId)
                    .then( () => {
                        this.subscribed = true
                    })
                    .catch(error => {
                    if(error.response.status == 404)
                        this.subscribed = false
                    })  
        },

        changePharmacySubscriptionStatus() {
            if (!this.subscribed)
                this.subscribeToPromotions()
            else
                this.unsubscribeFromPromotions()
        },

        subscribeToPromotions(){
            this.$http
            .post('patient/addPharmacyForPromotions/' + this.pharmacyId)
            .then( res => {
                this.subscribed = true
                if(res.status == 200)
                    this.toast('success', 'Uspešno', 'Uspešno ste se pretplatili na akcije i promocije apoteke!')    
            })
            .catch( error => {
                if(error.response.status == 400)
                    this.toast('danger', 'Neuspešno', 'Greška prilikom prijavljivanja na akcije i promocije!')
                else this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije!')  
            })
        },

        unsubscribeFromPromotions(){
            this.$http
                .delete('patient/deleteSubscribedPharmacy/' + this.pharmacyId)
                .then( res => {
                    if(res.status == 200)
                        this.toast('success', 'Uspešno', 'Uspešno ste otkazali pretplatu na akcije i promocije apoteke!')
                    
                    this.getPharmacySubscriptionStatus()
                })
                .catch(error => {
                    if(error.response.status == 400)
                        this.toast('danger', 'Neuspešno', 'Greška prilikom otkazivanja pretplate!')
                    else this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije!')
                })       
        },

        toast(variant, title, message){
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

</style>