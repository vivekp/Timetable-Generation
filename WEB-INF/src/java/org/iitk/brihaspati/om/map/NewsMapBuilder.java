package org.iitk.brihaspati.om.map;

import java.util.Date;
import java.math.BigDecimal;

import org.apache.torque.Torque;
import org.apache.torque.TorqueException;
import org.apache.torque.map.MapBuilder;
import org.apache.torque.map.DatabaseMap;
import org.apache.torque.map.TableMap;

/**
  */
public class NewsMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.NewsMapBuilder";


    /**
     * The database map.
     */
    private DatabaseMap dbMap = null;

    /**
     * Tells us if this DatabaseMapBuilder is built so that we
     * don't have to re-build it every time.
     *
     * @return true if this DatabaseMapBuilder is built
     */
    public boolean isBuilt()
    {
        return (dbMap != null);
    }

    /**
     * Gets the databasemap this map builder built.
     *
     * @return the databasemap
     */
    public DatabaseMap getDatabaseMap()
    {
        return this.dbMap;
    }

    /**
     * The doBuild() method builds the DatabaseMap
     *
     * @throws TorqueException
     */
    public void doBuild() throws TorqueException
    {
        dbMap = Torque.getDatabaseMap("default");

        dbMap.addTable("NEWS");
        TableMap tMap = dbMap.getTable("NEWS");

        tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);

        tMap.setPrimaryKeyMethodInfo(tMap.getName());

              tMap.addPrimaryKey("NEWS.NEWS_ID", new Integer(0));
                    tMap.addColumn("NEWS.GROUP_ID", new Integer(0));
                    tMap.addColumn("NEWS.USER_ID", new Integer(0));
                    tMap.addColumn("NEWS.NEWS_TITLE", new String());
                    tMap.addColumn("NEWS.NEWS_DESCRIPTION", new Object());
                    tMap.addColumn("NEWS.PUBLISH_DATE", new Date());
                    tMap.addColumn("NEWS.EXPIRY", new Integer(0));
                    tMap.addColumn("NEWS.EXPIRY_DATE", new Date());
          }
}
