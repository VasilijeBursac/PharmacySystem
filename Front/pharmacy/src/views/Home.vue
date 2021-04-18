<template>
	<div class="container">
		<b-carousel
			id="carousel-1"
			v-model="slide"
			:interval="4000"
			controls
			indicators
			background="#ababab"
			img-width="500"
			img-height="300"
			style="text-shadow: 1px 1px 2px #333;"
			@sliding-start="onSlideStart"
			@sliding-end="onSlideEnd"
			class="mt-3">
			<b-carousel-slide
				caption="Apoteke"
				text="Pronadjite najbližu apoteku koja zadovoljava Vaše potrebe."
				img-src="../assets/pharmacy.jpg">
			</b-carousel-slide>

			<b-carousel-slide
				caption="Lekovi"
				text="Rezervišite lekove iz udobnosti svog doma."
				img-src="../assets/woman.jpg">
			</b-carousel-slide>

			<b-carousel-slide
				caption="Pregledi"
				text="Zakažite savetovanje sa farmaceutom ili pregled kod dermatologa, u svega nekoliko klikova."
				img-src="../assets/examination.jpg">
			</b-carousel-slide>			

		</b-carousel>
		<b-row class="mb-3">
			<b-col offset="0">
				<b-card
					img-alt="Image"
					img-top
					tag="article"
					style="max-width: 20rem;"
					class="mb-2 mt-3 ">					
					<b-icon-shield-plus font-scale="3"></b-icon-shield-plus>
					<b-card-text class="mt-3">
						Mi saradjujemo sa velikim brojem apoteka, tako da su raznovrsna ponuda i veliki izbor zagarantovani. Pronadjite lekove ili stručnjake koji odgovaraju Vašim kriterijumima.
					</b-card-text>
				</b-card>
			</b-col>
			
			<b-col offset="0">
				<b-card
					img-alt="Image"
					img-top
					tag="article"
					style="max-width: 20rem;"
					class="mb-2 mt-3">					
					<b-icon-cash font-scale="3"></b-icon-cash>
					<b-card-text class="mt-3">
						Iskoristite posebne akcije i promocije koje naši partneri nude. Svaka kupovina ili rezervacija Vam donosi bonus poene, zahvaljujući kojima možete ostvariti dodatne popuste.
					</b-card-text>
				</b-card>
			</b-col>

			<b-col offset="0">
				<b-card
					img-alt="Image"
					img-top
					tag="article"
					style="max-width: 20rem;"
					class="mb-2 mt-3">					
					<b-icon-envelope font-scale="3"></b-icon-envelope>
					<b-card-text class="mt-3">
						Zahvaljujući našem naprednom sistemu email obaveštenja, bićete uvek u toku sa svim promocijama, ponudama, stanjem Vaših zakazanih pregleda i rezervisanih lekova.
					</b-card-text>
				</b-card>
			</b-col>
		</b-row>		

	</div>	
</template>

<script>
  export default {
    data() {
      return {
        slide: 0,
        sliding: null
      }
    },
    methods: {
      onSlideStart() {
        this.sliding = true
      },
      onSlideEnd() {
        this.sliding = false
      }
    }
  }
</script>

<style scoped>

</style>


