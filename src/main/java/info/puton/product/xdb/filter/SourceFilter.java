package info.puton.product.xdb.filter;

import info.puton.product.xdb.constant.DatabaseType;
import info.puton.product.xdb.model.DataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoyang on 3/21/16.
 */
public class SourceFilter {


    public static List<DataSource> toTdDataSources(List<DataSource> dataSources){

        List<DataSource> tdDataSources = new ArrayList<>();

        for (DataSource dataSource : dataSources) {

            if (dataSource.getDatabaseType().equals(DatabaseType.TERADATA)){

                tdDataSources.add(dataSource);

            }

        }

        return tdDataSources;
    }

    public static List<DataSource> toDb2DataSources(List<DataSource> dataSources){

        List<DataSource> db2DataSources = new ArrayList<>();

        for (DataSource dataSource : dataSources) {

            if (dataSource.getDatabaseType().equals(DatabaseType.DB2)){

                db2DataSources.add(dataSource);

            }

        }

        return db2DataSources;

    }

    public static List<DataSource> toHiveDataSources(List<DataSource> dataSources){

        List<DataSource> hiveDataSources = new ArrayList<>();

        for (DataSource dataSource : dataSources) {

            if (dataSource.getDatabaseType().equals(DatabaseType.HIVE)){

                hiveDataSources.add(dataSource);

            }

        }

        return hiveDataSources;

    }

}
