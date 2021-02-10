<template>
    <div id="map"></div>
</template>

<script>
export default {
    props: ['pharmacyId'],
    data: function() {
        return{
            pharmacy: {},
            geocodingURLbase: 'https://api.mapbox.com/geocoding/v5/mapbox.places/', 
            mapboxglAccessToken: 'pk.eyJ1IjoidmFzaWxpamVidXJzYWMiLCJhIjoiY2tlem8wdmw4MGJqcjJxbnhoOWFhNWtxeiJ9.SeYF-uQyF26hw2UYEI_YlQ',
            geocodingResults: {},
            pharmacyLocationCoordinates: [],
        } 
    },
    mounted(){
        this.$http
            .get('/pharmacy/' + this.pharmacyId)
            .then( res => {
                this.pharmacy = JSON.parse(JSON.stringify(res.data))
                this.getPharmacyLocationInfo();
            })
    },
    methods:{
        generateGeocodingURL(){
            var geocodingAddressParts = [this.pharmacy.address, this.pharmacy.city, this.pharmacy.country]
            var geocodingAddress = geocodingAddressParts.join(" ").replace(/ /g, "%20")
            
            var geocodingURL =  this.geocodingURLbase.concat(geocodingAddress, '.json?access_token=', this.mapboxglAccessToken)

            return geocodingURL;
        },

        getPharmacyLocationInfo(){
            var geocodingURL = this.generateGeocodingURL();
            
            this.$http
                .get(geocodingURL)
                .then( res => {
                    this.geocodingResults = JSON.parse(JSON.stringify(res.data))
                    this.pharmacyLocationCoordinates = this.geocodingResults.features[0].geometry.coordinates;

                    this.initMap();
                })
        },

        initMap(){
            var mapboxgl = require('mapbox-gl/dist/mapbox-gl.js');

			mapboxgl.accessToken = 'pk.eyJ1IjoidmFzaWxpamVidXJzYWMiLCJhIjoiY2tlem8wdmw4MGJqcjJxbnhoOWFhNWtxeiJ9.SeYF-uQyF26hw2UYEI_YlQ';
			var map = new mapboxgl.Map({
				container: 'map',
				style: 'mapbox://styles/mapbox/streets-v11',
				center: this.pharmacyLocationCoordinates,
				zoom: 14
			});

            map.resize()
            
            // Add location marker
            new mapboxgl.Marker()
                .setLngLat(this.pharmacyLocationCoordinates)
                .setPopup(new mapboxgl.Popup({ offset: 25 }) // Add popups
                .setHTML('<h3>' +  this.pharmacy.name + '</h3><p>' + this.pharmacy.address + ", " + this.pharmacy.city + ", " + this.pharmacy.country + '</p>'))
                .addTo(map);
        }
    }
}
</script>


<style>
    #map {
        position: absolute;
        width: 100%;
        height: 100%;
    }

    .mapboxgl-popup {
        max-width: 200px;
    }

    .mapboxgl-popup-content {
        text-align: center;
        font-family: 'Segoe UI', Avenir, Helvetica, Arial, sans-serif;
    }
</style>