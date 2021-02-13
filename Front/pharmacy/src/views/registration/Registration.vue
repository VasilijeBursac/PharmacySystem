<template>
    <div class="container">
        <h4 class="h4 align-middle my-4">Pristup sistemu</h4>
        <div class="registration-main">
            <b-card no-body >
                <b-tabs pills card align="center" lazy>
                    <b-tab title="Logovanje" active class="text-center">
                        <Login />
                    </b-tab>
                    <b-tab title="Registracija" >
                        <Register />
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
    </div>
</template>

<script>
import Login from "./Login.vue";
import Register from "./Register.vue";

export default {
    components: {
        Login,
        Register,
    }
}
</script>