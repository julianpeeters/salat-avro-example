//Taking salat and avro for a test run, writing binary avros to a binary output stream like in the example, 
//as well as writing to an avro file 

import models._
import com.banno.salat.avro._
import global._
import java.io.ByteArrayOutputStream
import java.io.File
import org.apache.avro._
import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.io._
import org.apache.avro.file._

object Main extends Application {
  val name = "Julian" 
    Console.println(name) 

  val record = Record(name)
    Console.println(record)

//------------------IN-MEMORY DATA SERIALIZATION------------------------
//for salat-avro we need to provide a record, and an encoder in order to serialize,
//and just a decoder to deserialize
  
  val baos = new ByteArrayOutputStream
  val binaryEncoder = EncoderFactory.get().binaryEncoder(baos, null)

//Serialize to an in-memory output stream:
  val dbo = grater[Record].serialize(record, binaryEncoder)
    Console.println(dbo)

//Read back to object:
  val bytes = baos.toByteArray() 
  val decoder = DecoderFactory.get().binaryDecoder(bytes, null)

  val obj1 = grater[Record].asObject(decoder)
    Console.println(obj1)

//-------------TO AND FROM AVRO DATAFILE------------------------------------------
//for sala-avro, we need to provide a schema, a file destination path, and
// a record. To deserialize from file we will need to provide an infile path

  val schema: Schema = grater[Record].asAvroSchema
    Console.println(schema)

  val infile = new File("/home/julianpeeters/input.avro")
  val outfile = new File("/home/julianpeeters/output.avro")

//Serialize to an Avro DataFile
  val avro = grater[Record].serializeToDataFile(schema, outfile, record)
      Console.println(avro)

//Deserialize from File: Read DataFile and deserialize back to object 
    val obj2 = grater[Record].asObjectFromDataFile(infile)
      Console.println(obj2)
}

