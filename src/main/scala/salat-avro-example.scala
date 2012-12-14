/*Taking salat and avro for a test run, writing binary avros to a binary output stream in-memory, as well as writing to an avro datafilestream, and finally, writing to an avro datafile (written with an avro DataFileWriter, and to be read by avro DataFileReaders).
*/ 

import models._
import com.banno.salat.avro._
import global._
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.BufferedInputStream
import org.apache.avro._
import org.apache.avro.io._
import org.apache.avro.file._

object Main extends Application {
  val myName = "Julian" 

  val myRecord = MyRecord(myName)
    Console.println(myRecord)

/*------------------IN-MEMORY DATA SERIALIZATION------------------------
For salat-avro 'case class to avro' serialization we need to provide a record, the record Case Class, and an encoder, and a decoder and record Case Class to deserialize back into the scala object of the case class. */

//Serialize to an in-memory output stream:  
  val baos = new ByteArrayOutputStream
  val binaryEncoder = EncoderFactory.get().binaryEncoder(baos, null)

  val dbo = grater[MyRecord].serialize(myRecord, binaryEncoder)
    
//Deserialize back to object:
  val bytes = baos.toByteArray() 

  val decoder = DecoderFactory.get().binaryDecoder(bytes, null)

  val objFromInMemory = grater[MyRecord].asObject(decoder)
    Console.println(objFromInMemory)

/*-------------TO AND FROM DATAFILESTREAM------------------------------------------
Like above (to a byte[] output stream) but this time to a file input/output stream (cannot be read by a datafilereader).
*/

  val infileStream = new File("/home/julianpeeters/inputStream.avro")
  val outfileStream = new File("/home/julianpeeters/outputStream.avro")
  
  val fos = new FileOutputStream(outfileStream)
  val encoderFile = EncoderFactory.get().binaryEncoder(fos, null)
  val fis = new FileInputStream(infileStream)
  val decoderFile = DecoderFactory.get().binaryDecoder(fis, null)

//Serialize to an Avro DataFile
  val afo = grater[MyRecord].serialize(myRecord, encoderFile)

//Deserialize from File: Read DataFileStream file and deserialize back to object    
  val objFromFileStream = grater[MyRecord].asObject(decoderFile) 
    Console.println(objFromFileStream)


/*-------------TO AND FROM AVRO DATAFILE------------------------------------------
In order to write avro files we need to provide a schema  (obtained from a salat method acting on the record Case Class), a file destination path, and a record. To deserialize from file we will need to provide an infile path
*/

  val schema: Schema = grater[MyRecord].asAvroSchema
  val infile = new File("/home/julianpeeters/input.avro")
  val outfile = new File("/home/julianpeeters/output.avro")
  
//Serialize to an Avro DataFile
  val avro = grater[MyRecord].serializeToDataFile(schema, outfile, myRecord)

//Deserialize from File: Read DataFile and deserialize back to object 
  val objFromFile = grater[MyRecord].asObjectFromDataFile(infile)  
    Console.println(objFromFile)

}

