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
 * extended all references should be to TurbineRole
 */
public abstract class BaseTurbineRole extends BaseObject
{
    /** The Peer class */
    private static final TurbineRolePeer peer =
        new TurbineRolePeer();

        
    /** The value for the roleId field */
    private int roleId;
      
    /** The value for the roleName field */
    private String roleName;
      
    /** The value for the objectdata field */
    private byte[] objectdata;
  
    
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
    public void setRoleId(int v) throws TorqueException
    {
    
                  if (this.roleId != v)
              {
            this.roleId = v;
            setModified(true);
        }
    
          
                                  
        // update associated TurbineRolePermission
        if (collTurbineRolePermissions != null)
        {
            for (int i = 0; i < collTurbineRolePermissions.size(); i++)
            {
                ((TurbineRolePermission) collTurbineRolePermissions.get(i))
                    .setRoleId(v);
            }
        }
                                          
        // update associated TurbineUserGroupRole
        if (collTurbineUserGroupRoles != null)
        {
            for (int i = 0; i < collTurbineUserGroupRoles.size(); i++)
            {
                ((TurbineUserGroupRole) collTurbineUserGroupRoles.get(i))
                    .setRoleId(v);
            }
        }
                      }
  
    /**
     * Get the RoleName
     *
     * @return String
     */
    public String getRoleName()
    {
        return roleName;
    }

                        
    /**
     * Set the value of RoleName
     *
     * @param v new value
     */
    public void setRoleName(String v) 
    {
    
                  if (!ObjectUtils.equals(this.roleName, v))
              {
            this.roleName = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Objectdata
     *
     * @return byte[]
     */
    public byte[] getObjectdata()
    {
        return objectdata;
    }

                        
    /**
     * Set the value of Objectdata
     *
     * @param v new value
     */
    public void setObjectdata(byte[] v) 
    {
    
                  if (!ObjectUtils.equals(this.objectdata, v))
              {
            this.objectdata = v;
            setModified(true);
        }
    
          
              }
  
         
                                
            
    /**
     * Collection to store aggregation of collTurbineRolePermissions
     */
    protected List collTurbineRolePermissions;

    /**
     * Temporary storage of collTurbineRolePermissions to save a possible db hit in
     * the event objects are add to the collection, but the
     * complete collection is never requested.
     */
    protected void initTurbineRolePermissions()
    {
        if (collTurbineRolePermissions == null)
        {
            collTurbineRolePermissions = new ArrayList();
        }
    }

    /**
     * Method called to associate a TurbineRolePermission object to this object
     * through the TurbineRolePermission foreign key attribute
     *
     * @param l TurbineRolePermission
     * @throws TorqueException
     */
    public void addTurbineRolePermission(TurbineRolePermission l) throws TorqueException
    {
        getTurbineRolePermissions().add(l);
        l.setTurbineRole((TurbineRole) this);
    }

    /**
     * The criteria used to select the current contents of collTurbineRolePermissions
     */
    private Criteria lastTurbineRolePermissionsCriteria = null;

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getTurbineRolePermissions(new Criteria())
     *
     * @throws TorqueException
     */
    public List getTurbineRolePermissions() throws TorqueException
    {
        if (collTurbineRolePermissions == null)
        {
            collTurbineRolePermissions = getTurbineRolePermissions(new Criteria(10));
        }
        return collTurbineRolePermissions;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this TurbineRole has previously
     * been saved, it will retrieve related TurbineRolePermissions from storage.
     * If this TurbineRole is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     *
     * @throws TorqueException
     */
    public List getTurbineRolePermissions(Criteria criteria) throws TorqueException
    {
        if (collTurbineRolePermissions == null)
        {
            if (isNew())
            {
               collTurbineRolePermissions = new ArrayList();
            }
            else
            {
                      criteria.add(TurbineRolePermissionPeer.ROLE_ID, getRoleId() );
                      collTurbineRolePermissions = TurbineRolePermissionPeer.doSelect(criteria);
            }
        }
        else
        {
            // criteria has no effect for a new object
            if (!isNew())
            {
                // the following code is to determine if a new query is
                // called for.  If the criteria is the same as the last
                // one, just return the collection.
                      criteria.add(TurbineRolePermissionPeer.ROLE_ID, getRoleId());
                      if (!lastTurbineRolePermissionsCriteria.equals(criteria))
                {
                    collTurbineRolePermissions = TurbineRolePermissionPeer.doSelect(criteria);
                }
            }
        }
        lastTurbineRolePermissionsCriteria = criteria;

        return collTurbineRolePermissions;
    }

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getTurbineRolePermissions(new Criteria(),Connection)
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getTurbineRolePermissions(Connection con) throws TorqueException
    {
        if (collTurbineRolePermissions == null)
        {
            collTurbineRolePermissions = getTurbineRolePermissions(new Criteria(10), con);
        }
        return collTurbineRolePermissions;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this TurbineRole has previously
     * been saved, it will retrieve related TurbineRolePermissions from storage.
     * If this TurbineRole is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getTurbineRolePermissions(Criteria criteria, Connection con)
            throws TorqueException
    {
        if (collTurbineRolePermissions == null)
        {
            if (isNew())
            {
               collTurbineRolePermissions = new ArrayList();
            }
            else
            {
                       criteria.add(TurbineRolePermissionPeer.ROLE_ID, getRoleId());
                       collTurbineRolePermissions = TurbineRolePermissionPeer.doSelect(criteria, con);
             }
         }
         else
         {
             // criteria has no effect for a new object
             if (!isNew())
             {
                 // the following code is to determine if a new query is
                 // called for.  If the criteria is the same as the last
                 // one, just return the collection.
                       criteria.add(TurbineRolePermissionPeer.ROLE_ID, getRoleId());
                       if (!lastTurbineRolePermissionsCriteria.equals(criteria))
                 {
                     collTurbineRolePermissions = TurbineRolePermissionPeer.doSelect(criteria, con);
                 }
             }
         }
         lastTurbineRolePermissionsCriteria = criteria;

         return collTurbineRolePermissions;
     }

                        
              
                    
                              
                                
                                                              
                                        
                    
                    
          
    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this TurbineRole is new, it will return
     * an empty collection; or if this TurbineRole has previously
     * been saved, it will retrieve related TurbineRolePermissions from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in TurbineRole.
     */
    protected List getTurbineRolePermissionsJoinTurbineRole(Criteria criteria)
        throws TorqueException
    {
        if (collTurbineRolePermissions == null)
        {
            if (isNew())
            {
               collTurbineRolePermissions = new ArrayList();
            }
            else
            {
                            criteria.add(TurbineRolePermissionPeer.ROLE_ID, getRoleId());
                            collTurbineRolePermissions = TurbineRolePermissionPeer.doSelectJoinTurbineRole(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            boolean newCriteria = true;
                        criteria.add(TurbineRolePermissionPeer.ROLE_ID, getRoleId());
                        if (!lastTurbineRolePermissionsCriteria.equals(criteria))
            {
                collTurbineRolePermissions = TurbineRolePermissionPeer.doSelectJoinTurbineRole(criteria);
            }
        }
        lastTurbineRolePermissionsCriteria = criteria;

        return collTurbineRolePermissions;
    }
                  
                    
                    
                                
                                                              
                                        
                    
                    
          
    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this TurbineRole is new, it will return
     * an empty collection; or if this TurbineRole has previously
     * been saved, it will retrieve related TurbineRolePermissions from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in TurbineRole.
     */
    protected List getTurbineRolePermissionsJoinTurbinePermission(Criteria criteria)
        throws TorqueException
    {
        if (collTurbineRolePermissions == null)
        {
            if (isNew())
            {
               collTurbineRolePermissions = new ArrayList();
            }
            else
            {
                            criteria.add(TurbineRolePermissionPeer.ROLE_ID, getRoleId());
                            collTurbineRolePermissions = TurbineRolePermissionPeer.doSelectJoinTurbinePermission(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            boolean newCriteria = true;
                        criteria.add(TurbineRolePermissionPeer.ROLE_ID, getRoleId());
                        if (!lastTurbineRolePermissionsCriteria.equals(criteria))
            {
                collTurbineRolePermissions = TurbineRolePermissionPeer.doSelectJoinTurbinePermission(criteria);
            }
        }
        lastTurbineRolePermissionsCriteria = criteria;

        return collTurbineRolePermissions;
    }
                            


                          
            
    /**
     * Collection to store aggregation of collTurbineUserGroupRoles
     */
    protected List collTurbineUserGroupRoles;

    /**
     * Temporary storage of collTurbineUserGroupRoles to save a possible db hit in
     * the event objects are add to the collection, but the
     * complete collection is never requested.
     */
    protected void initTurbineUserGroupRoles()
    {
        if (collTurbineUserGroupRoles == null)
        {
            collTurbineUserGroupRoles = new ArrayList();
        }
    }

    /**
     * Method called to associate a TurbineUserGroupRole object to this object
     * through the TurbineUserGroupRole foreign key attribute
     *
     * @param l TurbineUserGroupRole
     * @throws TorqueException
     */
    public void addTurbineUserGroupRole(TurbineUserGroupRole l) throws TorqueException
    {
        getTurbineUserGroupRoles().add(l);
        l.setTurbineRole((TurbineRole) this);
    }

    /**
     * The criteria used to select the current contents of collTurbineUserGroupRoles
     */
    private Criteria lastTurbineUserGroupRolesCriteria = null;

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getTurbineUserGroupRoles(new Criteria())
     *
     * @throws TorqueException
     */
    public List getTurbineUserGroupRoles() throws TorqueException
    {
        if (collTurbineUserGroupRoles == null)
        {
            collTurbineUserGroupRoles = getTurbineUserGroupRoles(new Criteria(10));
        }
        return collTurbineUserGroupRoles;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this TurbineRole has previously
     * been saved, it will retrieve related TurbineUserGroupRoles from storage.
     * If this TurbineRole is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     *
     * @throws TorqueException
     */
    public List getTurbineUserGroupRoles(Criteria criteria) throws TorqueException
    {
        if (collTurbineUserGroupRoles == null)
        {
            if (isNew())
            {
               collTurbineUserGroupRoles = new ArrayList();
            }
            else
            {
                      criteria.add(TurbineUserGroupRolePeer.ROLE_ID, getRoleId() );
                      collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelect(criteria);
            }
        }
        else
        {
            // criteria has no effect for a new object
            if (!isNew())
            {
                // the following code is to determine if a new query is
                // called for.  If the criteria is the same as the last
                // one, just return the collection.
                      criteria.add(TurbineUserGroupRolePeer.ROLE_ID, getRoleId());
                      if (!lastTurbineUserGroupRolesCriteria.equals(criteria))
                {
                    collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelect(criteria);
                }
            }
        }
        lastTurbineUserGroupRolesCriteria = criteria;

        return collTurbineUserGroupRoles;
    }

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getTurbineUserGroupRoles(new Criteria(),Connection)
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getTurbineUserGroupRoles(Connection con) throws TorqueException
    {
        if (collTurbineUserGroupRoles == null)
        {
            collTurbineUserGroupRoles = getTurbineUserGroupRoles(new Criteria(10), con);
        }
        return collTurbineUserGroupRoles;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this TurbineRole has previously
     * been saved, it will retrieve related TurbineUserGroupRoles from storage.
     * If this TurbineRole is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getTurbineUserGroupRoles(Criteria criteria, Connection con)
            throws TorqueException
    {
        if (collTurbineUserGroupRoles == null)
        {
            if (isNew())
            {
               collTurbineUserGroupRoles = new ArrayList();
            }
            else
            {
                       criteria.add(TurbineUserGroupRolePeer.ROLE_ID, getRoleId());
                       collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelect(criteria, con);
             }
         }
         else
         {
             // criteria has no effect for a new object
             if (!isNew())
             {
                 // the following code is to determine if a new query is
                 // called for.  If the criteria is the same as the last
                 // one, just return the collection.
                       criteria.add(TurbineUserGroupRolePeer.ROLE_ID, getRoleId());
                       if (!lastTurbineUserGroupRolesCriteria.equals(criteria))
                 {
                     collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelect(criteria, con);
                 }
             }
         }
         lastTurbineUserGroupRolesCriteria = criteria;

         return collTurbineUserGroupRoles;
     }

                              
              
                    
                    
                                
                                                              
                                        
                    
                    
          
    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this TurbineRole is new, it will return
     * an empty collection; or if this TurbineRole has previously
     * been saved, it will retrieve related TurbineUserGroupRoles from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in TurbineRole.
     */
    protected List getTurbineUserGroupRolesJoinTurbineUser(Criteria criteria)
        throws TorqueException
    {
        if (collTurbineUserGroupRoles == null)
        {
            if (isNew())
            {
               collTurbineUserGroupRoles = new ArrayList();
            }
            else
            {
                            criteria.add(TurbineUserGroupRolePeer.ROLE_ID, getRoleId());
                            collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelectJoinTurbineUser(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            boolean newCriteria = true;
                        criteria.add(TurbineUserGroupRolePeer.ROLE_ID, getRoleId());
                        if (!lastTurbineUserGroupRolesCriteria.equals(criteria))
            {
                collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelectJoinTurbineUser(criteria);
            }
        }
        lastTurbineUserGroupRolesCriteria = criteria;

        return collTurbineUserGroupRoles;
    }
                  
                    
                    
                                
                                                              
                                        
                    
                    
          
    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this TurbineRole is new, it will return
     * an empty collection; or if this TurbineRole has previously
     * been saved, it will retrieve related TurbineUserGroupRoles from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in TurbineRole.
     */
    protected List getTurbineUserGroupRolesJoinTurbineGroup(Criteria criteria)
        throws TorqueException
    {
        if (collTurbineUserGroupRoles == null)
        {
            if (isNew())
            {
               collTurbineUserGroupRoles = new ArrayList();
            }
            else
            {
                            criteria.add(TurbineUserGroupRolePeer.ROLE_ID, getRoleId());
                            collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelectJoinTurbineGroup(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            boolean newCriteria = true;
                        criteria.add(TurbineUserGroupRolePeer.ROLE_ID, getRoleId());
                        if (!lastTurbineUserGroupRolesCriteria.equals(criteria))
            {
                collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelectJoinTurbineGroup(criteria);
            }
        }
        lastTurbineUserGroupRolesCriteria = criteria;

        return collTurbineUserGroupRoles;
    }
                  
                    
                              
                                
                                                              
                                        
                    
                    
          
    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this TurbineRole is new, it will return
     * an empty collection; or if this TurbineRole has previously
     * been saved, it will retrieve related TurbineUserGroupRoles from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in TurbineRole.
     */
    protected List getTurbineUserGroupRolesJoinTurbineRole(Criteria criteria)
        throws TorqueException
    {
        if (collTurbineUserGroupRoles == null)
        {
            if (isNew())
            {
               collTurbineUserGroupRoles = new ArrayList();
            }
            else
            {
                            criteria.add(TurbineUserGroupRolePeer.ROLE_ID, getRoleId());
                            collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelectJoinTurbineRole(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            boolean newCriteria = true;
                        criteria.add(TurbineUserGroupRolePeer.ROLE_ID, getRoleId());
                        if (!lastTurbineUserGroupRolesCriteria.equals(criteria))
            {
                collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelectJoinTurbineRole(criteria);
            }
        }
        lastTurbineUserGroupRolesCriteria = criteria;

        return collTurbineUserGroupRoles;
    }
                            


          
     
    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
          save(TurbineRolePeer.getMapBuilder()
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
                    TurbineRolePeer.doInsert((TurbineRole) this, con);
                    setNew(false);
                }
                else
                {
                    TurbineRolePeer.doUpdate((TurbineRole) this, con);
                }
            }

                                      
                
            if (collTurbineRolePermissions != null)
            {
                for (int i = 0; i < collTurbineRolePermissions.size(); i++)
                {
                    ((TurbineRolePermission) collTurbineRolePermissions.get(i)).save(con);
                }
            }
                                          
                
            if (collTurbineUserGroupRoles != null)
            {
                for (int i = 0; i < collTurbineUserGroupRoles.size(); i++)
                {
                    ((TurbineUserGroupRole) collTurbineUserGroupRoles.get(i)).save(con);
                }
            }
                          alreadyInSave = false;
        }
      }


                          
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  roleId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        throws TorqueException
    {
            setRoleId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) throws TorqueException
    {
            setRoleId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getRoleId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public TurbineRole copy() throws TorqueException
    {
        return copyInto(new TurbineRole());
    }
  
    protected TurbineRole copyInto(TurbineRole copyObj) throws TorqueException
    {
          copyObj.setRoleId(roleId);
          copyObj.setRoleName(roleName);
          copyObj.setObjectdata(objectdata);
  
                    copyObj.setRoleId(0);
                        
                                      
                
        List v = getTurbineRolePermissions();
        for (int i = 0; i < v.size(); i++)
        {
            TurbineRolePermission obj = (TurbineRolePermission) v.get(i);
            copyObj.addTurbineRolePermission(obj.copy());
        }
                                                  
                
        v = getTurbineUserGroupRoles();
        for (int i = 0; i < v.size(); i++)
        {
            TurbineUserGroupRole obj = (TurbineUserGroupRole) v.get(i);
            copyObj.addTurbineUserGroupRole(obj.copy());
        }
                    
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public TurbineRolePeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("TurbineRole:\n");
        str.append("RoleId = ")
           .append(getRoleId())
           .append("\n");
        str.append("RoleName = ")
           .append(getRoleName())
           .append("\n");
        str.append("Objectdata = ")
           .append(getObjectdata())
           .append("\n");
        return(str.toString());
    }
}
