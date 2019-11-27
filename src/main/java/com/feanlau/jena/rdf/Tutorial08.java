package com.feanlau.jena.rdf;

import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;

import java.io.InputStream;


/**
 * Tutorial 8 - demonstrate Selector methods
 */
public class Tutorial08 extends Object {

    static final String inputFileName = "vc-db-1.rdf";

    public static void main(String args[]) {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // use the FileManager to find the input file
        InputStream in = FileManager.get().open(inputFileName);
        if (in == null) {
            throw new IllegalArgumentException("File: " + inputFileName + " not found");
        }

        // read the RDF/XML file
        model.read(in, "");

        // select all the resources with a VCARD.FN property
        // whose value ends with "Smith"
        StmtIterator iter = model.listStatements(
                new
                        SimpleSelector(null, VCARD.FN, (RDFNode) null) {
                            @Override
                            public boolean selects(Statement s) {
                                return s.getString().endsWith("Smith");
                            }
                        });
        if (iter.hasNext()) {
            System.out.println("The database contains vcards for:");
            while (iter.hasNext()) {
                System.out.println("  " + iter.nextStatement()
                        .getString());
            }
        } else {
            System.out.println("No Smith's were found in the database");
        }
    }
}