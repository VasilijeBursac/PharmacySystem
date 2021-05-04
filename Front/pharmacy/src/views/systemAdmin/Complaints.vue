<template>
  <div>
      <h3 id = "h3" class="h3 align-middle ">Sve zalbe korisnika u sistemu</h3>
      
      <div id="show-complaints">
            <h4 id = "h4">
                Zalbe na farmaceute :
            </h4>
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
                                    rows = "3"
                                    max-rows = "4"                                                 
                                    v-model="pharmacistResponses[index]"
                                                                
                                    >
                                </b-form-textarea>                    
                        </b-row>
                        <b-row >
                            <div id = "buttons">                        
                                <b-button  variant="success" class="mr-2" @click="respondToComplaint(complaint.email, complaint.complaintType, index)">
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
             <h4 id = "h4">
                Zalbe na dermatologe :
            </h4>
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
                                    rows = "3"
                                    max-rows = "4"                                                 
                                    v-model="dermatologistResponses[index]"                                 
                                    >
                                </b-form-textarea>                    
                        </b-row>
                        <b-row >
                            <div id = "buttons">                        
                                <b-button  variant="success" class="mr-2" @click="respondToComplaint(complaint.email, complaint.complaintType, index)">
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
             <h4 id = "h4">
                Zalbe na apoteke :
            </h4>
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
                                    rows = "3"
                                    max-rows = "4"                                                 
                                    v-model="pharmacyResponses[index]"                                
                                    >
                                </b-form-textarea>                    
                        </b-row>
                        <b-row >
                            <div id = "buttons">                        
                                <b-button  variant="success" class="mr-2" @click="respondToComplaint(complaint.email, complaint.complaintType, index)">
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
    respondToComplaint(email, complaintType, index){              
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
                response : this.responseText,
                email : this.email,
                complaintType : this.complaintType
        })
        .then( () => {
                this.toast('Odogovr na zalbu je poslat korisniku na mejl !','Uspešno!','success')                    
            })                    
            .catch(error => {
                if(error.response.status == 400) {
                    this.toast('Greska','Neuspešno', 'danger');              
                }
            });
               
    },
    toast(message, title, variant){
        this.$bvToast.toast(message, {
            title: title,
            variant: variant,
            autoHideDelay: 5000
        })
    }
  }, 
  created() {
            this.$axios.get("complaint/allComplaints")
            .then(response => {                
                var comlpaints = response.data.slice(0,10);
                comlpaints.forEach(element => {
                    if(element.complaintType == "PharmacistComplaint"){
                        this.pharmacistComplaintObjects.push({
                            name : "farmaceuta"
                        })
                        this.pharmacistComplaints.push(element)
                    }
                    else if(element.complaintType == "DermatologistComplaint"){
                        this.dermatologistComplaintObjects.push({
                            name : "dermatologa"
                        })
                        this.dermatologistComplaints.push(element)
                    }    
                    else {
                        this.pharmacyComplaintObjects.push({
                            name : "apoteku"
                        })
                        this.pharmacyComplaints.push(element)
                    }
                    
                });    
             
            }) 
       
        } 
};
</script>
<style>
    #show-complaints{
        max-width: 800px;
        margin: 50px auto 0 auto;
    }
    .single-complaint{
        padding:20px;
        margin: 20px 0 0 0;
        box-sizing: border-box;
        background-color: rgb(238, 238, 238);
    }
    .single-complaint label{
        font-size: 20px;
    }

    #h3{
         margin: 30px 0 0 0; 
    }

    #h4{
        margin: 60px 0px 25px 0px;
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