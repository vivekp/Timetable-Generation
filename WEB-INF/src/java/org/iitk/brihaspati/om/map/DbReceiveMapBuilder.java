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
public class DbReceiveMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.DbReceiveMapBuilder";


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

        dbMap.addTable("DB_RECEIVE");
        TableMap tMap = dbMap.getTable("DB_RECEIVE");

        tMap.setPrimaryKeyMethod("none");


              tMap.addPrimaryKey("DB_RECEIVE.MSG_ID", new Integer(0));
                    tMap.addPrimaryKey("DB_RECEIVE.RECEIVER_ID", new Integer(0));
                    tMap.addColumn("DB_RECEIVE.GROUP_ID", new Integer(0));
                    tMap.addColumn("DB_RECEIVE.READ_FLAG", new Integer(0));
          }
}
