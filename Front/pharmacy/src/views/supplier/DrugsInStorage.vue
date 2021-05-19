<template>
    <div class="container mt-4">
        <h5 class="h5 float-center text-center">Svi lekovi na stanju</h5>
        <b-table :tbody-transition-props="transProps" striped hover :items="items" :fields="fields">
        </b-table>
    </div>
</template>

<script>

export default {
    data(){
        return {
            items: [],
			fields:[{key:'Naziv leka', sortable:true}, {key:'Kolicina', sortable:true}],
            transProps: {
                name: "flip-list"
            },
        }
    },
    methods:{
        toast(message, title, variant){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        }
    },
    mounted(){
        this.$http
            .get('drugInStorage/allDrugsInStorage')
            .then( res => {
                if(res.status == 200){
                    let data = []
                        res.data.forEach(element => {
                            data.push({ 
                                'Naziv leka': element.drugName,
                                'Kolicina': element.quantity,
                            })
                        });
                        this.items = data
                }        
            })
            .catch( (error) => {
                if(error.response.status == 400)
                    this.toast('Trenutno nemate lekova u ponudi!','Neuspešno', 'danger')  
                else this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
                    
            })
    },
    components:{
    }
}
</script>