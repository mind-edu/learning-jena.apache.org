
package com.feanlau.arq.riot;

import org.apache.jena.atlas.lib.StrUtils;
import org.apache.jena.atlas.logging.LogCtl;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFParser;
import org.apache.jena.riot.SysRIOT;
import org.apache.jena.sparql.util.Context;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Set proeprties of the RDF/XML parser (ARP)
 */
public class ExRIOT_RDFXML_ReaderProperties {
    static {
        LogCtl.setCmdLogging();
    }

    public static void main(String[] args) {
        // Inline illustrative data.
        String data = StrUtils.strjoinNL
                ("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                        , "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\""
                        , "         xmlns:ex=\"http://examples.org/\">"
                        // This rdf:ID startswith a digit which normal causes a warning.
                        , "  <ex:Type rdf:ID='012345'></ex:Type>"
                        , "</rdf:RDF>"
                );
        System.out.println(data);
        System.out.println();
        // Properties to be set.
        // This is a map propertyName->value 
        Map<String, Object> properties = new HashMap<>();
        // See class ARPErrorNumbers for the possible ARP properies.
        properties.put("WARN_BAD_NAME", "EM_IGNORE");

        // Put a properties object into the Context.
        Context cxt = new Context();
        cxt.set(SysRIOT.sysRdfReaderProperties, properties);

        Model model = ModelFactory.createDefaultModel();
        // Build and run a parser
        RDFParser.create()
                .lang(Lang.RDFXML)
                .source(new StringReader(data))
                .context(cxt)
                .parse(model);
        System.out.println("== Parsed data output in Turtle");
        RDFDataMgr.write(System.out, model, Lang.TURTLE);
    }
}
