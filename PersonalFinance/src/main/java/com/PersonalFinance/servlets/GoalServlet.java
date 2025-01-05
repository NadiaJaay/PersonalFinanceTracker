package com.PersonalFinance.servlets;

import com.PersonalFinance.beans.Goal;
import com.PersonalFinance.dao.GoalDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/goals")
public class GoalServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getParameter("action");
	    String userId = (String) request.getSession().getAttribute("loggedInUserId");
	    String status = request.getParameter("status"); // "completed" or "pending"

	    if (userId == null) {
	        response.sendRedirect("login.jsp");
	        return;
	    }

	    if ("list".equals(action) || action == null) {
	        Boolean isCompleted = null;
	        if ("completed".equals(status)) {
	            isCompleted = true;
	        } else if ("pending".equals(status)) {
	            isCompleted = false;
	        }

	        // Fetch goals based on status
	        List<Goal> goals = GoalDao.fetchGoalsByStatus(userId, isCompleted);
	        request.setAttribute("goals", goals);
	        request.setAttribute("status", status);
	        request.getRequestDispatcher("goals.jsp").forward(request, response);
	    } else if ("edit".equals(action)) {
	        String goalId = request.getParameter("id");
	        Goal goal = GoalDao.fetchGoalById(goalId);

	        if (goal != null) {
	            request.setAttribute("goal", goal);
	            request.getRequestDispatcher("editGoal.jsp").forward(request, response);
	        } else {
	            request.setAttribute("error", "Goal not found.");
	            request.getRequestDispatcher("goals.jsp").forward(request, response);
	        }
	    } else if ("markComplete".equals(action)) {
	        String goalId = request.getParameter("id");
	        Goal goal = GoalDao.fetchGoalById(goalId);
	        if (goal != null) {
	            goal.setIsComplete(true);
	            GoalDao.updateGoal(goal.getGoal_id(), goal.getGoalName(), goal.getTargetAmount(),
	                    goal.getCurrentAmount(), goal.getDescription(), goal.getIsComplete(), goal.getEndDate());
	        }
	        response.sendRedirect("goals?action=list&status=pending");
	    } else if ("delete".equals(action)) {
	        String goalId = request.getParameter("id");
	        boolean success = GoalDao.deleteGoal(goalId);

	        if (success) {
	            response.sendRedirect("goals?action=list&status=pending");
	        } else {
	            request.setAttribute("error", "Failed to delete goal.");
	            request.getRequestDispatcher("goals.jsp").forward(request, response);
	        }
	    }
	}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = (String) request.getSession().getAttribute("loggedInUserId");

        if ("create".equals(action)) {
            // Create a new goal
            String goalName = request.getParameter("goalName");
            float targetAmount = Float.parseFloat(request.getParameter("targetAmount"));
            float currentAmount = Float.parseFloat(request.getParameter("currentAmount"));
            String description = request.getParameter("description");
            boolean isComplete = false; // Default to incomplete
            Date endDate = Date.valueOf(request.getParameter("endDate"));

            GoalDao.addGoal(userId, goalName, targetAmount, currentAmount, description, isComplete, endDate);
            response.sendRedirect("goals?action=list&status=pending");
        } else if ("edit".equals(action)) {
            // Update an existing goal
            String goalId = request.getParameter("goalId");
            String goalName = request.getParameter("goalName");
            float targetAmount = Float.parseFloat(request.getParameter("targetAmount"));
            float currentAmount = Float.parseFloat(request.getParameter("currentAmount"));
            String description = request.getParameter("description");
            boolean isComplete = request.getParameter("isComplete") != null;
            Date endDate = Date.valueOf(request.getParameter("endDate"));

            GoalDao.updateGoal(goalId, goalName, targetAmount, currentAmount, description, isComplete, endDate);
            response.sendRedirect("goals?action=list&status=pending");
        }
    }
}
