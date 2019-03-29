package db;

import entity.Plan;

public interface PlanDBConnection extends DBConnection {
    /**
     * Insert a Plan object entry into the table
     * 
     * @param plan the plan to be inserted
     * @return     whether the insertion is successful or not
     */
    public boolean insertPlan(Plan plan);
    
    /**
     * Get a plan of a user given user_id and plan_id
     * 
     * @param userId the user_id of the given user
     * @param planId the plan_id of the querying user plan
     */
    public Plan getPlan(int userId, int planId);
    
    /**
     * Delete a specific plan from the table
     * 
     * @param plan the plan to be deleted
     * @return     whether the plan has been deleted or not
     */
    public boolean deletePlan(Plan plan);
    
    /**
     * Update an existing plan with a new plan
     * 
     * @param oldPlan the existing plan that is about to be replaced
     * @param newPlan the new plan that is used to update the previous plan
     * @return        whether the plan has been successfully updated or not
     */
    public boolean updatePlan(Plan oldPlan, Plan newPlan);
}
