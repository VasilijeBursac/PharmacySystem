<template>
    <div class="container">
        <h4 class="h4 align-middle my-4">Administratori apoteka</h4>
        <div class="users-main">
            <b-card no-body >
                <b-tabs pills card align="center" lazy>
                    <b-tab title="Prikaz administratora apoteka" active class="text-center">
                        <PharmacyAdmins/>
                    </b-tab>
                     <b-tab title="Registruj novog administratora"  class="text-center">
                        <RegisterPharmacyAdmin />
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
    </div>
</template>

<script>
import PharmacyAdmins from "../users/PharmacyAdmins.vue";
import RegisterPharmacyAdmin from "../register/RegisterPharmacyAdmin.vue";


export default {
    components: {
        PharmacyAdmins,
        RegisterPharmacyAdmin
    }
}
</script>