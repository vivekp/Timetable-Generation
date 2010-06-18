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
public class TaskMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.TaskMapBuilder";


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

        dbMap.addTable("TASK");
        TableMap tMap = dbMap.getTable("TASK");

        tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);

        tMap.setPrimaryKeyMethodInfo(tMap.getName());

              tMap.addPrimaryKey("TASK.TASK_ID", new Integer(0));
                    tMap.addColumn("TASK.USER_ID", new Integer(0));
                    tMap.addColumn("TASK.TITLE", new String());
                    tMap.addColumn("TASK.STATUS", new Integer(0));
                    tMap.addPrimaryKey("TASK.START_DATE", new Date());
                    tMap.addColumn("TASK.END_DATE", new Date());
                    tMap.addColumn("TASK.EXPIRY", new Integer(0));
                    tMap.addColumn("TASK.EXPIRY_DATE", new Date());
                    tMap.addColumn("TASK.DUE_DAYS", new Integer(0));
                    tMap.addColumn("TASK.DUE_DATE", new Date());
          }
}
