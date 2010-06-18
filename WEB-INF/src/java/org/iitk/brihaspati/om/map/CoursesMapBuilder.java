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
public class CoursesMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.CoursesMapBuilder";


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

        dbMap.addTable("COURSES");
        TableMap tMap = dbMap.getTable("COURSES");

        tMap.setPrimaryKeyMethod("none");


              tMap.addPrimaryKey("COURSES.GROUP_NAME", new String());
                    tMap.addColumn("COURSES.CNAME", new String());
                    tMap.addColumn("COURSES.GROUP_ALIAS", new String());
                    tMap.addColumn("COURSES.DEPT", new String());
                    tMap.addColumn("COURSES.DESCRIPTION", new String());
                    tMap.addColumn("COURSES.ACTIVE", new Byte((byte)0));
                    tMap.addColumn("COURSES.CREATIONDATE", new Date());
                    tMap.addColumn("COURSES.LASTMODIFIED", new Date());
                    tMap.addColumn("COURSES.LASTACCESS", new Date());
                    tMap.addColumn("COURSES.QUOTA", new BigDecimal(0));
          }
}
