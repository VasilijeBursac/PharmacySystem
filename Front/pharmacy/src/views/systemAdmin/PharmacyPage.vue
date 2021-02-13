<template>
    <div class="container">
        <h4 class="h4 align-middle my-4">Apoteke</h4>
        <div class="pharmacies-main">
            <b-card no-body >
                <b-tabs pills card align="center" lazy>
                    <b-tab title="Prikaz apoteka" active class="text-center">
                        <Pharmacies/>
                    </b-tab>
                     <b-tab title="Dodaj novu apoteku"  class="text-center">
                        <RegisterPharmacy />
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
    </div>
</template>

<script>
import Pharmacies from "../systemAdmin/Pharmacies.vue";
import RegisterPharmacy from "../systemAdmin/register/RegisterPharmacy.vue";


export default {
    components: {
        Pharmacies,
        RegisterPharmacy
    }
}
</script>