package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (year != other.year) {
            return year - other.year
        } else if (month != other.month) {
            return month - other.month
        } else {
            return dayOfMonth - other.dayOfMonth
        }
    }

    operator fun plus(interval: TimeInterval) = addTimeIntervals(interval, 1)

    operator fun plus(repeatedTimeInterval: RepeatedTimeInterval) = addTimeIntervals(repeatedTimeInterval.interval, repeatedTimeInterval.number)
}

class RepeatedTimeInterval(val interval: TimeInterval, val number: Int)

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(number: Int) = RepeatedTimeInterval(this, number)
}

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        var current = start
        override fun hasNext(): Boolean {
            return current <= endInclusive
        }

        override fun next(): MyDate {
            if (!hasNext()) {
                throw NoSuchElementException()
            }

            val result = current
            current = current.nextDay()
            return result
        }

    }

    operator fun contains(date: MyDate): Boolean =  (start <= date) && (date <= endInclusive)
}
