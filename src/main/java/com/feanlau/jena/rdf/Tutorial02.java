package com.feanlau.jena.rdf;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

/** Tutorial 2 resources as property values
 */
public class Tutorial02 extends Object {

    public static void main (String args[]) {
        // some definitions
        String personURI    = "http://somewhere/JohnSmith";
        String givenName    = "John";
        String familyName   = "Smith";
        String fullName     = givenName + " " + familyName;

        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // create the resource
        //   and add the properties cascading style
        Resource johnSmith  = model.createResource(personURI)
                .addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.N,
                        model.createResource()
                                .addProperty(VCARD.Given, givenName)
                                .addProperty(VCARD.Family, familyName));

        // 输出结果
        model.write(System.out);
    }

}
