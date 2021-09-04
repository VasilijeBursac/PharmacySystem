<template>
    <div>
        <h6 class="h6">Ocena apoteke:</h6>
        <div class="pharmacy-rating d-flex justify-content-center">
            <h3 class="h3 mr-1">{{ pharmacy.rating != "0.0" ? pharmacy.rating : "Nema ocenu" }}</h3>
            <b-icon-star-fill width="32" height="32"></b-icon-star-fill>
        </div>                        
    </div>
</template>

<script>
export default {
    props: ["pharmacyId"],
    data: function() {
        return {
            pharmacy: {}
        }
    },

    mounted(){
        this.getPharmacy()
    },

    methods: {
        getPharmacy() {
            this.$http
            .get('/pharmacy/' + this.pharmacyId)
            .then( res => {
                this.pharmacy = JSON.parse(JSON.stringify(res.data))
            })
        },
    }
}
</script>

<style>

</style>