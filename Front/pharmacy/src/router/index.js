import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import PatientProfile from "../views/patient/profile/Profile.vue";
import SupplierProfile from "../views/supplier/SupplierProfile.vue";
import Registration from "../views/registration/Registration.vue";
import PharmacyAdminProfile from  "../views/pharmacyAdmin/profile/PharmacyAdminProfile.vue";
import DermatologistProfile from  "../views/dermatologistAndPharmacist/dermatologist/DermatologistProfile.vue";
import PharmacistProfile from  "../views/dermatologistAndPharmacist/pharmacist/PharmacistProfile.vue";
import Examination from "../views/dermatologistAndPharmacist/dermatologist/StartExamination.vue"
import IssueDrug from "../views/dermatologistAndPharmacist/pharmacist/IssueDrug.vue"
import SystemAdminsPage from "../views/systemAdmin/users/SystemAdminsPage.vue"
import Vacation from "../views/dermatologistAndPharmacist/ScheduleVacation.vue"
import MyCalendar from "../views/dermatologistAndPharmacist/myCalendar.vue"
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/registration",
    name: "Registration",
    component: Registration
  },
  {
    path: "/patient-profile",
    name: "Patient Profile",
    component: PatientProfile
  },
  {
    path: "/issue-drug",
    name: "Issue drug",
    component: IssueDrug
  },
  {
    path: "/my-calendar",
    name: "My Calendar",
    component: MyCalendar
  },
  {
    path: "/myPatients",
    name: "myPatients",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/dermatologistAndPharmacist/dermatologist/MyPatients.vue")
  }, 
  {
    path: "/searchedPatients",
    name: "searchedPatients",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/dermatologistAndPharmacist/dermatologist/SearchedPatients.vue")
  },
  {
    path: "/examination",
    name: "startExamination",
    component: Examination
  },
  {
    path: "/vacation",
    name: "scheduleVacation",
    component: Vacation
  },
  {
    path: "/newExamination",
    name: "newExamination",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/dermatologistAndPharmacist/dermatologist/NewExamination.vue")
  },
  {
    path: "/pharmacies",
    name: "Pharmacy",
    component: () =>
      import("../views/patient/pharmacy/Pharmacy.vue")
  },
  {
    path: "/pharmacy/:id/dermatologist-examinations",
    name: "DermatologisExaminations",
    component: () =>
      import("../views/pharmacy/DermatologistExaminations.vue")
  },
  {
    path: "/systemAdminsPage",
    name: "SystemAdminsPage",
    component: SystemAdminsPage
      
  },
  {
    path: "/pharmacyAdminsPage",
    name: "PharmacyAdminsPage",
    component: () =>
      import("../views/systemAdmin/users/PharmacyAdminsPage.vue")
  },
  {
    path: "/dermatologistsPage",
    name: "DermatologistsPage",
    component: () =>
      import("../views/systemAdmin/users/DermatologistsPage.vue")
  },
  {
    path: "/suppliersPage",
    name: "SuppliersPage",
    component: () =>
      import("../views/systemAdmin/users/SuppliersPage.vue")
  },
  {
    path: "/myexaminations",
    name: "MyExaminations",
    component: () =>
      import("../views/patient/examinations/MyExaminations.vue")
  },
  {
    path: "/pharmacy-admin-profile",
    name: "Pharmacy Admin Profile",
    component: PharmacyAdminProfile
  },
  {
    path: "/dermatologist-profile",
    name: "Dermatologist Profile",
    component: DermatologistProfile
  },
  {
    path: "/pharmacist-profile",
    name: "Pharmacist Profile",
    component: PharmacistProfile
  },
  {
    path: "/mydrugs",
    name: "MyDrugs",
    component: () =>
      import("../views/patient/drugs/Drugs.vue")
  },
  {
    path: "/pharmaciesPage",
    name: "Pharmacies",
    component: () =>
      import("../views/systemAdmin/PharmacyPage.vue")
  }, 
  {
    path: "/drugsPage",
    name: "Drugs",
    component: () =>
      import("../views/systemAdmin/DrugsPage.vue")
  },
  {
    path: "/pharmacy/:id",
    name: "PharmacyProfile",
    component: () =>
      import("../views/pharmacy/PharmacyProfile.vue")
  },
  {
    path: "/edit-pharmacy/:id",
    name: "EditPharmacyInfo",
    component: () =>
      import("../views/pharmacy/EditPharmacyInfo.vue")
  },
  {
    path: "/reports-for-pharmacy/:id",
    name: "PharmacyReports",
    component: () =>
      import("../views/pharmacy/pharmacy-reports/PharmacyReports.vue")
  },
  {
    path: "/ratings",
    name: "Rating",
    component: () =>
      import("../views/patient/rating/Rating.vue")
  },
  {
    path: "/complaints",
    name: "Complaints",
    component: () =>
      import("../views/systemAdmin/Complaints.vue")
  },
  {
    path: "/orders",
    name: "Orders",
    component: () =>
      import("../views/supplier/Orders.vue")
  },
  {
    path: "/supplier-drugs",
    name: "DrugsInStorage",
    component: () =>
      import("../views/supplier/DrugsInStorage.vue")
  },
  {
    path: "/supplier-offers",
    name: "Offers",
    component: () =>
      import("../views/supplier/Offers.vue")
  },
  {
    path: "/supplier-profile",
    name: "SupplierProfile",
    component: SupplierProfile
  },
  {
    path: "/patient-subsribed-pharmacies",
    name: "SubscribedPharmacies",
    component: () =>
    import("../views/patient/pharmacy/SubscribedPharmacies.vue")
  },
  {
    path: "/drugs",
    name: "AllDrugs",
    component: () =>
    import("../views/patient/drugs/AllDrugs.vue")
  },
  {
    path: "/drug-informations/:id/:name",
    name: "DrugInformations",
    component: () =>
    import("../views/patient/drugs/DrugInformations.vue")
  },
  {
    path: "/activation",
    name: "ActivationPage",
    component: () =>
    import("../views/registration/ActivationPage.vue")
  },
  {
    path: "/eRecipes",
    name: "ERecipesPage",
    component: () =>
    import("../views/patient/eRecipe/ERecipesPage.vue")
  },
  {
    path: "/pharmacy-drugs",
    name: "DrugsInPharmacy",
    component: () =>
    import("../views/pharmacyAdmin/drugs/DrugsInPharmacy.vue")
  },
  {
    path: "/promotions",
    name: "Promotions",
    component: () =>
    import("../views/pharmacyAdmin/promotions/Promotions.vue")
  },
  {
    path: "/pharmacy-orders",
    name: "PharmacyOrders",
    component: () =>
    import("../views/pharmacyAdmin/orders/PharmacyOrders.vue")
  },
  {
    path: "/pharmacists",
    name: "Pharmacists",
    component: () =>
    import("../views/pharmacyAdmin/pharmacists/Pharmacists.vue")
  },
  {
    path: "/dermatologists",
    name: "Dermatologists",
    component: () =>
    import("../views/pharmacyAdmin/dermatologists/Dermatologists.vue")
  },
  {
    path: "/vacation-requests",
    name: "VacationRequests",
    component: () =>
    import("../views/pharmacyAdmin/vacation-requests/VacationRequests.vue")
  },
  {
    path: "/pharmacy-examinations",
    name: "PharmacyExaminations",
    component: () =>
    import("../views/pharmacyAdmin/examinations/PharmacyExaminations.vue")
  },

];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
