package db;

import java.util.List;

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
     * Get a specific plan of a user given plan_id
     * 
     * @param planId the plan_id of the querying user plan
     */
    public Plan getPlan(int planId);
    
    /**
     * Get all saved plans of the given user
     * 
     * @param userId the user_id of the given user
     */
    public List<Plan> getAllPlans(int userId);
    
    /**
     * Delete a specific plan from the table
     * 
     * @param plan the ID of the plan to be deleted
     * @return     whether the plan has been deleted or not
     */
    public boolean deletePlan(int planId);
    
    /**
     * Update an existing plan with a new plan
     * 
     * @param oldName the name of the existing plan that is about to be replaced
     * @param newName the designated new name for the plan
     * @param userId  the user_id of the current user who owns this plan
     * @return        whether the plan has been successfully updated or not
     */
    public boolean updatePlan(String oldName, String newName, int userId);
}
