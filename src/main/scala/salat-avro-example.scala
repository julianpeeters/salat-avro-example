/*
 * Copyright 2013 Julian Peeters
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

  val myRecordIterator = Iterator[MyRecord](myRecord1, myRecord2, myRecord3)
/*
In this example, we'll need to show that both pre-serialized and deserialized records are equal.  But normally the iterator is consumed upon serialation, leaving us nothing to compare to the deserialized result.  To solve this, we'll make a copy of the iterator, serializing myRecordIterator1 and leaving myRecordIterator2 for use in comparison.  A `Stream` can always be had from an `Iterator` if immutability is desired, at the possible risk of memory issues for large files(?).
*/

  val(myRecordIterator1, myRecordIterator2) = myRecordIterator.duplicate

/*-------------STREAM RECORDS TO AND FROM AVRO DATAFILE------------------------------------------
In order to stream records to an avro file that can be read by an avro datafilereader, we need to provide record model, a destination file, and a stream of records. `serializeIteratorToDataFile` appends indiscriminately.  If there is no file, a file is created.  To deserialize from file we will need to provide an infile.
*/

//Serialize to an Avro DataFile
  val outfile = new File("/home/julianpeeters/streamOut1.avro")
  grater[MyRecord].serializeIteratorToDataFile(outfile, myRecordIterator1)

//Deserialize from File: Read DataFile and deserialize back to object 
  val streamInfile = new File("/home/julianpeeters/streamIn.avro")
  val sameRecordIterator = grater[MyRecord].asObjectIteratorFromDataFile(streamInfile)

//Verify Records are Equal
  println("All Records From Avro Data File Same As The Originals?: " + (sameRecordIterator sameElements myRecordIterator2))
}
