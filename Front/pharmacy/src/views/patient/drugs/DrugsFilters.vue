<template>
    <div class="pharmacy-wrapper">
        <h5 class="h5 text-left mb-4">Lekovi</h5>

        <div class="pharmacy-filters">        
            <b-card>
                <h6 class="h6 text-left mb-3">Pretraga lekova</h6>
                <b-form class="text-left">
                    <b-form-group id="name-group" label="Ime leka:" label-for="name-input">
                        <b-form-input
                            id="name-input"
                            v-model="name"
                            placeholder="Unesite ime"
                            required>
                        </b-form-input>
                    </b-form-group>

                    <b-form-group id="rating-group" label="Ocena:" label-for="rating-input">
                        <b-form-rating id="rating-input" v-model="rating"></b-form-rating>
                    </b-form-group>

                </b-form>

            </b-card>
         </div>
    </div>
</template>

<script>
export default {
    data(){
        return {
            name: '',
            rating: 0
        }
    },
    watch:{
        name: function(){
            this.$root.$emit('drug-name', this.name)
        },
        rating: function(){
            this.$root.$emit('drug-rating', this.rating)
        }
    }
}
</script>