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
 * extended all references should be to RemoteCourses
 */
public abstract class BaseRemoteCourses extends BaseObject
{
    /** The Peer class */
    private static final RemoteCoursesPeer peer =
        new RemoteCoursesPeer();

        
    /** The value for the srNo field */
    private int srNo;
      
    /** The value for the localCourseId field */
    private String localCourseId;
      
    /** The value for the remoteCourseId field */
    private String remoteCourseId;
      
    /** The value for the courseSeller field */
    private String courseSeller;
      
    /** The value for the coursePurchaser field */
    private String coursePurchaser;
      
    /** The value for the instituteIp field */
    private String instituteIp;
      
    /** The value for the instituteName field */
    private String instituteName;
      
    /** The value for the expiryDate field */
    private Date expiryDate;
      
    /** The value for the status field */
    private int status;
      
    /** The value for the secretKey field */
    private String secretKey;
  
    
    /**
     * Get the SrNo
     *
     * @return int
     */
    public int getSrNo()
    {
        return srNo;
    }

                        
    /**
     * Set the value of SrNo
     *
     * @param v new value
     */
    public void setSrNo(int v) 
    {
    
                  if (this.srNo != v)
              {
            this.srNo = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the LocalCourseId
     *
     * @return String
     */
    public String getLocalCourseId()
    {
        return localCourseId;
    }

                        
    /**
     * Set the value of LocalCourseId
     *
     * @param v new value
     */
    public void setLocalCourseId(String v) 
    {
    
                  if (!ObjectUtils.equals(this.localCourseId, v))
              {
            this.localCourseId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the RemoteCourseId
     *
     * @return String
     */
    public String getRemoteCourseId()
    {
        return remoteCourseId;
    }

                        
    /**
     * Set the value of RemoteCourseId
     *
     * @param v new value
     */
    public void setRemoteCourseId(String v) 
    {
    
                  if (!ObjectUtils.equals(this.remoteCourseId, v))
              {
            this.remoteCourseId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the CourseSeller
     *
     * @return String
     */
    public String getCourseSeller()
    {
        return courseSeller;
    }

                        
    /**
     * Set the value of CourseSeller
     *
     * @param v new value
     */
    public void setCourseSeller(String v) 
    {
    
                  if (!ObjectUtils.equals(this.courseSeller, v))
              {
            this.courseSeller = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the CoursePurchaser
     *
     * @return String
     */
    public String getCoursePurchaser()
    {
        return coursePurchaser;
    }

                        
    /**
     * Set the value of CoursePurchaser
     *
     * @param v new value
     */
    public void setCoursePurchaser(String v) 
    {
    
                  if (!ObjectUtils.equals(this.coursePurchaser, v))
              {
            this.coursePurchaser = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the InstituteIp
     *
     * @return String
     */
    public String getInstituteIp()
    {
        return instituteIp;
    }

                        
    /**
     * Set the value of InstituteIp
     *
     * @param v new value
     */
    public void setInstituteIp(String v) 
    {
    
                  if (!ObjectUtils.equals(this.instituteIp, v))
              {
            this.instituteIp = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the InstituteName
     *
     * @return String
     */
    public String getInstituteName()
    {
        return instituteName;
    }

                        
    /**
     * Set the value of InstituteName
     *
     * @param v new value
     */
    public void setInstituteName(String v) 
    {
    
                  if (!ObjectUtils.equals(this.instituteName, v))
              {
            this.instituteName = v;
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
     * Get the Status
     *
     * @return int
     */
    public int getStatus()
    {
        return status;
    }

                        
    /**
     * Set the value of Status
     *
     * @param v new value
     */
    public void setStatus(int v) 
    {
    
                  if (this.status != v)
              {
            this.status = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the SecretKey
     *
     * @return String
     */
    public String getSecretKey()
    {
        return secretKey;
    }

                        
    /**
     * Set the value of SecretKey
     *
     * @param v new value
     */
    public void setSecretKey(String v) 
    {
    
                  if (!ObjectUtils.equals(this.secretKey, v))
              {
            this.secretKey = v;
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
          save(RemoteCoursesPeer.getMapBuilder()
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
                    RemoteCoursesPeer.doInsert((RemoteCourses) this, con);
                    setNew(false);
                }
                else
                {
                    RemoteCoursesPeer.doUpdate((RemoteCourses) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  srNo ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setSrNo(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setSrNo(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getSrNo());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public RemoteCourses copy() throws TorqueException
    {
        return copyInto(new RemoteCourses());
    }
  
    protected RemoteCourses copyInto(RemoteCourses copyObj) throws TorqueException
    {
          copyObj.setSrNo(srNo);
          copyObj.setLocalCourseId(localCourseId);
          copyObj.setRemoteCourseId(remoteCourseId);
          copyObj.setCourseSeller(courseSeller);
          copyObj.setCoursePurchaser(coursePurchaser);
          copyObj.setInstituteIp(instituteIp);
          copyObj.setInstituteName(instituteName);
          copyObj.setExpiryDate(expiryDate);
          copyObj.setStatus(status);
          copyObj.setSecretKey(secretKey);
  
                    copyObj.setSrNo(0);
                                                                  
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public RemoteCoursesPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("RemoteCourses:\n");
        str.append("SrNo = ")
           .append(getSrNo())
           .append("\n");
        str.append("LocalCourseId = ")
           .append(getLocalCourseId())
           .append("\n");
        str.append("RemoteCourseId = ")
           .append(getRemoteCourseId())
           .append("\n");
        str.append("CourseSeller = ")
           .append(getCourseSeller())
           .append("\n");
        str.append("CoursePurchaser = ")
           .append(getCoursePurchaser())
           .append("\n");
        str.append("InstituteIp = ")
           .append(getInstituteIp())
           .append("\n");
        str.append("InstituteName = ")
           .append(getInstituteName())
           .append("\n");
        str.append("ExpiryDate = ")
           .append(getExpiryDate())
           .append("\n");
        str.append("Status = ")
           .append(getStatus())
           .append("\n");
        str.append("SecretKey = ")
           .append(getSecretKey())
           .append("\n");
        return(str.toString());
    }
}
