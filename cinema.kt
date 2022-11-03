package cinema

val rowsSeats = mutableListOf<MutableList<Char>>()
val prices = mutableListOf<MutableList<Int>>()

fun initSeats(
    rowsNum: Int,
    rowSeatsNum: Int,
) {
    // init rowsSeats
    for (n in 1..rowsNum) {
        val rowSeats = MutableList(rowSeatsNum) { 'S' }
        rowsSeats.add(rowSeats)
    }

    // init prices
    val totalSeats = rowsNum * rowSeatsNum

    val firstRows = if (totalSeats <= 60) {
        rowsNum
    } else if (rowsNum % 2 == 0) {
        rowsNum / 2
    } else {
        (rowsNum - 1) / 2
    }

    for (p in 1..rowsNum) {
        val ticketPrice = if (p <= firstRows) {
            10
        } else {
            8
        }

        val list = MutableList(rowSeatsNum) { ticketPrice }

        prices.add(list)
    }
}

fun printSeatsScheme() {
    println()

    val headerSize = rowsSeats[0].size

    val headerRange = 1..headerSize

    println("Cinema:")

    println("  ${headerRange.joinToString(" ")}")

    for (rowI in rowsSeats.indices) {
        val rowSeats = rowsSeats[rowI]

        println("${rowI + 1} ${rowSeats.joinToString(" ")}")
    }

    printMenu()
}

fun printBuyPrompt () {
    println()

    println("Enter a row number:")

    val rowIndex = readln().toInt() - 1

    println("Enter a seat number in that row:")

    val seatIndexInRow = readln().toInt() - 1

    if (
        rowIndex >= rowsSeats.size ||
        seatIndexInRow >= rowsSeats[0].size
    ) {
        println("\nWrong input!")
        return printBuyPrompt()
    }

    if (rowsSeats[rowIndex][seatIndexInRow] == 'B') {
        println("\nThat ticket has already been purchased!")
        return printBuyPrompt()
    }

    val ticketPrice = prices[rowIndex][seatIndexInRow]

    rowsSeats[rowIndex][seatIndexInRow] = 'B'

    println("Ticket price: $$ticketPrice")

    printMenu()
}

fun printMenu () {
    println()
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")

    val signal = readln().toInt()

    when (signal) {
        1 -> printSeatsScheme()
        2 -> printBuyPrompt()
        3 -> printStatistics()
    }
}

fun printStatistics () {
    println()

    var seatsCount = 0
    var purchasedCount = 0
    var currentIncome = 0
    var totalIncome = 0

    for (rowI in rowsSeats.indices) {
        for (seatI in rowsSeats[rowI].indices) {
            if (rowsSeats[rowI][seatI] == 'B') {
                purchasedCount++
                currentIncome += prices[rowI][seatI]
            }

            totalIncome += prices[rowI][seatI]
            seatsCount++
        }
    }

    val percentage = purchasedCount.toDouble()/seatsCount.toDouble() * 100
    val formatPercentage = "%.2f".format(percentage)

    println("Number of purchased tickets: $purchasedCount")
    println("Percentage: $formatPercentage%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")

    printMenu()
}

fun main() {
    println("Enter the number of rows:")
    val rowsNum = readln().toInt()

    println("Enter the number of seats in each row:")
    val rowSeatsNum = readln().toInt()

    initSeats(rowsNum, rowSeatsNum)

    printMenu()
}
