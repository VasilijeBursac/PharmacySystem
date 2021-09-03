<template>
    <div class="pharmacy-wrapper">
        <div class="pharmacy-filters">        
            <b-card>
                <h6 class="h6 text-left mb-3">Pretraga zaposlenih</h6>
                <b-form class="text-left">
                    <b-form-group id="name-group" label="Ime:" label-for="name-input">
                        <b-form-input
                            id="name-input"
                            v-model="name"
                            placeholder="Unesite ime"
                            required>
                        </b-form-input>
                    </b-form-group>

                    <b-form-group id="surname-group" label="Prezime:" label-for="surname-input">
                        <b-form-input
                            id="surname-input"
                            v-model="surname"
                            placeholder="Unesite prezime"
                            required>
                        </b-form-input>
                    </b-form-group>

                    <b-form-group id="pharmacy-name-group" label="Ime apoteke u kojoj je zaposlen:" label-for="pharmacy-name-input">
                        <b-form-input
                            id="name-input"
                            v-model="pharmacyName"
                            placeholder="Unesite ime apoteke"
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
            surname: '',
            pharmacyName: '',
            rating: 0
        }
    },
    watch:{
        name: function(){
            this.$root.$emit('employee-name', this.name)
        },
        surname: function(){
            this.$root.$emit('employee-surname', this.surname)
        },
        pharmacyName: function(){
            this.$root.$emit('pharmacy-name', this.pharmacyName)
        },
        rating: function(){
            this.$root.$emit('employee-rating', this.rating)
        }
    }
}
</script>