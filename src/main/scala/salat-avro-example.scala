/*Taking salat and avro for a test run, and adding , writing binary avros to a binary output stream in-memory, as well as writing to an avro datafilestream, and finally, writing to an avro datafile (written with an avro DataFileWriter, and to be read by avro DataFileReaders). -- Julian Peeters Dec. 2012
*/

/*
 * Copyright 2011 T8 Webware
 *   
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import Stream.cons
import models._
import com.banno.salat.avro._
import global._
import java.io.ByteArrayOutputStream
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.BufferedInputStream
import org.apache.avro._
import org.apache.avro.io._
import org.apache.avro.file._
import scala.util._

object Main extends App {

  val myRecord1 = MyRecord("Tortoise", 2, true)
  val myRecord2 = MyRecord("Achilles", 4, true)
  val myRecord3 = MyRecord("Escher", 6, true)

  val myRecords = Stream[MyRecord](myRecord1, myRecord2, myRecord3)
    Console.println("original records:" + myRecords.print)

/*------------------IN-MEMORY DATA SERIALIZATION------------------------
For salat-avro 'case class to avro' serialization we need to provide a record, the record Case Class, and an encoder, and a decoder and record Case Class to deserialize back into the scala object of the case class. 
*/

//Serialize to an in-memory output stream:  
  val baos = new ByteArrayOutputStream
  val binaryEncoder = EncoderFactory.get().binaryEncoder(baos, null)
  myRecords.foreach(i => grater[MyRecord].serialize(i, binaryEncoder))
    //Console.println("Serialized Records into a BAOS: " + baos)

//Deserialize back to object:
  val bytes = baos.toByteArray()
  val decoder = DecoderFactory.get().binaryDecoder(bytes, null)

  def objStream: Stream[MyRecord] = {            
    if (decoder.isEnd() == true) Stream.empty
    else cons(grater[MyRecord].asObject(decoder), objStream)  //The salat-avro grater is here in the stream def
  }                                                            

  Console.println("All Elements Correspond?: " + objStream.corresponds[MyRecord](myRecords)((i, j) => (i == j)))


/*-------------TO AND FROM DATAFILESTREAM------------------------------------------
Like above (to a byte[] output stream) but this time to a file input/output stream (cannot be read by a datafilereader).
*/
/*
//Serialize to a filestream
  val outfileStream = new File("/home/julianpeeters/output.stream")
  val fos = new FileOutputStream(outfileStream)
  val encoderFile = EncoderFactory.get().binaryEncoder(fos, null)
  val afo = grater[MyRecord].serialize(myRecord, encoderFile)


//Deserialize from File: Read DataFileStream file and deserialize back to object
  val infileStream = new File("/home/julianpeeters/input.stream")
  val fis = new FileInputStream(infileStream)
  val decoderFile = DecoderFactory.get().binaryDecoder(fis, null)

  val objFromFileStream = grater[MyRecord].asObject(decoderFile) 
    Console.println("from FileStream: " + objFromFileStream)
    Console.println("equal to the original?: " + (myRecord == objFromFileStream).toString)


/*-------------TO AND FROM AVRO DATAFILE------------------------------------------
In order to write avro files we need to provide a schema  (obtained from a salat method acting on the record Case Class), a file destination path, and a record. To deserialize from file we will need to provide an infile path
*/
  
//Serialize to an Avro DataFile
  val schema: Schema = grater[MyRecord].asAvroSchema
  val outfile = new File("/home/julianpeeters/output.avro")
  val avro = grater[MyRecord].serializeToDataFile(schema, outfile, myRecord)

//Deserialize from File: Read DataFile and deserialize back to object 
  val infile = new File("/home/julianpeeters/input.avro")
  val objFromFile = grater[MyRecord].asObjectFromDataFile(infile)  
    Console.println("from Avro DataFile: " + objFromFile)
    Console.println("equals orginal?: " + (myRecord == objFromFile).toString)
*/
}
