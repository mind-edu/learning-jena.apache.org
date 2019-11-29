package com.feanlau.arq;


// The ARQ application API.

import org.apache.jena.atlas.io.IndentedWriter;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.DC;

/**
 * Example 1 : Execute a simple SELECT query on a model
 * to find the DC titles contained in a model.
 */

public class ExQuerySelect1 {
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

        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            // Or QueryExecutionFactory.create(queryString, model) ;

            System.out.println("Titles: ");

            // Assumption: it's a SELECT query.
            ResultSet rs = qexec.execSelect();

            // The order of results is undefined. 
            for (; rs.hasNext(); ) {
                QuerySolution rb = rs.nextSolution();

                // Get title - variable names do not include the '?' (or '$')
                RDFNode x = rb.get("title");

                // Check the type of the result value
                if (x.isLiteral()) {
                    Literal titleStr = (Literal) x;
                    System.out.println("    " + titleStr);
                } else
                    System.out.println("Strange - not a literal: " + x);

            }
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
