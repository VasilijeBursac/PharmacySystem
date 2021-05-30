<template>
    <div class="container">
        <h4 class="h4 align-middle my-4">Aktivacija</h4>
        <div class="activation-page">
            <b-card no-body id = "card">
                <span id = "message">Dobrodosli! Uspesno ste aktivirali svoj nalog.</span>
            </b-card>
        </div>
    </div>
</template>

<script>


export default {
    data() {
        return {
          userId : this.$route.query.id,  
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
            .put('patient/activate/' + this.userId)
            .then( () => {
            })
            .catch( error => {
                if(error.response.status != 200)
                    this.toast('Greska prilikom aktivacije naloga!Molimo pokusajte kasnije.','Neuspešno', 'danger') 
            })
    }
}
</script>
<style >
    #message{
        font-size: 30px;
    }
    #card{
        text-align: center;
        margin: 100px;
    }
</style>