<template>
  <div>
      <h3 id = "h3" class="h3 align-middle ">Sve zalbe korisnika u sistemu</h3>
      
      <div id="show-complaints">
            <h4 id = "h4" v-if="pharmacistComplaints.length != 0">
                Zalbe na farmaceute :
            </h4>
             <b-card class="overflow-hidden" v-if="pharmacistComplaints.length != 0">
            <div v-for = "(complaint,index) in pharmacistComplaints" :key="complaint.text" class = "single-complaint">
                <h5 > {{index + 1}}. Zalba na {{pharmacistComplaintObjects[index].name}}: {{complaint.complaintObjectName}}</h5>
                    <b-row>
                        <label class="float-left  ml-3">Podnosilac zalbe : {{complaint.patient}}</label>
                    </b-row>
                    <b-row>
                        <label class="float-left ml-3">Tekst zalbe : {{complaint.text}}</label>        
                    </b-row>
                    <b-form @reset="clearTextArea(complaint.complaintType, index)"  >
                        <b-row>
                            <label class="float-left ml-3">Unesite odgovor : </label>
                                <b-form-textarea id="response"                            
                                    rows = "2"
                                    max-rows = "4"                                                 
                                    v-model="pharmacistResponses[index]"
                                                                
                                    >
                                </b-form-textarea>                    
                        </b-row>
                        <b-row >
                            <div id = "buttons">                        
                                <b-button  variant="success" class="mr-2" @click="respondToComplaint(complaint.id, complaint.email, complaint.complaintType, index)">
                                    <b-icon-check></b-icon-check>
                                    Odgovori </b-button>
                                <b-button  variant="danger" type = "reset">
                                    <b-icon-x></b-icon-x>
                                    Otkaži
                                </b-button>
                            </div>
                        </b-row> 
                    </b-form>    
                                                                     
            </div>
             </b-card>
             <h4 id = "h4" v-if="dermatologistComplaints.length != 0">
                Zalbe na dermatologe :
            </h4>
             <b-card class="overflow-hidden" v-if="dermatologistComplaints.length != 0">
            <div v-for = "(complaint,index) in dermatologistComplaints" :key="complaint.text" class = "single-complaint">
                <h5 > {{index + 1}}. Zalba na {{dermatologistComplaintObjects[index].name}}: {{complaint.complaintObjectName}}</h5>
                    <b-row>
                        <label class="float-left  ml-3">Podnosilac zalbe : {{complaint.patient}}</label>
                    </b-row>
                    <b-row>
                         <label class="float-left ml-3">Tekst zalbe : {{complaint.text}}</label>        
                    </b-row>
                     <b-form @reset="clearTextArea(complaint.complaintType, index)"  >
                        <b-row >
                            <label class="float-left ml-3">Unesite odgovor : </label>
                                <b-form-textarea id="response"                              
                                    rows = "2"
                                    max-rows = "4"                                                 
                                    v-model="dermatologistResponses[index]"                                 
                                    >
                                </b-form-textarea>                    
                        </b-row>
                        <b-row >
                            <div id = "buttons">                        
                                <b-button  variant="success" class="mr-2" @click="respondToComplaint(complaint.id, complaint.email, complaint.complaintType, index)">
                                    <b-icon-check></b-icon-check>
                                    Odgovori </b-button>
                                <b-button  variant="danger" type= "reset">
                                    <b-icon-x></b-icon-x>
                                    Otkaži
                                </b-button>
                            </div>
                        </b-row>
                     </b-form>                                                        
            </div>
             </b-card>
             <h4 id = "h4" v-if="pharmacyComplaints.length != 0">
                Zalbe na apoteke :
            </h4>
             <b-card class="overflow-hidden" v-if="pharmacyComplaints.length != 0">
            <div v-for = "(complaint,index) in pharmacyComplaints" :key="complaint.text" class = "single-complaint">
                <h5 > {{index + 1}}. Zalba na {{pharmacyComplaintObjects[index].name}}: {{complaint.complaintObjectName}}</h5>
                    <b-row>
                        <label class="float-left  ml-3">Podnosilac zalbe : {{complaint.patient}}</label>
                    </b-row>
                    <b-row>
                         <label class="float-left ml-3">Tekst zalbe : {{complaint.text}}</label>        
                    </b-row>
                     <b-form @reset="clearTextArea(complaint.complaintType, index)"   >
                        <b-row >
                            <label class="float-left ml-3">Unesite odgovor : </label>
                                <b-form-textarea id="response"                            
                                    rows = "2"
                                    max-rows = "4"                                                 
                                    v-model="pharmacyResponses[index]"                                
                                    >
                                </b-form-textarea>                    
                        </b-row>
                        <b-row >
                            <div id = "buttons">                        
                                <b-button  variant="success" class="mr-2" @click="respondToComplaint(complaint.id, complaint.email, complaint.complaintType, index)">
                                    <b-icon-check></b-icon-check>
                                    Odgovori </b-button>
                                <b-button  variant="danger" type = "reset">
                                    <b-icon-x></b-icon-x>
                                    Otkaži
                                </b-button>
                            </div>
                        </b-row>
                     </b-form>                                                        
            </div>
             </b-card>
      </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
        pharmacistComplaints: [],
        dermatologistComplaints: [],
        pharmacyComplaints: [],
        pharmacistComplaintObjects: [],
        pharmacyComplaintObjects: [],
        dermatologistComplaintObjects: [],
        pharmacistResponses:[],
        dermatologistResponses:[],
        pharmacyResponses:[],
        responseText: '',
        email: '',
        complaintType: ''
    };
  },
  methods:{
    clearTextArea(complaintType, index){      
        if(complaintType == "PharmacistComplaint")
            this.pharmacistResponses[index] = ''           
        else if (complaintType == "DermatologistComplaint")
            this.dermatologistResponses[index] = ''   
        else this.pharmacyResponses[index] = '' 
               
    },
    respondToComplaint(id, email, complaintType, index){            
        this.email = email;
        this.complaintType = complaintType;
        if(complaintType == "PharmacistComplaint")
            this.responseText = this.pharmacistResponses[index]
        else if (complaintType == "DermatologistComplaint")
            this.responseText = this.dermatologistResponses[index]     
        else  this.responseText = this.pharmacyResponses[index]
        
        if(this.responseText == '' || this.responseText == null){
           this.toast('Ne mozete poslati prazan odgovor!','Neuspešno', 'danger');
           return;    
        }      
        this.$http.post("complaint/respondToComplaint",{
                id : id,
                response : this.responseText,
                email : email,
                complaintType : complaintType
        })
        .then( () => {
                this.toast('Odogovr na zalbu je poslat korisniku na mejl !','Uspešno!','success')
                window.location.reload();
            })                    
            .catch(error => {
                if(error.response.status == 400) 
                    this.toast('Greska prilikom slanja odgovora !', 'Neuspešno', 'danger')
                else if (error.response.status == 405)
                    this.toast('Greska! Na ovu zalbu je vec poslat odgovor.','Neuspešno', 'danger')  
                else this.toast('Desila se greška! Molimo pokušajte kasnije','Neuspešno', 'danger')     
            });
               
    },
    deleteElementFromList(elementForDeleting,list){
          var newArray = list.filter(element => {
          return element !== elementForDeleting;
        });
          list = newArray
    },
    toast(message, title, variant){
        this.$bvToast.toast(message, {
            title: title,
            variant: variant,
            autoHideDelay: 5000
        })
    }
  }, 
  mounted() {
            this.$axios.get("complaint/allComplaints")
            .then(response => {                
                var comlpaints = response.data.slice(0,10);
                comlpaints.forEach(element => {
                    if(element.complaintType == "PharmacistComplaint" && !element.responded){
                        this.pharmacistComplaintObjects.push({
                            name : "farmaceuta"
                        })
                        this.pharmacistComplaints.push(element)
                    }
                    else if(element.complaintType == "DermatologistComplaint" && !element.responded){
                        this.dermatologistComplaintObjects.push({
                            name : "dermatologa"
                        })
                        this.dermatologistComplaints.push(element)
                    }    
                    else if(!element.responded){
                        this.pharmacyComplaintObjects.push({
                            name : "apoteku"
                        })
                        this.pharmacyComplaints.push(element)
                    }
                    
                });    
             
            }) .catch(error => {
                if(error.response.status == 404)
                    this.toast('Trenutno ne postoji nijedna zalba u sistemu!','Neuspešno', 'danger');  
                });
       
        } 
};
</script>
<style>
    #show-complaints{
        max-width: 800px;
        margin: 50px auto 0 auto;
    }
    .single-complaint{
        padding:17px;
        margin: 20px 0 0 0;
        box-sizing: border-box;
        background-color: rgb(238, 238, 238);
    }
    .single-complaint label{
        font-size: 17px;
    }

    #h3{
         margin: 20px 0 0 0; 
    }

    #h4{
        margin: 50px 0px 25px 0px;
        text-align: left;
    }

    #response {
        width: 570px;
        margin: 10px 0px 0px 10px;
    }

    #buttons{
        margin: 20px 250px 0 280px;
         
    }
 
</style>