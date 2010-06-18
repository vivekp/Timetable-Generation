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
 * extended all references should be to MailReceive
 */
public abstract class BaseMailReceive extends BaseObject
{
    /** The Peer class */
    private static final MailReceivePeer peer =
        new MailReceivePeer();

        
    /** The value for the mailId field */
    private int mailId;
      
    /** The value for the receiverId field */
    private int receiverId;
      
    /** The value for the mailReadflag field */
    private int mailReadflag;
      
    /** The value for the mailType field */
    private int mailType;
  
    
    /**
     * Get the MailId
     *
     * @return int
     */
    public int getMailId()
    {
        return mailId;
    }

                        
    /**
     * Set the value of MailId
     *
     * @param v new value
     */
    public void setMailId(int v) 
    {
    
                  if (this.mailId != v)
              {
            this.mailId = v;
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
     * Get the MailReadflag
     *
     * @return int
     */
    public int getMailReadflag()
    {
        return mailReadflag;
    }

                        
    /**
     * Set the value of MailReadflag
     *
     * @param v new value
     */
    public void setMailReadflag(int v) 
    {
    
                  if (this.mailReadflag != v)
              {
            this.mailReadflag = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the MailType
     *
     * @return int
     */
    public int getMailType()
    {
        return mailType;
    }

                        
    /**
     * Set the value of MailType
     *
     * @param v new value
     */
    public void setMailType(int v) 
    {
    
                  if (this.mailType != v)
              {
            this.mailType = v;
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
          save(MailReceivePeer.getMapBuilder()
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
                    MailReceivePeer.doInsert((MailReceive) this, con);
                    setNew(false);
                }
                else
                {
                    MailReceivePeer.doUpdate((MailReceive) this, con);
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
                      setMailId(((NumberKey)keys[0]).intValue());
                        setReceiverId(((NumberKey)keys[1]).intValue());
              }

    /**
     * Set the PrimaryKey using SimpleKeys.
     *
         * @param int mailId
         * @param int receiverId
         */
    public void setPrimaryKey( int mailId, int receiverId)
        
    {
            setMailId(mailId);
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
              pks[0] = SimpleKey.keyFor(getMailId());
                  pks[1] = SimpleKey.keyFor(getReceiverId());
                  return comboPK;
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public MailReceive copy() throws TorqueException
    {
        return copyInto(new MailReceive());
    }
  
    protected MailReceive copyInto(MailReceive copyObj) throws TorqueException
    {
          copyObj.setMailId(mailId);
          copyObj.setReceiverId(receiverId);
          copyObj.setMailReadflag(mailReadflag);
          copyObj.setMailType(mailType);
  
                    copyObj.setMailId(0);
                              copyObj.setReceiverId(0);
                        
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public MailReceivePeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("MailReceive:\n");
        str.append("MailId = ")
           .append(getMailId())
           .append("\n");
        str.append("ReceiverId = ")
           .append(getReceiverId())
           .append("\n");
        str.append("MailReadflag = ")
           .append(getMailReadflag())
           .append("\n");
        str.append("MailType = ")
           .append(getMailType())
           .append("\n");
        return(str.toString());
    }
}
