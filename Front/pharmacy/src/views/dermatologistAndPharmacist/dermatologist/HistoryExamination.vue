<template>
  <div class="history-examinations">
    <b-button v-b-modal="'history_examinations'" variant="outline-success">
      <b-icon-key-fill></b-icon-key-fill>
      Promeni lozinku
    </b-button>

    <b-modal id="history_examinations" hide-footer title="Promeni lozinku">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group
          id="old-password-group"
          label="Stara lozinka:"
          label-for="old-password-input"
          class="text-left"
        >
          <b-form-input
            id="old-password-input"
            v-model="form.oldPassword"
            placeholder="Unesite staru lozinku"
            required
          >
          </b-form-input>
        </b-form-group>

        <b-form-group
          id="new-password-group"
          label="Nova lozinka:"
          label-for="new-password-input"
          class="text-left"
        >
          <b-form-input
            id="new-password-input"
            v-model="form.newPassword"
            placeholder="Unesite novu lozinku"
            required
          >
          </b-form-input>
        </b-form-group>

        <b-button type="submit" block variant="success">
          Izmeni lozinku
        </b-button>

        <b-button type="submit" block variant="danger">
          Nazad
        </b-button>

        <!--<b-button class="mt-3" variant="outline-danger" block @click="hideModal">Close Me</b-button>
            <b-button class="mt-2" variant="outline-warning" block @click="toggleModal">Toggle Me</b-button>-->
      </b-form>
    </b-modal>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [],
    };
  },
};
</script>
