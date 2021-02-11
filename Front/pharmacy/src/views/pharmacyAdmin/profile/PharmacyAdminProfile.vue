<template>
    <div class="container">
        <h4 class="h4 align-middle my-4">Profil administratora apoteke</h4>
        <div class="profile-main">
            <b-card no-body>
                <b-tabs pills card align="center" lazy>
                    <b-tab title="Lične informacije" active>
                        <PharmacyAdminProfileInformation />
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
    </div>
</template>

<script>
import PharmacyAdminProfileInformation from "./PharmacyAdminProfileInformation.vue";

export default {
    data(){
        return{
        }
    },
    mounted(){
    },
    components: {
        PharmacyAdminProfileInformation,
    }
}
</script>