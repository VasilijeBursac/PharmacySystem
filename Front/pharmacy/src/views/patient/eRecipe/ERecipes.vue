<template>
 <div>
      <div id="show-eRecipes">
            <div v-for = "eRecipe in eRecipes" :key="eRecipe.id" class = "single-eRecipe">
                <b-row>
                    <label class="float-left  ml-3">Datum izdavanja : {{new Date(eRecipe.dateOfIssue).toLocaleString()}} </label>
                </b-row>
                <b-row>
                    <label class="float-left ml-3">Sifra eRecepta : {{eRecipe.id}}</label>        
                </b-row>
                <b-table id = "table"  hover :items="eRecipe.drugs" :fields="fields" :tbody-transition-props="transProps">
                </b-table>                                                                  
            </div>

      </div>
  </div>
</template>

<script>


export default {
    data(){
        return {
            eRecipes: [],
            fields: [   
                { label: "Sifra leka", key: "code", sortable: true },
                { label: "Naziv leka", key: "name", sortable: true },
                { label: "Kolicina", key: "quantity", sortable: true }
            ],
      transProps: {
        name: "flip-list"
      },
            showDialog: false
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
            .get('eRecipes')
            .then( res => {
                if(res.status == 200){
                    this.eRecipes = res.data
                }        
            })
            .catch( error => {
                if(error.response.status == 404)
                    this.toast('Nemate nijedan izdati eRecept!','Neuspešno', 'danger')
                else this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
            })
    },
    components:{
  
    }
}
</script>
<style>
    #show-eRecipes{
        max-width: 800px;
        margin: 50px auto 0 auto;
    }
    .single-eRecipe{
        padding:17px;
        margin: 20px 0 20px 0;
        box-sizing: border-box;
        background-color: rgb(238, 238, 238);
    }
    .single-eRecipe label{
        font-size: 17px;
    }
    #table{
         background-color: rgb(255, 255, 255);
    }
 
</style>