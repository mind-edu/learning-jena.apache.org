package com.feanlau.arq;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;
import org.apache.jena.sparql.engine.QueryIterator;
import org.apache.jena.sparql.engine.binding.Binding;

/**
 * Simple example to show parsing a query and producing the
 * SPARQL algebra expression for the query.
 */
public class AlgebraEx {
    public static void main(String[] args) {
        String s = "SELECT DISTINCT ?s { ?s ?p ?o }";

        // Parse
        Query query = QueryFactory.create(s);
        System.out.println(query);

        // Generate algebra
        Op op = Algebra.compile(query);
        op = Algebra.optimize(op);
        System.out.println(op);

        // Execute it.
        QueryIterator qIter = Algebra.exec(op, ExQuerySelect1.createModel());

        // Results
        for (; qIter.hasNext(); ) {
            Binding b = qIter.nextBinding();
            System.out.println(b);
        }
        qIter.close();
    }
}
