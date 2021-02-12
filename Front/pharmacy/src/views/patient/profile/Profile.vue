<template>
    <div class="container">
        <h4 class="h4 align-middle my-4">Profil korisnika</h4>
        <div class="profile-main mb-5">
            <b-card no-body>
                <b-tabs pills card align="center" lazy>
                    <b-tab title="Lične informacije" active>
                        <ProfileInformation />
                    </b-tab>
                    <b-tab title="Alergije">
                        <Allergies />
                    </b-tab>
                    <b-tab title="Loyalty program i poeni">
                        <Loyalty/>
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
    </div>
</template>

<script>
import ProfileInformation from "./ProfileInformation.vue";
import Allergies from "./Allergies.vue";
import Loyalty from "./Loyalty.vue";

export default {
    data(){
        return{
        }
    },
    mounted(){
    },
    components: {
        ProfileInformation,
        Allergies,
        Loyalty
    }
}
</script>
