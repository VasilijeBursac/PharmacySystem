<template>
    <div class="container mt-4">
        <div class="title-options clearfix">
            <h5 class="h5 float-left mb-4">Lekovi</h5>
        </div>

        <b-row>
            <b-col cols="3">                
                <PharmacyFilters />
            </b-col>
            <b-col>
                <PharmacyTable />
            </b-col>
        </b-row>
    </div>
    
</template>

<script>
import PharmacyFilters from '../../../components/filters/PharmacyFilters.vue'
import PharmacyTable from './PharmacyTable.vue'

export default {
    components:{
        PharmacyFilters,
        PharmacyTable
    }
}
</script>