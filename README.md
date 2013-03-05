salat-avro-example: Streaming records to and from Avro Datafiles with `Iterator`s
=================================================================================================================

In order to stream records to an avro file that can be read by an avro datafilereader, we need to provide record model, a destination file, and a stream of records. `serializeIteratorToDataFile` appends indiscriminately.  If there is no file, a file is created.  To deserialize from file we will need to provide an infile.

      //Serialize to an Avro DataFile
        val myRecordIterator = Iterator[MyRecord](myRecord1, myRecord2, myRecord3)
        val outfile = new File("/home/julianpeeters/streamOut.avro")
        
        grater[MyRecord].serializeIteratorToDataFile(outfile, myRecordIterator)
        
      //Deserialize from File: Read DataFile and deserialize back to object 
        val streamInfile = new File("/home/julianpeeters/streamIn.avro")
        
        val sameRecordIterator = grater[MyRecord].asObjectIteratorFromDataFile(streamInfile)


###For other examples, please see each branch:###

<a href = https://github.com/julianpeeters/salat-avro-example/tree/salat>salat example</a> - The stock example, to and from DAO

<a href = https://github.com/julianpeeters/salat-avro-example/tree/single-record-in-memory>salat-avro example</a> - The stock example, single record in-memory serialization to and from a ByteArrayOutputStream 

<a href = https://github.com/julianpeeters/salat-avro-example/tree/iterator-to-and-from-avro-datafile>salat-avro example: To and from an Avro Datafile</a> - Streaming records to and from Avro Datafiles


--------------------------


#####Non-canonical examples *not currently supported* by Salat-Avro#####

To use these examples, you must use Salat-Avro version 0.0.8-archive in the Salat-Avro branch <a href = https://github.com/julianpeeters/salat-avro/tree/archiveKitchenSink>archiveKitchenSink</a>. It has the necessary methods for the following examples in `AvroGrater.scala`, which were otherwise removed from the master branch of Salat-Avro (note: no tests!).

<a href = https://github.com/julianpeeters/salat-avro-example/tree/single-record-to-and-from-avro-datafile>salat-avro example: Single Record to/from Avro Datafile</a> -- Reads and writes one record at a time, closing the file after each write. Not exactly useful, adds extra methods to `AvroGrater.scala`, and therefore was those methods were removed from my fork's 'master' branch of Salat-Avro.
 
<a href = https://github.com/julianpeeters/salat-avro-example/tree/stream-to-and-from-fileouputstream>salat-avro example: Stream Records to/from Fileoutstream</a> -- Create a `Stream` from a file by `cons`-ing recursively on a datasource. Non-canonical transport across the wire, requires extra `Stream` method in `AvroGrater.scala`, and therefore was those methods were removed from my fork's 'master' branch of Salat-Avro.

<a href = https://github.com/julianpeeters/salat-avro-example/tree/stream-to-and-from-avro-datafile>salat-avro example: Stream Records to/from Avro Datafile</a> -- Serialize/Deserialize records from `Stream[Record]` to an Avro Datafile and back again.  Immutable scala `Stream`s seem like the natural data structure for the language, but, even defined with `def`, I'm worried that de/serialization will cause memory issues. I need to test it with a large file, but since a `Stream` an always be had from an `Iterator`, and an `Iterator` seems more memory-safe, I decided to use the iterator version, removing the `Stream` methods from `AvroGrater.scala`.
