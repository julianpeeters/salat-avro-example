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
import java.io.FileInputStream
import java.io.FileOutputStream
import org.apache.avro._
import org.apache.avro.io._

object Main extends App {

  val myRecord1 = MyRecord("Tortoise", 2, true)
  val myRecord2 = MyRecord("Achilles", 4, true)
  val myRecord3 = MyRecord("Escher", 6, true)

  val myRecordStream = Stream[MyRecord](myRecord1, myRecord2, myRecord3)
    Console.println("Original Records: " + myRecordStream.toList)

/*-------------STREAM AVROS TO AND FROM FILEOUTPUTSTREAM------------------------------------------
Another example of a non-canonical way to read streams or records, in this case from a file.  Should be performant since `objectStreamFromFileStream` is defined as `def`?  Must take care to manage schema properly since there is no header to the stream
*/ 

//Serialize to a filestream
  val outfileStream = new File("/home/julianpeeters/streamOutFile.avro")
  val fos = new FileOutputStream(outfileStream)
  val encoderFile = EncoderFactory.get().binaryEncoder(fos, null)

  myRecordStream.foreach(i => grater[MyRecord].serialize(i, encoderFile))

//Deserialize from File: Read DataFileStream file and deserialize back to object
  val infileStream = new File("/home/julianpeeters/streamInFile.avro")
  val fis = new FileInputStream(infileStream)
  val decoderFile = DecoderFactory.get().binaryDecoder(fis, null)

  def objectStreamFromFileStream: Stream[MyRecord] = {
    if (decoderFile.isEnd() == true) Stream.empty
    else Stream.cons(grater[MyRecord].asObject(decoderFile), objectStreamFromFileStream) 
  }

  Console.println("All Records Correspond?: " + objectStreamFromFileStream.corresponds[MyRecord](myRecordStream)((i, j) => (i == j)))

}
