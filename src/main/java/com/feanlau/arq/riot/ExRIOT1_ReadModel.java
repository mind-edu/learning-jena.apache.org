
package com.feanlau.arq.riot;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFLanguages;

/**
 * Example of using RIOT with Jena readers.
 * An application can use model.read or RDFDataMgr.
 */
public class ExRIOT1_ReadModel {
    public static void main(String... argv) {
        Model m = ModelFactory.createDefaultModel();
        // read into the model.
        m.read("data.ttl");

        // Alternatively, use the RDFDataMgr, which reads from the web,
        // with content negotiation.  Plain names are assumed to be 
        // local files where file extension indicates the syntax.  

        Model m2 = RDFDataMgr.loadModel("data.ttl");

        // read in more data, the remote server serves up the data
        // with the right MIME type.
        RDFDataMgr.read(m2, "http://host/some-published-data");


        // Read some data but also give a hint for the synatx if it is not
        // discovered by inspecting the file or by HTTP content negotiation.  
        RDFDataMgr.read(m2, "some-more-data.out", RDFLanguages.TURTLE);
    }
}
