package com.feanlau.sparql;



import org.apache.jena.query.*;
        import org.apache.jena.rdf.model.Model;
        import org.apache.jena.rdf.model.Resource;
        import org.apache.jena.util.FileManager;

/**
 * ARQ中基础查询的例子，学习如何解析SPARQL查询语句，并且执行查询
 * <p>
 * 关键类有
 * <p>
 * 1. Query
 * 2. QueryFactory
 * 3. QueryExecution
 * 4. QueryExecutionFactory
 * 5. DatasetFactory
 * <p>
 * 查询之后，返回结果中的涉及的关键类有
 * <p>
 * 1. QuerySolution
 * 2. ResultSet
 * 3. ResultSetFormatter
 */

public class sparql_query1 {

    // 完整的解析->查询->处理结果流程
    public static void query() {
        // 加载数据
        Model model = FileManager.get().loadModel("vc-db-1.rdf");

        // 编写查询语句，可以看到挺麻烦的
        String queryString = "SELECT ?x" +
                "WHERE { " +
                "?x  <http://www.w3.org/2001/vcard-rdf/3.0#FN>  \"John Smith\" " +
                "}";

        // 通过QueryFactory解析查询语句，获取Query对象
        Query query = QueryFactory.create(queryString);
        try {
            // 通过QueryExecution执行查询语句
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            // 获取查询之后的结果
            ResultSet results = qexec.execSelect();

            System.out.println(results.nextSolution());
//            // 遍历结果，进行处理
            for (; results.hasNext(); ) {
                // 获取每一条查询结果
                QuerySolution soln = results.nextSolution();
                Resource r = soln.getResource("x");
                // RDFNode x = soln.get("varName")
                // Literal l = soln.getLiteral("varL")
                System.out.println("查询结果为：" + r.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        System.out.println("------完整的查询流程---------");
        query();
        System.out.println("------简化的查询流程---------");
    }

}