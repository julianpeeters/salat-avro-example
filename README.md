salat-avro-example: The stock example, single record in-memory serialization to and from a ByteArrayOutputStream
================================================================================================================

For salat-avro 'case class to avro' serialization we need to provide a record, the record Case Class, and an encoder, and a decoder and record Case Class to deserialize back into the scala object of the case class. 

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
          
          
          
###For other examples, please see each branch:###

<a href = https://github.com/julianpeeters/salat-avro-example/tree/salat>salat example</a> - The stock example, to and from DAO

<a href = https://github.com/julianpeeters/salat-avro-example/tree/single-record-in-memory>salat-avro example</a> - The stock example, single record in-memory serialization to and from a ByteArrayOutputStream 

<a href = https://github.com/julianpeeters/salat-avro-example/tree/iterator-to-and-from-avro-datafile>salat-avro example: To and from an Avro Datafile</a> - Streaming records to and from Avro Datafiles


--------------------------


#####Non-canonical examples *not currently supported* by Salat-Avro#####

To use these examples, you must use Salat-Avro version 0.0.8-archive in the Salat-Avro branch <a href = https://github.com/julianpeeters/salat-avro/tree/archiveKitchenSink>archiveKitchenSink</a>. It has the necessary methods for the following examples in `AvroGrater.scala`, which were otherwise removed from the master branch of Salat-Avro (note: no tests!).

<a href = https://github.com/julianpeeters/salat-avro-example/tree/single-record-to-and-from-avro-datafile>salat-avro example: Single Record to/from Avro Datafile</a> -- Reads and writes one record at a time, closing the file after each write. Not exactly useful, adds extra methods to `AvroGrater.scala`, and therefore was those methods were removed from my fork's 'master' branch of Salat-Avro.
 
