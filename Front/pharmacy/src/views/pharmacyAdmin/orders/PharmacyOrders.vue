<template>
    <div class="container mt-4">
        <AddEditOrderModal :pharmacyId="myPharmacyId" />

        <div class="title-options clearfix">
            <h5 class="h5 float-left mb-4">Narudžbenice apoteke</h5>
            
                
            <div class="float-right d-flex">
                <!-- <b-form-group id = "order-filters" class="float-right text-right" v-slot="{ ariaDescribedby }">
                    <b-form-checkbox-group
                        id="checkbox-group"
                        v-model="orderStatus"
                        :aria-describedby="ariaDescribedby"
                        name="flavour-2"
                    >
                        <b-form-checkbox value="Na čekanju" unchecked-value="">
                            Na čekanju
                        </b-form-checkbox>
                        <b-form-checkbox value="Obrađena" unchecked-value="">
                            Obrađene
                        </b-form-checkbox>
                    </b-form-checkbox-group>               
                </b-form-group> -->

                
                <b-form inline>
                    <b-form-select
                    v-model="statusFilter" 
                    size="sm" class="float-right text-right mt-n3">
                        <b-form-select-option value="">
                            Sve
                        </b-form-select-option>

                        <b-form-select-option value="Čeka ponude">
                            Čeka ponude
                        </b-form-select-option>

                        <b-form-select-option value="Obrađena">
                            Obrađena
                        </b-form-select-option>
                </b-form-select>
                </b-form>

                <b-button variant="success" class=" mt-n2 mb-3 ml-2" @click="openAddOrderModal">
                    <b-icon icon="plus-circle"></b-icon>
                    Dodaj narudžbenicu
                </b-button>
            </div>
        </div>

        <b-row>
            <b-col>
                <PharmacyOrdersTable :pharmacyId="myPharmacyId" />
            </b-col>
        </b-row>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import PharmacyOrdersTable from "./PharmacyOrdersTable.vue"
import AddEditOrderModal from "./AddEditOrderModal.vue"

export default {
    data: function() {
        return {
            statusFilter: ""
        }
    },

    computed: {
        ...mapState(['myPharmacyId'])
    },

    watch:{
        statusFilter: function(){
            this.$root.$emit('filter-orders', this.statusFilter)
        },
    },

    mounted() {
        
    },

    methods: {
        openAddOrderModal() {
            this.$bvModal.show('add-order-modal')
        }
    },

    components: {
        PharmacyOrdersTable,
        AddEditOrderModal
    }
}
</script>

<style>

</style>