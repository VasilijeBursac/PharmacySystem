<template>
    <div class="container">
        <h4 class="h4 align-middle my-4">E recepti</h4>
        <div class="eRecipes-main">
            <b-card no-body >
                <b-tabs pills card align="center" lazy>
                    <b-tab title="Svi moji e recepti" active class="text-center">
                        <ERecipes/>
                    </b-tab>
                     <b-tab title="Generisi e recept"  class="text-center">
                         <GenerateERecipe/>
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
    </div>
</template>

<script>
import ERecipes from "./ERecipes.vue";
import GenerateERecipe from "./GenerateERecipe.vue";



export default {
    components: {
        ERecipes,
        GenerateERecipe

    }
}
</script>
