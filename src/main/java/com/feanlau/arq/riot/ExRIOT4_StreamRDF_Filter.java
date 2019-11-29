package com.feanlau.arq.riot;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.riot.RDFParser;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.riot.system.StreamRDFLib;
import org.apache.jena.riot.system.StreamRDFWrapper;
import org.apache.jena.sparql.vocabulary.FOAF;

/**
 * Example of using RIOT : extract only certain properties from a parser run
 */
public class ExRIOT4_StreamRDF_Filter {
    public static void main(String... argv) {
        String filename = "data.ttl";

        // Write a stream out. 
        StreamRDF output = StreamRDFLib.writer(System.out);

        // Wrap in a filter.
        StreamRDF filtered = new FilterSinkRDF(output, FOAF.name, FOAF.knows);

        // Call the parsing process.
        RDFParser.source(filename).parse(filtered);
    }

    // The filtering StreamRDF
    static class FilterSinkRDF extends StreamRDFWrapper {
        private final Node[] properties;

        FilterSinkRDF(StreamRDF dest, Property... properties) {
            super(dest);
            this.properties = new Node[properties.length];
            for (int i = 0; i < properties.length; i++)
                this.properties[i] = properties[i].asNode();
        }

        @Override
        public void triple(Triple triple) {
            for (Node p : properties) {
                if (triple.getPredicate().equals(p))
                    super.triple(triple);
            }
        }
    }
}
