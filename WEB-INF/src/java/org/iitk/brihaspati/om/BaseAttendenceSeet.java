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
 * extended all references should be to AttendenceSeet
 */
public abstract class BaseAttendenceSeet extends BaseObject
{
    /** The Peer class */
    private static final AttendenceSeetPeer peer =
        new AttendenceSeetPeer();

        
    /** The value for the id field */
    private int id;
      
    /** The value for the userId field */
    private int userId;
      
    /** The value for the loginDate field */
    private Date loginDate;
      
    /** The value for the ipaddress field */
    private String ipaddress;
  
    
    /**
     * Get the Id
     *
     * @return int
     */
    public int getId()
    {
        return id;
    }

                        
    /**
     * Set the value of Id
     *
     * @param v new value
     */
    public void setId(int v) 
    {
    
                  if (this.id != v)
              {
            this.id = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the UserId
     *
     * @return int
     */
    public int getUserId()
    {
        return userId;
    }

                        
    /**
     * Set the value of UserId
     *
     * @param v new value
     */
    public void setUserId(int v) 
    {
    
                  if (this.userId != v)
              {
            this.userId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the LoginDate
     *
     * @return Date
     */
    public Date getLoginDate()
    {
        return loginDate;
    }

                        
    /**
     * Set the value of LoginDate
     *
     * @param v new value
     */
    public void setLoginDate(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.loginDate, v))
              {
            this.loginDate = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Ipaddress
     *
     * @return String
     */
    public String getIpaddress()
    {
        return ipaddress;
    }

                        
    /**
     * Set the value of Ipaddress
     *
     * @param v new value
     */
    public void setIpaddress(String v) 
    {
    
                  if (!ObjectUtils.equals(this.ipaddress, v))
              {
            this.ipaddress = v;
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
          save(AttendenceSeetPeer.getMapBuilder()
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
                    AttendenceSeetPeer.doInsert((AttendenceSeet) this, con);
                    setNew(false);
                }
                else
                {
                    AttendenceSeetPeer.doUpdate((AttendenceSeet) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  id ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public AttendenceSeet copy() throws TorqueException
    {
        return copyInto(new AttendenceSeet());
    }
  
    protected AttendenceSeet copyInto(AttendenceSeet copyObj) throws TorqueException
    {
          copyObj.setId(id);
          copyObj.setUserId(userId);
          copyObj.setLoginDate(loginDate);
          copyObj.setIpaddress(ipaddress);
  
                    copyObj.setId(0);
                              
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public AttendenceSeetPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("AttendenceSeet:\n");
        str.append("Id = ")
           .append(getId())
           .append("\n");
        str.append("UserId = ")
           .append(getUserId())
           .append("\n");
        str.append("LoginDate = ")
           .append(getLoginDate())
           .append("\n");
        str.append("Ipaddress = ")
           .append(getIpaddress())
           .append("\n");
        return(str.toString());
    }
}
