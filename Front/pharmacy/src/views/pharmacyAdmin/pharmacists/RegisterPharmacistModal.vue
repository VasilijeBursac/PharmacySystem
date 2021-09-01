<template>
    <b-modal id="register-pharmacist-modal" scrollable hide-footer title="Registracija farmaceuta" 
        @hide="resetFields">
        <b-form>
            <b-form-group id="email-group" label="Email:" label-for="email-input" class="text-left">
                <b-form-input
                    class="text-left"
                    id="email-input"
                    v-model="form.email"
                    placeholder="Unesite email"
                    required
                    trim>
                </b-form-input>
            </b-form-group>

            <b-form-group id="password-group" label="Lozinka:" label-for="password-input" class="text-left">
                <b-form-input
                    class="text-left"
                    type = "password"
                    id="password-input"
                    v-model="form.password"
                    placeholder="Unesite lozinku"
                    required
                    trim>
                </b-form-input>
            </b-form-group>

            <b-form-group id="password-confirmation-group" label="Ponovljena lozinka:" label-for="password-confirmation-input" class="text-left">
                <b-form-input
                    class="text-left"
                    type = "password"
                    id="password-confirmation-input"
                    v-model="form.passwordConfirmation"
                    placeholder="Ponovite lozinku"
                    required>
                </b-form-input>   
            </b-form-group>
            
            <b-form-group id="name-group" label="Ime:" label-for="name-input" class="text-left">
                <b-form-input
                    class="text-left"
                    id="name-input"
                    v-model="form.name"
                    placeholder="Unesite ime"
                    required
                    trim>
                </b-form-input>
            </b-form-group>

            <b-form-group id="surname-group" label="Prezime:" label-for="surname-input" class="text-left">
                <b-form-input
                    class="text-left"
                    id="surname-input"
                    v-model="form.surname"
                    placeholder="Unesite prezime"
                    required
                    trim>
                </b-form-input>
            </b-form-group>

            <b-form-group id="address-group" label="Adresa:" label-for="address-input" class="text-left">
                <b-form-input
                    class="text-left"
                    id="address-input"
                    v-model="form.address"
                    placeholder="Unesite adresu"
                    required
                    trim>
                </b-form-input>
            </b-form-group>

            <b-form-group id="city-group" label="Grad:" label-for="city-input" class="text-left">
                <b-form-input
                    class="text-left"
                    id="city-input"
                    v-model="form.city"
                    placeholder="Unesite grad "
                    required
                    trim>
                </b-form-input>
            </b-form-group>

            <b-form-group id="country-group" label="Država:" label-for="country-input" class="text-left">
                <b-form-input
                    class="text-left"
                    id="country-input"
                    v-model="form.country"
                    placeholder="Unesite državu "
                    required
                    trim>
                </b-form-input>
            </b-form-group>

            <b-form-group id="phone-group" label="Broj telefona:" label-for="phone-input" class="text-left">
                <b-form-input
                    class="text-left"
                    id="phone-input"
                    v-model="form.phoneNumber"
                    placeholder="Unesite broj telefona "
                    required
                    trim>
                </b-form-input>
            </b-form-group>

            <b-form-group id="time-start-group" label="Početak radnog vremena:" label-for="time-start-picker" class="text-left">
                <b-form-timepicker v-model="form.startTime" id="time-start-picker" placeholder="Izaberite vreme početka radnog vremena" locale="en">
                </b-form-timepicker>
            </b-form-group>

            <b-form-group id="time-end-group" label="Kraj radnog vremena:" label-for="time-end-picker" class="text-left">
                <b-form-timepicker v-model="form.endTime" id="time-end-picker" placeholder="Izaberite vreme kraja radnog vremena" locale="en">
                </b-form-timepicker>
            </b-form-group>


            <div class="buttons mt-5">
                <b-button variant="success" block @click="registerNewPharmacist">
                    Registruj farmaceuta
                </b-button>

                <b-button variant="danger" block @click="closeModal">
                    Zatvori
                </b-button>
            </div>
        </b-form>
    </b-modal>
</template>

<script>
import { mapState } from 'vuex';

export default {
    props: {
        pharmacyId: {
            type: Number,
            default: null
        },
    },
    data: function(){
        return {
            form: {
                email: "",
                password: "",
                passwordConfirmation: "",
                name: "",
                surname: "",
                address: "",
                city: "",
                country: "",
                phoneNumber: "",
                startTime: "",
                endTime: "",
            },

            invalidInput: false
        }
    },

    computed: {
        ...mapState(['myPharmacyId']),
    },

    mounted() {
        
    },

    methods: {
        registerNewPharmacist() {
            this.invalidInput = Object.keys(this.form).some(field => this.form[field] == "")
            
            if (this.invalidInput){
                this.toast('danger', 'Neuspešno', 'Morate popuniti sva polja!')
                return;
            }

            if(this.form.password !== this.form.passwordConfirmation){
                this.toast('danger', 'Neuspešno', 'Unosi nove lozinke se ne poklapaju!')
                return;
            }
            
            this.$http
            .post('auth/signupPharmacist', {
                email: this.form.email,
                password: this.form.password,
                firstName: this.form.name,
                lastName: this.form.surname,
                address: this.form.address,
                city: this.form.city,
                country: this.form.country,
                phoneNumber: this.form.phoneNumber,
                startDate: this.formatDateTime(this.form.startTime),
                endDate: this.formatDateTime(this.form.endTime),
                pharmacyId: this.pharmacyId
            })
            .then( () => {
                this.toast('success', 'Uspešno', 'Uspešno ste registrovali farmaceuta.')
                this.closeModal();
                this.$root.$emit('update-pharmacy-pharmacists')
            })
            .catch( (error) => {
                if (error.response.status == 403 || error.response.status == 401)
                    this.toast('danger', 'Neuspešno', 'Niste autorizovani za datu akciju.')
                else 
                    this.toast('danger', 'Neuspešno', 'Desila se greška! Molimo pokušajte kasnije.')
            })
        },

        formatDateTime(selectedTime) {
            let currentDate = new Date().toISOString().split("T")[0]
            return new Date(currentDate + " " + selectedTime).getTime()
        },

        resetFields(){
            Object.keys(this.form).forEach((index) => {
                this.form[index] = ''
            });
        },

        closeModal(){
            this.$bvModal.hide('register-pharmacist-modal')
        },

        toast(variant, title, message){
            this.$bvToast.toast(message, {
                title: title,
                variant: variant,
                autoHideDelay: 5000
            })
            scroll(0,0)
        }
    },
}
</script>

<style>
    
</style>