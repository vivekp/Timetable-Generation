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
 * extended all references should be to Lecture
 */
public abstract class BaseLecture extends BaseObject
{
    /** The Peer class */
    private static final LecturePeer peer =
        new LecturePeer();

        
    /** The value for the lectureid field */
    private int lectureid;
      
    /** The value for the groupName field */
    private String groupName;
      
    /** The value for the lecturename field */
    private String lecturename;
      
    /** The value for the lectureinfo field */
    private String lectureinfo;
      
    /** The value for the urlname field */
    private String urlname;
      
    /** The value for the phoneno field */
    private String phoneno;
      
    /** The value for the forvideo field */
    private String forvideo;
      
    /** The value for the foraudio field */
    private String foraudio;
      
    /** The value for the forwhiteboard field */
    private String forwhiteboard;
      
    /** The value for the sessiondate field */
    private Date sessiondate;
      
    /** The value for the sessiontime field */
    private String sessiontime;
      
    /** The value for the duration field */
    private String duration;
      
    /** The value for the repeat field */
    private String repeat;
      
    /** The value for the fortime field */
    private String fortime;
  
    
    /**
     * Get the Lectureid
     *
     * @return int
     */
    public int getLectureid()
    {
        return lectureid;
    }

                        
    /**
     * Set the value of Lectureid
     *
     * @param v new value
     */
    public void setLectureid(int v) 
    {
    
                  if (this.lectureid != v)
              {
            this.lectureid = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the GroupName
     *
     * @return String
     */
    public String getGroupName()
    {
        return groupName;
    }

                        
    /**
     * Set the value of GroupName
     *
     * @param v new value
     */
    public void setGroupName(String v) 
    {
    
                  if (!ObjectUtils.equals(this.groupName, v))
              {
            this.groupName = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Lecturename
     *
     * @return String
     */
    public String getLecturename()
    {
        return lecturename;
    }

                        
    /**
     * Set the value of Lecturename
     *
     * @param v new value
     */
    public void setLecturename(String v) 
    {
    
                  if (!ObjectUtils.equals(this.lecturename, v))
              {
            this.lecturename = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Lectureinfo
     *
     * @return String
     */
    public String getLectureinfo()
    {
        return lectureinfo;
    }

                        
    /**
     * Set the value of Lectureinfo
     *
     * @param v new value
     */
    public void setLectureinfo(String v) 
    {
    
                  if (!ObjectUtils.equals(this.lectureinfo, v))
              {
            this.lectureinfo = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Urlname
     *
     * @return String
     */
    public String getUrlname()
    {
        return urlname;
    }

                        
    /**
     * Set the value of Urlname
     *
     * @param v new value
     */
    public void setUrlname(String v) 
    {
    
                  if (!ObjectUtils.equals(this.urlname, v))
              {
            this.urlname = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Phoneno
     *
     * @return String
     */
    public String getPhoneno()
    {
        return phoneno;
    }

                        
    /**
     * Set the value of Phoneno
     *
     * @param v new value
     */
    public void setPhoneno(String v) 
    {
    
                  if (!ObjectUtils.equals(this.phoneno, v))
              {
            this.phoneno = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Forvideo
     *
     * @return String
     */
    public String getForvideo()
    {
        return forvideo;
    }

                        
    /**
     * Set the value of Forvideo
     *
     * @param v new value
     */
    public void setForvideo(String v) 
    {
    
                  if (!ObjectUtils.equals(this.forvideo, v))
              {
            this.forvideo = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Foraudio
     *
     * @return String
     */
    public String getForaudio()
    {
        return foraudio;
    }

                        
    /**
     * Set the value of Foraudio
     *
     * @param v new value
     */
    public void setForaudio(String v) 
    {
    
                  if (!ObjectUtils.equals(this.foraudio, v))
              {
            this.foraudio = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Forwhiteboard
     *
     * @return String
     */
    public String getForwhiteboard()
    {
        return forwhiteboard;
    }

                        
    /**
     * Set the value of Forwhiteboard
     *
     * @param v new value
     */
    public void setForwhiteboard(String v) 
    {
    
                  if (!ObjectUtils.equals(this.forwhiteboard, v))
              {
            this.forwhiteboard = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Sessiondate
     *
     * @return Date
     */
    public Date getSessiondate()
    {
        return sessiondate;
    }

                        
    /**
     * Set the value of Sessiondate
     *
     * @param v new value
     */
    public void setSessiondate(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.sessiondate, v))
              {
            this.sessiondate = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Sessiontime
     *
     * @return String
     */
    public String getSessiontime()
    {
        return sessiontime;
    }

                        
    /**
     * Set the value of Sessiontime
     *
     * @param v new value
     */
    public void setSessiontime(String v) 
    {
    
                  if (!ObjectUtils.equals(this.sessiontime, v))
              {
            this.sessiontime = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Duration
     *
     * @return String
     */
    public String getDuration()
    {
        return duration;
    }

                        
    /**
     * Set the value of Duration
     *
     * @param v new value
     */
    public void setDuration(String v) 
    {
    
                  if (!ObjectUtils.equals(this.duration, v))
              {
            this.duration = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Repeat
     *
     * @return String
     */
    public String getRepeat()
    {
        return repeat;
    }

                        
    /**
     * Set the value of Repeat
     *
     * @param v new value
     */
    public void setRepeat(String v) 
    {
    
                  if (!ObjectUtils.equals(this.repeat, v))
              {
            this.repeat = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Fortime
     *
     * @return String
     */
    public String getFortime()
    {
        return fortime;
    }

                        
    /**
     * Set the value of Fortime
     *
     * @param v new value
     */
    public void setFortime(String v) 
    {
    
                  if (!ObjectUtils.equals(this.fortime, v))
              {
            this.fortime = v;
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
          save(LecturePeer.getMapBuilder()
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
                    LecturePeer.doInsert((Lecture) this, con);
                    setNew(false);
                }
                else
                {
                    LecturePeer.doUpdate((Lecture) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  lectureid ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setLectureid(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setLectureid(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getLectureid());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public Lecture copy() throws TorqueException
    {
        return copyInto(new Lecture());
    }
  
    protected Lecture copyInto(Lecture copyObj) throws TorqueException
    {
          copyObj.setLectureid(lectureid);
          copyObj.setGroupName(groupName);
          copyObj.setLecturename(lecturename);
          copyObj.setLectureinfo(lectureinfo);
          copyObj.setUrlname(urlname);
          copyObj.setPhoneno(phoneno);
          copyObj.setForvideo(forvideo);
          copyObj.setForaudio(foraudio);
          copyObj.setForwhiteboard(forwhiteboard);
          copyObj.setSessiondate(sessiondate);
          copyObj.setSessiontime(sessiontime);
          copyObj.setDuration(duration);
          copyObj.setRepeat(repeat);
          copyObj.setFortime(fortime);
  
                    copyObj.setLectureid(0);
                                                                                          
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public LecturePeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("Lecture:\n");
        str.append("Lectureid = ")
           .append(getLectureid())
           .append("\n");
        str.append("GroupName = ")
           .append(getGroupName())
           .append("\n");
        str.append("Lecturename = ")
           .append(getLecturename())
           .append("\n");
        str.append("Lectureinfo = ")
           .append(getLectureinfo())
           .append("\n");
        str.append("Urlname = ")
           .append(getUrlname())
           .append("\n");
        str.append("Phoneno = ")
           .append(getPhoneno())
           .append("\n");
        str.append("Forvideo = ")
           .append(getForvideo())
           .append("\n");
        str.append("Foraudio = ")
           .append(getForaudio())
           .append("\n");
        str.append("Forwhiteboard = ")
           .append(getForwhiteboard())
           .append("\n");
        str.append("Sessiondate = ")
           .append(getSessiondate())
           .append("\n");
        str.append("Sessiontime = ")
           .append(getSessiontime())
           .append("\n");
        str.append("Duration = ")
           .append(getDuration())
           .append("\n");
        str.append("Repeat = ")
           .append(getRepeat())
           .append("\n");
        str.append("Fortime = ")
           .append(getFortime())
           .append("\n");
        return(str.toString());
    }
}
