<template>
    <b-modal :id="'reserve-drug-modal-' + drug.drugId" hide-footer title="Rezervacija leka">
        <p>Apoteka: {{ pharmacy.name }}</p>
        <p>Lek: {{ drug.drugName }}</p>
        <p>Cena: {{ drug.price }} </p>
        <p>Broj Loyalty poena: {{ drug.loyaltyPoints }}</p>    

        <b-form>
            <b-form-datepicker id="date-input" v-model="date" placeholder="Izaberite datum" ></b-form-datepicker>

            <div class="buttons mt-5">
                <b-button variant="success" block @click="reserveDrug">
                    Rezerviši
                </b-button>

                <b-button variant="danger" block @click="closeModal">
                    Zatvori
                </b-button>
            </div>
        </b-form>
    </b-modal>
</template>

<script>
export default {
    props: {
        pharmacyId: {

            default: null
        },

        drug: {
            type: Object,
            default: function () {
                return {}
            }
        }
    },

    data: function() {
        return{
            pharmacy: {},
            date: ''
        }
    },

    mounted() {
        this.getPharmacy()
    },

    methods: {
        getPharmacy() {
            this.$http
                .get('/pharmacy/' + this.pharmacyId)
                .then( res => {
                    this.pharmacy = res.data
                })
        },

        reserveDrug(){
            if(new Date(this.date).getTime() < new Date().getTime()){
                this.toast('danger', 'Neuspešno', 'Rok za preuzimanje ne može biti pre današnjeg dana!')
                return;
            }
            
            this.$http
            .post('reservation/reserve', {
                drugInPharmacyId: {pharmaciId: this.drug.pharmacyId, drugId: this.drug.drugId},
                deadline: new Date(this.date + ' 00:00').getTime()
            })
            .then( res => {
                if(res.status == 200){
                    this.toast('success', 'Uspešno', 'Uspešno ste rezervisali lek! Uskoro ćete primiti email.')
                    this.closeModal();
                    this.$root.$emit('update-pharmacy-drugs')
                }        
            })
            .catch( (error) => {
                if(error.response.status != 403)
                    this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije') 
                else 
                    this.toast('danger', 'Neuspešno', 'Nažalost, izabranog leka više nema stanju!','Neuspešno') 
            })
            
        },

        closeModal(){
            this.$bvModal.hide('reserve-drug-modal-' + this.drug.drugId)
        },

        toast(variant, title, message){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
            scroll(0,0)
        }
    }
}
</script>

<style>

</style>