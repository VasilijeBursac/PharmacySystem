<template>
  <div class="demo-app">
    <notifications animation-type="velocity" />
    <div class="demo-app-main">
      <FullCalendar class="demo-app-calendar" :options="calendarOptions">
        <template v-slot:eventContent="arg">
          {{ arg.timeText }}
          {{ arg.event.title }}
          <br />
        </template>
      </FullCalendar>
    </div>
    <div>
      <b-modal ref="my-modal"  hide-footer title= "INFORMACIJE">
        <b-row text-align-center>
          <b-col sm="3"> </b-col>
          <b-col>
            <h4
              text-align-center
              v-bind:style="{ align: 'center', justify: 'center' }"
            >
              {{ this.pharmacyName }}
            </h4>
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <b-form inline>
              <b-form-input
                v-if="isAvailable"
                v-model="name"
                id="inline-form-input-name"
                class="mb-2 mr-sm-2 mb-sm-0"
                placeholder="Nema pacijenta"
              ></b-form-input>
            </b-form>
          </b-col>
          <b-col>
            <b-form inline>
              <b-form-input
                v-if="isAvailable"
                v-model="surname"
                id="inline-form-input-name"
                class="mb-2 mr-sm-2 mb-sm-0"
                placeholder="Nema pacijenta"
              ></b-form-input>
            </b-form>
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <b-form inline>
              <b-form-input
                v-model="startDate"
                id="inline-form-input-name"
                class="mb-2 mr-sm-2 mb-sm-0"
                placeholder="Jane Doe"
              ></b-form-input>
            </b-form>
          </b-col>
          <b-col>
            <b-form inline>
              <b-form-input
                v-model="endDate"
                id="inline-form-input-name"
                class="mb-2 mr-sm-2 mb-sm-0"
                placeholder="Jane Doe"
              ></b-form-input>
            </b-form>
          </b-col>
          <b-col>
            <b-button
              class="mt-3"
              variant="outline-info"
              block
              @click="hideModal"
              >Nazad</b-button
            >
          </b-col>

          <b-col v-if="isCurrentExamination">
            <b-button
              class="mt-3"
              variant="outline-danger"
              block
              @click="notHere"
              visible="false"
              >Nema korisnika</b-button
            >
          </b-col>
          <b-col v-if="isCurrentExamination">
            <b-button v-if="userRole == 'ROLE_DERMATOLOGIST'"
              class="mt-3"
              variant="success"
              block
              @click="startExamination"
              >Pregledaj</b-button
            >
             <b-button v-if="userRole == 'ROLE_PHARMACIST'"
              class="mt-3"
              variant="success"
              block
              @click="startExamination"
              >Savetuj</b-button
            >
          </b-col>
        </b-row>
      </b-modal>
    </div>
  </div>
</template>

<script>
import FullCalendar from "@fullcalendar/vue";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";

export default {
  components: {
    FullCalendar, // make the <FullCalendar> tag available
  },

  data: function() {
    return {
      calendarOptions: {
        plugins: [
          dayGridPlugin,
          timeGridPlugin,
          interactionPlugin, // needed for dateClick
        ],
        headerToolbar: {
          left: "prev,next today",
          center: "title",
          right: "dayGridMonth,timeGridWeek,timeGridDay",
        },
        initialView: "dayGridMonth",
        editable: false,
        selectable: true,
        selectMirror: true,
        dayMaxEvents: true,
        weekends: true,
        select: this.handleDateSelect,
        eventClick: this.handleEventClick,
        eventsSet: this.handleEvents,
        events: this.examinations,
        /* you can update a remote database when these fire:
        eventAdd:
        eventChange:
        eventRemove:
        */
      },
      isAvailable: true,
      examinations: [],
      currentEvents: [],
      name: "",
      surname: "",
      pharmacyName: "",
      startDate: "",
      endDate: "",
      patientId: "",
      examinationId: "",
      currentExamination: "",
      isCurrentExamination: false,
      definedExaminations: [],
    };
  },
  created() {
    // GET request for examination information
    this.$axios
      .get("http://localhost:9001/examination/examinaitonForCalendar/")
      .then((response) => {
        this.definedExaminations = response.data;
        let examinationsForCalendar = [];
        for (let i in response.data) {
          let ex = response.data[i];
          let startDate = new Date(ex.start);
          let endDate = new Date(startDate.getTime() + 30 * 60000);
          let newEvent = {
            id: ex.id,
            title: ex.pharmacyName,
            start: new Date(ex.start),
            end: endDate,
            backgroundColor: "#ff0000",
          };
          examinationsForCalendar.push(newEvent);
        }
        this.examinations = examinationsForCalendar;
        this.calendarOptions.events = examinationsForCalendar;
      })
      .catch((error) => {
        this.errorMessage = error.message;
        //console.error("There was an error!", error);
        this.$notify({
          type: "success",
          title: "Success",
          text: this.message,
          closeOnClick: true,
        });
      });
  }, mounted() {
    this.userRole = localStorage.getItem("UserRole");
  },

  methods: {
    handleWeekendsToggle() {
      this.calendarOptions.weekends = !this.calendarOptions.weekends; // update a property
    },

    handleDateSelect(selectInfo) {
      //let title = prompt('Please enter a new title for your event')
      let calendarApi = selectInfo.view.calendar;

      calendarApi.unselect(); // clear date selection

      // if (title) {
      //   calendarApi.addEvent({
      //     id: createEventId(),
      //     title,
      //     start: selectInfo.startStr,
      //     end: selectInfo.endStr,
      //     allDay: selectInfo.allDay
      //   })
      // }
    },

    handleEventClick(clickInfo) {
      this.$refs["my-modal"].show();
      //console.log(clickInfo);
      let exam = this.definedExaminations.filter((obj) => {
        return obj.id == clickInfo.event.id;
      });
      this.pharmacyName = exam[0].pharmacyName;
      this.name = exam[0].patientName;
      this.surname = exam[0].patientSurname;
      this.startDate = new Date(exam[0].start);
      this.endDate = new Date(this.startDate.getTime() + 30 * 60000);
      this.startDate = this.startDate.toLocaleString();
      this.endDate = this.endDate.toLocaleString();
      this.patientId = exam[0].patientId;
      this.examinationId = exam[0].id;
      if (this.name === "") {
        this.isAvailable = false;
      } else {
        this.isAvailable = true;
      }
      this.getCurrentExamination();
    },
    hideModal() {
      this.$refs["my-modal"].hide();
    },
    startExamination() {
      this.$router.push("examination");
    },
    handleEvents(events) {
      this.currentEvents = events;
    },
    getCurrentExamination() {
      this.$axios
        .get("http://localhost:9001/examination/soonestExamination/")
        .then((response) => {
          this.currentExamination = response.data;
          if (
            this.currentExamination.soonestExamination.id == this.examinationId
          ) {
            this.isCurrentExamination = true;
          } else {
            this.isCurrentExamination = false;
          }
        })
        .catch((error) => {
          this.errorMessage = error.message;
          //console.error("There was an error!", error);
        });
    },
    notHere() {
      this.$axios
        .post("http://localhost:9001/patient/addPenaltyPoint/" + this.patientId)
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
          window.location.reload();
        })
        .catch((error) => {
          this.errorMessage = error.message;
          //console.error("There was an error!", error);
          this.$notify({
            type: "error",
            title: "Error",
            text: "Moslo je do greske. Molimo pokusajte kasnije!",
            closeOnClick: true,
          });
        });
    },
  },
};
</script>

<style lang="css">
h2 {
  margin: 0;
  font-size: 16px;
}

ul {
  margin: 0;
  padding: 0 0 0 1.5em;
}

li {
  margin: 1.5em 0;
  padding: 0;
}

b {
  /* used for event dates/times */
  margin-right: 3px;
}

.demo-app {
  display: flex;
  min-height: 100%;
  font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
  font-size: 14px;
}

.demo-app-sidebar {
  width: 300px;
  line-height: 1.5;
  background: #eaf9ff;
  border-right: 1px solid #d3e2e8;
}

.demo-app-sidebar-section {
  padding: 2em;
}

.demo-app-main {
  flex-grow: 1;
  padding: 3em;
}

.fc {
  /* the calendar root */
  max-width: 1100px;
  margin: 0 auto;
}
</style>
