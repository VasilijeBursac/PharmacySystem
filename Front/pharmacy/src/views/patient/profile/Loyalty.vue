<template>
    <div class="loyalty">
         <b-overlay
                :show="isBusy"
                rounded
                bg-color="white"
                spinner-small
                spinner-variant="primary"
                block>
        <b-row>
            <b-col class="text-left">
                <h6 class="h6">Loyalty program</h6>
                <div class="loyalty-category">
                    <b-icon-award-fill width="32" height="32"></b-icon-award-fill>
                    <h3 class="h3">{{loyalty_category | upperCase}}</h3>
                </div>                        
                <span>{{discount}}% popusta</span>
            </b-col>
            <b-col class="text-left">
                <h6 class="h6">Lojalni poeni</h6>
                <div class="loyalty-points">
                    <span>{{loyalty_points}}</span>
                    <span> </span>
                    <span> POENA</span>
                </div>                       
            </b-col>
            <b-col class="text-left">
                <h6 class="h6">Kazneni penali</h6>
                <div class="penalty-points">
                    <span>{{penalty_points}}</span>
                    <span> POENA</span>
                </div>
            </b-col>
        </b-row>
         </b-overlay>
    </div>
</template>

<script>
export default {
    data(){
        return{
            loyalty_points: 0,
            penalty_points: 0,
            discount: 0,
            loyalty_category: '',

            isBusy: true
        }
    },
    methods:{
        fillCategory(){
            this.$http
            .get('/loyalty/points')
            .then( res => {
                this.loyalty_category = res.data.name
                this.discount = res.data.discount
            })
        }
    },
    mounted(){
        this.$http
            .get('/patient/points/' + this.$store.getters.getUserId)
            .then( res => {
                this.loyalty_points = res.data.loyaltyPoints
                this.penalty_points = res.data.penaltyPoints
                this.fillCategory()

                this.isBusy = false
            })
            .catch(() => {
                this.isBusy = false
            })
    },
    filters:{
        upperCase: function(value){
            return value.toUpperCase()
        }
    }
}
</script>

<style scoped>

    .loyalty-category{
        display: flex;
        align-content: center;
    }

    /*.loyalty-points{
        border: 2px solid black;
        border-radius: 50%;
        width: 100px;
        height: 100px;
        text-align: center;
        align-self: center;
    }*/

    .penalty-points > *{
        display: block;
    }

</style>