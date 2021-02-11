<template>
    <nav class="navbar navbar-expand-sm navbar-light bg-light">
        <div class="container">
            <div class="navbar-header">                
            <a class="navbar-brand" href="#"><router-link to="/">Apoteka Isabela</router-link></a>
            </div>        
            <SupplierHeader v-if="userRole == 'ROLE_SUPPLIER'" />
            <PatientHeader v-else-if="userRole == 'ROLE_PATIENT'" />
            <DermatologistHeader v-else-if="userRole == 'ROLE_DERMATOLOGIST'" />
            <PharmacistHeader v-else-if="userRole == 'ROLE_PHARMACIST'" />
            <UnregisteredHeader v-else-if="userRole == 'ROLE_UNREGISTERED'" />
            <PharmacyAdminHeader v-else-if="userRole == 'ROLE_PHARMACY_ADMIN'" />
            <SystemAdminHeader v-else-if="userRole == 'ROLE_SYSTEM_ADMIN'" />
        </div>
    </nav>
</template>

<script>
import PatientHeader from "@/views/header/PatientHeader.vue";
import SupplierHeader from "@/views/header/SupplierHeader.vue";
import UnregisteredHeader from "@/views/header/UnregisteredHeader.vue";
import PharmacyAdminHeader from "@/views/header/PharmacyAdminHeader.vue";
import SystemAdminHeader from "@/views/header/SystemAdminHeader.vue";
import PharmacistHeader from "@/views/header/PharmacistHeader.vue";
import DermatologistHeader from "@/views/header/DermatologistHeader.vue";


export default {
    data: function() {
        return{
            userRole: 'ROLE_UNREGISTERED'
        }
    },
	components: {
        PatientHeader,
        SupplierHeader,
        UnregisteredHeader,
        PharmacyAdminHeader,
        SystemAdminHeader,
        PharmacistHeader,
        DermatologistHeader
    },
    mounted(){
        this.userRole = localStorage.getItem("UserRole");
        if(localStorage.getItem("UserRole") == null){
            localStorage.setItem("UserRole", "ROLE_UNREGISTERED")
        }
    }
};
</script>

<style>
    .nav>li {
        margin-left: 0.2rem;
    }

    .nav .nav-link{
        font-size: 0.85rem;
        font-weight: 500;
    }
</style>