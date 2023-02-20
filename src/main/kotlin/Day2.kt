import java.util.Scanner

val sc = Scanner(System.`in`)
const val MM = 1_500_000
fun printHeader() {
    println("=========================================================")
    println("Enter the number to run programs")
    println("0. Exit program")
    println("1. Enter 5 employee")
    println("2. Print all employee")
    println("3. Set job and level")
    println("4. Guess salary")
    println("5. High or Low")
}

fun init(): Employee {
    println("=========================================================")
    println("Enter information")
    print("Name: ")
    val name = readln()
    print("Age: ")
    val age = sc.nextInt()
    print("Salary: ")
    val salary = sc.nextInt()
    return Employee(name = name, salary = salary, age = age)
}

class Employee(val name: String = "", val age: Int = 0, val salary: Int = 0) {
    var job: String? = null
    var level: Int = 0
    var late = 0
    fun print(): String {
        return if (job != null) "$name\t$age\t$salary\t$job"
        else "$name\t$age\t$salary"
    }

    fun setJobAndLevel() {
        println("Job: ")
        job = readln()
        println("Level: ")
        level = sc.nextInt()
    }
}

val highSalary: () -> Unit = {
    println("High Salary!")
}

val lowSalary = {
    println("Low Salary")
}

fun Employee.highOrLowSalary(): () -> Unit {
    return if (salary > 1000) highSalary else lowSalary
}

fun guess(employee: Employee, new: () -> Int): String {
    val newSalary = new()
    return if (employee.salary < newSalary) "increased"
    else "decreased"
}

fun main() {
    val employees = Array(5) { Employee() }
    while (true) {
        printHeader()
        when (sc.nextInt()) {
            0 -> break
            1 -> {
                for (i in employees.indices)
                    employees[i] = init()
            }

            2 -> {
                println("name\tage\tsalary\tjob")
                for (i in employees.indices) println(employees[i].print())
            }

            3 -> {
                println("Enter employee index:")
                val index = sc.nextInt()
                employees[index].let {
                    println(it.print())
                    it.setJobAndLevel()
                    println("=========================================================")
                    println(it.print())
                }
            }

            4 -> {
                println("Enter employee index:")
                val index = sc.nextInt()
                with(employees[index]) {
                    print()
                    if (this.job != null) {
                        println(guess(employee = this) {
                            (MM * level) - ((MM * late) / 4)
                        })
                    }
                }
            }

            5 -> {
                println("Enter employee index:")
                val index = sc.nextInt()
                employees[index].highOrLowSalary()
            }
        }
    }
}