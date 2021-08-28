<template>
    <div class="container mt-4">
        <div class="title-options clearfix">
            <h5 class="h5 float-left mb-4">Lekovi</h5>
        </div>
        
        <b-row>
            <b-col cols="3">                
                <DrugsFilters />
            </b-col>
            <b-col>
                <DrugsTable  />
            </b-col>
        </b-row>
    </div>
    
</template>

<script>
import DrugsFilters from '../../../components/filters/DrugsFilters.vue'
import DrugsTable from './DrugsTable.vue'

export default {
    components:{
        DrugsFilters,
        DrugsTable
    }
}
</script>