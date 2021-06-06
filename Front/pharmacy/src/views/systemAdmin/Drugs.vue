<template>
  <div id="drugs-table">
        <b-table id = "table" striped hover primary-key="name" :tbody-transition-props="transProps" :items="items" :fields="fields"></b-table>
  </div>
</template>
<script>
export default {
  data() {
    return {
      fields: [   
        { label: "Ime", key: "name", sortable: true },
        { label: "Sifra", key: "code", sortable: true },
        { label: "Tip", key: "type", sortable: true },
        { label: "Oblik", key: "shape", sortable: true },
        { label: "Proizvodjac", key: "manifacturer", sortable: true },
        { label: "Lojalni peni", key: "loyalityPoints", sortable: true },
      ],
      transProps: {
        name: "flip-list"
      },
      items: []
    };
  }  ,created() {
            // GET request for examination information
          this.$axios.get("drugs/")
            .then(response => {                
                this.items = response.data;
            
            })
           .catch(error => {
          if(error.response.status == 404)
            this.toast('Trenutno ne postoji nijedan lek u sistemu','Neuspešno', 'danger');  
          }
          );
  },
  methods: {
    toast(message, title, variant){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
        }
  }
};
</script>
<style>
table .flip-list-move {
  transition: transform 1s;
}
table {
     margin-top: 30px;
}

</style>