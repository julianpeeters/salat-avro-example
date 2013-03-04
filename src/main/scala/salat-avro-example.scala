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
import java.io.ByteArrayOutputStream
import org.apache.avro._
import org.apache.avro.io._
import org.apache.avro.file._

object Main extends App {


  val myName = "Hello"
  val myInt = 4
  val myBool = true 

  val myRecord = MyRecord(myName, myInt, myBool)
    Console.println("original record:" + myRecord)

/*------------------IN-MEMORY DATA SERIALIZATION------------------------
For salat-avro 'case class to avro' serialization we need to provide a record, the record Case Class, and an encoder, and a decoder and record Case Class to deserialize back into the scala object of the case class. 
*/

//Serialize to an in-memory output stream:  
  val baos = new ByteArrayOutputStream
  val binaryEncoder = EncoderFactory.get().binaryEncoder(baos, null)

  grater[MyRecord].serialize(myRecord, binaryEncoder)
    
//Deserialize back to object:
  val bytes = baos.toByteArray() 

  val decoder = DecoderFactory.get().binaryDecoder(bytes, null)

  val objFromInMemory = grater[MyRecord].asObject(decoder)
    Console.println("from memory: " + objFromInMemory)
    Console.println("equal to original?: " + (myRecord == objFromInMemory))

}
