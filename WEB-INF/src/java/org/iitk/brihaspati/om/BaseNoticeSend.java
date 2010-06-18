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
 * extended all references should be to NoticeSend
 */
public abstract class BaseNoticeSend extends BaseObject
{
    /** The Peer class */
    private static final NoticeSendPeer peer =
        new NoticeSendPeer();

        
    /** The value for the noticeId field */
    private int noticeId;
      
    /** The value for the noticeSubject field */
    private String noticeSubject;
      
    /** The value for the userId field */
    private int userId;
      
    /** The value for the groupId field */
    private int groupId;
      
    /** The value for the roleId field */
    private int roleId;
      
    /** The value for the postTime field */
    private Date postTime;
      
    /** The value for the expiry field */
    private int expiry;
      
    /** The value for the expiryDate field */
    private Date expiryDate;
  
    
    /**
     * Get the NoticeId
     *
     * @return int
     */
    public int getNoticeId()
    {
        return noticeId;
    }

                        
    /**
     * Set the value of NoticeId
     *
     * @param v new value
     */
    public void setNoticeId(int v) 
    {
    
                  if (this.noticeId != v)
              {
            this.noticeId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the NoticeSubject
     *
     * @return String
     */
    public String getNoticeSubject()
    {
        return noticeSubject;
    }

                        
    /**
     * Set the value of NoticeSubject
     *
     * @param v new value
     */
    public void setNoticeSubject(String v) 
    {
    
                  if (!ObjectUtils.equals(this.noticeSubject, v))
              {
            this.noticeSubject = v;
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
     * Get the RoleId
     *
     * @return int
     */
    public int getRoleId()
    {
        return roleId;
    }

                        
    /**
     * Set the value of RoleId
     *
     * @param v new value
     */
    public void setRoleId(int v) 
    {
    
                  if (this.roleId != v)
              {
            this.roleId = v;
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
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
          save(NoticeSendPeer.getMapBuilder()
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
                    NoticeSendPeer.doInsert((NoticeSend) this, con);
                    setNew(false);
                }
                else
                {
                    NoticeSendPeer.doUpdate((NoticeSend) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  noticeId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setNoticeId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setNoticeId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getNoticeId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public NoticeSend copy() throws TorqueException
    {
        return copyInto(new NoticeSend());
    }
  
    protected NoticeSend copyInto(NoticeSend copyObj) throws TorqueException
    {
          copyObj.setNoticeId(noticeId);
          copyObj.setNoticeSubject(noticeSubject);
          copyObj.setUserId(userId);
          copyObj.setGroupId(groupId);
          copyObj.setRoleId(roleId);
          copyObj.setPostTime(postTime);
          copyObj.setExpiry(expiry);
          copyObj.setExpiryDate(expiryDate);
  
                    copyObj.setNoticeId(0);
                                                      
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public NoticeSendPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("NoticeSend:\n");
        str.append("NoticeId = ")
           .append(getNoticeId())
           .append("\n");
        str.append("NoticeSubject = ")
           .append(getNoticeSubject())
           .append("\n");
        str.append("UserId = ")
           .append(getUserId())
           .append("\n");
        str.append("GroupId = ")
           .append(getGroupId())
           .append("\n");
        str.append("RoleId = ")
           .append(getRoleId())
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
        return(str.toString());
    }
}
