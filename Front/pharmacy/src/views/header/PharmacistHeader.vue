<template>    
    <ul class="nav navbar-nav">
        <li class="nav-item active"><a href="/my-calendar"  class="nav-link">Moj radni kalendar</a></li>
         <li class="nav-item"><a  href="/searchedPatients" class="nav-link">Zapocni savetovanje</a></li>
        <li class="nav-item"><a href="/myPatients" class="nav-link">Moji pacijenti</a></li>
        <li class="nav-item"><a  href="/vacation" class="nav-link">Godisnji odmor i odsustva</a></li>
        <li class="nav-item"><a href="/issue-drug" class="nav-link">Preporuci lekove</a></li>
       
        <b-nav-item-dropdown text="Mirko Mirković" right>
          <b-dropdown-item href="#"><router-link to="/pharmacist-profile">Moj profil</router-link></b-dropdown-item>
          <b-dropdown-item href="#" @click="logout" >Odjava</b-dropdown-item>
        </b-nav-item-dropdown>
    </ul>   
</template>
