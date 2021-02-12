<template>
    <div class="container">
        <h4 class="h4 align-middle my-4">Izveštaji o apoteci</h4>
        <div class="pharmacy-reports-main">
            <b-card no-body>
                <b-tabs pills card align="center" lazy>
                    <b-tab title="Ocena apoteke" active>
                    </b-tab>

                    <b-tab title="Ocene farmaceuta">    
                    </b-tab>

                    <b-tab title="Ocene dermatologa">    
                    </b-tab>

                    <b-tab title="Održani pregledi">   
                    </b-tab>

                    <b-tab title="Potrošnja lekova">   
                    </b-tab>

                    <b-tab title="Prihodi apoteke">   
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return{
            pharmacyId: this.$route.params.id,
            pharmacy: {}
        }
    },
    mounted(){
    },
    components: {
        
    }
}
</script>