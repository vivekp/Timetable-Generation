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
 * extended all references should be to SurveyStudent
 */
public abstract class BaseSurveyStudent extends BaseObject
{
    /** The Peer class */
    private static final SurveyStudentPeer peer =
        new SurveyStudentPeer();

        
    /** The value for the surveystId field */
    private int surveystId;
      
    /** The value for the surveyId field */
    private int surveyId;
      
    /** The value for the instId field */
    private int instId;
      
    /** The value for the cid field */
    private String cid;
      
    /** The value for the stuId field */
    private String stuId;
      
    /** The value for the ques1 field */
    private int ques1;
      
    /** The value for the ques2 field */
    private int ques2;
      
    /** The value for the ques3 field */
    private int ques3;
      
    /** The value for the ques4 field */
    private int ques4;
      
    /** The value for the ques5 field */
    private int ques5;
      
    /** The value for the totalmarks field */
    private int totalmarks;
  
    
    /**
     * Get the SurveystId
     *
     * @return int
     */
    public int getSurveystId()
    {
        return surveystId;
    }

                        
    /**
     * Set the value of SurveystId
     *
     * @param v new value
     */
    public void setSurveystId(int v) 
    {
    
                  if (this.surveystId != v)
              {
            this.surveystId = v;
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
     * Get the InstId
     *
     * @return int
     */
    public int getInstId()
    {
        return instId;
    }

                        
    /**
     * Set the value of InstId
     *
     * @param v new value
     */
    public void setInstId(int v) 
    {
    
                  if (this.instId != v)
              {
            this.instId = v;
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
     * Get the StuId
     *
     * @return String
     */
    public String getStuId()
    {
        return stuId;
    }

                        
    /**
     * Set the value of StuId
     *
     * @param v new value
     */
    public void setStuId(String v) 
    {
    
                  if (!ObjectUtils.equals(this.stuId, v))
              {
            this.stuId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Ques1
     *
     * @return int
     */
    public int getQues1()
    {
        return ques1;
    }

                        
    /**
     * Set the value of Ques1
     *
     * @param v new value
     */
    public void setQues1(int v) 
    {
    
                  if (this.ques1 != v)
              {
            this.ques1 = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Ques2
     *
     * @return int
     */
    public int getQues2()
    {
        return ques2;
    }

                        
    /**
     * Set the value of Ques2
     *
     * @param v new value
     */
    public void setQues2(int v) 
    {
    
                  if (this.ques2 != v)
              {
            this.ques2 = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Ques3
     *
     * @return int
     */
    public int getQues3()
    {
        return ques3;
    }

                        
    /**
     * Set the value of Ques3
     *
     * @param v new value
     */
    public void setQues3(int v) 
    {
    
                  if (this.ques3 != v)
              {
            this.ques3 = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Ques4
     *
     * @return int
     */
    public int getQues4()
    {
        return ques4;
    }

                        
    /**
     * Set the value of Ques4
     *
     * @param v new value
     */
    public void setQues4(int v) 
    {
    
                  if (this.ques4 != v)
              {
            this.ques4 = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Ques5
     *
     * @return int
     */
    public int getQues5()
    {
        return ques5;
    }

                        
    /**
     * Set the value of Ques5
     *
     * @param v new value
     */
    public void setQues5(int v) 
    {
    
                  if (this.ques5 != v)
              {
            this.ques5 = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Totalmarks
     *
     * @return int
     */
    public int getTotalmarks()
    {
        return totalmarks;
    }

                        
    /**
     * Set the value of Totalmarks
     *
     * @param v new value
     */
    public void setTotalmarks(int v) 
    {
    
                  if (this.totalmarks != v)
              {
            this.totalmarks = v;
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
          save(SurveyStudentPeer.getMapBuilder()
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
                    SurveyStudentPeer.doInsert((SurveyStudent) this, con);
                    setNew(false);
                }
                else
                {
                    SurveyStudentPeer.doUpdate((SurveyStudent) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  surveystId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setSurveystId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setSurveystId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getSurveystId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public SurveyStudent copy() throws TorqueException
    {
        return copyInto(new SurveyStudent());
    }
  
    protected SurveyStudent copyInto(SurveyStudent copyObj) throws TorqueException
    {
          copyObj.setSurveystId(surveystId);
          copyObj.setSurveyId(surveyId);
          copyObj.setInstId(instId);
          copyObj.setCid(cid);
          copyObj.setStuId(stuId);
          copyObj.setQues1(ques1);
          copyObj.setQues2(ques2);
          copyObj.setQues3(ques3);
          copyObj.setQues4(ques4);
          copyObj.setQues5(ques5);
          copyObj.setTotalmarks(totalmarks);
  
                    copyObj.setSurveystId(0);
                                                                        
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public SurveyStudentPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("SurveyStudent:\n");
        str.append("SurveystId = ")
           .append(getSurveystId())
           .append("\n");
        str.append("SurveyId = ")
           .append(getSurveyId())
           .append("\n");
        str.append("InstId = ")
           .append(getInstId())
           .append("\n");
        str.append("Cid = ")
           .append(getCid())
           .append("\n");
        str.append("StuId = ")
           .append(getStuId())
           .append("\n");
        str.append("Ques1 = ")
           .append(getQues1())
           .append("\n");
        str.append("Ques2 = ")
           .append(getQues2())
           .append("\n");
        str.append("Ques3 = ")
           .append(getQues3())
           .append("\n");
        str.append("Ques4 = ")
           .append(getQues4())
           .append("\n");
        str.append("Ques5 = ")
           .append(getQues5())
           .append("\n");
        str.append("Totalmarks = ")
           .append(getTotalmarks())
           .append("\n");
        return(str.toString());
    }
}
