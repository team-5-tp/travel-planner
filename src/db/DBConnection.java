package db;

import entity.Plan;

public interface DBConnection {
	public void close();
	// Methods for user table
	public boolean userInsert(String username, String password);
	public void userDelete(int user_id);
	public boolean userUpdate(String username_new, String password_new,String username, String password);
	public boolean userVerify(String username, String password);
	
	// Methods for plan table
	/**
	 * Insert a Plan object entry into the table
	 * 
	 * @param plan the plan to be inserted
	 * @return     whether the insertion is successful or not
	 */
	public boolean insertPlan(Plan plan);
	
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

