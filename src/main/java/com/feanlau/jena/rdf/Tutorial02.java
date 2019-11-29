package com.feanlau.jena.rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

/**
 * Tutorial 2 resources as property values
 */
public class Tutorial02 extends Object {

    public static void main(String args[]) {
        // 一些定义
        String personURI = "http://somewhere/JohnSmith";
        String givenName = "John";
        String familyName = "Smith";
        String fullName = givenName + " " + familyName;

        // 创建一个模型
        Model model = ModelFactory.createDefaultModel();

        // 创建资源
        // 增加属性
        Resource johnSmith = model.createResource(personURI)
                .addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.N, model.createResource()
                        .addProperty(VCARD.Given, givenName)
                        .addProperty(VCARD.Family, familyName));

        // 输出结果
        model.write(System.out);
    }

}
