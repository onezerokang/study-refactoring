package chap04

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

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

}