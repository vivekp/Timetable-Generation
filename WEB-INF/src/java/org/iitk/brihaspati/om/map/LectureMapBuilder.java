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
public class LectureMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.LectureMapBuilder";


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

        dbMap.addTable("LECTURE");
        TableMap tMap = dbMap.getTable("LECTURE");

        tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);

        tMap.setPrimaryKeyMethodInfo(tMap.getName());

              tMap.addPrimaryKey("LECTURE.LECTUREID", new Integer(0));
                    tMap.addColumn("LECTURE.GROUP_NAME", new String());
                    tMap.addColumn("LECTURE.LECTURENAME", new String());
                    tMap.addColumn("LECTURE.LECTUREINFO", new String());
                    tMap.addColumn("LECTURE.URLNAME", new String());
                    tMap.addColumn("LECTURE.PHONENO", new String());
                    tMap.addColumn("LECTURE.FORVIDEO", new String());
                    tMap.addColumn("LECTURE.FORAUDIO", new String());
                    tMap.addColumn("LECTURE.FORWHITEBOARD", new String());
                    tMap.addColumn("LECTURE.SESSIONDATE", new Date());
                    tMap.addColumn("LECTURE.SESSIONTIME", new String());
                    tMap.addColumn("LECTURE.DURATION", new String());
                    tMap.addColumn("LECTURE.REPEAT", new String());
                    tMap.addColumn("LECTURE.FORTIME", new String());
          }
}
