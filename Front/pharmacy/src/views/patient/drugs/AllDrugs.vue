<template>
    <div class="container">
        <b-row class="mt-4">
            <b-col cols="4">                
                <DrugsFilters />
            </b-col>
            <b-col>
                <DrugsTable class="mt-5" />
            </b-col>
        </b-row>
    </div>
    
</template>

<script>
import DrugsFilters from './DrugsFilters.vue'
import DrugsTable from './DrugsTable.vue'

export default {
    components:{
        DrugsFilters,
        DrugsTable
    }
}
</script>