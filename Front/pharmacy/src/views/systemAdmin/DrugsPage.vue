<template>
    <div class="container">
        <h4 class="h4 align-middle my-4">Lekovi</h4>
        <div class="drugs-main">
            <b-card no-body >
                <b-tabs pills card align="center" lazy>
                    <b-tab title="Prikaz lekova" active class="text-center">
                        <Drugs/>
                    </b-tab>
                     <b-tab title="Dodaj novi lek"  class="text-center">
                        <RegisterNewDrug />
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
    </div>
</template>

<script>
import Drugs from "../systemAdmin/Drugs.vue";
import RegisterNewDrug from "../systemAdmin/register/RegisterNewDrug.vue";


export default {
    components: {
        Drugs,
        RegisterNewDrug
    }
}
</script>