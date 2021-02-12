<template>
  <div class="container">
    <notifications animation-type="velocity" />
    <h4 class="h4 align-middle my-4" v-if="userRole == 'ROLE_DERMATOLOGIST'">
      Pregled pacijenta
    </h4>
    <h4 class="h4 align-middle my-4" v-if="userRole == 'ROLE_PHARMACIST'">
      Savetovanje pacijenta
    </h4>
    <div>
      <b-modal
        ref="my-modal"
        hide-footer
        title="Zamenski lekovi dostupni u apoteci"
      >
        <div class="d-block text-center">
          <div v-for="(item, index) in subdrugs" :key="item">
            <input v-model="item.name" />
            <button v-on:click="defAdd(item, index)">Dodaj</button>
          </div>
        </div>
        <b-row>
          <b-col>
            <b-button
              class="mt-3"
              variant="outline-danger"
              block
              @click="hideModal"
              >Close Me</b-button
            >
          </b-col>
        </b-row>
      </b-modal>

      <b-modal ref="specification-modal" hide-footer title="Specifikacija leka">
        <b-row>
          <b-col>
            <h5>Preporucena doza : {{ this.dose }}</h5>
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <div id="app">
              <b-table
                primary-key="id"
                :tbody-transition-props="transProps"
                :items="kontraindikacije"
                :fields="Fields"
              ></b-table>
            </div>
          </b-col>
          <b-col>
            <div id="app">
              <b-table
                primary-key="id"
                :tbody-transition-props="transProps"
                :items="sastojci"
                :fields="Ingr"
              ></b-table>
            </div>
          </b-col>
          <b-col>
            <div id="app">
              <b-table
                primary-key="id"
                :tbody-transition-props="transProps"
                :items="zamenskiLekovi"
                :fields="Subs"
                :headerTitle="Zamenski"
              ></b-table>
            </div>
          </b-col>
        </b-row>
      </b-modal>
    </div>
    <div class="profile-main">
      <b-card no-body>
        <b-tabs pills card align="center" lazy>
          <b-tab
            v-if="userRole == 'ROLE_DERMATOLOGIST'"
            title="Informacije o pacijentu i pregledu"
            active
          >
            <b-card>
              <b-form-group
                label="Ime pacijenta :"
                label-for="nested-street"
                label-cols-sm="3"
                label-align-sm="right"
              >
                <input v-model="name" />
              </b-form-group>

              <b-form-group
                label="Prezime pacijenta :"
                label-for="nested-city"
                label-cols-sm="3"
                label-align-sm="right"
              >
                <input v-model="surname" />
              </b-form-group>

              <b-form-group
                label="Zakazan pregled :"
                label-for="nested-state"
                label-cols-sm="3"
                label-align-sm="right"
              >
                <input v-model="start" />
              </b-form-group>
            </b-card>
          </b-tab>
          <b-tab
            v-if="userRole == 'ROLE_PHARMACIST'"
            title="Informacije o pacijentu i savetovanju"
            active
          >
            <b-card>
              <b-form-group
                label="Ime pacijenta :"
                label-for="nested-street"
                label-cols-sm="3"
                label-align-sm="right"
              >
                <input v-model="name" />
              </b-form-group>

              <b-form-group
                label="Prezime pacijenta :"
                label-for="nested-city"
                label-cols-sm="3"
                label-align-sm="right"
              >
                <input v-model="surname" />
              </b-form-group>

              <b-form-group
                label="Vreme savetovanja :"
                label-for="nested-state"
                label-cols-sm="3"
                label-align-sm="right"
              >
                <input v-model="start" />
              </b-form-group>
            </b-card>
          </b-tab>
          <b-tab title="Istorija poseta ">
            <b-table striped hover :items="istorijaPoseta" :fields="History"></b-table>
          </b-tab>
          <b-tab
            v-if="userRole == 'ROLE_DERMATOLOGIST'"
            title="Zakazivanje pregleda"
          >
            <NewExamination />
          </b-tab>
          <b-tab
            v-if="userRole == 'ROLE_PHARMACIST'"
            title="Zakazivanje savetovanja"
          >
            <NewExamination />
          </b-tab>
          <b-tab title="Terapija">
            <b-row>
              <b-col>
                <h5>Postavi dijagnozu</h5>
                <b-form-textarea
                  id="textarea"
                  v-model="text"
                  placeholder="Unesi text..."
                  rows="5"
                  max-rows="10"
                  required
                >
                </b-form-textarea>
              </b-col>
              <b-col>
                <h4>Trajanje terapije</h4>
                <input
                  type="number"
                  v-model="therapyDuration"
                  min="0"
                  max="100"
                  required
                />
              </b-col>
            </b-row>
            <b-row>
              <b-col> </b-col>
              <b-col> </b-col>
            </b-row>

            <b-row>
              <b-col>
                <h5>Lista lekova</h5>
                <div v-for="item in allDrugs" :key="item">
                  <input v-model="item.name" />
                  <button v-on:click="getSpec(item.id)">Specifikacija</button>
                  <button v-on:click="add(item)">Dodaj</button>
                </div>
              </b-col>

              <b-col>
                <h5>Lekovi za terapiju</h5>
                <div v-for="(item, index) in therapy" :key="item">
                  <input v-model="item.name" />
                  <button v-on:click="subtract(item, index)">Ukloni</button>
                </div>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <b-button variant="success" v-on:click="submit()"
                  >SUBMIT INFORMACIJA O PREGLEDU</b-button
                >
              </b-col>
            </b-row>
          </b-tab>
        </b-tabs>
      </b-card>
    </div>
  </div>
</template>

<script>
import NewExamination from "./NewExamination.vue";
export default {
  data() {
    return {
      Fields: [{ key: "kontraindikacije", sortable: true }],
      History: [
        { key: "imeStrucnogLica", sortable: true },
        { key: "prezimeStrucnogLica", sortable: true },
        { key: "vremePregleda", sortable: true },
      ],
      Ingr: [{ key: "sastojci", sortable: true }],
      Subs: [{ key: "zamenskiLekovi", sortable: true }],
      n: "",
      surname: "",
      start: "",
      examinationId: 0,
      items: [],
      istorijaPoseta : [],
      allDrugs: [],
      text: "",
      therapy: [],
      subdrugs: [],
      added: 0,
      dose: 0,
      sastojci: [],
      therapyDuration: 0,
      zamenskiLekovi: [],
      kontraindikacije: [],
    };
  },
  created() {
    // GET request for examination information
    this.$axios
      .get("examination/soonestExamination/")
      .then((response) => {
        this.examination = response.data;
        this.name = this.examination.soonestExamination.dermatologistName;
        this.surname = this.examination.soonestExamination.dermatologistSurname;
        this.start = new Date(
          this.examination.soonestExamination.examinationStart
        ).toLocaleString();
        this.items = this.examination.historyExaminations;
        for (let i in this.items) {
          this.istorijaPoseta.push({
            imeStrucnogLica : this.items[i].dermatologistName,
            prezimeStrucnogLica : this.items[i].dermatologistSurname,
            vremePregleda : new Date(
            this.examination.historyExaminations[i].examinationStart
          ).toLocaleString()
          })
          this.items[i].examinationStart = new Date(
            this.examination.historyExaminations[i].examinationStart
          ).toLocaleString();
        }
        this.examinationId = this.examination.soonestExamination.id;
        this.allDrugs = this.examination.drugsForPatient;
      })
      .catch((error) => {
        this.errorMessage = error.message;
        //console.error("There was an error!", error);
      });
  },
  mounted() {
    this.userRole = localStorage.getItem("UserRole");
  },
  components: {
    NewExamination,
  },
  methods: {
    add: function(item) {
      this.$axios
        .get(
          "drugs/isAvailableInPharmacy/" + item.id + "/" + this.examinationId
        )
        .then((response) => {
          this.isAvailable = response.data;
          // ako lek nije dostupan obavesti ga
          if (this.isAvailable.available) {
            this.therapy.push(item);
            this.getDrugs(item);
          } else {
            this.$notify({
              type: "info",
              title: "Empty",
              text: " U apoteci nema trazenog leka!",
              closeOnClick: true,
            });
            this.subdrugs = response.data.drugsDTO;
            this.$refs["my-modal"].show();
          }
        })
        .catch((error) => {
          this.errorMessage = error.message;
          //console.error("There was an error!", error);
        });
    },
    getDrugs: function(drug) {
      var newArray = this.allDrugs.filter(function(drug2) {
        return drug2.id != drug.id;
      });
      this.allDrugs = newArray;
    },
    getSubDrugs: function(drug) {
      var newArray = this.allDrugs.filter(function(drug2) {
        return drug2.id != drug.id;
      });
      var newArray2 = this.subdrugs.filter(function(drug2) {
        return drug2.id != drug.id;
      });
      this.allDrugs = newArray;
      this.subdrugs = newArray2;
    },

    defAdd: function(item) {
      this.added = 0;
      this.therapy.forEach((value) => {
        if (value.id == item.id) {
          this.added = 1;
        }
      });
      if (this.added === 0) {
        this.therapy.push(item);
      } else {
        this.$notify({
          type: "info",
          title: "Empty",
          text: " Lek je vec dodat!",
          closeOnClick: true,
        });
      }

      this.getSubDrugs(item);
    },
    subtract: function(item, index) {
      this.allDrugs.push(item);
      this.therapy.splice(index, 1);
    },
    getSpec: function(id) {
      // treba da pozove zahtev na osnovu ovoga
      this.$axios
        .get("http://localhost:9001/drugs/drugSpecification/" + id)
        .then((response) => {
          this.kontraindikacije = []
          this.sastojci = []
          this.zamenskiLekovi = []
          for(var i in  response.data.contraindication ){
              this.kontraindikacije.push({
                kontraindikacije : response.data.contraindication[i].name
              })
          }
          for(var j in  response.data.ingredients ){
              this.sastojci.push({
                sastojci : response.data.ingredients[j].ingredient
              })
          }
          for(var k in  response.data.substitute ){
              this.zamenskiLekovi.push({
                zamenskiLekovi : response.data.substitute[k].name
              })
          }
          this.dose = response.data.suggestedDose;
          // ako lek nije dostupan obavesti ga
          this.$refs["specification-modal"].show();
        });
    },
    hideModal() {
      this.$refs["my-modal"].hide();
    },
    submit: function() {
      this.$axios
        .post("http://localhost:9001/examination/updateExamination", {
          id: this.examinationId,
          therapyDuration: this.therapyDuration,
          diagnosis: this.text,
          drugs: this.therapy,
        })
        .then((response) => {
          this.message = response.data;
          this.$notify({
            type: "success",
            title: "Success",
            text: this.message,
            closeOnClick: true,
          });
        });

      setTimeout(() => {
        this.$router.push("my-calendar");
      }, 2000);
    },
  },
};
</script>
