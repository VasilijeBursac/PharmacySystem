<template>
  <div id="pharmacies-table">
        <b-table id = "table" striped hover primary-key="id" :tbody-transition-props="transProps" :items="items" :fields="fields"></b-table>
  </div>
</template>
<script>
export default {
  data() {
    return {
      fields: [   
        { label: "Ime", key: "name", sortable: true },
        { label: "Adresa", key: "address", sortable: true },
        { label: "Grad", key: "city", sortable: true },
        { label: "Drzava", key: "country", sortable: true },
      ],
      transProps: {
        name: "flip-list"
      },
      items: []
    };
  },created() {
            // GET request for examination information
            this.$axios.get("pharmacy/allPharmacies")
            .then(response => {              
                this.items = response.data;            
            })
            .catch(error => {
            if(error.response.status == 404)
                this.toast('Trenutno ne postoji nijedna apoteka u sistemu','Neuspešno', 'danger'); 
            });
  },methods: {
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