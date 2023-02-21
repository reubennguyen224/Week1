import kotlin.math.sqrt

interface Calculator {
    fun middle(b: Point3D): Point3D
    fun distance(b: Point3D): Double
}

data class Point3D(val x: Double, val y: Double, val z: Double = 0.0)

class CalculateCoordinate(val a: Point3D) : Calculator {
    override fun middle(b: Point3D): Point3D {
        return if (a.z != 0.0 || b.z != 0.0) Point3D((a.x + b.x) / 2, (a.y + b.y) / 2, (a.z + b.z) / 2)
        else Point3D((a.x + b.x) / 2, (a.y + b.y) / 2)
    }

    override fun distance(b: Point3D): Double {
        return if (a.z != 0.0 || b.z != 0.0) sqrt(((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y) + (a.z - b.z) * (a.z - b.z)))
        else sqrt(((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)))
    }

    companion object {
        @JvmStatic
        fun distance(a: Point3D, b: Point3D): Double {
            return sqrt(((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)))
        }
    }

}

data class Vector(val x: Double, val y: Double, val z: Double = 0.0)

abstract class VectorInOxyz {
    abstract fun findDirectionVector(a: Point3D, b: Point3D): Vector
    abstract fun findNormalVector(a: Point3D, b: Point3D): Vector
    abstract fun findNormalVector(a: Point3D, b: Point3D, c: Point3D): Vector
}

class Oxy() : VectorInOxyz() {
    override fun findDirectionVector(a: Point3D, b: Point3D): Vector {
        val x = a.x - b.x
        val y = a.y - b.y
        return Vector(1.0, x / y)
    }

    override fun findNormalVector(a: Point3D, b: Point3D): Vector {
        val directVector = findDirectionVector(a, b)
        return Vector(directVector.x * -1, directVector.y)

    }

    override fun findNormalVector(a: Point3D, b: Point3D, c: Point3D): Vector {
        val directVector1 = findDirectionVector(a, b)
        val directVector2 = findDirectionVector(a, c)
        return Vector(
            directVector1.y * directVector2.z - directVector1.z * directVector2.y,
            directVector1.z * directVector2.x - directVector1.x * directVector2.z,
            directVector1.x * directVector2.y - directVector1.y * directVector2.x
        )
    }
}

fun main() {
    fun printRequest() {
        println("=========================================")
        println("Enter number to using program:")
        println("0. Exit program")
        println("1. Find middle of 2 point in 2D")
        println("2. distance between 2 point in 2D")
        println("3. distance between 2 point using companion object in 2D")
        println("4. Find middle of 2 point in 3D")
        println("5. Find center of triangle")
        println("6. Find Normal Vector of Plane")
        println("7. Find Normal Vector of line")
        println("8. Find Direct Vector of line")
    }

    var x: Double
    var y: Double
    var z: Double

    val newPoint2D: () -> Point3D = {
        x = sc.nextDouble()
        y = sc.nextDouble()
        Point3D(x, y)
    }

    val newPoint3D: () -> Point3D = {
        x = sc.nextDouble()
        y = sc.nextDouble()
        z = sc.nextDouble()
        Point3D(x, y, z)
    }

    fun CalculateCoordinate.findCenter(b: Point3D, c: Point3D): Point3D {
        return Point3D((a.x + b.x + c.x) / 3, (a.y + b.y + c.y) / 3)
    }

    while (true) {
        printRequest()
        when (sc.nextInt()) {
            0 -> break
            1 -> {
                println("Enter 2 Points:")
                print("A: ")
                val a = newPoint2D()
                print("B: ")
                val b = newPoint2D()
                val middle = CalculateCoordinate(a = a).middle(b = b)
                println("Middle point M(x = ${middle.x}, y = ${middle.y})")
            }

            2 -> {
                println("Enter 2 Points:")
                print("A: ")
                val a = newPoint2D()
                print("B: ")
                val b = newPoint2D()
                println("Distance between 2 points: ${CalculateCoordinate(a).distance(b)}")
            }

            3 -> {
                println("Enter 2 Points:")
                print("A: ")
                val a = newPoint2D()
                print("B: ")
                val b = newPoint2D()
                println("Distance between 2 points: ${CalculateCoordinate.distance(a, b)}")
            }

            4 -> {
                println("Enter 2 Points:")
                print("A: ")
                val a = newPoint3D()
                print("B: ")
                val b = newPoint3D()
                val middle = CalculateCoordinate(a = a).middle(b = b)
                println("Middle point M: $middle")
            }

            5 -> {
                println("Enter 3 points of triangle:")
                print("A: ")
                val a = newPoint2D()
                print("B: ")
                val b = newPoint2D()
                print("C: ")
                val c = newPoint2D()
                val center = CalculateCoordinate(a).findCenter(b, c)
                println("Center of triangle: G(x = ${center.x}, y = ${center.y})")
            }

            6 -> {
                println("Enter 3 points of plane:")
                print("A: ")
                val a = newPoint2D()
                print("B: ")
                val b = newPoint2D()
                print("C: ")
                val c = newPoint2D()
                println(Oxy().findNormalVector(a, b, c))
            }

            7 -> {
                println("Enter 2 Points:")
                print("A: ")
                val a = newPoint2D()
                print("B: ")
                val b = newPoint2D()
                println(Oxy().findNormalVector(a, b))
            }

            8 -> {
                println("Enter 2 Points:")
                print("A: ")
                val a = newPoint2D()
                print("B: ")
                val b = newPoint2D()
                println(Oxy().findDirectionVector(a, b))
            }
        }
    }
}