package com.shearer.jetmoviedb.features.movie.list

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ListConfigTest {

    @Test fun popularConfig_getType_returnsPopular() {
        assertThat(PopularConfig().getType()).isEqualTo("popular")
    }

    @Test fun searchConfig_getType_returnsSearchWithPrefix() {
        assertThat(SearchConfig("search").getType()).isEqualTo("s-search")
    }
}