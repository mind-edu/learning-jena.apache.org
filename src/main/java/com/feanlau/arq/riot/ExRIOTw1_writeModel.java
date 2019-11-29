
package com.feanlau.arq.riot;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

/** Example writing a model with RIOT */
public class ExRIOTw1_writeModel {
    public static void main(String[] args) {
        Model model = RDFDataMgr.loadModel("D.ttl");

        System.out.println();
        System.out.println("#### ---- Write as Turtle");
        System.out.println();
        RDFDataMgr.write(System.out, model, Lang.TURTLE);

        System.out.println();
        System.out.println("#### ---- Write as Turtle (streaming)");
        System.out.println();
        RDFDataMgr.write(System.out, model, RDFFormat.TURTLE_BLOCKS);

        System.out.println();
        System.out.println("#### ---- Write as Turtle via model.write");
        System.out.println();
        model.write(System.out, "TTL");
    }

}

