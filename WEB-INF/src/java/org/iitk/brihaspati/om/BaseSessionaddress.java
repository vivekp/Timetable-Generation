package org.iitk.brihaspati.om;


import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.torque.TorqueException;
import org.apache.torque.om.BaseObject;
import org.apache.torque.om.ComboKey;
import org.apache.torque.om.DateKey;
import org.apache.torque.om.NumberKey;
import org.apache.torque.om.ObjectKey;
import org.apache.torque.om.SimpleKey;
import org.apache.torque.om.StringKey;
import org.apache.torque.om.Persistent;
import org.apache.torque.util.Criteria;
import org.apache.torque.util.Transaction;


/**
 * You should not use this class directly.  It should not even be
 * extended all references should be to Sessionaddress
 */
public abstract class BaseSessionaddress extends BaseObject
{
    /** The Peer class */
    private static final SessionaddressPeer peer =
        new SessionaddressPeer();

        
    /** The value for the serialno field */
    private int serialno;
      
    /** The value for the multicastaddress field */
    private String multicastaddress;
      
    /** The value for the flag field */
    private int flag;
  
    
    /**
     * Get the Serialno
     *
     * @return int
     */
    public int getSerialno()
    {
        return serialno;
    }

                        
    /**
     * Set the value of Serialno
     *
     * @param v new value
     */
    public void setSerialno(int v) 
    {
    
                  if (this.serialno != v)
              {
            this.serialno = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Multicastaddress
     *
     * @return String
     */
    public String getMulticastaddress()
    {
        return multicastaddress;
    }

                        
    /**
     * Set the value of Multicastaddress
     *
     * @param v new value
     */
    public void setMulticastaddress(String v) 
    {
    
                  if (!ObjectUtils.equals(this.multicastaddress, v))
              {
            this.multicastaddress = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Flag
     *
     * @return int
     */
    public int getFlag()
    {
        return flag;
    }

                        
    /**
     * Set the value of Flag
     *
     * @param v new value
     */
    public void setFlag(int v) 
    {
    
                  if (this.flag != v)
              {
            this.flag = v;
            setModified(true);
        }
    
          
              }
  
         
                
     
    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
          save(SessionaddressPeer.getMapBuilder()
                .getDatabaseMap().getName());
      }

    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
       * Note: this code is here because the method body is
     * auto-generated conditionally and therefore needs to be
     * in this file instead of in the super class, BaseObject.
       *
     * @param dbName
     * @throws TorqueException
     */
    public void save(String dbName) throws TorqueException
    {
        Connection con = null;
          try
        {
            con = Transaction.begin(dbName);
            save(con);
            Transaction.commit(con);
        }
        catch(TorqueException e)
        {
            Transaction.safeRollback(con);
            throw e;
        }
      }

      /** flag to prevent endless save loop, if this object is referenced
        by another object which falls in this transaction. */
    private boolean alreadyInSave = false;
      /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.  This method
     * is meant to be used as part of a transaction, otherwise use
     * the save() method and the connection details will be handled
     * internally
     *
     * @param con
     * @throws TorqueException
     */
    public void save(Connection con) throws TorqueException
    {
          if (!alreadyInSave)
        {
            alreadyInSave = true;


  
            // If this object has been modified, then save it to the database.
            if (isModified())
            {
                if (isNew())
                {
                    SessionaddressPeer.doInsert((Sessionaddress) this, con);
                    setNew(false);
                }
                else
                {
                    SessionaddressPeer.doUpdate((Sessionaddress) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  serialno ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setSerialno(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setSerialno(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getSerialno());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public Sessionaddress copy() throws TorqueException
    {
        return copyInto(new Sessionaddress());
    }
  
    protected Sessionaddress copyInto(Sessionaddress copyObj) throws TorqueException
    {
          copyObj.setSerialno(serialno);
          copyObj.setMulticastaddress(multicastaddress);
          copyObj.setFlag(flag);
  
                    copyObj.setSerialno(0);
                        
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public SessionaddressPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("Sessionaddress:\n");
        str.append("Serialno = ")
           .append(getSerialno())
           .append("\n");
        str.append("Multicastaddress = ")
           .append(getMulticastaddress())
           .append("\n");
        str.append("Flag = ")
           .append(getFlag())
           .append("\n");
        return(str.toString());
    }
}
