<template>
    <div class="container">
        <h4 class="h4 align-middle my-4">Administratori sistema</h4>
        <div class="users-main">
            <b-card no-body >
                <b-tabs pills card align="center" lazy>
                    <b-tab title="Prikaz administratora sistema" active class="text-center" >
                        <SystemAdmins/>
                    </b-tab>
                     <b-tab title="Registruj novog administratora"  class="text-center" >
                        <RegisterSystemAdmin />
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
    </div>
</template>

<script>
import SystemAdmins from "../users/SystemAdmins.vue";
import RegisterSystemAdmin from "../register/RegisterSystemAdmin.vue";


export default {

    components: {
        SystemAdmins,
        RegisterSystemAdmin
    }

}
</script>