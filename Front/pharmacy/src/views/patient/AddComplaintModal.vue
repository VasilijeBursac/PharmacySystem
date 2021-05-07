<template>
  <div class="complaintModal">

    <b-modal id="complaintModal" hide-footer title="Podnosenje zalbe za izabranu apoteku">
        <b-form @submit="addComplaint">
            <b-form-group id="complaint-group" label="Unesite zalbu:" label-for="comlpaint-input" class="text-center">
                <b-form-textarea
                    rows = "3"
                    max-rows = "4"
                    class="text-center"
                    id="comlpaint-input"
                    v-model="text"
                    placeholder="Unesite tekst zalbe"
                    required>
                </b-form-textarea>
            </b-form-group>
        
                <b-button type = "submit" block variant="success">
                    Podnesi zalbu
                </b-button>
      

            <b-button @click="closeModal" block variant="danger">
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
            objectId : null,
            complaintType : '',
            text: '',
            isBusy: false
        }
    },
    methods: {
        addComplaint(event){
            event.preventDefault()
                this.isBusy = true
                this.$http
                    .post("complaint/addComplaint",{
                        objectId : this.objectId,
                        text : this.text,
                        type : this.complaintType
                    })
                    .then( () => {
                        this.toast('Uspešno ste podneli zalbu !' , 'Uspešno', 'success')
                        this.closeModal()
                        this.isBusy = false
                    })
                    .catch( error => {
                        this.isBusy = false
                        if(error.response.status == 400)
                            this.toast('Greska prilikom dodavanja zalbe !', 'Neuspešno', 'danger')
                        else this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
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
            this.$bvModal.hide('complaintModal')
            this.text = ''
            this.complaintType = ''
            this.objectId = null

        }
    },
    mounted(){
         this.$root.$on('show-complaint-modal', (params) => {
            this.$bvModal.show('complaintModal')
            this.objectId = params.objectId
            this.complaintType = params.complaintType
        })
    }
}
</script>