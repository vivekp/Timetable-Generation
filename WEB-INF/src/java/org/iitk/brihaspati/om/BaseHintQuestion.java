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
 * extended all references should be to HintQuestion
 */
public abstract class BaseHintQuestion extends BaseObject
{
    /** The Peer class */
    private static final HintQuestionPeer peer =
        new HintQuestionPeer();

        
    /** The value for the questionId field */
    private int questionId;
      
    /** The value for the questionName field */
    private String questionName;
  
    
    /**
     * Get the QuestionId
     *
     * @return int
     */
    public int getQuestionId()
    {
        return questionId;
    }

                        
    /**
     * Set the value of QuestionId
     *
     * @param v new value
     */
    public void setQuestionId(int v) 
    {
    
                  if (this.questionId != v)
              {
            this.questionId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the QuestionName
     *
     * @return String
     */
    public String getQuestionName()
    {
        return questionName;
    }

                        
    /**
     * Set the value of QuestionName
     *
     * @param v new value
     */
    public void setQuestionName(String v) 
    {
    
                  if (!ObjectUtils.equals(this.questionName, v))
              {
            this.questionName = v;
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
          save(HintQuestionPeer.getMapBuilder()
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
                    HintQuestionPeer.doInsert((HintQuestion) this, con);
                    setNew(false);
                }
                else
                {
                    HintQuestionPeer.doUpdate((HintQuestion) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  questionId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setQuestionId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setQuestionId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getQuestionId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public HintQuestion copy() throws TorqueException
    {
        return copyInto(new HintQuestion());
    }
  
    protected HintQuestion copyInto(HintQuestion copyObj) throws TorqueException
    {
          copyObj.setQuestionId(questionId);
          copyObj.setQuestionName(questionName);
  
                    copyObj.setQuestionId(0);
                  
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public HintQuestionPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("HintQuestion:\n");
        str.append("QuestionId = ")
           .append(getQuestionId())
           .append("\n");
        str.append("QuestionName = ")
           .append(getQuestionName())
           .append("\n");
        return(str.toString());
    }
}
