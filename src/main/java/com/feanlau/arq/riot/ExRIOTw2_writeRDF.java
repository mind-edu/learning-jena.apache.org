
package com.feanlau.arq.riot;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

/** Other writer examples */
public class ExRIOTw2_writeRDF {
    public static void main(String[] args) {
        Model model = RDFDataMgr.loadModel("D.ttl");
        System.out.println();
        System.out.println("#### ---- Write as TriG");
        System.out.println();
        // This will be the default graph of the dataset written.
        RDFDataMgr.write(System.out, model, Lang.TRIG);

        // Loading Turtle as Trig reads into the default graph.
        Dataset dataset = RDFDataMgr.loadDataset("D.ttl");
        System.out.println();
        System.out.println("#### ---- Write as NQuads");
        System.out.println();
        RDFDataMgr.write(System.out, dataset, Lang.NQUADS);
    }

}

