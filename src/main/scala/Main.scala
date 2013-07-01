import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.global._

object Main extends App {
  val greeting = "Hello World!" 
    Console.println(greeting) 

  val a = MyRecord(greeting)
    Console.println(a)

  val dbo = grater[MyRecord].asDBObject(a)
    Console.println(dbo)
}
