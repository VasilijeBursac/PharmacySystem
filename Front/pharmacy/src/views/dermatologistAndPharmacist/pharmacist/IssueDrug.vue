<template>
  <div>
    <notifications animation-type="velocity" />
    <b-card-group deck>
      <b-card header-tag="header">
        <b-row>
          <b-col sm="4"> </b-col>
          <b-col sm="3">
            <h6>Unesite jedinstveni broj rezervacije leka :</h6>
            <b-form-group
              id="fieldset-1"
              label-for="input-1"
              valid-feedback="Thank you!"
              :invalid-feedback="invalidFeedback"
              :state="state"
            >
              <b-form-input
                v-model="drugReservation"
                type="number"
                id="input-1"
                :state="state"
                trim
                required
              ></b-form-input>
            </b-form-group>

            <b-button @click="find" variant="success">Pronadji</b-button>
          </b-col>
        </b-row>

        <b-row v-show="forShow">
          <b-col sm="4"> </b-col>
          <b-col sm="3">
            <h6>Rezervisani lek je :</h6>
            <b-form-group
              id="fieldset-1"
              label-for="input-1"
              valid-feedback="Thank you!"
              :invalid-feedback="invalidFeedback"
              :state="state"
            >
              <b-form-input
                v-model="drugShow"
                id="input-1"
                :state="state"
                trim
                required
                disabled
              ></b-form-input>
            </b-form-group>

            <b-button @click="sell" variant="success">Prodaj</b-button>
          </b-col>
        </b-row>
      </b-card>
    </b-card-group>
  </div>
</template>

<script>
export default {
  data() {
    return {
      forShow: false,
      reservationIdForSend: "",
      drugReservation: "",
      drugShow: "",
    };
  },
  methods: {
    find: function() {
      this.reservationIdForSend = this.drugReservation;
      this.$axios
        .get(
          "http://localhost:9001/reservation/reservedDrugs/" +
            this.drugReservation
        )
        .then((response) => {
          // ako lek nije dostupan obavesti ga
          if (response.status == 200) {
            this.forShow = true;
            this.drug = response.data;
            this.drugShow =
              " " +
              response.data.code +
              " , " +
              response.data.name +
              "          Loyalty points: " +
              response.data.loyalityPoints;
          }
        })
        .catch((error) => {
          this.errorMessage = error.message;
          //console.error("There was an error!", error);
          this.drug = "";
          this.drugShow = " ";
          if (error.response.status == 405) {
            this.$notify({
              type: "error",
              title: "Error",
              text: " Rezervacija je istekla!",
              closeOnClick: true,
            });
          } else {
            this.$notify({
              type: "error",
              title: "Error",
              text: " Ne postoji trazena rezervacija!",
              closeOnClick: true,
            });
          }
        });
    },
    sell: function() {
      this.$axios
        .post(
          "http://localhost:9001/reservation/sellDrug/" +
            this.reservationIdForSend
        )

        .then((response) => {
          this.message = response.data;
          this.$notify({
            type: "success",
            title: "Success",
            text: " Lek je uspesno prodat!",
            closeOnClick: true,
          });

          this.forShow = false;
        })
        .catch((error) => {
          this.errorMessage = error.message;
          //console.error("There was an error!", error);
          this.$notify({
            type: "error",
            title: "Erroe",
            text: " Doslo je do greske prilikom cuvanja izmena!",
            closeOnClick: true,
          });
        });
    },
  },
};
</script>
