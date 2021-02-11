
<template>
    <div class="register">
        <b-form id = "form" v-if="show">
            <b-row>
                <b-col>
                <b-form-group id="name-group" label="Naziv:" label-for="name-input" class="text-center">
                    <b-form-input
                        class="text-center"
                        id="name-input"
                        v-model="drug.name"
                        placeholder="Unesite naziv leka"
                        required>
                    </b-form-input>
                </b-form-group>

                <b-form-group id="code-group" label="Sifra:" label-for="code-input" class="text-center">
                    <b-form-input
                        class="text-center"
                        id="code-input"
                        v-model="drug.code"
                        placeholder="Unesite sifru leka"
                        required>
                    </b-form-input>
                </b-form-group>

                <b-form-group id="type-group" label="Tip leka:" label-for="typa-input" class="text-center">
                    <b-form-input
                        class="text-center"
                        id="type-input"
                        v-model="drug.type"
                        placeholder="Unesite tip leka"
                        required>
                    </b-form-input>
                </b-form-group>

                <b-form-group id="shape-group" label="Oblik:" label-for="shape-input" class="text-center">
                    <b-form-input
                        class="text-center"
                        id="shape-input"
                        v-model="drug.shape"
                        placeholder="Unesite oblik leka"
                        required>
                    </b-form-input>
                </b-form-group>
            </b-col>
            <b-col>
            <b-form-group id="manifacturer-group" label="Proizvodjac:" label-for="manifacturer-input" class="text-center">
                <b-form-input
                    class="text-center"
                    id="manifacturer-input"
                    v-model="drug.manifacturer"
                    placeholder="Unesite proizvodjaca"
                    required>
                </b-form-input>
            </b-form-group>

            <b-form-group id="suggestedDose-group" label="Preporucena doza:" label-for="suggestedDose-input" class="text-center">
                    <b-form-input
                        class="text-center"
                        id="suggestedDose-input"
                        v-model="drug.suggestedDose"
                        placeholder="Unesite preporucenu dozu"
                        required>
                    </b-form-input>
                </b-form-group>

             <b-form-group id="loyaltyPoints-group" label="Lojalni poeni:" label-for="loyaltyPoints-input" class="text-center">
                <b-form-input

                    class="text-center"
                    type = "number"
                    min = "0"
                    id="loyaltyPoints-input"
                    v-model="drug.loyaltyPoints"
                    placeholder="Unesite broj lojalti poena"
                    required>
                </b-form-input>
            </b-form-group>

            <b-form-group id="additionalInfo-group" label="Dodatne napomene:" label-for="additionalInfo-input" class="text-center">
                <b-form-textarea
                    rows = "3"
                    class="text-center"
                    id="additionalInfo-input"
                    v-model="drug.additionalInfo"
                    placeholder="Unesite dodatne napomene"
                    required>
                </b-form-textarea>   
            </b-form-group>
                    
         </b-col>
        </b-row>
        <b-row>
            <b-col >
                 <div>
                    <b-form-group id="contraindication-group" label="Unesite nezeljeno dejstvo:" label-for="contraindication-input" class="text-center">
                        <b-form-input
                            class="text-center"
                            id="contraindication-input"
                            v-model="drug.contraindication"
                            placeholder="Unesite nezeljeno dejstvo"
                            required>
                        </b-form-input>
                    
                    </b-form-group>
                    <b-button  @click="addContraindication()"  class="mr-2">
                        Dodaj nezeljeno dejstvo
                    </b-button>
                </div>
                <div id="contraindications-table">
                    <b-table id = "contraindicationsTable" striped  :tbody-transition-props="transProps" :items="drug.contraindications" :fields="Contraindicationfields">
                           <template #cell(Ukloni)="row">
                                {{row.value}}
                                <b-button @click="deleteContraindication(row.item)">
                                    <b-icon-x></b-icon-x>                               
                                </b-button>
                           </template>
                    </b-table>
                </div>
            </b-col>
            <b-col>
                <div>
                    <b-form-group id="ingredients-group" label="Unesite sastojak:" label-for="ingredient-input" class="text-center">
                        <b-form-input
                            class="text-center"
                            id="ingredient-input"
                            v-model="drug.ingredient"
                            placeholder="Unesite sastojak"
                            required>
                        </b-form-input>
                    
                    </b-form-group>
                    <b-button  @click="addIngredient()" class="mr-2">
                        Dodaj sastojak
                    </b-button>
                </div>
                <div id="ingredients-table">
                    <b-table id = "ingredientsTable" striped  :tbody-transition-props="transProps" :items="drug.ingredients" :fields="Ingredientfields">
                        <template #cell(Ukloni)="row">
                            <b-button @click="deleteIngredient(row.item)" >
                                 <b-icon-x></b-icon-x>                               
                            </b-button>
                        </template>
                    </b-table>
                </div>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                 <b-form-group id="substituteDrugs-group" label="Izaberi zamenski lek:" label-for="substituteDrugs-input" class="text-center">
                    <b-select v-model="selected" >
                        <option v-for="sdrug in allDrugs" v-bind:key = "sdrug" v-bind:value="sdrug">{{sdrug.name}}</option>
                    </b-select>
                </b-form-group>
                <b-button  @click="addSubstituteDrug()" class="mr-2">
                        Dodaj zamenski lek
                </b-button>
            </b-col>
             <b-col>
                 <div id="substituteDrugs-table">
                    <b-table id = "substituteDrugsTable" striped  :tbody-transition-props="transProps" :items="drug.substituteDrugs" :fields="SubDrugfields">
                        <template #cell(Ukloni)="row">
                            <b-button @click="deleteSubstitueDrug(row.item)" >
                                 <b-icon-x></b-icon-x>                               
                            </b-button>
                        </template>
                    </b-table>
                </div>
            </b-col>
        </b-row>
        </b-form>
        <div class="buttons text-center">                        
                <b-button @click="onSubmit()" variant="success" class="mr-2">
                    <b-icon-check></b-icon-check>
                    Dodaj lek</b-button>
                <b-button @click="onReset()" variant="danger">
                    <b-icon-x></b-icon-x>
                    Otkaži
                </b-button>
            </div>
    </div>
</template>

<script>


export default {
    data() {
        return {
            drug: {
                name: '',
                code: '',
                type: '',
                shape: '',
                manifacturer: '',
                additionalInfo: '',
                loyaltyPoints: '',
                suggestedDose: '' ,
                contraindication: '',
                contraindications: [],
                ingredient: '',
                ingredients: [],
                substituteDrugs: []
            },
            allDrugs: [],
            selected: '',
            Contraindicationfields: [{
                key: 'name',
                label: 'Uneta nezeljena dejstva',
                }, 'Ukloni'],
            Ingredientfields: [{
                key: 'name',
                label: 'Uneti sastojci',
            },'Ukloni'],
            SubDrugfields:[{
                key: 'name',
                label: 'Izabrani zamenski lekovi',
            },'Ukloni'],
                show: true
        }
    },
    methods: {
        addContraindication(){
            if(this.drug.contraindication !== ""){
                 this.drug.contraindications.push({ name : this.drug.contraindication}); 
                 this.drug.contraindication = '';              
            }
           
        },
        addIngredient(){
            if(this.drug.ingredient !== ""){
                 this.drug.ingredients.push({ name : this.drug.ingredient}); 
                 this.drug.ingredient = '';              
            }
           
        },
         deleteContraindication(deletedContraindication){
          var newArray = this.drug.contraindications.filter(function (contraindication) {
          return contraindication !== deletedContraindication;
        });
          this.drug.contraindications = newArray
        },
         deleteIngredient(deletedIngredient){
          var newArray = this.drug.ingredients.filter(function (ingredient) {
          return ingredient !== deletedIngredient;
        });
          this.drug.ingredients = newArray
        },
         addSubstituteDrug(){
          if(this.selected !== "" && !this.drug.substituteDrugs.includes(this.selected)){
                 this.drug.substituteDrugs.push(this.selected); 
                 this.selected = '';              
            }
        },
        deleteSubstitueDrug(deletedSubstitueDrug){
          var newArray = this.drug.substituteDrugs.filter(function (substituteDrug) {
          return substituteDrug !== deletedSubstitueDrug;
        });
          this.drug.substituteDrugs = newArray
        },
            onSubmit() {
                alert(JSON.stringify(this.drug));
                this.$http
                .post("drugs/addDrug",{
                    code : this.drug.code,
                    loyalityPoints : this.drug.loyaltyPoints,
                    name : this.drug.name,
                    type : this.drug.type,
                    shape : this.drug.shape,
                    manifacturer: this.drug.manifacturer,
                    additionalInfo : this.drug.additionalInfo,
                    substituteDrugs : this.drug.substituteDrugs,
            })
            .then( () => {
                  this.toast()  
                   
                })                    
                .catch(function (error) {
                    if(error.response.status === 500) {
                    alert('Error');               
                    }
                });       
        },
        toast(){
            this.$bvToast.toast(`Uspešno ste dodali novi lek!`, {
                title: 'Uspešno!',
                variant: 'success',
                autoHideDelay: 5000
            })
        },
        onReset() {
         
            // Reset our form values
            this.drug.name = ''
            this.drug.code = ''
            this.drug.type = ''
            this.drug.shape = ''
            this.drug.manifacturer = ''
            this.drug.additionalInfo = ''
            this.drug.contraindication = ''
            this.drug.contraindications = []
            this.drug.ingredient = ''
            this.drug.ingredients = []
            this.drug.loyaltyPoints = ''
            this.drug.suggestedDose = ''
            this.drug.substituteDrugs = []
            this.selected = ''
          
            // Trick to reset/clear native browser form validation state
            this.show = false
            this.$nextTick(() => {
                this.show = true
            })
        }
    },

    components:{
      
    },

    mounted(){
         this.$http
                .get("drugs/")
                .then( res => {   
                        this.allDrugs= res.data        		
                })       
    }
}
</script>
<style scoped>
    #form {
        width : 70%;
        margin : auto
    }
   /* #loyaltyPoints-group {
        margin-top: 7%;
    } */
</style>
