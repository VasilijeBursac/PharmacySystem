<template>
  <div id="systemAdmins-table">

        <b-table id = "table" striped hover primary-key="id" :tbody-transition-props="transProps" :items="items" :fields="fields"></b-table>
  </div>
</template>
<script>
export default {
  data() {
    return {
      fields: [   
         { key: "email", sortable: true },
        { label: "Ime", key: "name", sortable: true },
        { label: "Prezime", key: "surname", sortable: true },
        { label: "Adresa", key: "address", sortable: true },
        { label: "Grad", key: "city", sortable: true },
        { label: "Drzava", key: "country", sortable: true },
         { label: "Telefon", key: "phoneNumber", sortable: true }
      ],
      transProps: {
        name: "flip-list"
      },
      items: []
    };
  },created() {
            // GET request for examination information
            this.$axios.get("http://localhost:9001/pharmacyAdmin/allPharmacyAdmins")
            .then(response => { 
               
                this.items = response.data;
            
            })
            .catch(error => {
            this.errorMessage = error.message;
            //console.error("There was an error!", error);
            });}
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