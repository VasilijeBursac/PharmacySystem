<template>
    <div>{{pharmacy.rating}}</div>
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