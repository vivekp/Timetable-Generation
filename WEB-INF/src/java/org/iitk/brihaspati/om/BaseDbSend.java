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
 * extended all references should be to DbSend
 */
public abstract class BaseDbSend extends BaseObject
{
    /** The Peer class */
    private static final DbSendPeer peer =
        new DbSendPeer();

        
    /** The value for the msgId field */
    private int msgId;
      
    /** The value for the replyId field */
    private int replyId;
      
    /** The value for the msgSubject field */
    private String msgSubject;
      
    /** The value for the userId field */
    private int userId;
      
    /** The value for the groupId field */
    private int groupId;
      
    /** The value for the postTime field */
    private Date postTime;
      
    /** The value for the expiry field */
    private int expiry;
      
    /** The value for the expiryDate field */
    private Date expiryDate;
      
    /** The value for the permission field */
    private int permission;
      
    /** The value for the grpmgmtType field */
    private String grpmgmtType;
  
    
    /**
     * Get the MsgId
     *
     * @return int
     */
    public int getMsgId()
    {
        return msgId;
    }

                        
    /**
     * Set the value of MsgId
     *
     * @param v new value
     */
    public void setMsgId(int v) 
    {
    
                  if (this.msgId != v)
              {
            this.msgId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the ReplyId
     *
     * @return int
     */
    public int getReplyId()
    {
        return replyId;
    }

                        
    /**
     * Set the value of ReplyId
     *
     * @param v new value
     */
    public void setReplyId(int v) 
    {
    
                  if (this.replyId != v)
              {
            this.replyId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the MsgSubject
     *
     * @return String
     */
    public String getMsgSubject()
    {
        return msgSubject;
    }

                        
    /**
     * Set the value of MsgSubject
     *
     * @param v new value
     */
    public void setMsgSubject(String v) 
    {
    
                  if (!ObjectUtils.equals(this.msgSubject, v))
              {
            this.msgSubject = v;
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
     * Get the PostTime
     *
     * @return Date
     */
    public Date getPostTime()
    {
        return postTime;
    }

                        
    /**
     * Set the value of PostTime
     *
     * @param v new value
     */
    public void setPostTime(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.postTime, v))
              {
            this.postTime = v;
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
     * Get the Permission
     *
     * @return int
     */
    public int getPermission()
    {
        return permission;
    }

                        
    /**
     * Set the value of Permission
     *
     * @param v new value
     */
    public void setPermission(int v) 
    {
    
                  if (this.permission != v)
              {
            this.permission = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the GrpmgmtType
     *
     * @return String
     */
    public String getGrpmgmtType()
    {
        return grpmgmtType;
    }

                        
    /**
     * Set the value of GrpmgmtType
     *
     * @param v new value
     */
    public void setGrpmgmtType(String v) 
    {
    
                  if (!ObjectUtils.equals(this.grpmgmtType, v))
              {
            this.grpmgmtType = v;
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
          save(DbSendPeer.getMapBuilder()
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
                    DbSendPeer.doInsert((DbSend) this, con);
                    setNew(false);
                }
                else
                {
                    DbSendPeer.doUpdate((DbSend) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  msgId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setMsgId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setMsgId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getMsgId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public DbSend copy() throws TorqueException
    {
        return copyInto(new DbSend());
    }
  
    protected DbSend copyInto(DbSend copyObj) throws TorqueException
    {
          copyObj.setMsgId(msgId);
          copyObj.setReplyId(replyId);
          copyObj.setMsgSubject(msgSubject);
          copyObj.setUserId(userId);
          copyObj.setGroupId(groupId);
          copyObj.setPostTime(postTime);
          copyObj.setExpiry(expiry);
          copyObj.setExpiryDate(expiryDate);
          copyObj.setPermission(permission);
          copyObj.setGrpmgmtType(grpmgmtType);
  
                    copyObj.setMsgId(0);
                                                                  
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public DbSendPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("DbSend:\n");
        str.append("MsgId = ")
           .append(getMsgId())
           .append("\n");
        str.append("ReplyId = ")
           .append(getReplyId())
           .append("\n");
        str.append("MsgSubject = ")
           .append(getMsgSubject())
           .append("\n");
        str.append("UserId = ")
           .append(getUserId())
           .append("\n");
        str.append("GroupId = ")
           .append(getGroupId())
           .append("\n");
        str.append("PostTime = ")
           .append(getPostTime())
           .append("\n");
        str.append("Expiry = ")
           .append(getExpiry())
           .append("\n");
        str.append("ExpiryDate = ")
           .append(getExpiryDate())
           .append("\n");
        str.append("Permission = ")
           .append(getPermission())
           .append("\n");
        str.append("GrpmgmtType = ")
           .append(getGrpmgmtType())
           .append("\n");
        return(str.toString());
    }
}
