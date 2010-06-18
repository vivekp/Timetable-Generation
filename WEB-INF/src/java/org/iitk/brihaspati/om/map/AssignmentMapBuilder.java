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
public class AssignmentMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.AssignmentMapBuilder";


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

        dbMap.addTable("ASSIGNMENT");
        TableMap tMap = dbMap.getTable("ASSIGNMENT");

        tMap.setPrimaryKeyMethod("none");


              tMap.addPrimaryKey("ASSIGNMENT.ASSIGN_ID", new String());
                    tMap.addColumn("ASSIGNMENT.GROUP_NAME", new String());
                    tMap.addColumn("ASSIGNMENT.TOPIC_NAME", new String());
                    tMap.addColumn("ASSIGNMENT.CUR_DATE", new Date());
                    tMap.addColumn("ASSIGNMENT.DUE_DATE", new Date());
                    tMap.addColumn("ASSIGNMENT.PER_DATE", new Date());
                    tMap.addColumn("ASSIGNMENT.GRADE", new Integer(0));
          }
}
