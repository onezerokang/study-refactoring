package chap04

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

@DisplayName("province")
class ProvinceTest {
    private lateinit var asia: Province

    @BeforeEach
    fun setUp() {
        asia = Province(sampleProvinceData())
    }

    @DisplayName("shortfall")
    @Test
    fun shortfall() {
        val asia = Province(sampleProvinceData())
        assertThat(asia.shortfall()).isEqualTo(5)
    }

    @DisplayName("profit")
    @Test
    fun profit() {
        assertThat(asia.profit()).isEqualTo(230)
    }

    @DisplayName("change production")
    @Test
    fun changeProduction() {
        asia.producers[0].production(20)
        assertAll(
            { assertThat(asia.shortfall()).isEqualTo(-6) },
            { assertThat(asia.profit()).isEqualTo(292) }
        )
    }

}