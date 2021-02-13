<template>
    <div class="allergies">
        <h5 class="h5 text-left">Spisak vaših alegija</h5>
        <b-row>
            <b-col>
                <ul class="text-left">
                    <li v-for="(allergy, index) in splicedAllergies(0)" :key="index">
                        {{allergy.name}}
                        <b-button @click="deleteAllergy(allergy)" size="sm" variant="danger" class="button ml-2">
                            <b-icon-trash-fill scale="0.75" shift-v="3" shift-h="-5" variant="default"></b-icon-trash-fill>
                        </b-button>
                    </li>
                </ul>
            </b-col>

            <b-col>
                <ul class="text-left">
                    <li v-for="(allergy, index) in splicedAllergies(1)" :key="index">
                        {{allergy.name}}
                        <b-button @click="deleteAllergy(allergy)" size="sm" variant="danger" class="button ml-2">
                            <b-icon-trash-fill scale="0.75" shift-v="3" shift-h="-5" variant="default"></b-icon-trash-fill>
                        </b-button>
                    </li>
                </ul>
            </b-col>

            <b-col>
                <ul class="text-left">
                    <li v-for="(allergy, index) in splicedAllergies(2)" :key="index">
                        {{allergy.name}}
                        <b-button @click="deleteAllergy(allergy)" size="sm" variant="danger" class="button ml-2">
                            <b-icon-trash-fill scale="0.75" shift-v="3" shift-h="-5" variant="default"></b-icon-trash-fill>
                        </b-button>
                    </li>
                </ul>
            </b-col>
        </b-row>

        <AddAllergyModal class="mb-4" />
        
    </div>
</template>

<script>
import AddAllergyModal from './AddAllergyModal.vue';
export default {
    data() {
        return {
            allergies: []
        }
    },
    mounted(){
        this.$http
            .get('patient/allergies/' + this.$store.getters.getUserId)
            .then( res => {
                this.allergies = res.data
            })

        this.$root.$on('add-allergy', (allergy) => {
            //console.log(allergy)
            this.allergies.push(allergy)
        })
    },
    methods:{
        splicedAllergies: function(index) {
            let allergies = Array.from(this.allergies)
            return allergies.slice(Math.ceil(allergies.length / 3) * index, Math.ceil(allergies.length / 3) * index + Math.ceil(allergies.length / 3) )
        },
        deleteAllergy: function(allergy){
            this.$http
                .delete('patient/allergies/' + allergy.id)
                .then( (res) => {
                    if(res.status == 200){
                        let index = this.allergies.indexOf(allergy)
                        this.allergies.splice(index, 1)
                        this.toast('Uspešno ste izbrisali alergiju!', 'Uspešno', 'success')
                    }
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
        AddAllergyModal
    }
}
</script>

<style scoped>

    .button{
        width: 1.4rem;
        height: 1.4rem;
    }

</style>