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
 * Simple example of SPARQL/Update : read a update script from a file and execute it
 */
public class UpdateReadFromFile {
    static {
        LogCtl.setCmdLogging();
    }

    public static void main(String[] args) {
        // Create an empty GraphStore (has an empty default graph and no named graphs) 
        Dataset dataset = DatasetFactory.createTxnMem();

        // ---- Read and update script in one step.
        UpdateAction.readExecute("update.ru", dataset);

        // ---- Reset.
        UpdateAction.parseExecute("DROP ALL", dataset);

        // ---- Read the update script, then execute, in separate two steps
        UpdateRequest request = UpdateFactory.read("update.ru");
        UpdateAction.execute(request, dataset);

        // Write in debug format.
        System.out.println("# Debug format");
        SSE.write(dataset);

        System.out.println();

        System.out.println("# N-Quads: S P O G");
        RDFDataMgr.write(System.out, dataset, Lang.NQUADS);
    }
}
