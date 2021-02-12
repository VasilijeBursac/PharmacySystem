<template>
    <div class="pharmacy-drugs">
        <b-table striped hover :items="items"  class="text-middle mt-2">
			<template>
				<b-button size="sm" >
					Prikaži detaljnije
				</b-button>
			</template>
        </b-table>
    </div>
</template>

<script>
export default {
    props: ['pharmacyId'],
    data: function() {
        return{
            items: [],
            
        } 
    },
    mounted(){
        this.$http
            .get('/drugs/byPharmacyId/' + this.pharmacyId)
            .then( res => {
                this.items = JSON.parse(JSON.stringify(res.data))
                console.log(this.items)
            })
    },
    methods:{

    },
    components:{
    }
}
</script>