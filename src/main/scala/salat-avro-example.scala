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

object Main extends App {


  val myName = "Julian"
  val myInt = 4
  val myBool = true 

  val myRecord = MyRecord(myName, myInt, myBool)
    Console.println("original record:" + myRecord)


/*-------------TO AND FROM AVRO DATAFILE------------------------------------------
In order to write avro files, you will need to provide the record model(in this case 'MyRecord'), a destination file, and a record. To deserialize from an avro datafile we will need to provide the record model, and an infile.
*/
  
//Serialize to an Avro DataFile
  val outfile = new File("/home/julianpeeters/output.avro")

  grater[MyRecord].serializeToDataFile(outfile, myRecord)

//Deserialize from File: Read DataFile and deserialize back to object 
  val infile = new File("/home/julianpeeters/input.avro")
  val objFromFile = grater[MyRecord].asObjectFromDataFile(infile)  
    Console.println("from Avro DataFile: " + objFromFile)
    Console.println("equals orginal?: " + (myRecord == objFromFile).toString)
}
