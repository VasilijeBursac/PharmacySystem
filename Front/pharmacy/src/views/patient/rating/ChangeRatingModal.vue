<template>
    <div>
        <b-modal id="change-rating" hide-footer title="Izmenjivanje ocene">
            <b-form>
                <h6 class="h6 mb-3"><span class="span">Predmet ocenjivanja:</span> {{name}}</h6>
                <b-form-group id="change-rating-group" ref="changeRating" label="Izaberite ocenu:" label-for="change-rating-input">
                    <b-form-rating v-model="rating"></b-form-rating>
                </b-form-group>

                <b-button @click="changeRating()" block variant="success">
                    Izmeni ocenu
                </b-button>
            </b-form>      
        </b-modal>
    </div>    
</template>

<script>
export default {
    data(){
        return{
            id: 0,
            name: '',
            rating: 0
        }
    },
    methods:{
        changeRating(){
            this.$http
            .get('rating/change?id=' + this.id + "&rating=" + this.rating)
            .then( res => {
                if(res.status == 200){
                    this.toast('Uspešno ste promenili ocenu!', 'Uspešno', 'success')
                    this.$bvModal.hide('change-rating')
                    setTimeout( () => {
                        this.$router.go(0)
                    }, 250)
                }        
            })
            .catch( (error) => {
                if(error.response.status != 200)
                    this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
            })
        },
        toast(message, title, variant){
            this.$root.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        }
    },
    mounted(){
        this.$root.$on('show-change-rating-modal', (row) => {
            this.$bvModal.show('change-rating')
            this.id = row.id
            this.name = row.ime
            this.rating = row.ocena
        })
    }
}
</script>

<style scoped>
    .span{
        font-weight: normal;
    }
</style>