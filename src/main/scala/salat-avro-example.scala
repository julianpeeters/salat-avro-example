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

import models._
import com.banno.salat.avro._
import global._
import java.io.File
import org.apache.avro._
import org.apache.avro.io._
import org.apache.avro.file._
import org.apache.avro.generic._

object Main extends App {

  val myRecord1 = MyRecord("Tortoise", 2, true)
  val myRecord2 = MyRecord("Achilles", 4, true)
  val myRecord3 = MyRecord("Escher", 6, true)

  val myRecordStream = Stream[MyRecord](myRecord1, myRecord2, myRecord3)
    Console.println("Original Records: " + myRecordStream.toList)

/*-------------TO AND FROM AVRO DATAFILE------------------------------------------
In order to stream records to an avro file that can be read by an avro datafilereader, we need to provide record model, a schema  (obtained from a salat method acting on the record model), a destination file, and a stream of records. To deserialize from file we will need to provide an infile, and use the grater's DataFileReader and DatumReader.
*/

//Serialize to an Avro DataFile
  val schema: Schema = grater[MyRecord].asAvroSchema
  val outfile = new File("/home/julianpeeters/streamOut.avro")
  val avroDataFileWriter = grater[MyRecord].asDataFileWriter

  avroDataFileWriter.create(schema, outfile)  
  myRecordStream.foreach (i => avroDataFileWriter.append(i))
  avroDataFileWriter.close

//Deserialize from File: Read DataFile and deserialize back to object 
  val streamInfile = new File("/home/julianpeeters/streamIn.avro")
  val avroDataFileReader = grater[MyRecord].asDataFileReader(streamInfile)
  val avroDatumReader = grater[MyRecord].asGenericDatumReader

  def objStreamFromFile: Stream[models.MyRecord] = {
    if (avroDataFileReader.hasNext !=true) Stream.empty
    else Stream.cons(avroDatumReader.applyValues((avroDataFileReader.next().asInstanceOf[GenericData.Record])).asInstanceOf[models.MyRecord], objStreamFromFile)
  }

  Console.println("All Records From Avro Data File Correspond To The Originals?: " + objStreamFromFile.corresponds[MyRecord](myRecordStream)((i, j) => (i == j)))
}
