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
        { key: "name", sortable: true },
        { key: "surname", sortable: true },
        { key: "address", sortable: true },
        { key: "city", sortable: true },
        { key: "country", sortable: true },
         { key: "phoneNumber", sortable: true }
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