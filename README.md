salat-avro-example
==================

trying to get a simple example of salat, then salat-avro to work.

working with these tools and from their examples:

<a href = http://github.com/novus/salat>salat</a>

<a href = https://github.com/T8Webware/salat-avro>salat-avro</a>

In order to stream records to an avro file that can be read by an avro datafilereader, we need to provide record model, a destination file, and a stream of records. `serializeIteratorToDataFile` appends indiscriminately.  If there is no file, a file is created.  To deserialize from file we will need to provide an infile.
