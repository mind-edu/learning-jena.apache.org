package com.feanlau.jena.rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;

import java.io.InputStream;

/**
 * Tutorial 7 - selecting the VCARD resources
 */
public class Tutorial07 extends Object {

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
        ResIterator iter = model.listResourcesWithProperty(VCARD.FN);
        if (iter.hasNext()) {
            System.out.println("The database contains vcards for:");
            while (iter.hasNext()) {
                System.out.println("  " + iter.nextResource()
                        .getRequiredProperty(VCARD.FN)
                        .getString());
            }
        } else {
            System.out.println("No vcards were found in the database");
        }
    }
}