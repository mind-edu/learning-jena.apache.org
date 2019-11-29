package com.feanlau.arq;

import org.apache.jena.query.*;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

public class ExampleDBpedia1 {
    static public void main(String... argv) {
        String queryStr = "select distinct ?Concept where {[] a ?Concept} LIMIT 10";
        Query query = QueryFactory.create(queryStr);

        // Remote execution.
        try (QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query)) {
            // Set the DBpedia specific timeout.
            ((QueryEngineHTTP) qexec).addParam("timeout", "10000");

            // Execute.
            ResultSet rs = qexec.execSelect();
            ResultSetFormatter.out(System.out, rs, query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
