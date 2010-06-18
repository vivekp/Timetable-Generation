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
public class CalInformationMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.CalInformationMapBuilder";


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

        dbMap.addTable("CAL_INFORMATION");
        TableMap tMap = dbMap.getTable("CAL_INFORMATION");

        tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);

        tMap.setPrimaryKeyMethodInfo(tMap.getName());

              tMap.addPrimaryKey("CAL_INFORMATION.INFO_ID", new Integer(0));
                    tMap.addColumn("CAL_INFORMATION.USER_ID", new Integer(0));
                    tMap.addColumn("CAL_INFORMATION.GROUP_ID", new Integer(0));
                    tMap.addColumn("CAL_INFORMATION.P_DATE", new Date());
                    tMap.addColumn("CAL_INFORMATION.DETAIL_INFORMATION", new Object());
                    tMap.addColumn("CAL_INFORMATION.START_TIME", new Date());
                    tMap.addColumn("CAL_INFORMATION.END_TIME", new Date());
                    tMap.addColumn("CAL_INFORMATION.EXPIRY", new Integer(0));
                    tMap.addColumn("CAL_INFORMATION.EXPIRY_DATE", new Date());
          }
}
