
package com.feanlau.arq.riot;

import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.riot.RDFParser;
import org.apache.jena.riot.system.ErrorHandlerFactory;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.riot.system.StreamRDFLib;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Example of using RIOT directly.
 * <p>
 * The parsers produce a stream of triples and quads so processing does not need
 * to hold everything in memory at the same time. See also {@code ExRIOT_4}
 */
public class ExRIOT3_RDFParser {
    public static void main(String... argv) throws IOException {
        // ---- Parse to a Sink.
        StreamRDF noWhere = StreamRDFLib.sinkNull();

        // --- Or create a parser and do the parsing with detailed setup.
        String baseURI = "http://example/base";

        // It is always better to use an InputStream, rather than a Java Reader.
        // The parsers will do the necessary character set conversion.  
        try (InputStream in = new FileInputStream("data.trig")) {
            RDFParser.create()
                    .source(in)
                    .lang(RDFLanguages.TRIG)
                    .errorHandler(ErrorHandlerFactory.errorHandlerStrict)
                    .base("http://example/base")
                    .parse(noWhere);
        }
    }
}
