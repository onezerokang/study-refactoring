package chap01

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StatementTest {
    @Test
    fun statement() {
        val plays = mapOf(
            "hamlet" to Play(name = "Hamlet", type = "tragedy"),
            "as-like" to Play(name = "As You Like It", type = "comedy"),
            "othello" to Play(name = "Othello", type = "tragedy"),
        )
        val invoice = Invoice(
            customer = "BigCo",
            performances = listOf(
                Invoice.Performance("hamlet", 55),
                Invoice.Performance("as-like", 35),
                Invoice.Performance("othello", 40)
            )
        )

        val result = statement(invoice, plays)

        assertThat(result).isEqualTo(
            """
            청구 내역 (고객명: BigCo)
             Hamlet: $650.00 (55석)
             As You Like It: $580.00 (35석)
             Othello: $500.00 (40석)
            총액: $1,730.00
            적립 포인트: 47점
        """.trimIndent() + '\n'
        )
    }
}
