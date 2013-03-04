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
import java.io.BufferedInputStream///////////////////////////////////////////////////////////////////TODO take this out?
import org.apache.avro._
import org.apache.avro.io._
import org.apache.avro.file._
import scala.util._                                                                      //TODO take this out? 

object Main extends App {

  val myRecord1 = MyRecord("Tortoise", 2, true)
  val myRecord2 = MyRecord("Achilles", 4, true)
  val myRecord3 = MyRecord("Escher", 6, true)

  val myRecordStream = Stream[MyRecord](myRecord1, myRecord2, myRecord3)
    Console.println("Original Records: " + myRecordStream.toList)

/*------------------IN-MEMORY DATA SERIALIZATION------------------------
For salat-avro 'case class to avro' serialization we need to provide a record, the record Case Class, and an encoder, and a decoder and record Case Class to deserialize back into the scala object of the case class. 
*/
/*
//Serialize to an in-memory output stream:  
  val baos = new ByteArrayOutputStream
  val binaryEncoder = EncoderFactory.get().binaryEncoder(baos, null)
  myRecordStream.foreach(i => grater[MyRecord].serialize(i, binaryEncoder))
    //Console.println("Serialized Records into a BAOS: " + baos)

//Deserialize back to object:
  val bytes = baos.toByteArray()
  val decoder = DecoderFactory.get().binaryDecoder(bytes, null)

  def objStreamFromInMemory: Stream[MyRecord] = {      // Define as a def so as to not eat memory by holding onto the head      
    if (decoder.isEnd() == true) Stream.empty
    else cons(grater[MyRecord].asObject(decoder), objStreamFromInMemory)  //The salat-avro grater is here in the stream def
  }

  Console.println("All Records Correspond?: " + objStreamFromInMemory.corresponds[MyRecord](myRecordStream)((i, j) => (i == j)))
*/
/*
/*-------------TO AND FROM DATAFILESTREAM------------------------------------------
Like above (to a byte[] output stream) but this time to a file input/output stream (cannot be read by a datafilereader).
*/ TODO I think I may have broken this... needs testing by making a new file... or acutally learning specs2 maybe?

//Serialize to a filestream
  val outfileStream = new File("/home/julianpeeters/output.stream")
  val fos = new FileOutputStream(outfileStream)
  val encoderFile = EncoderFactory.get().binaryEncoder(fos, null)

  myRecordStream.foreach(i => grater[MyRecord].serialize(i, encoderFile))


//Deserialize from File: Read DataFileStream file and deserialize back to object
  val infileStream = new File("/home/julianpeeters/input.stream")
  val fis = new FileInputStream(infileStream)
  val decoderFile = DecoderFactory.get().binaryDecoder(fis, null)

  def objStreamFromFileStream: Stream[MyRecord] = {
    if (decoderFile.isEnd() == true) Stream.empty
    else cons(grater[MyRecord].asObject(decoderFile), objStreamFromFileStream) 
  }
  Console.println("All Records Correspond?: " + objStreamFromFileStream.corresponds[MyRecord](myRecords)((i, j) => (i == j)))

*/


/*-------------TO AND FROM AVRO DATAFILE------------------------------------------
In order to write avro files that can be read by an avro datafilereader, we need to provide a schema  (obtained from a salat method acting on the record Case Class), a file destination path, and a record. To deserialize from file we will need to provide an infile path
*/

//Serialize to an Avro DataFile
  val schema: Schema = grater[MyRecord].asAvroSchema
  val outfile = new File("/home/julianpeeters/streamOut.avro")

  def writer = myRecordStream.foreach(i => grater[MyRecord].serializeToDataFile(schema, outfile, i))
    Console.println("writer: " + writer)
  


/*  
//Serialize to an Avro DataFile
  val schema: Schema = grater[MyRecord].asAvroSchema
  val outfile = new File("/home/julianpeeters/streamOutput.avro")

  grater[MyRecord].serializeToDataFile(schema, outfile, myRecords)

//Deserialize from File: Read DataFile and deserialize back to object 
  val streamInfile = new File("/home/julianpeeters/streamIn.avro")
  val objFromFile = grater[MyRecord].asObjectFromDataFile(streamInfile)  
    Console.println("from Avro DataFile: " + objFromFile)
    Console.println("equals orginal?: " + (myRecord == objFromFile).toString)
*/
}
