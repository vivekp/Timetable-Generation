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
public class NoticeSendMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.NoticeSendMapBuilder";


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

        dbMap.addTable("NOTICE_SEND");
        TableMap tMap = dbMap.getTable("NOTICE_SEND");

        tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);

        tMap.setPrimaryKeyMethodInfo(tMap.getName());

              tMap.addPrimaryKey("NOTICE_SEND.NOTICE_ID", new Integer(0));
                    tMap.addColumn("NOTICE_SEND.NOTICE_SUBJECT", new String());
                    tMap.addColumn("NOTICE_SEND.USER_ID", new Integer(0));
                    tMap.addColumn("NOTICE_SEND.GROUP_ID", new Integer(0));
                    tMap.addColumn("NOTICE_SEND.ROLE_ID", new Integer(0));
                    tMap.addColumn("NOTICE_SEND.POST_TIME", new Date());
                    tMap.addColumn("NOTICE_SEND.EXPIRY", new Integer(0));
                    tMap.addColumn("NOTICE_SEND.EXPIRY_DATE", new Date());
          }
}
