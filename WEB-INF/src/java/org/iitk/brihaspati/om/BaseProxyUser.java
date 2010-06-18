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
 * extended all references should be to ProxyUser
 */
public abstract class BaseProxyUser extends BaseObject
{
    /** The Peer class */
    private static final ProxyUserPeer peer =
        new ProxyUserPeer();

        
    /** The value for the username field */
    private String username;
      
    /** The value for the password field */
    private String password;
      
    /** The value for the ipaddress field */
    private String ipaddress;
      
    /** The value for the lectureId field */
    private int lectureId;
      
    /** The value for the portNo field */
    private int portNo;
  
    
    /**
     * Get the Username
     *
     * @return String
     */
    public String getUsername()
    {
        return username;
    }

                        
    /**
     * Set the value of Username
     *
     * @param v new value
     */
    public void setUsername(String v) 
    {
    
                  if (!ObjectUtils.equals(this.username, v))
              {
            this.username = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Password
     *
     * @return String
     */
    public String getPassword()
    {
        return password;
    }

                        
    /**
     * Set the value of Password
     *
     * @param v new value
     */
    public void setPassword(String v) 
    {
    
                  if (!ObjectUtils.equals(this.password, v))
              {
            this.password = v;
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
     * Get the LectureId
     *
     * @return int
     */
    public int getLectureId()
    {
        return lectureId;
    }

                        
    /**
     * Set the value of LectureId
     *
     * @param v new value
     */
    public void setLectureId(int v) 
    {
    
                  if (this.lectureId != v)
              {
            this.lectureId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the PortNo
     *
     * @return int
     */
    public int getPortNo()
    {
        return portNo;
    }

                        
    /**
     * Set the value of PortNo
     *
     * @param v new value
     */
    public void setPortNo(int v) 
    {
    
                  if (this.portNo != v)
              {
            this.portNo = v;
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
          save(ProxyUserPeer.getMapBuilder()
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
                    ProxyUserPeer.doInsert((ProxyUser) this, con);
                    setNew(false);
                }
                else
                {
                    ProxyUserPeer.doUpdate((ProxyUser) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  username ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setUsername(key.toString());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setUsername(key);
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getUsername());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public ProxyUser copy() throws TorqueException
    {
        return copyInto(new ProxyUser());
    }
  
    protected ProxyUser copyInto(ProxyUser copyObj) throws TorqueException
    {
          copyObj.setUsername(username);
          copyObj.setPassword(password);
          copyObj.setIpaddress(ipaddress);
          copyObj.setLectureId(lectureId);
          copyObj.setPortNo(portNo);
  
                    copyObj.setUsername((String)null);
                                    
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public ProxyUserPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("ProxyUser:\n");
        str.append("Username = ")
           .append(getUsername())
           .append("\n");
        str.append("Password = ")
           .append(getPassword())
           .append("\n");
        str.append("Ipaddress = ")
           .append(getIpaddress())
           .append("\n");
        str.append("LectureId = ")
           .append(getLectureId())
           .append("\n");
        str.append("PortNo = ")
           .append(getPortNo())
           .append("\n");
        return(str.toString());
    }
}
