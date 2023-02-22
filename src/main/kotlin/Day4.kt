import java.io.*

data class Subject(
    val name: String,
    var finalScoreByNumber: Double = 0.0,
    var finalScoreByWord: Char = 'F',
    var pass: Boolean = finalScoreByWord != 'F'
) : Serializable

class FullName(private val firstName: String, private val lastName: String) : Serializable {
    override fun toString(): String {
        return "$firstName $lastName"
    }
}

abstract class User {
    abstract var ID: String
    abstract var fullName: FullName?
    abstract var address: String
    abstract var age: Int
}

data class Student(
    override var fullName: FullName?,
    override var ID: String,
    override var address: String,
    override var age: Int,
    val classroom: String,
    val subjectList: List<Subject>
) : User(), Serializable

class Lecturer(
    override var ID: String,
    override var fullName: FullName?,
    override var address: String,
    override var age: Int,
    val department: String
) : User() {
    override fun toString(): String {
        return "Lecturer ${fullName.toString()} (Id: $ID, address: $address, age: $age, department: $department)"
    }
}

fun interface SubjectAction {
    fun setWordScore(subject: Subject)
}

val studentList = ArrayList<Student>()
val lecturerList = ArrayList<Lecturer>()
val subjectList = arrayListOf(
    Subject("Math"), Subject("Physics"), Subject("Alchemist"), Subject("Analyst and Design Information System")
)

fun main() {
    val setWordScore = SubjectAction { subject ->
        when {
        subject.finalScoreByNumber > 9.0 -> subject.finalScoreByWord = 'A'
        subject.finalScoreByNumber > 7.0 -> subject.finalScoreByWord = 'B'
        subject.finalScoreByNumber > 5.0 -> subject.finalScoreByWord = 'C'
        subject.finalScoreByNumber > 4.0 -> subject.finalScoreByWord = 'D'
        else -> subject.finalScoreByWord = 'F'
    } }
    fun printRequest() {
        println("===========================================")
        println("0. Exit")
        println("1. Enter Student Information")
        println("2. Enter Lecturer Information")
        println("3. Enter Scores")
        println("4. Print all student information")
        println("5. Print all lecturer information")
    }

    val file = File("Student.txt")
    try {
        val inputStream = FileInputStream(file)
        val objectInput = ObjectInputStream(inputStream)
        val student = objectInput.readObject() as Student
        studentList.add(student)
        inputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    while (true) {
        printRequest()
        when (sc.nextInt()) {
            0 -> break
            1 -> {
                println("=============================================")
                print("Enter first name: ")
                val firstName = readln()
                print("Enter last name: ")
                val lastName = readln()
                print("Student ID: ")
                val id = readln()
                print("Age: ")
                val age = sc.nextInt()
                print("Classroom: ")
                val classroom = readln()
                print("Address: ")
                val address = readln()
                val newStudent = Student(
                    fullName = FullName(firstName = firstName, lastName = lastName),
                    ID = id,
                    age = age,
                    classroom = classroom,
                    address = address,
                    subjectList = subjectList.toMutableList()
                )
                studentList.add(
                    newStudent
                )
                try {
                    val outputStream = FileOutputStream(file)
                    val objectOutput = ObjectOutputStream(outputStream)
                    objectOutput.writeObject(newStudent)
                    objectOutput.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            2 -> {
                println("=============================================")
                print("Enter first name: ")
                val firstName = readln()
                print("Enter last name: ")
                val lastName = readln()
                print("Lecturer ID: ")
                val id = readln()
                print("Age: ")
                val age = sc.nextInt()
                print("Department: ")
                val department = readln()
                print("Address: ")
                val address = readln()
                lecturerList.add(
                    Lecturer(
                        ID = id,
                        fullName = FullName(firstName = firstName, lastName = lastName),
                        age = age,
                        address = address,
                        department = department
                    )
                )
            }

            3 -> {
                println("Enter student Name:")
                val id = readln()
                val studentFilter = studentList.filter {
                    it.fullName.toString().contains(id, true)
                }
                if (studentFilter.isEmpty()) println("Wrong student ID") else {
                    for (i in studentFilter.indices) {
                        println("$i\t\t${studentFilter[i]}")
                    }
                }
                println("Enter numeric order of student")
                var i: Int
                do {
                    i = sc.nextInt()
                } while (i !in studentFilter.indices)
                val student = studentFilter[i]
                student.subjectList.forEach {
                    println("Enter ${it.name} score:")
                    it.finalScoreByNumber = sc.nextDouble()
                    when {
                        it.finalScoreByNumber > 9.0 -> it.finalScoreByWord = 'A'
                        it.finalScoreByNumber > 7.0 -> it.finalScoreByWord = 'B'
                        it.finalScoreByNumber > 5.0 -> it.finalScoreByWord = 'C'
                        it.finalScoreByNumber > 4.0 -> it.finalScoreByWord = 'D'
                        else -> it.finalScoreByWord = 'F'
                    }
                }
            }

            4 -> {
                studentList.sortedBy {
                    it.age
                }
                studentList.forEach {
                    println(it)
                    try {
                        val outputStream = FileOutputStream(file)
                        val objectOutput = ObjectOutputStream(outputStream)
                        objectOutput.writeObject(it)
                        objectOutput.close()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }

            5 -> {
                lecturerList.forEach {
                    println(it.toString())
                }
            }
        }
    }
}