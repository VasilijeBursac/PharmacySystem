<template>
 <div>
      <h3 id = "h3" class="h3 align-middle ">Sve narudzbenice</h3>     
      <div id="show-orders">
            <div v-for = "order in orders" :key="order.orderId" class = "single-order">
                <b-row>
                    <label class="float-left  ml-3">Narudzbenica za apoteku : {{order.pharmacyName}}</label>
                </b-row>
                <b-row>
                    <label class="float-left ml-3">Rok za isporuku : {{new Date(order.deadline).toLocaleString()}}</label>        
                </b-row>
                <b-row>
                    <label class="float-left ml-3">Status narudzbenice : {{order.status == "Waiting" ? "Ceka ponude" : "Obradjena"}}</label>        
                </b-row>
                <b-table id = "table"  hover :items="order.drugsInOrder" :fields="fields" :tbody-transition-props="transProps">
                </b-table>     
                <div id = "button">                        
                    <b-button  variant="success" @click="openModal(order.orderId, order.deadline)">
                        <b-icon-pencil-square></b-icon-pencil-square>
                        Napisi ponudu
                    </b-button>   
                </div>                                                                             
            </div>
            <AddOfferModal/>  
      </div>
  </div>
</template>

<script>
import AddOfferModal from "./AddOfferModal.vue";

export default {
    data(){
        return {
            orders: [],
            fields: [   
                { label: "Naziv leka", key: "drugName", sortable: true },
                { label: "Kolicina", key: "quantity", sortable: true }
            ],
      transProps: {
        name: "flip-list"
      },
            showDialog: false
        }
    },
    methods:{
        openModal(orderId, deadline){
            this.$root.$emit('show-offer-modal',{
                orderId : orderId,
                deadline : deadline
            })
        }
    },
    mounted(){
        this.$http
            .get('orders/allWaitingOrders')
            .then( res => {
                if(res.status == 200){
                    this.orders = res.data
                }        
            })
            .catch( error => {
                if(error.response.status == 404)
                    this.toast('Trenutno ne postoji nijedna narudbenica!','Neuspešno', 'danger')
                else this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
            })
    },
    components:{
        AddOfferModal
    }
}
</script>
<style>
    #show-orders{
        max-width: 800px;
        margin: 50px auto 0 auto;
    }
    .single-order{
        padding:17px;
        margin: 20px 0 0 0;
        box-sizing: border-box;
        background-color: rgb(238, 238, 238);
    }
    .single-order label{
        font-size: 17px;
    }

    #h3{
         margin: 20px 0 0 0; 
    }

    #h4{
        margin: 50px 0px 25px 0px;
        text-align: left;
    }

    #table{
         background-color: rgb(255, 255, 255);
    }
 
</style>