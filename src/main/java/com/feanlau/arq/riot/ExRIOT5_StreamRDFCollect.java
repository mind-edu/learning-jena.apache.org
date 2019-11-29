package com.feanlau.arq.riot;

import org.apache.jena.graph.Triple;
import org.apache.jena.riot.RDFParser;
import org.apache.jena.riot.lang.CollectorStreamBase;
import org.apache.jena.riot.lang.CollectorStreamTriples;

/**
 * Example of using RIOT for streaming RDF to be stored into a Collection.
 * <p>
 * Suitable for single-threaded parsing, for use with small data or distributed
 * computing frameworks (e.g. Hadoop) where the overhead of creating many threads
 * is significant.
 *
 * @see CollectorStreamBase
 */
public class ExRIOT5_StreamRDFCollect {

    public static void main(String... argv) {
        final String filename = "data.ttl";

        CollectorStreamTriples inputStream = new CollectorStreamTriples();
        RDFParser.source(filename).parse(inputStream);

        for (Triple triple : inputStream.getCollected()) {
            System.out.println(triple);
        }
    }

}
