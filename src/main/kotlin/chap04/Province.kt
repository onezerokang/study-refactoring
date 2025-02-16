package chap04

import kotlin.math.min

class Province(provinceData: ProvinceData) {
    val name = provinceData.name
    val producers: MutableList<Producer> = mutableListOf()
    var totalProduction = 0
    var demand = provinceData.demand
    val price = provinceData.price

    init {
        provinceData.producers.forEach { d -> addProducer(Producer(this, d)) }
    }

    private fun addProducer(arg: Producer) {
        producers.add(arg)
        totalProduction += arg.production
    }

    fun shortfall(): Int {
        return this.demand - this.totalProduction
    }

    fun profit(): Int {
        return demandValue() - demandCost()
    }

    private fun demandValue(): Int {
        return satisfiedDemand() * this.price
    }

    private fun satisfiedDemand(): Int {
        return min(this.demand, this.totalProduction)
    }

    private fun demandCost(): Int {
        var remainingDemand = this.demand
        var result = 0
        this.producers
            .sortedBy { it.cost }
            .forEach {
                val contribution = min(remainingDemand, it.production)
                remainingDemand -= contribution
                result += contribution * it.cost
            }
        return result
    }

    fun demand(arg: String) {
        this.demand = arg.toInt()
    }
}