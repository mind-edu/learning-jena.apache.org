package com.feanlau.arq;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;

public class ExampleDBpedia2 {
    static public void main(String... argv) {
        String queryString =
                "SELECT * WHERE { " +
                        "    SERVICE <http://dbpedia-live.openlinksw.com/sparql?timeout=2000> { " +
                        "        SELECT DISTINCT ?company where {?company a <http://dbpedia.org/ontology/Company>} LIMIT 20" +
                        "    }" +
                        "}";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, ModelFactory.createDefaultModel())) {
            ResultSet rs = qexec.execSelect();
            ResultSetFormatter.out(System.out, rs, query);
        }
    }
}
