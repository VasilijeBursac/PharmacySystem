<template>
  <div class="allergy-modal">
      <b-button 
                variant="success"
                size="sm"
                class="float-left"
                @click="openModal">
                <b-icon-plus></b-icon-plus>
                Dodaj alergiju
    </b-button>

    <b-modal id="allergy" hide-footer title="Dodaj alergiju na lek">
        <b-form>
            <b-form-select v-model="selected" :options="options" class="mb-3"></b-form-select>

            <b-button @click="addAllergy" block variant="success">
                Dodaj alergiju
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
            selected: null,
            options: []
        }
    },
    methods: {
        addAllergy: function(){
            if(this.selected != null){
                this.$http
                    .post('patient/allergies/' + this.selected)
                    .then( () => {
                        this.$root.$emit('add-allergy', {
                            id: this.selected,
                            name: this.options[this.selected].text
                        })
                        this.toast('Uspešno ste dodali alergiju!', 'Uspešno', 'success')
                        this.closeModal()
                        this.selected = null
                    })
                    .catch( (error) => {
                        if(error.response.status == 400){
                            this.toast('Ne možete dodati dve iste alergije!', 'Neuspešno', 'danger')
                        }else this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
                })
            }            
        },
        toast(message, title, variant){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        },
        openModal(){
            this.$bvModal.show('allergy')
        },
        closeModal(){
            this.$bvModal.hide('allergy')
        }
    },
    mounted(){
        this.$http
            .get('drugs')
            .then( (res) => {
                if(res.status == 200){
                    this.options = []
                    this.options.push({
                            value: null,
                            text: 'Izaberite lek'
                        })
                    res.data.forEach(drug => {
                        this.options.push({
                            value: drug.id,
                            text: drug.name
                        })
                    });
                }
            })
    }
}
</script>