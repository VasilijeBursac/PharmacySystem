<template>
	<div class="container">
		<b-carousel
			id="carousel-1"
			v-model="slide"
			:interval="4000"
			controls
			indicators
			background="#ababab"
			img-width="1024"
			img-height="300"
			style="text-shadow: 1px 1px 2px #333;"
			@sliding-start="onSlideStart"
			@sliding-end="onSlideEnd"
			class="mt-3">
			<b-carousel-slide
				caption="First slide"
				text="Nulla vitae elit libero, a pharetra augue mollis interdum."
				img-src="../assets/pharmacy.jpg">
			</b-carousel-slide>

			<b-carousel-slide
				caption="First slide"
				text="Nulla vitae elit libero, a pharetra augue mollis interdum."
				img-src="../assets/scientist.jpg">
			</b-carousel-slide>

			<b-carousel-slide
				caption="First slide"
				text="Nulla vitae elit libero, a pharetra augue mollis interdum."
				img-src="../assets/woman.jpg">
			</b-carousel-slide>

		</b-carousel>

		<b-row>
			<b-col offset="0">
				<b-card
					img-alt="Image"
					img-top
					tag="article"
					style="max-width: 20rem;"
					class="mb-2 mt-3 ">					
					<b-icon-person font-scale="4"></b-icon-person>
					<b-card-text class="mt-2">
						Some quick example text to build on the card title and make up the bulk of the card's content.
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
					<b-icon-person font-scale="4"></b-icon-person>
					<b-card-text class="mt-2">
						Some quick example text to build on the card title and make up the bulk of the card's content.
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
					<b-icon-person font-scale="4"></b-icon-person>
					<b-card-text class="mt-2">
						Some quick example text to build on the card title and make up the bulk of the card's content.
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


