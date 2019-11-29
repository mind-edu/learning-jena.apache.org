package com.feanlau.jena.rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

/**
 * Tutorial 1 creating a simple model
 */

public class Tutorial01 extends Object {
    // some definitions
    static String personURI = "http://somewhere/JohnSmith";
    static String fullName = "John Smith";

    public static void main(String args[]) {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // create the resource
        Resource johnSmith = model.createResource(personURI);

        // add the property
        johnSmith.addProperty(VCARD.FN, fullName);

        // 输出结果
        model.write(System.out);

    }
}
