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
public class ProxyUserMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.ProxyUserMapBuilder";


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

        dbMap.addTable("PROXY_USER");
        TableMap tMap = dbMap.getTable("PROXY_USER");

        tMap.setPrimaryKeyMethod("none");


              tMap.addPrimaryKey("PROXY_USER.USERNAME", new String());
                    tMap.addColumn("PROXY_USER.PASSWORD", new String());
                    tMap.addColumn("PROXY_USER.IPADDRESS", new String());
                    tMap.addColumn("PROXY_USER.LECTURE_ID", new Integer(0));
                    tMap.addColumn("PROXY_USER.PORT_NO", new Integer(0));
          }
}
