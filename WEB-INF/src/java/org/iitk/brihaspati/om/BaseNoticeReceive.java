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
 * extended all references should be to NoticeReceive
 */
public abstract class BaseNoticeReceive extends BaseObject
{
    /** The Peer class */
    private static final NoticeReceivePeer peer =
        new NoticeReceivePeer();

        
    /** The value for the noticeId field */
    private int noticeId;
      
    /** The value for the receiverId field */
    private int receiverId;
      
    /** The value for the groupId field */
    private int groupId;
      
    /** The value for the readFlag field */
    private int readFlag;
  
    
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
     * Get the ReceiverId
     *
     * @return int
     */
    public int getReceiverId()
    {
        return receiverId;
    }

                        
    /**
     * Set the value of ReceiverId
     *
     * @param v new value
     */
    public void setReceiverId(int v) 
    {
    
                  if (this.receiverId != v)
              {
            this.receiverId = v;
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
     * Get the ReadFlag
     *
     * @return int
     */
    public int getReadFlag()
    {
        return readFlag;
    }

                        
    /**
     * Set the value of ReadFlag
     *
     * @param v new value
     */
    public void setReadFlag(int v) 
    {
    
                  if (this.readFlag != v)
              {
            this.readFlag = v;
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
          save(NoticeReceivePeer.getMapBuilder()
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
                    NoticeReceivePeer.doInsert((NoticeReceive) this, con);
                    setNew(false);
                }
                else
                {
                    NoticeReceivePeer.doUpdate((NoticeReceive) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                                    
  
    private final SimpleKey[] pks = new SimpleKey[2];
    private final ComboKey comboPK = new ComboKey(pks);
    
    /**
     * Set the PrimaryKey with an ObjectKey
     *
     * @param key
     */
    public void setPrimaryKey(ObjectKey key) throws TorqueException
    {
        SimpleKey[] keys = (SimpleKey[]) key.getValue();
        SimpleKey tmpKey = null;
                      setNoticeId(((NumberKey)keys[0]).intValue());
                        setReceiverId(((NumberKey)keys[1]).intValue());
              }

    /**
     * Set the PrimaryKey using SimpleKeys.
     *
         * @param int noticeId
         * @param int receiverId
         */
    public void setPrimaryKey( int noticeId, int receiverId)
        
    {
            setNoticeId(noticeId);
            setReceiverId(receiverId);
        }

    /**
     * Set the PrimaryKey using a String.
     */
    public void setPrimaryKey(String key) throws TorqueException
    {
        setPrimaryKey(new ComboKey(key));
    }
  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
              pks[0] = SimpleKey.keyFor(getNoticeId());
                  pks[1] = SimpleKey.keyFor(getReceiverId());
                  return comboPK;
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public NoticeReceive copy() throws TorqueException
    {
        return copyInto(new NoticeReceive());
    }
  
    protected NoticeReceive copyInto(NoticeReceive copyObj) throws TorqueException
    {
          copyObj.setNoticeId(noticeId);
          copyObj.setReceiverId(receiverId);
          copyObj.setGroupId(groupId);
          copyObj.setReadFlag(readFlag);
  
                    copyObj.setNoticeId(0);
                              copyObj.setReceiverId(0);
                        
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public NoticeReceivePeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("NoticeReceive:\n");
        str.append("NoticeId = ")
           .append(getNoticeId())
           .append("\n");
        str.append("ReceiverId = ")
           .append(getReceiverId())
           .append("\n");
        str.append("GroupId = ")
           .append(getGroupId())
           .append("\n");
        str.append("ReadFlag = ")
           .append(getReadFlag())
           .append("\n");
        return(str.toString());
    }
}
