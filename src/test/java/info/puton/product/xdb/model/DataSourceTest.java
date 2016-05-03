package info.puton.product.xdb.model;

import info.puton.product.xdb.exception.XDBException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by taoyang on 3/15/16.
 */
public class DataSourceTest {

    @Test
    public void testNew() throws XDBException {

        String dsStr = "HIVE.P_SCHEMA.MOBILE.NUMBER";

        DataSource ds = new DataSource(dsStr);

        System.out.println(ds);

    }

}