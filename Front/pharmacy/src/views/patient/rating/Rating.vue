<template>
    <div class="container mt-4">
        <h5 class="h5 float-left text-left">Sve ocene</h5>

        <RatingModal class="float-right mt-n2 mb-3"/>

        <ChangeRatingModal />

        <b-table ref="drug" striped hover :items="items" :fields="fields">
            <template #cell(akcije)="row">
                <b-button @click="showChangeDialog(row)" size="sm" >
                    <b-icon-pencil-square></b-icon-pencil-square>
                    Izmeni ocenu
                </b-button>
            </template>
        </b-table>
    </div>
</template>

<script>

import ChangeRatingModal from './ChangeRatingModal'

export default {
    data(){
        return {
            items: [],
			fields:['predmet ocene', 'ime', 'ocena', 'akcije'],

            showDialog: false
        }
    },
    methods:{
        showChangeDialog(row){
            this.$root.$emit('show-change-rating-modal', row.item)
        }
    },
    mounted(){
        this.$http
            .get('rating/all')
            .then( res => {
                if(res.status == 200){
                    let data = []
                        res.data.forEach(element => {
                            data.push({ 
                                'predmet ocene': element.ratingObject,
                                'ime': element.objectName,
                                'ocena': element.rating,
                                'id': element.id
                            })
                        });
                        this.items = data
                }        
            })
            .catch( (error) => {
                if(error.response.status != 200)
                    this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
            })
    },
    components:{
        RatingModal: () => import('./RatingModal.vue'),
        ChangeRatingModal
    }
}
</script>