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
 * extended all references should be to CalInformation
 */
public abstract class BaseCalInformation extends BaseObject
{
    /** The Peer class */
    private static final CalInformationPeer peer =
        new CalInformationPeer();

        
    /** The value for the infoId field */
    private int infoId;
      
    /** The value for the userId field */
    private int userId;
      
    /** The value for the groupId field */
    private int groupId;
      
    /** The value for the pDate field */
    private Date pDate;
      
    /** The value for the detailInformation field */
    private byte[] detailInformation;
      
    /** The value for the startTime field */
    private Date startTime;
      
    /** The value for the endTime field */
    private Date endTime;
      
    /** The value for the expiry field */
    private int expiry;
      
    /** The value for the expiryDate field */
    private Date expiryDate;
  
    
    /**
     * Get the InfoId
     *
     * @return int
     */
    public int getInfoId()
    {
        return infoId;
    }

                        
    /**
     * Set the value of InfoId
     *
     * @param v new value
     */
    public void setInfoId(int v) 
    {
    
                  if (this.infoId != v)
              {
            this.infoId = v;
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
     * Get the GroupId
     *
     * @return int
     */
    public int getGroupId()
    {
        return groupId;
    }

                        
    /**
     * Set the value of GroupId
     *
     * @param v new value
     */
    public void setGroupId(int v) 
    {
    
                  if (this.groupId != v)
              {
            this.groupId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the PDate
     *
     * @return Date
     */
    public Date getPDate()
    {
        return pDate;
    }

                        
    /**
     * Set the value of PDate
     *
     * @param v new value
     */
    public void setPDate(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.pDate, v))
              {
            this.pDate = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the DetailInformation
     *
     * @return byte[]
     */
    public byte[] getDetailInformation()
    {
        return detailInformation;
    }

                        
    /**
     * Set the value of DetailInformation
     *
     * @param v new value
     */
    public void setDetailInformation(byte[] v) 
    {
    
                  if (!ObjectUtils.equals(this.detailInformation, v))
              {
            this.detailInformation = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the StartTime
     *
     * @return Date
     */
    public Date getStartTime()
    {
        return startTime;
    }

                        
    /**
     * Set the value of StartTime
     *
     * @param v new value
     */
    public void setStartTime(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.startTime, v))
              {
            this.startTime = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the EndTime
     *
     * @return Date
     */
    public Date getEndTime()
    {
        return endTime;
    }

                        
    /**
     * Set the value of EndTime
     *
     * @param v new value
     */
    public void setEndTime(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.endTime, v))
              {
            this.endTime = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Expiry
     *
     * @return int
     */
    public int getExpiry()
    {
        return expiry;
    }

                        
    /**
     * Set the value of Expiry
     *
     * @param v new value
     */
    public void setExpiry(int v) 
    {
    
                  if (this.expiry != v)
              {
            this.expiry = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the ExpiryDate
     *
     * @return Date
     */
    public Date getExpiryDate()
    {
        return expiryDate;
    }

                        
    /**
     * Set the value of ExpiryDate
     *
     * @param v new value
     */
    public void setExpiryDate(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.expiryDate, v))
              {
            this.expiryDate = v;
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
          save(CalInformationPeer.getMapBuilder()
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
                    CalInformationPeer.doInsert((CalInformation) this, con);
                    setNew(false);
                }
                else
                {
                    CalInformationPeer.doUpdate((CalInformation) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  infoId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setInfoId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setInfoId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getInfoId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public CalInformation copy() throws TorqueException
    {
        return copyInto(new CalInformation());
    }
  
    protected CalInformation copyInto(CalInformation copyObj) throws TorqueException
    {
          copyObj.setInfoId(infoId);
          copyObj.setUserId(userId);
          copyObj.setGroupId(groupId);
          copyObj.setPDate(pDate);
          copyObj.setDetailInformation(detailInformation);
          copyObj.setStartTime(startTime);
          copyObj.setEndTime(endTime);
          copyObj.setExpiry(expiry);
          copyObj.setExpiryDate(expiryDate);
  
                    copyObj.setInfoId(0);
                                                            
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public CalInformationPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("CalInformation:\n");
        str.append("InfoId = ")
           .append(getInfoId())
           .append("\n");
        str.append("UserId = ")
           .append(getUserId())
           .append("\n");
        str.append("GroupId = ")
           .append(getGroupId())
           .append("\n");
        str.append("PDate = ")
           .append(getPDate())
           .append("\n");
        str.append("DetailInformation = ")
           .append(getDetailInformation())
           .append("\n");
        str.append("StartTime = ")
           .append(getStartTime())
           .append("\n");
        str.append("EndTime = ")
           .append(getEndTime())
           .append("\n");
        str.append("Expiry = ")
           .append(getExpiry())
           .append("\n");
        str.append("ExpiryDate = ")
           .append(getExpiryDate())
           .append("\n");
        return(str.toString());
    }
}
