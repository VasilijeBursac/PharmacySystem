<template>

  <div>
  <notifications animation-type="velocity"/>
   <b-modal ref="my-modal" hide-footer title="Da li je pacijent dosao na pregled?">
            <div class="d-block text-center">
              
            </div>
            <b-row>
              <b-col>
                <b-button
                  class="mt-3"
                  variant="outline-primary"
                  block
                  @click="hideModal"
                  >NE</b-button
                >
              </b-col>
              <b-col>
                <b-button
                  class="mt-3"
                  variant="success"
                  block
                  @click="startIt"
                  >DA</b-button
                >
              </b-col>
            </b-row>
          </b-modal>
  <h4>Izaberite pacijenta za kojeg ce se vrsiti pregled</h4>
   <b-input-group prepend="Prezime i ime" class="mt-3">
    <b-form-input v-model="imeIPrezime"></b-form-input>
    <b-input-group-append>
      <b-button variant="info" v-on:click="search(imeIPrezime)">Pretrazi</b-button>
    </b-input-group-append>
  </b-input-group>
     <b-table
            primary-key="id"
            
            :items="items"
            :fields="Fields"
             selectable
           :select-mode="single"
            responsive="sm"
            ref='selectableTable'
             @row-selected="onRowSelected"
          ></b-table>
  </div>
  
</template>

<script>
export default {
    data(){
       return{ items : [],
        Fields: [
        { key: "name" , sortable: true },
        { key: "surname", sortable: true },
      ], imeIPrezime : '',
         choosedPatient :'',
          userRole: 'ROLE_UNREGISTERED',
      };
    },
     created() {
  // GET request using axios with error handling
   this.$axios.get("http://localhost:9001/patient/patientBySurnameAndName/*")
            .then(response => this.items = response.data)
            .catch(error => {
            this.errorMessage = error.message;
            console.error("There was an error!", error);});
    },methods: {
        search: function(imeIPrezime){
           if(imeIPrezime.trim() === "")
                {
                    imeIPrezime = "*"
                }

            this.$axios.get("http://localhost:9001/patient/patientBySurnameAndName/"+ imeIPrezime)
            .then(response => { this.items = response.data
            if(response.data.length === 0){
                 this.$notify({  
                type: "info",
                title: 'Empty',
                text: ' Nema trazenih korisnika!',
                closeOnClick : true
            });
            }})
            .catch(() => {
            this.$notify({
                 type: "error",
                title: 'Error',
                text: 'Doslo je do greske prilikom poziva!',              
                closeOnClick : true
            });
        })},
        subtract: function(dec){
            this.age -= dec;
        },startExamination:function(){
            this.$axios
            .get("http://localhost:9001/examination/isPatientAppropriate/"+this.choosedPatient.id)
          .then((response) => {
            this.message = response.data;
            if (response.status == 200) {
              this.open()

              this.$notify({
                type: "success",
                title: "Success",
                text: this.message,
                closeOnClick: true,
              });
            }else{
              this.$notify({
                type: "error",
                title: "Error",
                text: this.message,
                closeOnClick: true,
              });
          }
        }).catch((error) => {
          this.errorMessage = error.message;
          console.error("There was an error!", error);
          this.$notify({
          type: "error",
                title: "Error",
                text: "Izabrani pacijent nema trenutno zakazan pregled!",
                closeOnClick: true,
          });
           this.$refs.selectableTable.clearSelected();
        });
        },
        updateXY: function(event){
            this.x = event.offsetX;
            this.y = event.offsetY;
        },onRowSelected(items) {
          this.choosedPatient = items[0]
          this.startExamination()
          this.selected = items
           
      
         this.getDefExaminations(items[0])
        }, open: function() {
            this.$refs["my-modal"].show();
        }, startIt:function(){
             this.$router.push("examination");
        }, hideModal: function() {
          this.$refs.selectableTable.clearSelected();
          this.$refs["my-modal"].hide();
               this.$axios
        .post("http://localhost:9001/patient/addPenaltyPoint/"+this.choosedPatient.id)
        .then((response) => {
          this.message = response.data;
          if (response.status == 200) {
            this.$notify({
              type: "success",
              title: "Success",
              text: this.message,
              closeOnClick: true,
            });
          }
        }).catch((error) => {
            this.errorMessage = error.message;
            console.error("There was an error!", error);
            this.$notify({
            type: "error",
            title: "Error",
            text: "Moslo je do greske. Molimo pokusajte kasnije!",
            closeOnClick: true,
          });
           this.$refs.selectableTable.clearSelected();
        });
        },
    }
}
</script>

