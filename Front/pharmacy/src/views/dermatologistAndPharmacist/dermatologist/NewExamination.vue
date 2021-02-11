<template>
  <div class="container">
    <notifications animation-type="velocity" />
    <h4 class="h4 align-middle my-4"></h4>

    <b-row>
      <b-col>
        <div id="app">
          <h4>Izaberite zeljeni termin</h4>
          <b-table
            primary-key="id"
            :items="definedExaminations"
            :fields="Fields"
            :select-mode="selectMode"
            responsive="sm"
            ref="selectableTable"
            selectable
            @row-selected="onRowSelected"
          ></b-table>
        </div>
      </b-col>

      <b-col>
        <div>
          <b-button v-on:click="open()" variant="outline-primary"
            >Unesite drugi termin</b-button
          >
          <b-modal ref="my-modal" hide-footer title="Unesite zeljeni termin">
            <div class="d-block text-center">
              <b-row>
                <b-col sm="3">
                  <b-form-input type="date" v-model="date"></b-form-input>
                </b-col>
                <b-col sm="3">
                  <b-form-input type="time" v-model="time"></b-form-input>
                </b-col>
              </b-row>
            </div>
            <b-row>
              <b-col>
                <b-button
                  class="mt-3"
                  variant="outline-primary"
                  block
                  @click="hideModal"
                  >Close</b-button
                >
              </b-col>
              <b-col>
                <b-button
                  class="mt-3"
                  variant="success"
                  block
                  @click="submitModal"
                  >Submit</b-button
                >
              </b-col>
            </b-row>
          </b-modal>
        </div>
      </b-col>
    </b-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      Fields: [
        { key: "examinationStart", sortable: true },
        { key: "price", sortable: true },
      ],
      definedExaminations: [],
      date: 0,
      time: 0,
    };
  },
  created() {
    // GET request for examination information
    this.$axios
      .get("http://localhost:9001/examination/definedExaminations/" + 4)
      .then((response) => {
        this.definedExaminations = response.data;
        for (let i in this.definedExaminations) {
          this.definedExaminations[i].examinationStart = new Date(
            response.data[i].examinationStart
          ).toLocaleString();
        }
      })
      .catch((error) => {
        this.errorMessage = error.message;
        //console.error("There was an error!", error);
      });
  },
  methods: {
    open: function() {
      this.$refs["my-modal"].show();
    },
    onRowSelected(items) {
      this.selected = items;
      this.$axios
        .post("http://localhost:9001/examination/saveExamination/"+  items[0].examinationId)
        .then((response) => {
          this.message = response.data;
          if (response.status == 200) {
            this.$notify({
              type: "success",
              title: "Success",
              text: this.message,
              closeOnClick: true,
            });
          }
        });
      this.getDefExaminations(items[0]);
    },
    hideModal: function() {
      this.$refs["my-modal"].hide();
    },
    getDefExaminations: function(examination) {
      var newArray = this.definedExaminations.filter(function(examination2) {
        return examination2.examinationId != examination.examinationId;
      });
      this.definedExaminations = newArray;
    },
    submitModal: function() {
      let startDate = new Date(this.date);
      let startTime = this.time.split(":");
      startDate.setHours(startTime[0], startTime[1], 0, 0);
      startDate = startDate.getTime();
      this.$axios
        .post("http://localhost:9001/examination/scheduleExamination", { date: startDate })
        .then((response) => {
          this.message = response.data;
          if (response.status == 200) {
            this.$notify({
              type: "success",
              title: "Success",
              text: this.message,
              closeOnClick: true,
            });
          }
        })
        .catch((error) => {
          this.errorMessage = error.message;
          //console.error("There was an error!", error);
          this.$notify({
            type: "error",
            title: "Error",
            text: "Nije moguce zakazati pregled u izabranom terminu!",
            closeOnClick: true,
          });
        });
      this.$refs["my-modal"].hide();
    },
  },
};
</script>
