<template>
  <div>
    <notifications animation-type="velocity" />
    <b-row>
      <b-col sm="4"> </b-col>
      <b-col sm="4">
        <label for="example-datepicker"
          >Izaberite datum pocetka odmora/odsustva</label
        >
        <b-form-datepicker
          id="example-datepicker"
          v-model="start"
          class="mb-2"
          required
        ></b-form-datepicker>
      </b-col>
    </b-row>
    <b-row>
      <b-col sm="4"> </b-col>
      <b-col sm="4">
        <label for="example-datepicker"
          >Izaberite datum zavrsetka odmora/odsustva</label
        >
        <b-form-datepicker
          id="example-datepicker"
          v-model="end"
          class="mb-2"
          required
        ></b-form-datepicker>
      </b-col>
    </b-row>
    <b-row>
      <b-col sm="4"> </b-col>
      <b-col sm="4">
        <b-button variant="success" @click="schedule">Zakazi</b-button>
      </b-col>
    </b-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      start: "",
      end: "",
    };
  },
  methods: {
    schedule: function() {
      if (this.start === "" || this.end === "") {
        this.$notify({
          type: "error",
          title: "Error",
          text: "Niste uneli oba datuma!",
          closeOnClick: true,
        });
      } else if (this.start > this.end) {
        this.$notify({
          type: "error",
          title: "Error",
          text: "Pogresno uneseni datumi",
          closeOnClick: true,
        });
      } else {
        let startDate = new Date(this.start);
        startDate = startDate.getTime();

        let endDate = new Date(this.end);
        endDate = endDate.getTime();

        this.$axios
          .post("http://localhost:9001/vacation/scheduleVacation", {
            startDate: startDate,
            endDate: endDate,
          })
          .then((response) => {
            this.message = response.data;
            if (response.status == 200) {
              this.$notify({
                type: "success",
                title: "Success",
                text: this.message,
                closeOnClick: true,
              });
            } else {
              this.$notify({
                type: "error",
                title: "Error",
                text: this.message,
                closeOnClick: true,
              });
            }
          });
      }
    },
  },
};
</script>
