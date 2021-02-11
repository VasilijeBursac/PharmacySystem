<template>
    <div class="container">
        <h4 class="h4 align-middle my-4">Dermatolozi</h4>
        <div class="users-main">
            <b-card no-body >
                <b-tabs pills card align="center" lazy>
                    <b-tab title="Prikaz dermatologa" active class="text-center">
                        <Dermatologists/>
                    </b-tab>
                     <b-tab title="Registruj novog dermatologa"  class="text-center">
                        <RegisterDermatologist />
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
    </div>
</template>

<script>
import Dermatologists from "../users/Dermatologists.vue";
import RegisterDermatologist from "../register/RegisterDermatologist.vue";


export default {
    components: {
        Dermatologists,
        RegisterDermatologist
    }
}
</script>