(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d225421"],{e440:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"container"},[a("h4",{staticClass:"h4 align-middle mt-4"},[t._v("Slobodni termini kod dermatologa")]),a("h5",{staticClass:"h5 align-middle my-3 text-success"},[t._v("Apoteka Jankovic")]),a("b-table",{ref:"table",attrs:{striped:"",hover:"",items:t.items,fields:t.fields},scopedSlots:t._u([{key:"cell(akcije)",fn:function(e){return[a("b-button",{attrs:{size:"sm"},on:{click:function(a){return t.schedule(e)}}},[t._v(" Zakaži pregled ")])]}}])})],1)},i=[],s=(a("4160"),a("a434"),a("159b"),{data:function(){return{data:[],fields:["termin","dermatolog",{key:"ocena",sortable:!0},{key:"cena",sortable:!0},"akcije"]}},methods:{schedule:function(t){var e=this;this.$http.get("examination/schedule/"+t.item.id).then((function(a){200==a.status&&(e.toast(),e.data.splice(t.index,1),e.$refs.table.refresh())}))},toast:function(){this.$bvToast.toast("Uspešno ste zakazali pregled!",{title:"Uspešno!",variant:"success",autoHideDelay:5e3})}},computed:{items:function(){return this.data}},mounted:function(){var t=this;this.$http.post("pharmacy/1/dermatologist-examinations",{type:"DermatologistExamination"}).then((function(e){var a=[];e.data.forEach((function(t){a.push({termin:new Date(t.term).toLocaleString(),dermatolog:t.employee,ocena:0!=t.employeeRating?t.employeeRating:"Nema ocenu",cena:t.price,id:t.examinationId})})),t.data=a}))}}),o=s,c=a("2877"),l=Object(c["a"])(o,n,i,!1,null,null,null);e["default"]=l.exports}}]);
//# sourceMappingURL=chunk-2d225421.b57d6835.js.map