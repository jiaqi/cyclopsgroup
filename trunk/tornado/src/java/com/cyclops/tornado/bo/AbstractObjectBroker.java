/*
 * Created on 2003-10-22
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cyclops.tornado.bo;
import java.util.Collections;
import java.util.List;

import org.apache.torque.util.Criteria;
/**
 * @author jiaqi guo
 * @email g-cyclops@users.sourceforge.net
 */
public abstract class AbstractObjectBroker extends ObjectBroker {
    /** Delete records by a Criteria
     * @param crit Criteria to delete
     * @throws Exception TorqueException actually
     */
    public void delete(Criteria crit) throws Exception {
        delete(getObjectClass(), crit);
    }
    /** Delete a single record by it's primary key
     * @param id PrimaryKey value
     * @throws Exception TorqueException actually
     */
    public void deleteByPK(int id) throws Exception {
        Criteria crit = new Criteria();
        crit.and(getPrimaryKey(), id);
        delete(crit);
    }
    /** Delete record by a group of id
     * @param ids Id array
     * @throws Exception TorqueException actually
     */
    public void deleteByPKs(int[] ids) throws Exception {
        if (ids == null || ids.length == 0) {
            return;
        }
        Criteria crit = new Criteria();
        crit.addIn(getPrimaryKey(), ids);
        delete(crit);
    }
    /** Method getObjectClass()
     * @return What class does this broker focus on
     */
    protected abstract Class getObjectClass();
    /** Method getPrimaryKey()
     * @return Primary key field
     */
    protected abstract String getPrimaryKey();
    /** Implementation of method query() in this class
     * @see com.cyclops.tornado.bo.ObjectBroker#query(java.lang.Class, org.apache.torque.util.Criteria)
     */
    public List query(Criteria crit) throws Exception {
        return query(getObjectClass(), crit);
    }
    /** Method queryAll()
     * @return All of records
     * @throws Exception From torque
     */
    public List queryAll() throws Exception {
        return query(
            new Criteria().and(
                getPrimaryKey(),
                (Object) null,
                Criteria.NOT_EQUAL));
    }
    /** Find records by a group of primary keys
     * @param ids Id array
     * @return List of results
     * @throws Exception TorqueException actually
     */
    public List queryByPKs(int[] ids) throws Exception {
        if (ids == null || ids.length == 0) {
            return Collections.EMPTY_LIST;
        }
        Criteria crit = new Criteria();
        crit.addIn(getPrimaryKey(), ids);
        return query(crit);
    }
    /** Implementation of method retrieve() in this class
     * @see com.cyclops.tornado.bo.ObjectBroker#retrieve(java.lang.Class, org.apache.torque.util.Criteria)
     */
    public Object retrieve(Criteria crit) throws Exception {
        return retrieve(getObjectClass(), crit);
    }
    /** Method retrieveByPK()
     * @param id Int id value
     * @return Single object, null if not found
     * @throws Exception from torque
     */
    public Object retrieveByPK(int id) throws Exception {
        Criteria crit = new Criteria();
        crit.and(getPrimaryKey(), id);
        return retrieve(crit);
    }
}
