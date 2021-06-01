<template>
  <div>
    <h6 id = "h6" class="h6 float-left mb-3 mt-4">Izaberite QR kod sa listom lekova</h6>       
    <b-form-file
        accept=".jpg, .png"
        v-model="file"
        @change="showDrugs=false"
        :state="Boolean(file)"
        placeholder="Izaberite sliku ili je prevucite ovde..."
        drop-placeholder="Prevucite fajl ovde..."
    ></b-form-file>
    <b-row class= " ml-1 mt-3">
        <div>
            Izabrani fajl: {{ file ? file.name : '' }}
            <b-button class = "ml-3" v-if="file" @click="decodeQrCode(file.name)" size="sm" variant="success" >
                Dekodiraj QR kod
            </b-button>
        </div>
    </b-row>
    <div v-if="showDrugs">
        <b-row class= " ml-1 mt-3">
            <h6 class="h6 float-middle mb-3 mt-4">Lekovi za e recept i njihova kolicina:</h6>   
        </b-row>    
        <div v-for="(drug,index) in drugNames" v-bind:key="drug">
            <span class= "">{{drug}} : {{drugQuantities[index]}}</span>
        </div>
    </div>
     <b-row class= " ml-1 mt-3">
        <div v-if="showDrugs && items.length != 0">
          <h6 id = "h6" class="h6 float-left mb-1 mt-4">Apoteke koje na stanju imaju date lekove</h6>       
        </div>
    </b-row>
    <b-table v-if="items.length != 0" striped hover :items="items" :fields="fields">
            <template #cell(akcije)="row">
                <b-button  @click="generateERecipe(row)" size="sm" >
                    <b-icon-check></b-icon-check>
                    Generisi recept
                </b-button>
            </template>
        </b-table>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        file: null,
        drugNames: [],
        drugQuantities:[],
        showDrugs:false,
        items: [],
		fields: [   
            { label: "Naziv apoteke", key: "pharmacyName", sortable: true },
            { label: "Ocena", key: "pharmacyRating", sortable: true },
            { label: "Grad", key: "city", sortable: true },
            { label: "Ukupna cena svih lekova", key: "totalPrice", sortable: true },
            'akcije'
      ],
      }
    },
    methods:{
        decodeQrCode(fileName){
            this.drugQuantities = []
            this.drugNames = []
            this.$http
                .post('qrCode/decodeQrCode',fileName)
                .then( res => {
                   var drugsWithQuantites= res.data.split(",");
                    drugsWithQuantites.forEach(element => {
                        this.drugNames.push(element.split(":")[0])
                        this.drugQuantities.push(element.split(":")[1])
                    });
                    this.showDrugs = true;
                    this.getPharmaciesForErecipe()
                })
                .catch( (error) => {
                    if(error.response.status == 404){
                        this.toast('Izabrana slika ne sadrzi qrCode','Neuspešno', 'danger')
                        return 
                    } else if(error.response.status == 400){
                         this.toast('Morate izabrati sliku iz foldera QrCodes u okviru resources foldera','Neuspešno', 'danger')
                        return 
                    }else this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')
                        return  
                })
           

        },
        getPharmaciesForErecipe(){
            this.$http
                .post('pharmacy/pharmaciesForErecipe',{
                    drugNames : this.drugNames,
                    drugQuantities : this.drugQuantities
                })
                .then( res => {
                    this.items = res.data;
                })
                .catch( (error) => {
                    this.items = []
                    if(error.response.status == 404)
                        this.toast('Nazalost, niejdna apoteka nema na stanju sve navedene lekove!','Neuspešno', 'danger') 
                    else if(error.response.status == 400)
                         this.toast('Nazalost, u sistemu ne postoje registrovani svi od navedenih lekova!','Neuspešno', 'danger') 
                    else this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')  
                })    
        },
        generateERecipe(row){
            this.$http
                .post('eRecipes/addERecipe',{
                    pharmacyId : row.item.pharmacyId,
                    dateOfIssue : new Date(),
                    drugNames : this.drugNames,
                    quantities : this.drugQuantities
                })
                .then( () => {
                        this.toast('Potvrda za izdavanje e recepta vam je poslata na mejl!','Uspesno', 'success')
                })
                .catch( (error) => {
                    this.items = []
                    if(error.response.status == 400)
                        this.toast('Greska prilikom generisanja e recepta!','Neuspešno', 'danger') 
                    else if (error.response.status == 405)
                        this.toast('Greska! Stanje lekova se promenilo i nije moguce izdati sve lekove!Pokusajte u drugoj apoteci!.','Neuspešno', 'danger')  
                    else this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')     
                })    
        },
        toast(message, title, variant){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        }
    }
  }
</script>