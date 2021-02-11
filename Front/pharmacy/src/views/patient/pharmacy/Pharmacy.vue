<template>
    <div class="container">
        <b-row class="mt-4">
            <b-col cols="4">                
                <PharmacyFilters />
            </b-col>
            <b-col>
                <PharmacyTable class="mt-5" />
            </b-col>
        </b-row>
    </div>
    
</template>

<script>
import PharmacyFilters from './PharmacyFilters.vue'
import PharmacyTable from './PharmacyTable.vue'

export default {
    components:{
        PharmacyFilters,
        PharmacyTable
    }
}
</script>