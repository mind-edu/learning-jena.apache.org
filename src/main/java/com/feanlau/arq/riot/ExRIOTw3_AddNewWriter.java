package com.feanlau.arq.riot;

import org.apache.jena.atlas.io.IndentedWriter;
import org.apache.jena.atlas.logging.LogCtl;
import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.*;
import org.apache.jena.riot.adapters.RDFWriterRIOT;
import org.apache.jena.riot.system.PrefixMap;
import org.apache.jena.riot.system.RiotLib;
import org.apache.jena.riot.writer.WriterGraphRIOTBase;
import org.apache.jena.sparql.sse.SSE;
import org.apache.jena.sparql.util.Context;

import java.io.OutputStream;
import java.io.Writer;

/** Example of registering a new writer with RIOT */
public class ExRIOTw3_AddNewWriter {
    static {
        LogCtl.setCmdLogging();
    }

    // See also ExRIOT6_AddNewLang
    public static void main(String[] args) {
        System.out.println("## Example of a registering a new language with RIOT for writing");
        System.out.println();

        // Register the language
        Lang lang = LangBuilder.create("SSE", "text/x-sse").addFileExtensions("rsse").build();
        RDFLanguages.register(lang);

        // Create format and register the association of language and default format.
        // We are creating only one format here but in geenral theer can be variants
        // (e.g. TURTLE - pretty printed or streamed) 
        RDFFormat format = new RDFFormat(lang);
        RDFWriterRegistry.register(lang, format);

        // Register the writer factory
        RDFWriterRegistry.register(format, new SSEWriterFactory());

        // ---- Use the register writer
        Model model = RDFDataMgr.loadModel("/home/afs/tmp/D.ttl");
        // Write
        System.out.println("## Write by format");
        RDFDataMgr.write(System.out, model, format);
        System.out.println();
        System.out.println("## Write by language");
        RDFDataMgr.write(System.out, model, lang);

        // ---- Or use Model.write
        System.out.println("## Write by Model.write");
        model.write(System.out, "SSE");
    }

    static class SSEWriterFactory implements WriterGraphRIOTFactory {
        @Override
        public WriterGraphRIOT create(RDFFormat syntaxForm) {
            return new SSEWriter();
        }
    }

    static class SSEWriter extends WriterGraphRIOTBase {
        // Ignore externally provided prefix map and baseURI
        @Override
        public void write(OutputStream out, Graph graph, PrefixMap prefixMap, String baseURI, Context context) {
            SSE.write(out, graph);
        }

        @Override
        public Lang getLang() {
            return RDFLanguages.contentTypeToLang("text/x-sse");
        }

        @Override
        public void write(Writer out, Graph graph, PrefixMap prefixMap, String baseURI, Context context) {
            // Writers are discouraged : just hope the charset is UTF-8.
            IndentedWriter x = RiotLib.create(out);
            SSE.write(x, graph);
        }
    }

    // Model.write adapter - must be public.
    public static class RDFWriterSSE extends RDFWriterRIOT {
        public RDFWriterSSE() {
            super("SSE");
        }
    }
}

