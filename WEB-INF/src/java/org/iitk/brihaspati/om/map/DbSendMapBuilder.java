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
public class DbSendMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.DbSendMapBuilder";


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

        dbMap.addTable("DB_SEND");
        TableMap tMap = dbMap.getTable("DB_SEND");

        tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);

        tMap.setPrimaryKeyMethodInfo(tMap.getName());

              tMap.addPrimaryKey("DB_SEND.MSG_ID", new Integer(0));
                    tMap.addColumn("DB_SEND.REPLY_ID", new Integer(0));
                    tMap.addColumn("DB_SEND.MSG_SUBJECT", new String());
                    tMap.addColumn("DB_SEND.USER_ID", new Integer(0));
                    tMap.addColumn("DB_SEND.GROUP_ID", new Integer(0));
                    tMap.addColumn("DB_SEND.POST_TIME", new Date());
                    tMap.addColumn("DB_SEND.EXPIRY", new Integer(0));
                    tMap.addColumn("DB_SEND.EXPIRY_DATE", new Date());
                    tMap.addColumn("DB_SEND.PERMISSION", new Integer(0));
                    tMap.addColumn("DB_SEND.GRPMGMT_TYPE", new String());
          }
}
