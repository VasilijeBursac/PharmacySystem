<template>
  <div id="app">
    <b-table primary-key="id" :tbody-transition-props="transProps" :items="items" :fields="fields"></b-table>
  </div>
</template>
<script>
export default {
  name: "App",
  data() {
    return {
      fields: [
        { key: "id", sortable: true },
        { key: "name", sortable: true },
        { key: "surname", sortable: true },
        { key: "term", sortable: true }
      ],
      transProps: {
        name: "flip-list"
      },
      items: []
    };
  },created() {
            // GET request for examination information
            this.$axios.get("http://localhost:9001/patient/examinatedPatients/"+1)
            .then(response => {this.items = response.data
              for(let i in this.items){
                this.items[i].term = new Date(response.data[i].term).toLocaleString()
              }
            })
            .catch(error => {
            this.errorMessage = error.message;
            console.error("There was an error!", error);});}
};
</script>
<style>
table .flip-list-move {
  transition: transform 1s;
}
</style>