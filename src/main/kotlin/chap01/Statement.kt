package chap01

import java.text.NumberFormat
import java.util.*
import kotlin.math.max

fun createStatementData(invoice: Invoice, plays: Map<String, Play>): StatementData {

    fun playFor(perf: Invoice.Performance): Play {
        return plays[perf.playId]!!
    }

    fun totalAmount(enrichedPerformances: List<StatementData.EnrichedPerformance>): Int {
        return enrichedPerformances.fold(0) { total, perf ->
            total + perf.amount
        }
    }

    fun volumeCreditsFor(aPerformance: Invoice.Performance): Int {
        var result = 0
        result += max(aPerformance.audience - 30, 0)
        if ("comedy" == playFor(aPerformance).type) {
            result += aPerformance.audience / 5
        }
        return result
    }

    fun totalVolumeCredits(enrichedPerformances: List<StatementData.EnrichedPerformance>): Int {
        return enrichedPerformances.fold(0) { total, perf ->
            total + perf.volumeCredits
        }
    }

    fun enrichPerformance(aPerformance: Invoice.Performance): StatementData.EnrichedPerformance {
        val calculator = PerformanceCalculator(aPerformance, playFor(aPerformance))
        return StatementData.EnrichedPerformance(
            aPerformance.playId,
            aPerformance.audience,
            playFor(aPerformance),
            calculator.amount(),
            volumeCreditsFor(aPerformance),
        )
    }

    fun enrichedPerformances(): List<StatementData.EnrichedPerformance> {
        return invoice.performances.map { enrichPerformance(it) }
    }

    return StatementData(
        invoice.customer,
        totalAmount(enrichedPerformances()),
        totalVolumeCredits(enrichedPerformances()),
        enrichedPerformances()
    )
}

fun statement(invoice: Invoice, plays: Map<String, Play>): String {
    return renderPlainText(createStatementData(invoice, plays))
}

fun htmlStatement(invoice: Invoice, plays: Map<String, Play>): String {
    return renderHtml(createStatementData(invoice, plays))
}

fun usd(amount: Int): String {
    return NumberFormat.getCurrencyInstance(Locale.US).format(amount / 100)
}

fun renderPlainText(data: StatementData): String {

    var result = "청구 내역 (고객명: ${data.customer})\n"

    for (perf: StatementData.EnrichedPerformance in data.performances) {
        // 청구 내역을 출력한다.
        result += " ${perf.play.name}: ${usd(perf.amount)} (${perf.audience}석)\n"
    }

    result += "총액: ${usd(data.totalAmount)}\n"
    result += "적립 포인트: ${data.totalVolumeCredits}점\n"
    return result
}

fun renderHtml(data: StatementData): String {

    var result = "<h1>청구 내역 (고객명: ${data.customer})</h1>\n"
    result += "<table>\n"
    result += "<tr><th>연극</th><th>좌석 수</th><th>금액</th></tr>"

    for (perf: StatementData.EnrichedPerformance in data.performances) {
        // 청구 내역을 출력한다.
        result += "<tr><td>${perf.play.name}</td><td>${perf.audience}석</td>"
        result += "<td>${usd(perf.amount)}</td></tr>\n"
    }
    result += "</table>\n"
    result += "<p>총액: <em>${usd(data.totalAmount)}</em></p>\n"
    result += "<p>적립 포인트: <em>${data.totalVolumeCredits}</em>점</p>\n"
    return result
}
