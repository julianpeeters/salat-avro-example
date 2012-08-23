import models._

import com.banno.salat.avro._

//import com.banno.salat.avro.AvroGrater._
import global._
import java.io.ByteArrayOutputStream
import org.apache.avro._
import org.apache.avro.generic._
import org.apache.avro.io._

object Main extends Application {
  val greeting = "Hello World!" 
    Console.println(greeting) 
  
  val baos = new ByteArrayOutputStream
  val binaryEncoder = EncoderFactory.get().binaryEncoder(baos, null)

  val a = Record(greeting)
    Console.println(a)

  val dbo = grater[Record].serialize(a, binaryEncoder)
    Console.println(dbo)
  
 // val schema = Schema.parse(getClass().getResourceAsStream("schema_record.avsc"))
   // Console.println(schema)

  val schema: Schema = grater[Record].asAvroSchema
    Console.println(schema)

 // val gr = new GenericData.Record(schema) 
  //  Console.println(gr)

  //val gdw = new GenericDatumWriter(schema)
   // Console.println(gdw)
   // gdw.write(gr, binaryEncoder)


 // val output = gdw.write(dbo, binaryEncoder)
   // Console.println(output)

 // baos.toByteArray() //1st step for decoding?

}

