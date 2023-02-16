import java.lang.NumberFormatException

class Accessor{
    private var _name : String? = null
    var name: String
        get() {
            return _name ?: throw AssertionError("Null name")
        }
        set(value) {
            _name = value
        }

    var age = 0
        set(value) {
            if (value < 0){
                println("Sai định dạng tuổi")
            } else field = value
        }
}
class Foo{
    lateinit var value: String
    fun init(){
        if (!this::value.isInitialized){
            println("Not init yet!")
            value = "Full"
        } else {
            println("Already init!")
        }
    }

}
fun header(){
    println("\n===============================================")
    println("Nhập số tương ứng để chạy chương trình")
    println("0. Dừng chương trình")
    println("1. Khởi tạo lazy")
    println("2. Custom getter/setter")
    println("3. Khởi tạo lateinit")
} //function top-level using print header only
fun main() {
    fun customAccessor(){
        println("\n===============================================")
        val accessor = Accessor()

        print("Nhập tên: ")
        var input: String = readln()
        if (input.isEmpty()) accessor.name = "null"
        else accessor.name = input
        print("Nhập tuổi (bằng số): ")
        input = readln()
        if (input.isEmpty()) accessor.age = 0
        else accessor.age = try {
            input.toInt()
        } catch (e: Exception){
            e.printStackTrace()
            0
        }

        println("Tên: ${accessor.name} \nTuổi: ${accessor.age}")
    }

    fun lazyInit(){
        println("\n===============================================")
        val y: Int by lazy{
            println("Running lambda")
            10
        }
        for (i in 1..3){
            println("$i calling: ")
            println(y)
        }
    }
    fun lateInit(){
        println("\n===============================================")
        val foo = Foo()
        foo.init()
        foo.init()
    }

    header()
    var k : String = readln()
    do {
        when (k.toInt()){
            1 -> lazyInit()
            2 -> customAccessor()
            3 -> lateInit()
            0 -> break
        }
        header()
        k = readln()
    } while (true)

}