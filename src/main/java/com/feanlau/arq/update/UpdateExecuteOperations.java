package com.feanlau.arq.update;

import org.apache.jena.atlas.logging.LogCtl;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.sse.SSE;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;

/**
 * Build an update request up out of individual Updates specified as strings.
 * See UpdateProgrammatic for another way to build up a request.
 * These two approaches can be mixed.
 */

public class UpdateExecuteOperations {
    static {
        LogCtl.setCmdLogging();
    }

    public static void main(String[] args) {
        // Create an empty DatasetGraph (has an empty default graph and no named graphs) 
        Dataset dataset = DatasetFactory.createTxnMem();

        ex1(dataset);
        ex2(dataset);
        ex3(dataset);
    }

    public static void ex1(Dataset dataset) {
        // Execute one operation.
        UpdateAction.parseExecute("LOAD <file:etc/update-data.ttl>", dataset);
    }

    public static void ex2(Dataset dataset) {
        // Execute a series of operations at once.
        // See ex3 for a better way to build up a request
        // For maximum portability, multiple operations should be separated by a ";".
        // The "\n" imporves readability and parser error messages.
        String cmd = String.join(" ;\n",
                "DROP ALL",
                "CREATE GRAPH <http://example/g2>",   // Not needed for most datasets
                "LOAD <file:etc/update-data.ttl> INTO GRAPH <http://example/g2>");
        // check string created
        System.out.println(cmd);
        UpdateAction.parseExecute(cmd, dataset);
    }

    public static void ex3(Dataset dataset) {
        // Build up the request then execute it.
        // This is the preferred way for complex sequences of operations. 
        UpdateRequest request = UpdateFactory.create();
        request.add("DROP ALL")
                .add("CREATE GRAPH <http://example/g2>");
        // Different style.
        // Equivalent to request.add("...")
        UpdateFactory.parse(request, "LOAD <file:etc/update-data.ttl> INTO GRAPH <http://example/g2>");

        // And perform the operations.
        UpdateAction.execute(request, dataset);

        System.out.println("# Debug format");
        SSE.write(dataset);

        System.out.println();

        System.out.println("# N-Quads: S P O G");
        RDFDataMgr.write(System.out, dataset, Lang.NQUADS);
    }
}
