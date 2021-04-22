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
            this.errorMessage = error.message;
            //console.error("There was an error!", error)
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