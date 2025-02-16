package chap04

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("province")
class ProvinceTest {

    @DisplayName("shortfall")
    @Test
    fun shortfall() {
        val asia = Province(sampleProvinceData())
        assertThat(asia.shortfall()).isEqualTo(5)
    }

}