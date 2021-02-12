<template>
  <div id="app">
    <b-table
      primary-key="id"
      :tbody-transition-props="transProps"
      :items="mojiPacijenti"
      :fields="fields"
    ></b-table>
  </div>
</template>
<script>
export default {
  name: "App",
  data() {
    return {
      fields: [
        { key: "ime", sortable: true },
        { key: "prezime", sortable: true },
        { key: "pregled", sortable: true },
      ],
      mojiPacijenti : [],
      transProps: {
        name: "flip-list",
      },
      items: [],
    };
  },
  created() {
    // GET request for examination information
    this.$axios
      .get('patient/examinatedPatients/')
      .then((response) => {
        this.items = response.data;
        for (let i in this.items) {
          this.items[i].term = new Date(response.data[i].term).toLocaleString();
          this.mojiPacijenti.push({
              ime : this.items[i].name,
              prezime : this.items[i].surname,
              pregled : new Date(response.data[i].term).toLocaleString()
          })
         
        }
      })
      .catch((error) => {
        this.errorMessage = error.message;
        //console.error("There was an error!", error);
      });
  },
};
</script>
<style>
table .flip-list-move {
  transition: transform 1s;
}
</style>
