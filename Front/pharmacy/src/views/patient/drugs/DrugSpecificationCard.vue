<template>
    <div class="drug-specification-card">
        <b-card class="overflow-hidden">
        <b-row>
            <b-col>
                    <h6 class="h6 float-left text-left mb-4">Specifikacija leka</h6>
            </b-col>    
        </b-row>      
        <b-row>
            <b-col class=" float-left text-left mb-4">
                Sastojci : {{ingredients}}
            </b-col>    
        </b-row>        
        <b-row>
            <b-col class=" float-left text-left mb-4">
                Kontraindikacije : {{contraindications}}
            </b-col>    
        </b-row>
        <b-row>
            <b-col class=" float-left text-left mb-4">
                Zamenski lekovi : {{substituteDrugs}}
            </b-col>    
        </b-row>
        <b-row>
            <b-col class=" float-left text-left">
                Preporuceni unos : {{suggestedDose}}
            </b-col>    
        </b-row>
        </b-card>
    </div>        
</template>

<script>

export default {
    props: ['drugId'],
    data: function() {
        return{
            drugSpecification: {},
            contraindications: '',
            ingredients: '',
            substituteDrugs: '',
            suggestedDose: 0
        } 
    },
    methods:{
        toast(message, title, variant){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        },
        getContraindicationsString(){
            this.drugSpecification.contraindications.forEach(element => {
                this.contraindications += element.name.toLowerCase() + "," 
                });
            this.contraindications = this.contraindications.substring(0,this.contraindications.length - 1) + "."
            if(this.contraindications == '.') this.contraindications = "nema podataka o kontraindikacijama."
        },
        getIngredientsString(){
            this.drugSpecification.ingredients.forEach(element => {
                this.ingredients += element.name.toLowerCase() + "," 
                });
            this.ingredients = this.ingredients.substring(0,this.ingredients.length - 1) + "."
            if(this.ingredients == '.') this.ingredients = "nema podataka o sastojcima."
        },
        getSubstituteDrugsString(){
            this.drugSpecification.substituteDrugs.forEach(element => {
                this.substituteDrugs += element.name.toLowerCase() + "," 
                });
            this.substituteDrugs = this.substituteDrugs.substring(0,this.substituteDrugs.length - 1) + "."
            if(this.substituteDrugs == '.') this.substituteDrugs = "nema podataka o zamenskim lekovima."
        }
    },
     mounted(){
        this.$http
            .get('drugs/drugSpecification/' + this.drugId)
            .then( res => {
                this.drugSpecification = res.data;
                this.suggestedDose = this.drugSpecification.suggestedDose
                this.getIngredientsString()
                this.getContraindicationsString()
                this.getSubstituteDrugsString()
            })
    },
}
</script>
<style >
</style>