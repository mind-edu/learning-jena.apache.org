package com.feanlau.arq.bgpmatching;


import org.apache.jena.graph.Triple;
import org.apache.jena.graph.impl.GraphBase;
import org.apache.jena.sparql.core.BasicPattern;
import org.apache.jena.sparql.engine.ExecutionContext;
import org.apache.jena.sparql.engine.QueryIterator;
import org.apache.jena.sparql.engine.iterator.QueryIterTriplePattern;
import org.apache.jena.sparql.engine.main.StageGenerator;

/**
 * Example stage generator that compiles a BasicPattern into a sequence of
 * individual triple matching steps.
 */

public class StageGeneratorAlt implements StageGenerator {
    StageGenerator other;

    public StageGeneratorAlt(StageGenerator other) {
        this.other = other;
    }


    @Override
    public QueryIterator execute(BasicPattern pattern,
                                 QueryIterator input,
                                 ExecutionContext execCxt) {
        // Just want to pick out some BGPs (e.g. on a particualr graph)
        // Test ::  execCxt.getActiveGraph() 
        if (!(execCxt.getActiveGraph() instanceof GraphBase))
            // Example: pass on up to the original StageGenerator if
            // not based on GraphBase (which most Graph implementations are). 
            return other.execute(pattern, input, execCxt);

        System.err.println("MyStageGenerator.compile:: triple patterns = " + pattern.size());

        // Stream the triple matches together, one triple matcher at a time. 
        QueryIterator qIter = input;
        for (Triple triple : pattern.getList())
            qIter = new QueryIterTriplePattern(qIter, triple, execCxt);
        return qIter;
    }
}
