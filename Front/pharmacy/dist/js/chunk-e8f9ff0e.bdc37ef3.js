(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-e8f9ff0e"],{"027d":function(e,t,s){"use strict";s("6a0c")},2623:function(e,t,s){"use strict";s.r(t);var r=function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"container"},[s("h4",{staticClass:"h4 align-middle my-4"},[e._v("Administratori apoteka")]),s("div",{staticClass:"users-main"},[s("b-card",{attrs:{"no-body":""}},[s("b-tabs",{attrs:{pills:"",card:"",align:"center",lazy:""}},[s("b-tab",{staticClass:"text-center",attrs:{title:"Prikaz administratora apoteka",active:""}},[s("PharmacyAdmins")],1),s("b-tab",{staticClass:"text-center",attrs:{title:"Registruj novog administratora"}},[s("RegisterPharmacyAdmin")],1)],1)],1)],1)])},a=[],i=function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{attrs:{id:"systemAdmins-table"}},[s("b-table",{attrs:{id:"table",striped:"",hover:"","primary-key":"id","tbody-transition-props":e.transProps,items:e.items,fields:e.fields}})],1)},n=[],o={data:function(){return{fields:[{key:"email",sortable:!0},{key:"firstName",sortable:!0},{key:"lastName",sortable:!0},{key:"address",sortable:!0},{key:"city",sortable:!0},{key:"country",sortable:!0},{key:"phoneNumber",sortable:!0}],transProps:{name:"flip-list"},items:[]}},created:function(){var e=this;this.$axios.get("http://localhost:9001/pharmacyAdmin/allPharmacyAdmins").then((function(t){e.items=t.data})).catch((function(t){e.errorMessage=t.message,console.error("There was an error!",t)}))}},l=o,u=(s("027d"),s("2877")),c=Object(u["a"])(l,i,n,!1,null,null,null),d=c.exports,p=function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"register"},[e.show?s("b-form",{attrs:{id:"form"},on:{submit:e.onSubmit,reset:e.onReset}},[s("b-form-group",{staticClass:"text-center",attrs:{id:"email-group",label:"Email:","label-for":"email-input"}},[s("b-form-input",{staticClass:"text-center",attrs:{id:"email-input",placeholder:"Unesite email",required:""},model:{value:e.user.email,callback:function(t){e.$set(e.user,"email",t)},expression:"user.email"}})],1),s("b-form-group",{staticClass:"text-center",attrs:{id:"password-group",label:"Lozinka:","label-for":"password-input"}},[s("b-form-input",{staticClass:"text-center",attrs:{type:"password",id:"password-input",placeholder:"Unesite lozinku",required:""},model:{value:e.user.password,callback:function(t){e.$set(e.user,"password",t)},expression:"user.password"}})],1),s("b-form-group",{staticClass:"text-center",attrs:{id:"passwordCheck-group",label:"Ponovljena lozinka","label-for":"address-input"}},[s("b-form-input",{staticClass:"text-center",attrs:{type:"password",id:"passwordCheck-input",placeholder:"Ponovite lozinku",required:""},model:{value:e.user.passwordCheck,callback:function(t){e.$set(e.user,"passwordCheck",t)},expression:"user.passwordCheck"}})],1),e.passwordError?s("label",{staticStyle:{color:"red"}},[e._v("Lozinke se ne podudaraju !")]):e._e(),s("b-form-group",{staticClass:"text-center",attrs:{id:"name-group",label:"Ime:","label-for":"name-input"}},[s("b-form-input",{staticClass:"text-center",attrs:{id:"name-input",placeholder:"Unesite ime",required:""},model:{value:e.user.name,callback:function(t){e.$set(e.user,"name",t)},expression:"user.name"}})],1),s("b-form-group",{staticClass:"text-center",attrs:{id:"surname-group",label:"Prezime:","label-for":"surname-input"}},[s("b-form-input",{staticClass:"text-center",attrs:{id:"surname-input",placeholder:"Unesite prezime",required:""},model:{value:e.user.surname,callback:function(t){e.$set(e.user,"surname",t)},expression:"user.surname"}})],1),s("b-form-group",{staticClass:"text-center",attrs:{id:"address-group",label:"Adresa:","label-for":"address-input"}},[s("b-form-input",{staticClass:"text-center",attrs:{id:"address-input",placeholder:"Unesite adresu prebivalista",required:""},model:{value:e.user.address,callback:function(t){e.$set(e.user,"address",t)},expression:"user.address"}})],1),s("b-form-group",{staticClass:"text-center",attrs:{id:"city-group",label:"Grad:","label-for":"city-input"}},[s("b-form-input",{staticClass:"text-center",attrs:{id:"city-input",placeholder:"Unesite grad ",required:""},model:{value:e.user.city,callback:function(t){e.$set(e.user,"city",t)},expression:"user.city"}})],1),s("b-form-group",{staticClass:"text-center",attrs:{id:"country-group",label:"Drzava:","label-for":"country-input"}},[s("b-form-input",{staticClass:"text-center",attrs:{id:"country-input",placeholder:"Unesite drzavu ",required:""},model:{value:e.user.country,callback:function(t){e.$set(e.user,"country",t)},expression:"user.country"}})],1),s("b-form-group",{staticClass:"text-center",attrs:{id:"phone-group",label:"Telefon:","label-for":"phone-input"}},[s("b-form-input",{staticClass:"text-center",attrs:{id:"phone-input",placeholder:"Unesite broj telefona ",required:""},model:{value:e.user.phone,callback:function(t){e.$set(e.user,"phone",t)},expression:"user.phone"}})],1),s("b-form-group",{staticClass:"text-center",attrs:{id:"pharmacies-group",label:"Apoteka:","label-for":"pharmacy-input"}},[s("select",{directives:[{name:"model",rawName:"v-model",value:e.selected,expression:"selected"}],on:{change:function(t){var s=Array.prototype.filter.call(t.target.options,(function(e){return e.selected})).map((function(e){var t="_value"in e?e._value:e.value;return t}));e.selected=t.target.multiple?s:s[0]}}},e._l(e.pharmacies,(function(t){return s("option",{key:t,domProps:{value:t.id}},[e._v(e._s(t.name))])})),0)]),s("div",{staticClass:"buttons text-center"},[s("b-button",{staticClass:"mr-2",attrs:{type:"submit",variant:"success"}},[s("b-icon-check"),e._v(" Registruj se")],1),s("b-button",{attrs:{type:"reset",variant:"danger"}},[s("b-icon-x"),e._v(" Otkaži ")],1)],1)],1):e._e()],1)},m=[],b=(s("b0c0"),{data:function(){return{user:{email:"",password:"",passwordCheck:"",name:"",surname:"",address:"",city:"",country:"",phone:""},pharmacies:[],selected:"",passwordError:!1,show:!0}},methods:{onSubmit:function(e){var t=this;e.preventDefault(),this.passwordError=!1,this.user.passwordCheck!==this.user.password&&(this.passwordError=!0),this.passwordError||this.$http.post("auth/signupPharmacyAdmin",{email:this.user.email,password:this.user.password,firstName:this.user.name,lastName:this.user.surname,address:this.user.address,city:this.user.city,country:this.user.country,phoneNumber:this.user.phone,pharmacyId:this.selected.value}).then((function(){t.toast()})).catch((function(e){500===e.response.status&&alert("Vec postoji korisnik sa unetim imejlom")}))},toast:function(){this.$bvToast.toast("Uspešno ste registrovali novog administratora apoteke!",{title:"Uspešno!",variant:"success",autoHideDelay:5e3})},onReset:function(e){var t=this;e.preventDefault(),this.user.email="",this.user.password="",this.user.passwordCheck="",this.user.name="",this.user.surname="",this.user.address="",this.user.city="",this.user.phone="",this.passwordError=!1,this.$nextTick((function(){t.show=!0}))}},components:{},mounted:function(){var e=this;this.$http.get("pharmacy/allPharmacies").then((function(t){e.pharmacies=t.data}))}}),h=b,f=(s("b4b5"),Object(u["a"])(h,p,m,!1,null,"752d7af6",null)),y=f.exports,v={components:{PharmacyAdmins:d,RegisterPharmacyAdmin:y}},k=v,g=Object(u["a"])(k,r,a,!1,null,null,null);t["default"]=g.exports},"6a0c":function(e,t,s){},"7bba":function(e,t,s){},b4b5:function(e,t,s){"use strict";s("7bba")}}]);
//# sourceMappingURL=chunk-e8f9ff0e.bdc37ef3.js.map