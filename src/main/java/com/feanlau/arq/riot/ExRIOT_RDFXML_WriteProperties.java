package com.feanlau.arq.riot;

import org.apache.jena.atlas.lib.StrUtils;
import org.apache.jena.atlas.logging.LogCtl;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.*;
import org.apache.jena.sparql.util.Context;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Example of setting properties for RDF/XML writer via RIOT
 */
public class ExRIOT_RDFXML_WriteProperties {
    static {
        LogCtl.setCmdLogging();
    }

    public static void main(String... args) {
        // Data.
        String x = StrUtils.strjoinNL
                ("PREFIX : <http://example.org/>"
                        , ":s :p :o ."
                );
        Model model = ModelFactory.createDefaultModel();
        RDFDataMgr.read(model, new StringReader(x), null, Lang.TURTLE);

        // Write, default settings.
        writePlain(model);
        System.out.println();

        // Write, with properties 
        writeProperties(model);
    }

    /**
     * Write in plain, not pretty ("abbrev") format.
     */
    private static void writePlain(Model model) {
        System.out.println("**** RDFXML_PLAIN");
        RDFDataMgr.write(System.out, model, RDFFormat.RDFXML_PLAIN);
        System.out.println();
    }

    /**
     * Write with properties
     */
    public static void writeProperties(Model model) {
        System.out.println("**** RDFXML_PLAIN+properties");
        System.out.println("**** Adds XML declaration");

        // Properties to be set.
        // See https://jena.apache.org/documentation/io/rdfxml_howto.html#advanced-rdfxml-output
        // for details of properties.
        Map<String, Object> properties = new HashMap<>();
        properties.put("showXmlDeclaration", "true");

        // Put a properties object into the Context.
        Context cxt = new Context();
        cxt.set(SysRIOT.sysRdfWriterProperties, properties);

        RDFWriter.create()
                .base("http://example.org/")
                .format(RDFFormat.RDFXML_ABBREV)
                .context(cxt)
                .source(model)
                .output(System.out);
    }
}
