<template>
    <div>
        <h6 class="h6 mb-3 text-left">Broj održanih pregleda na mesečnom nivou</h6>
        <Chart />

        <h6 class="h6 mt-5 mb-3 text-left">Broj održanih pregleda na kvartalnom nivou</h6>
        <Chart />

        <h6 class="h6 mt-5 mb-3 text-left">Broj održanih pregleda na godišnjem nivou</h6>
        <Chart />
    </div>
</template>

<script>
export default {
    props: ["pharmacyId"],
    data: function() {
        return {

        }
    },

    mounted() {
        this.getChartDataLastMonth()
        // this.getChartDataLastQuartal()
        // this.getChartDataLastYear()
    },

    methods: {
        getChartDataLastMonth() {
            this.$http
                .post('/examination/report/sadadas/' + this.pharmacyId, {
                    startDate: new Date(new Date().getTime() - 30*24*60*60*1000),
                    endDate: new Date()
                })
                .then( res => {

                    console.log(new Date(new Date().getTime() - 30*24*60*60*1000))
                    
                    console.log(res.data)
                })
                .catch((error) => {
                    console.log(error)
                    if (error.response.status == 403 || error.response.status == 401) {
                        this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                    }
                    else 
                        this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
                })
        }
    },

    components: {
        Chart: () => import("@/components/Chart.vue")
    }
}
</script>

<style>

</style>