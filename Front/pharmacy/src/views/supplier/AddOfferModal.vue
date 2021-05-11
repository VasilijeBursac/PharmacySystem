<template>
  <div>
    <b-modal id="offerModal" hide-footer title="Davanje ponude za narudzbenicu">
        <b-form @submit="addOffer">
            <b-form-group id="totalPrice-group" label="Unesite ukupnu cenu ponude :" label-for="totalPrice-input" class="text-center">
                <b-form-input
                    type = "number"
                    min = "0"
                    class="text-center"
                    id="totalPrice-input"
                    v-model="totalPrice"
                    placeholder="Unesite cenu"
                    required>
                </b-form-input>
            </b-form-group>
            <b-form-group id="date-group" label="Izaberite rok isporuke :" label-for="date-input">
                <b-form-datepicker id="date-input" v-model="date" placeholder="Izaberite datum" class="mb-2"></b-form-datepicker>
                <b-form-timepicker v-model="time" placeholder="Izaberite vreme" locale="en"></b-form-timepicker>
            </b-form-group>
            <b-button type = "submit" block variant="success">
                <b-icon-check></b-icon-check>
                Posalji ponudu
            </b-button>
            <b-button @click="closeModal" block variant="danger">
                <b-icon-x></b-icon-x>
                Otkaži
            </b-button>
        </b-form>
    </b-modal>
  </div>
</template>

<script>
export default {
   
    data(){
        return{
            date: '',
            time: '',
            totalPrice: 0,
            orderId: 0,
            deadline : null,
            isBusy: false
        }
    },
    methods: {
        addOffer(event){
            event.preventDefault()
                this.isBusy = true
    
                if(this.totalPrice == 0){
                    this.toast('Morate uneti ukupnu cenu!', 'Neuspešno', 'danger')
                    return;
                }
                if(this.date == '' || this.time == ''){
                    this.toast('Morate izabrati datum i vreme!', 'Neuspešno', 'danger')
                    return;
                }
                 if(new Date(this.date + ' ' + this.time).getTime() < new Date()){
                    this.toast('Rok isporuke ne moze biti pre danasnjeg datuma!', 'Neuspešno', 'danger')
                    return;
                }
                if(new Date(this.date + ' ' + this.time).getTime() > new Date(this.deadline)){
                    this.toast('Rok isporuke ne sme biti nakon krajnjeg roka u narudzbenici!', 'Neuspešno', 'danger')
                    return;
                }
                this.$http
                    .post("offers/addOffer",{
                        totalPrice : this.totalPrice,
                        deliveryDeadline : new Date(this.date + ' ' + this.time).getTime(),
                        orderId : this.orderId
                    })
                    .then( () => {
                        this.toast('Uspešno ste poslali ponudu!' , 'Uspešno', 'success')
                        this.closeModal()
                        this.isBusy = false
                    })
                    .catch( error => {
                        this.isBusy = false
                        if(error.response.status == 400)
                            this.toast('Greska prilikom dodavanja zalbe!', 'Neuspešno', 'danger')
                        else if(error.response.status == 405)
                            this.toast('Ne mozete poslati ponudu jer nemate dovoljan broj lekova na stanju!','Neuspešno', 'danger')
                        else this.toast('Desila se greška! Molimo pokušajte kasnije.','Neuspešno', 'danger')  
                })           
        },
        toast(message, title, variant){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        },
     
        closeModal(){
            this.$bvModal.hide('offerModal')
            this.date = ''
            this.time = ''
            this.totalPrice = 0
            this.orderId = 0
            this.deadline = null

        }
    },
    mounted(){
        this.$root.$on('show-offer-modal', params => {
            this.orderId = params.orderId
            this.deadline = params.deadline
            this.$bvModal.show('offerModal')
        })
    }
}
</script>