<template>
    <div class="pharmacy-dermatologists">
        <RejectionReasonInputModal />
        
        <b-table striped hover :busy="isBusy" :items="items | formatDate | formatRequestStatus" :fields="fields" class="text-middle mt-0">
			<template #table-busy>
				<div class="text-center text-danger my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong> Učitavanje...</strong>
				</div>
			</template>

            <template #cell(actions)="row">
                <b-button v-if="row.item.status == 'Čeka obradu'"
                size="sm" variant="success" @click="selectVacationRequest(row.item, true)">
                    <b-icon icon="check"></b-icon>
					Prihvati
				</b-button>

                <b-button v-if="row.item.status == 'Čeka obradu'"
                size="sm" variant="danger" class="ml-2" @click="selectVacationRequest(row.item, false)">
                    <b-icon icon="x"></b-icon>
					Odbij
				</b-button>
			</template>
        </b-table>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import RejectionReasonInputModal from "./RejectionReasonInputModal.vue"

export default {
    props: ["pharmacyId"],
    data: function() {
        return{
            loggedUserRole: this.$store.getters.getUserRole,

            items: [],
            fields: [
                { key: 'name', label: 'Ime', sortable: true}, 
                { key: 'surname', label: 'Prezime', sortable: true}, 
                { key: 'timePeriod.startDate', label: 'Datum početka', sortable: true}, 
                { key: 'timePeriod.endDate', label: 'Datum kraja', sortable: true},
                { key: 'status', label: 'Status', sortable: true},
                { key: 'actions', label: 'Akcije'}
            ],

            isBusy: true,

            selectedVacationRequest: {},
            rejectionReasonMessage: "",
            approveSelectedRequest: true 
        }
    },

    computed: {
        ...mapState(['myPharmacyId']),
    },
    
    mounted(){
        this.getVacationRequests()

        this.$root.$on('add-rejection-reason', (rejectionReasonMessage) => {
            this.rejectionReasonMessage = rejectionReasonMessage;
        })

        this.$root.$on('rejection-reason-modal-closed', () => {
            this.changeVacationRequestStatus()
        })
    },

    methods: {
        getVacationRequests() {
            this.$http
                .get("/vacation/byPharmacyId/" + this.pharmacyId)
                .then( res => {
                    this.isBusy = false

                    this.items = res.data
                    console.log(this.items)
                })
                .catch((error) => {
                    this.isBusy = false
                    
                    console.log(error)
                    if (error.response.status == 403 || error.response.status == 401) 
                        this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                    else if (error.response.status == 404)
                        this.toast('danger', 'Neuspešno', 'Trenutno nema zahteva za odsustva u sistemu.')
                    else {
                        this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
                        window.location.reload()
                    }
                })
        },

        selectVacationRequest(request, isApproved){
            this.selectedVacationRequest = request
            this.approveSelectedRequest = isApproved

            if(!isApproved)
                this.$bvModal.show('rejection-reason-modal')
            else
                this.changeVacationRequestStatus()
        },

        changeVacationRequestStatus(){
            if (!this.approveSelectedRequest && this.rejectionReasonMessage == ""){
                this.toast('danger', 'Neuspešno', 'Niste uneli razlog odbijanja zahteva!')
                this.resetSelections()
                return;
            }

            this.$http
            .post('vacation/respond/' + this.selectedVacationRequest.id, {
                approved: this.approveSelectedRequest,
                responseMessage: this.rejectionReasonMessage
            })
            .then( () => {
                this.toast('success', 'Uspešno', 'Uspešno ste obradili zahtev za odsustvo!')
                this.getVacationRequests()
                this.resetSelections()
            })
            .catch( (error) => {
                if (error.response.status == 403 || error.response.status == 401)
                    this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                else if (error.response.status == 400){
                    this.toast('danger', 'Neuspešno', 'Nije moguće obraditi zahtev za odsustvo.')
                    window.location.reload()
                }
                else 
                    this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
            })
        },

        resetSelections(){
            this.selectedVacationRequest = {}
            this.rejectionReasonMessage = ""
            this.approveSelectedRequest = true
        },

        toast(variant, title, message){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        },
    },

    filters: {
        formatDate: function(items) {
            items.forEach((item, index) => {
                let formatedStartDate = new Date(item.timePeriod.startDate).toLocaleDateString()
                let formatedEndDate = new Date(item.timePeriod.endDate).toLocaleDateString()
                items[index] = {...items[index], timePeriod: {
                    startDate: formatedStartDate,
                    endDate: formatedEndDate
                }}
            })
            return items
        },

        formatRequestStatus: function(items){
            items.forEach((item, index) => {
                let formatedStatus

                if (item.status === "Created")
                    formatedStatus = "Čeka obradu"
                else if (item.status === "Approved")
                    formatedStatus = "Prihvaćen"
                else if (item.status === "Rejected")
                    formatedStatus = "Odbijen"
                else
                    formatedStatus = item.status

                items[index] = {...items[index], 
                status: formatedStatus}
            })
            return items
        }
    },

    components: {
        RejectionReasonInputModal
    }
}
</script>

<style>

</style>