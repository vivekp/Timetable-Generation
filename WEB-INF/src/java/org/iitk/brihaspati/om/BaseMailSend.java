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
 * extended all references should be to MailSend
 */
public abstract class BaseMailSend extends BaseObject
{
    /** The Peer class */
    private static final MailSendPeer peer =
        new MailSendPeer();

        
    /** The value for the mailId field */
    private int mailId;
      
    /** The value for the senderId field */
    private int senderId;
      
    /** The value for the mailSubject field */
    private String mailSubject;
      
    /** The value for the replyStatus field */
    private int replyStatus;
      
    /** The value for the postTime field */
    private Date postTime;
  
    
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
     * Get the SenderId
     *
     * @return int
     */
    public int getSenderId()
    {
        return senderId;
    }

                        
    /**
     * Set the value of SenderId
     *
     * @param v new value
     */
    public void setSenderId(int v) 
    {
    
                  if (this.senderId != v)
              {
            this.senderId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the MailSubject
     *
     * @return String
     */
    public String getMailSubject()
    {
        return mailSubject;
    }

                        
    /**
     * Set the value of MailSubject
     *
     * @param v new value
     */
    public void setMailSubject(String v) 
    {
    
                  if (!ObjectUtils.equals(this.mailSubject, v))
              {
            this.mailSubject = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the ReplyStatus
     *
     * @return int
     */
    public int getReplyStatus()
    {
        return replyStatus;
    }

                        
    /**
     * Set the value of ReplyStatus
     *
     * @param v new value
     */
    public void setReplyStatus(int v) 
    {
    
                  if (this.replyStatus != v)
              {
            this.replyStatus = v;
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
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
          save(MailSendPeer.getMapBuilder()
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
                    MailSendPeer.doInsert((MailSend) this, con);
                    setNew(false);
                }
                else
                {
                    MailSendPeer.doUpdate((MailSend) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  mailId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setMailId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setMailId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getMailId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public MailSend copy() throws TorqueException
    {
        return copyInto(new MailSend());
    }
  
    protected MailSend copyInto(MailSend copyObj) throws TorqueException
    {
          copyObj.setMailId(mailId);
          copyObj.setSenderId(senderId);
          copyObj.setMailSubject(mailSubject);
          copyObj.setReplyStatus(replyStatus);
          copyObj.setPostTime(postTime);
  
                    copyObj.setMailId(0);
                                    
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public MailSendPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("MailSend:\n");
        str.append("MailId = ")
           .append(getMailId())
           .append("\n");
        str.append("SenderId = ")
           .append(getSenderId())
           .append("\n");
        str.append("MailSubject = ")
           .append(getMailSubject())
           .append("\n");
        str.append("ReplyStatus = ")
           .append(getReplyStatus())
           .append("\n");
        str.append("PostTime = ")
           .append(getPostTime())
           .append("\n");
        return(str.toString());
    }
}
