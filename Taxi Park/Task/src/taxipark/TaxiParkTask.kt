package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =  this.allDrivers.filter { it.name !in this.trips.map { it.driver.name } }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        this.allPassengers.filter {
            var passenger :String = it.name
            this.trips.flatMap{ it.passengers}.count { it.name==passenger }>=minTrips
        }.toSet()
/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        this.allPassengers.filter {
            var passenger : String = it.name
            this.trips.filter{ it.driver.name == driver.name }.count { passenger in it.passengers.map { it.name } }>1
        }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
       this.allPassengers.filter {
           var passenger : String= it.name
           var A : List<Trip> = this.trips.filter { it.discount == null }
           var B : List<Trip> = this.trips.filter { it.discount != null }
           A.flatMap { it.passengers }.count { it.name == passenger }<B.flatMap { it.passengers }.count { it.name == passenger }
       }.toSet()


/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    var map : Map<IntRange?,List<Int?>> = mutableMapOf()
    val durations : List<Int> = this.trips.map { it.duration }
    var range : IntRange?
    for (duration in durations){
        var a :Int = duration/10 * 10
        var b :Int = duration/10 * 10 +9
        range = a..b
        if (map.containsKey(range)) map = map + Pair(range, map.getValue(range).plus(duration))
        else map = map + Pair(range, listOf(duration))
    }
    return map.maxBy { it.value.size  }?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    val totalIncome : Double = this.trips.sumByDouble { it.cost }
    var bestPerformers :Map<String?,List<Double?>> = mutableMapOf()
    val drivers :Int = this.allDrivers.size *20 /100
    var list : List<Trip> = this.trips
    for (i in 0..drivers-1){
        var performance :Pair<String?,Double?> = list.maxBy { it.cost }?.driver?.name to list.filter{ it.driver.name == list.maxBy { it.cost }?.driver?.name }.sumByDouble{ it.cost}
        list = list.filter{it.driver.name !=performance.first}
        if (bestPerformers.containsKey(performance.first)) bestPerformers = bestPerformers + Pair(performance.first, bestPerformers.getValue(performance.first).plus(performance.second))
        else bestPerformers = bestPerformers + Pair(performance.first, listOf(performance.second))
    }


    if (this.trips.size!=0)return bestPerformers.flatMap{ it.value }.sum()>=(totalIncome*80/100)
    else return false
}

private fun List<Double?>.sum(): Double {
    var res : Double = 0.0
    for (i in 0 until this.size) res = res + (this.get(i) ?: 0.0)
 return res
}

