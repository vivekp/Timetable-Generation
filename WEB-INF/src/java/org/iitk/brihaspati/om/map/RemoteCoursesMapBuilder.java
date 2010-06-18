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
public class RemoteCoursesMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.RemoteCoursesMapBuilder";


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

        dbMap.addTable("REMOTE_COURSES");
        TableMap tMap = dbMap.getTable("REMOTE_COURSES");

        tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);

        tMap.setPrimaryKeyMethodInfo(tMap.getName());

              tMap.addPrimaryKey("REMOTE_COURSES.SR_NO", new Integer(0));
                    tMap.addColumn("REMOTE_COURSES.LOCAL_COURSE_ID", new String());
                    tMap.addColumn("REMOTE_COURSES.REMOTE_COURSE_ID", new String());
                    tMap.addColumn("REMOTE_COURSES.COURSE_SELLER", new String());
                    tMap.addColumn("REMOTE_COURSES.COURSE_PURCHASER", new String());
                    tMap.addColumn("REMOTE_COURSES.INSTITUTE_IP", new String());
                    tMap.addColumn("REMOTE_COURSES.INSTITUTE_NAME", new String());
                    tMap.addColumn("REMOTE_COURSES.EXPIRY_DATE", new Date());
                    tMap.addColumn("REMOTE_COURSES.STATUS", new Integer(0));
                    tMap.addColumn("REMOTE_COURSES.SECRET_KEY", new String());
          }
}
