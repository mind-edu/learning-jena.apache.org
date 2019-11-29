
package com.feanlau.arq.riot;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.riot.RDFDataMgr;

import static org.apache.jena.riot.RDFLanguages.TRIG;

/**
 * Example of using RIOT : reading data into datasets.
 */
public class ExRIOT2_ReadDataset {
    public static void main(String... argv) {
        Dataset ds = null;

        // Read a TriG file into quad storage in-memory.
        ds = RDFDataMgr.loadDataset("data.trig");

        // read some (more) data into a dataset graph.
        RDFDataMgr.read(ds, "data2.trig");

        // Create a dataset,
        Dataset ds2 = DatasetFactory.createTxnMem();
        // read in data, indicating the syntax in case the remote end does not
        // correctly provide the HTTP content type.
        RDFDataMgr.read(ds2, "http://host/data2.unknown", TRIG);
    }
}
