package com.feanlau.arq;


// The ARQ application API.

import org.apache.jena.atlas.io.IndentedWriter;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DC;

/**
 * Example 2 : Execute a simple SELECT query on a model
 * to find the DC titles contained in a model.
 * Show how to print results twice.
 */

public class ExQuerySelect2 {
    static public final String NL = System.getProperty("line.separator");

    public static void main(String[] args) {
        // Create the data.
        // This wil be the background (unnamed) graph in the dataset.
        Model model = createModel();

        // First part or the query string 
        String prolog = "PREFIX dc: <" + DC.getURI() + ">";

        // Query string.
        String queryString = prolog + NL +
                "SELECT ?title WHERE {?x dc:title ?title}";

        Query query = QueryFactory.create(queryString);
        // Print with line numbers
        query.serialize(new IndentedWriter(System.out, true));
        System.out.println();

        // Create a single execution of this query, apply to a model
        // which is wrapped up as a Dataset

        // Or QueryExecutionFactory.create(queryString, model) ;        
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            // A ResultSet is an iterator - any query solutions returned by .next()
            // are not accessible again.
            // Create a ResultSetRewindable that can be reset to the beginning.
            // Do before first use.

            ResultSetRewindable rewindable = ResultSetFactory.makeRewindable(qexec.execSelect());
            ResultSetFormatter.out(rewindable);
            rewindable.reset();
            ResultSetFormatter.out(rewindable);
        }
    }

    public static Model createModel() {
        Model m = ModelFactory.createDefaultModel();

        Resource r1 = m.createResource("http://example.org/book#1");
        Resource r2 = m.createResource("http://example.org/book#2");

        r1.addProperty(DC.title, "SPARQL - the book")
                .addProperty(DC.description, "A book about SPARQL");

        r2.addProperty(DC.title, "Advanced techniques for SPARQL");

        return m;
    }
}
