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
 * extended all references should be to SurveyResult
 */
public abstract class BaseSurveyResult extends BaseObject
{
    /** The Peer class */
    private static final SurveyResultPeer peer =
        new SurveyResultPeer();

        
    /** The value for the resultId field */
    private int resultId;
      
    /** The value for the surveyId field */
    private int surveyId;
      
    /** The value for the cid field */
    private String cid;
      
    /** The value for the numStuAttd field */
    private int numStuAttd;
      
    /** The value for the totalmarksPer field */
    private int totalmarksPer;
      
    /** The value for the grade field */
    private String grade;
  
    
    /**
     * Get the ResultId
     *
     * @return int
     */
    public int getResultId()
    {
        return resultId;
    }

                        
    /**
     * Set the value of ResultId
     *
     * @param v new value
     */
    public void setResultId(int v) 
    {
    
                  if (this.resultId != v)
              {
            this.resultId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the SurveyId
     *
     * @return int
     */
    public int getSurveyId()
    {
        return surveyId;
    }

                        
    /**
     * Set the value of SurveyId
     *
     * @param v new value
     */
    public void setSurveyId(int v) 
    {
    
                  if (this.surveyId != v)
              {
            this.surveyId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Cid
     *
     * @return String
     */
    public String getCid()
    {
        return cid;
    }

                        
    /**
     * Set the value of Cid
     *
     * @param v new value
     */
    public void setCid(String v) 
    {
    
                  if (!ObjectUtils.equals(this.cid, v))
              {
            this.cid = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the NumStuAttd
     *
     * @return int
     */
    public int getNumStuAttd()
    {
        return numStuAttd;
    }

                        
    /**
     * Set the value of NumStuAttd
     *
     * @param v new value
     */
    public void setNumStuAttd(int v) 
    {
    
                  if (this.numStuAttd != v)
              {
            this.numStuAttd = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the TotalmarksPer
     *
     * @return int
     */
    public int getTotalmarksPer()
    {
        return totalmarksPer;
    }

                        
    /**
     * Set the value of TotalmarksPer
     *
     * @param v new value
     */
    public void setTotalmarksPer(int v) 
    {
    
                  if (this.totalmarksPer != v)
              {
            this.totalmarksPer = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Grade
     *
     * @return String
     */
    public String getGrade()
    {
        return grade;
    }

                        
    /**
     * Set the value of Grade
     *
     * @param v new value
     */
    public void setGrade(String v) 
    {
    
                  if (!ObjectUtils.equals(this.grade, v))
              {
            this.grade = v;
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
          save(SurveyResultPeer.getMapBuilder()
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
                    SurveyResultPeer.doInsert((SurveyResult) this, con);
                    setNew(false);
                }
                else
                {
                    SurveyResultPeer.doUpdate((SurveyResult) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  resultId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setResultId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setResultId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getResultId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public SurveyResult copy() throws TorqueException
    {
        return copyInto(new SurveyResult());
    }
  
    protected SurveyResult copyInto(SurveyResult copyObj) throws TorqueException
    {
          copyObj.setResultId(resultId);
          copyObj.setSurveyId(surveyId);
          copyObj.setCid(cid);
          copyObj.setNumStuAttd(numStuAttd);
          copyObj.setTotalmarksPer(totalmarksPer);
          copyObj.setGrade(grade);
  
                    copyObj.setResultId(0);
                                          
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public SurveyResultPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("SurveyResult:\n");
        str.append("ResultId = ")
           .append(getResultId())
           .append("\n");
        str.append("SurveyId = ")
           .append(getSurveyId())
           .append("\n");
        str.append("Cid = ")
           .append(getCid())
           .append("\n");
        str.append("NumStuAttd = ")
           .append(getNumStuAttd())
           .append("\n");
        str.append("TotalmarksPer = ")
           .append(getTotalmarksPer())
           .append("\n");
        str.append("Grade = ")
           .append(getGrade())
           .append("\n");
        return(str.toString());
    }
}
