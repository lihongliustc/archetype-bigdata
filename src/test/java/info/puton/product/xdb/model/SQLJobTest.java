package info.puton.product.xdb.model;

import org.junit.Test;

/**
 * Created by taoyang on 3/30/16.
 */
public class SQLJobTest {

    @Test
    public void testConstruct(){

//        SQLJob SQLJob = new SQLJob("td.sparktst");
        SQLJob SQLJob = new SQLJob("db2.sparktst");

        System.out.println(SQLJob);

    }

}