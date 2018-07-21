package com.telus.utility

import com.telus.R

class ImageResource {

    private val imageMap: Map<String, Int>

    init {
        imageMap = HashMap()
        imageMap.put("sparrow.jpg", R.drawable.sparrow)
        imageMap.put("karate_kid.jpg", R.drawable.karate_kid)
        imageMap.put("hours_127.jpg", R.drawable.hours_127)
        imageMap.put("case_39.jpg", R.drawable.case_39)
        imageMap.put("legacy_24.jpg", R.drawable.legacy_24)
        imageMap.put("area_51.jpg", R.drawable.area_51)
        imageMap.put("big_hero_6.jpg", R.drawable.big_hero_6)
        imageMap.put("deadpool.jpg", R.drawable.deadpool)
        imageMap.put("night_crawler.jpg", R.drawable.night_crawler)
        imageMap.put("mindhunter.jpg", R.drawable.mindhunter)

        imageMap.put("the_shape_of_water.jpg", R.drawable.the_shape_of_water)
        imageMap.put("lucy.jpg", R.drawable.lucy)
        imageMap.put("saving_mr_bank.jpg", R.drawable.saving_mr_bank)
        imageMap.put("mazerunner_death_cure.jpg", R.drawable.mazerunner_death_cure)
        imageMap.put("years_12.jpg", R.drawable.years_12)
        imageMap.put("shark_night.jpg", R.drawable.shark_night)
        imageMap.put("alien.jpg", R.drawable.alien)
        imageMap.put("jigsaw.jpg", R.drawable.jigsaw)
        imageMap.put("black_swan.jpg", R.drawable.black_swan)
        imageMap.put("the_miracle_season.jpg", R.drawable.the_miracle_season)
    }

    fun getDrawable(key: String): Int? {
        return imageMap[key]
    }

}