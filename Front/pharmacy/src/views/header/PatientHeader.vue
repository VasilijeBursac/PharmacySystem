<template>    
    <ul class="nav navbar-nav">      
        <li class="nav-item active"><a href="#" class="nav-link"><router-link to="/pharmacies">Pregled apoteka</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link">Pregled lekova</a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="/myexaminations">Moji pregledi</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link">Moji eRecepti</a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="/mydrugs">Moji lekovi</router-link></a></li>
        <li class="nav-item"><a href="#" class="nav-link">Pretplaćene apoteke</a></li>
        <li class="nav-item"><a href="#" class="nav-link">Žalbe</a></li>
        <li class="nav-item"><a href="#" class="nav-link"><router-link to="/ratings">Ocene</router-link></a></li>
        <b-nav-item-dropdown text="Mirko Mirković" right>
          <b-dropdown-item href="#"><router-link to="/patient-profile">Moj profil</router-link></b-dropdown-item>
          <b-dropdown-item href="#" @click="logout" >Odjava</b-dropdown-item>
        </b-nav-item-dropdown>
    </ul>
</template>


