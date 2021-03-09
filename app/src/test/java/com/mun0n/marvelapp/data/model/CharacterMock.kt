package com.mun0n.marvelapp.data.model

import com.mun0n.marvelapp.model.Data
import com.mun0n.marvelapp.model.SingleCharacterResponse

object CharacterMock {
    val CHARACTER_RESPONSE = SingleCharacterResponse(
        code = 200,
        status = "Ok",
        copyright = "© 2021 MARVEL",
        attributionText = "Data provided by Marvel. © 2021 MARVEL",
        attributionHTML = "<a href=\"http://marvel.com\">Data provided by Marvel. © 2021 MARVEL</a>",
        etag = "fa7bd2bd49857b7030797f72a1f9f64133aa5d82",
        data = Data(
            limit = 20,
            offset = 20,
            total = 20, count = 20,
            results = emptyList()
        )
    )
}