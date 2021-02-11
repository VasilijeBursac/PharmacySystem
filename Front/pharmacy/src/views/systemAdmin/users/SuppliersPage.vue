<template>
    <div class="container">
        <h4 class="h4 align-middle my-4">Dobavljaci</h4>
        <div class="users-main">
            <b-card no-body >
                <b-tabs pills card align="center" lazy>
                    <b-tab title="Prikaz dobavljaca" active class="text-center">
                        <Suppliers/>
                    </b-tab>
                     <b-tab title="Registruj novog dobavljaca"  class="text-center">
                        <RegisterSupplier />
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
    </div>
</template>

<script>
import Suppliers from "../users/Suppliers.vue";
import RegisterSupplier from "../register/RegisterSupplier.vue";


export default {
    components: {
        Suppliers,
        RegisterSupplier
    }
}
</script>