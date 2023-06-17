package com.example.dispositivos_moviles_proyecto_gc_es1.logic.list

data class Heroes(var id: Int, var heroe: String, var comic: String, var img: String) {

}
class ListItems{
    fun retornarHeroes(): List<Heroes> {
        val items = listOf(
            Heroes(
                1,
                "Deadpool",
                "Deadpool",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8926324-large-2680196.jpg"
            ),

            Heroes(
                2,
                "Elektra",
                "Elektra",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8324004-daredevil_woman_without_fear_vol_1_1_unknown_comic_books_exclusive_virgin_variant.jpg"

            ),

            Heroes(
                3,
                "Daredevil",
                "Daredevil",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11118/111187046/7397359-0398898002-EQH1ysWWsAA7QLf"

            ),

            Heroes(
                4,
                "Gambit",
                "X-men",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11126/111269625/7324346-excalibur%20variant.jpg"

            ),
            Heroes(
                5,
                "Spiderman",
                "Amazing Spiderman",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8126579-amazing_spider-man_vol_5_54_stormbreakers_variant_textless.jpg"

            )
        )

        return items
    }


}

