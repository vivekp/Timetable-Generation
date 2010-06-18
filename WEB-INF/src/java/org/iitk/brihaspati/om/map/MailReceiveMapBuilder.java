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
public class MailReceiveMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.MailReceiveMapBuilder";


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

        dbMap.addTable("MAIL_RECEIVE");
        TableMap tMap = dbMap.getTable("MAIL_RECEIVE");

        tMap.setPrimaryKeyMethod("none");


              tMap.addPrimaryKey("MAIL_RECEIVE.MAIL_ID", new Integer(0));
                    tMap.addPrimaryKey("MAIL_RECEIVE.RECEIVER_ID", new Integer(0));
                    tMap.addColumn("MAIL_RECEIVE.MAIL_READFLAG", new Integer(0));
                    tMap.addColumn("MAIL_RECEIVE.MAIL_TYPE", new Integer(0));
          }
}
