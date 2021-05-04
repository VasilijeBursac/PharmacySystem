<template>
    <b-container class="container mt-4">
        <b-row>
            <b-col cols="6">
                <h5 class="h5 float-left text-left mb-4">Profil apoteke</h5>
            </b-col>
            <b-col>
                    <b-button 
                        v-if="loggedUserRole == 'ROLE_PATIENT' && showComplaintModall == true" 
                        class="float-right mt-n2 mb-3 mr-n4"
                        variant="danger"
                        @click="openModal">
                        <b-icon-exclamation></b-icon-exclamation>
                        Podnesi zalbu
                    </b-button>
                 <AddComplaintModal/>
            </b-col>
            <b-col>
                <div class="pharmacy-admin-actions-buttons" v-if="loggedUserRole == 'ROLE_PHARMACY_ADMIN'" >
                    <b-button variant="success" class="float-right mt-n2 mb-3 ml-2 "
                        @click="$router.push({name: 'PharmacyReports', params: { id: pharmacyId }})">
                        <b-icon-graph-up shift-h="-3"></b-icon-graph-up>
                        Prikaži izveštaje
                    </b-button>

                    <b-button variant="success" class="float-right mt-n2 mb-3" 
                        @click="$router.push({name: 'EditPharmacyInfo', params: { id: pharmacyId }})">
                        <b-icon-pencil-square shift-h="-3"></b-icon-pencil-square>
                        Izmeni informacije
                    </b-button>
                </div>
                <div class="patient-actions-buttons" v-if="loggedUserRole == 'ROLE_PATIENT'">           
                    <b-button variant="success" class="float-right mt-n2 mb-3 ml-2 "
                        @click="$router.push({path: `/pharmacy/${pharmacyId}/dermatologist-examinations`})">
                        <b-icon-calendar2-week shift-h="-3"></b-icon-calendar2-week>
                        Termini kod dermatologa 
                    </b-button>
                   
                </div>

                
            </b-col>
        </b-row>
    
        <b-row>
            <b-col>
                <PharmacyInfoCard v-bind:pharmacyId="pharmacyId" />
            </b-col>
        </b-row>

        <b-row>
            <b-col>
                <PharmacyTablesCard v-bind:pharmacyId="pharmacyId" />
            </b-col>
        </b-row>
        
    </b-container>    
</template>

<script>
import PharmacyInfoCard from "@/views/pharmacy/PharmacyInfoCard.vue";
import PharmacyTablesCard from "@/views/pharmacy/PharmacyTablesCard.vue";
import AddComplaintModal from "../patient/AddComplaintModal.vue";

export default {
    data: function() {
        return{
            loggedUserRole: this.$store.getters.getUserRole,
            pharmacyId: this.$route.params.id,
            pharmacy: {},
            objectId: '',
            showComplaintModall: false

        } 
    },
    mounted(){
        this.$http
            .get('/pharmacy/' + this.pharmacyId)
            .then( res => {
                this.pharmacy = JSON.parse(JSON.stringify(res.data))
            }),
            this.isAnyDrugFromChoosenPharmacySoldToPatient()
    },
    methods:{
        openModal(){
             this.$root.$emit('show-complaint-modal', {
                 objectId : this.pharmacyId,
                 complaintType :  "PharmacyComplaint"
             })
        },
        isAnyDrugFromChoosenPharmacySoldToPatient(){
            if(this.loggedUserRole == 'ROLE_PATIENT'){
                this.$http
                .get('/reservation/checkForComplaint',{
                 params : {
                     patientId : this.$store.getters.getUserId,
                     pharmacyId : this.pharmacyId
                    }
                 })
                .then( () => {
                    this.showComplaintModall = true;
                }) 
            }
            
        }
    },
    components:{
        PharmacyInfoCard,
        PharmacyTablesCard,
        AddComplaintModal
    }
}
</script>