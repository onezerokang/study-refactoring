package chap04

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.*

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

    @DisplayName("zero demand")
    @Test
    fun zeroDemand() {
        asia.demand = 0
        assertAll(
            { assertThat(asia.shortfall()).isEqualTo(-25) },
            { assertThat(asia.profit()).isZero() }
        )
    }

    @DisplayName("negative demand")
    @Test
    fun negativeDemand() {
        asia.demand = -1
        assertAll(
            { assertThat(asia.shortfall()).isEqualTo(-26) },
            { assertThat(asia.profit()).isEqualTo(-10) }
        )
    }

    @DisplayName("empty string demand")
    @Test
    fun emptyStringDemand() {
        assertThatThrownBy { asia.demand("") }
            .isInstanceOf(NumberFormatException::class.java)
    }

    @DisplayName("string for producers")
    @Test
    fun stringForProducers() {

    }

    @Nested
    @DisplayName("no producers")
    inner class Context_with_no_producers {
        private lateinit var noProducers: Province

        @BeforeEach
        fun setUp() {
            noProducers = Province(
                ProvinceData(
                    name = "No producers",
                    producers = emptyList(),
                    demand = 30,
                    price = 20,
                )
            )
        }

        @DisplayName("shortfall")
        @Test
        fun shortfall() {
            assertThat(noProducers.shortfall()).isEqualTo(30)
        }

        @DisplayName("profit")
        @Test
        fun profit() {
            assertThat(noProducers.profit()).isZero()
        }
    }

}