<template>
    <div class="container">
        <h4 class="h4 align-middle my-4">Profil dobavljaca </h4>
        <div class="profile-main">
            <b-card no-body>
                <b-tabs pills card align="center" lazy>
                    <b-tab title="Lične informacije" active>
                        <SupplierProfileInformation />
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
    </div>
</template>

<script>
import SupplierProfileInformation from "./SupplierProfileInformation.vue";

export default {
    data(){
        return{
        }
    },
    components: {
        SupplierProfileInformation,
    }
}
</script>