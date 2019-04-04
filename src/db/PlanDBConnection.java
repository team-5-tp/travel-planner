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
     * @param id     the id of the querying plan
     * @param userId ID of the owner of the plan
     * @return the plan with the specified id
     */
    public Plan getPlan(int Id, int userId);
    
    /**
     * Get all saved plans of the given user
     * 
     * @param userId the user_id of the given user
     * @return a list of all of the plans saved by the user
     */
    public List<Plan> getAllPlans(int userId);
    
    /**
     * Delete a specific plan from the table
     * 
     * @param id     the ID of the plan to be deleted
     * @param userId the ID of the user who owns the plan
     * @return     whether the plan has been deleted or not
     */
    public boolean deletePlan(int planId, int userId);
    
    /**
     * Update an existing plan with a new plan
     * 
     * @param id  the ID of the existing plan that is about to be updated
     * @param newName the designated new name for the plan
     * @param userId  the ID of the user who owns the plan
     * @return        whether the plan has been successfully updated or not
     */
    public boolean updatePlan(int id, String newName, int userId);
}
