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
public class TurbineScheduledJobMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.TurbineScheduledJobMapBuilder";


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
        dbMap = Torque.getDatabaseMap("brihaspati");

        dbMap.addTable("TURBINE_SCHEDULED_JOB");
        TableMap tMap = dbMap.getTable("TURBINE_SCHEDULED_JOB");

        tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);

        tMap.setPrimaryKeyMethodInfo(tMap.getName());

              tMap.addPrimaryKey("TURBINE_SCHEDULED_JOB.JOB_ID", new Integer(0));
                    tMap.addColumn("TURBINE_SCHEDULED_JOB.SECOND", new Integer(0));
                    tMap.addColumn("TURBINE_SCHEDULED_JOB.MINUTE", new Integer(0));
                    tMap.addColumn("TURBINE_SCHEDULED_JOB.HOUR", new Integer(0));
                    tMap.addColumn("TURBINE_SCHEDULED_JOB.WEEK_DAY", new Integer(0));
                    tMap.addColumn("TURBINE_SCHEDULED_JOB.DAY_OF_MONTH", new Integer(0));
                    tMap.addColumn("TURBINE_SCHEDULED_JOB.TASK", new String());
                    tMap.addColumn("TURBINE_SCHEDULED_JOB.EMAIL", new String());
                    tMap.addColumn("TURBINE_SCHEDULED_JOB.PROPERTY", new Object());
          }
}
