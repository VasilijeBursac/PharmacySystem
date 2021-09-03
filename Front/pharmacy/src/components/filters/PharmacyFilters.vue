<template>
    <div class="pharmacy-wrapper">
        <div class="pharmacy-filters">        
            <b-card>
                <h6 class="h6 text-left mb-3">Pretraga apoteka</h6>
                <b-form class="text-left">
                    <b-form-group id="name-group" label="Ime apoteke:" label-for="name-input">
                        <b-form-input
                            id="name-input"
                            v-model="name"
                            placeholder="Unesite ime"
                            required>
                        </b-form-input>
                    </b-form-group>

                    <b-form-group id="city-group" label="Grad:" label-for="city-input">
                        <b-form-input
                            id="city-input"
                            v-model="city"
                            placeholder="Unesite grad"
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
            city: '',
            rating: 0
        }
    },
    watch:{
        name: function(){
            this.$root.$emit('pharmacy-name', this.name)
        },
        city: function(){
            this.$root.$emit('pharmacy-city', this.city)
        },
        rating: function(){
            this.$root.$emit('pharmacy-rating', this.rating)
        }
    }
}
</script>