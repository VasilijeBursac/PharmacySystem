<template>
  <div class="change-password">
      <b-button v-b-modal="'change-password'" 
                type="submit" 
                variant="outline-success">
                <b-icon-key-fill></b-icon-key-fill>
                Promeni lozinku
    </b-button>

    <b-modal id="change-password" hide-footer title="Promeni lozinku">
        <b-form>
            <b-form-group id="old-password-group" label="Stara lozinka:" label-for="old-password-input" class="text-left">
                <b-form-input
                    id="old-password-input"
                    v-model="form.oldPassword"
                    placeholder="Unesite staru lozinku"
                    type="password">
                </b-form-input>
            </b-form-group>

            <b-form-group id="new-password-group" label="Nova lozinka:" label-for="new-password-input" class="text-left">
                <b-form-input
                    id="new-password-input"
                    v-model="form.newPassword"
                    placeholder="Unesite novu lozinku"
                    type="password">
                </b-form-input>
            </b-form-group>

            <b-form-group id="repeat-new-password-group" label="Ponovite novu lozinku:" label-for="repeat-new-password-input" class="text-left">
                <b-form-input
                    id="repeat-new-password-input"
                    v-model="form.repeatedNewPassword"
                    placeholder="Ponovo unesite novu lozinku"
                    type="password">
                </b-form-input>
            </b-form-group>

            <b-button type="submit" @click="changePassword" block variant="success">
                Izmeni lozinku
            </b-button>

            <b-button type="submit" @click="closeModal" block variant="danger">
                Otkaži
            </b-button>

        </b-form>
    </b-modal>
  </div>
</template>

<script>
export default {
    data(){
        return{
            form:{
                oldPassword:'',
                newPassword: '',
                repeatedNewPassword: ''
            }
        }
    },

    methods: {
        changePassword(event){
            event.preventDefault()

            if(this.form.newPassword == '' || this.form.repeatedNewPassword == '' || this.oldPassword == '' ){
                this.toast('danger', 'Neuspešno!', 'Morate popuniti sva polja!')
                return;
            }

            if (this.form.newPassword != this.form.repeatedNewPassword){
                this.toast('danger', 'Neuspešno!', 'Greška pri izmeni lozinke! Nove lozinke se ne poklapa!')
                return
            }
            else{
                this.$http
                    .post('/auth/change-password', 
                        {
                            oldPassword: this.form.oldPassword, 
                            newPassword: this.form.newPassword
                        })
                    .then( res => {
                        if(res.status == 200){
                            this.toast('success', 'Uspešno!', `Uspešno ste izmenili svoju lozinku!`)
                            this.closeModal();

                            if ( localStorage.getItem("UserRole") === "ROLE_PATIENT") {
                                this.$router.push("patient-profile");
                            }
                            if (localStorage.getItem("UserRole") === "ROLE_SYSTEM_ADMIN") {
                                this.$router.push("systemAdminsPage");
                            }
                            if (localStorage.getItem("UserRole") === "ROLE_PHARMACY_ADMIN") {
                                this.$router.push("patient-profile");
                            }
                            if (localStorage.getItem("UserRole") === "ROLE_DERMATOLOGIST") {
                                this.$router.push("dermatologist-profile");
                            }
                            if (localStorage.getItem("UserRole") === "ROLE_PHARMACIST") {
                                this.$router.push("pharmacist-profile");
                            }
                            if (localStorage.getItem("UserRole") === "ROLE_SUPPLIER") {
                                this.$router.push("patient-profile");
                            }



                        } else {
                            this.toast('danger', 'Neuspešno!', 'Greška pri izmeni lozinke!')
                        }
                    })
                    .catch(() => 
                        this.toast('danger', 'Neuspešno!', 'Greška pri izmeni lozinke! Niste uneli ispravnu staru lozinku!')
                    )

            }
        },

         toast(variant, title, message){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
            scroll(0,0)
        },

        closeModal(){
            this.$bvModal.hide('change-password')
            this.resetInputFields()
        },

        resetInputFields(){
            this.form.oldPassword = '';
            this.form.newPassword = '';
            this.form.repeatedNewPassword = '';
        }
    }
}
</script>