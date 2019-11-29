package com.feanlau.arq.update;

import org.apache.jena.atlas.logging.LogCtl;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.modify.request.Target;
import org.apache.jena.sparql.modify.request.UpdateCreate;
import org.apache.jena.sparql.modify.request.UpdateDrop;
import org.apache.jena.sparql.modify.request.UpdateLoad;
import org.apache.jena.sparql.sse.SSE;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;

/**
 * Build an update request up out of individual Update objects, not by parsing.
 * This is quite low-level.
 * See UpdateExecuteOperations for ways to build the request up from strings.
 * These two approaches can be mixed.
 */

public class UpdateProgrammatic {
    static {
        LogCtl.setCmdLogging();
    }

    public static void main(String[] args) {
        Dataset dataset = DatasetFactory.createTxnMem();

        UpdateRequest request = UpdateFactory.create();

        request.add(new UpdateDrop(Target.ALL));
        request.add(new UpdateCreate("http://example/g2"));
        request.add(new UpdateLoad("file:etc/update-data.ttl", "http://example/g2"));
        UpdateAction.execute(request, dataset);

        System.out.println("# Debug format");
        SSE.write(dataset);

        System.out.println();

        System.out.println("# N-Quads: S P O G");
        RDFDataMgr.write(System.out, dataset, Lang.NQUADS);
    }
}
