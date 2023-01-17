package employee.crud.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.map.ObjectWriter;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import employee.crud.beans.Employee;
import employee.crud.dao.EmployeeDAO;
import employee.crud.dao.EmployeeDAOImpl;

@WebServlet("/")
public class EmployeeController extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private EmployeeDAO employeeDAO;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		employeeDAO = new EmployeeDAOImpl();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getServletPath();

		try 
		{
			//find the right action
			switch (action) 
			{
				case "/list":
					listAllEmployees(request, response);
					break;
				case "/insert":
					insertEmployee(request, response);
					break;
				case "/delete":
					deleteEmployee(request, response);
					break;
				case "/get":
					getEmployee(request, response);
					break;
				case "/update":
					updateEmployee(request, response);
					break;
				default:
					listAllEmployees(request, response);
					break;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private void getEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException 
	{
		System.out.println("Entering getEmployee");
		
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		System.out.println("getEmployee, employeeId==>" + employeeId);
		
		Employee employee = employeeDAO.getEmployee(employeeId);		
		System.out.println("getEmployee, employee details==>" + employee);
		
		//request.setAttribute("employee", employee);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String employeeAsJSON = ow.writeValueAsString(employee);
		response.getOutputStream().write(employeeAsJSON.getBytes());
	}
	
	private void listAllEmployees(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException 
	{
		System.out.println("Entering listAllEmployees");
		
		List<Employee> employees = employeeDAO.getAllEmployees();
		System.out.println("Getting listAllEmployees");
		System.out.println("listAllEmployees employees size ==>" + employees.size());
		
		request.setAttribute("employees", employees);
		redirectToList(request, response);
	}

	private void insertEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException 
	{
		System.out.println("Entering insertEmployee");
		
		String name = request.getParameter("name");
		System.out.println("insertEmployee, name==>" + name);
		
		String address = request.getParameter("address");
		System.out.println("Entering insertEmployee");
		
		String email = request.getParameter("email");
		System.out.println("Entering insertEmployee");
		
		String phone = request.getParameter("phone");
		System.out.println("Entering insertEmployee");
				
		Employee employee = new Employee(name, address, email, phone);
		boolean result = employeeDAO.addEmployee(employee);
		System.out.println("insertResult result ==>" + result);				
		
		List<Employee> employees = employeeDAO.getAllEmployees();
		System.out.println("insertResult employees ==>" + employees.size());
		
		request.setAttribute("employees", employees);			
		request.setAttribute("insertResult", result+"");		
		redirectToList(request, response);
	}

	private void updateEmployee(HttpServletRequest request, HttpServletResponse response)  
	{
		try
		{
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			
			Employee employee = new Employee(id, name, address, email, phone);		
			boolean result = employeeDAO.updateEmployee(employee);
			System.out.println("updateEmployee result ==>" + result);
			
			List<Employee> employees = employeeDAO.getAllEmployees();
			System.out.println("updateEmployee employees ==>" + employees.size());
			
			request.setAttribute("employees", employees);
			request.setAttribute("updateEmployee", result+"");
			redirectToList(request, response);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String employeeIds = request.getParameter("employeeIds");
			StringTokenizer tokenizer = new StringTokenizer(employeeIds, ",");
			
			while(tokenizer.hasMoreTokens())
			{
				String employeeId = tokenizer.nextElement().toString();
				System.out.println("Employee ID to be deleted,  employeeId ==>" + employeeId);
				
				boolean result = employeeDAO.deleteEmployee(Integer.parseInt(employeeId));
				System.out.println("is employeeId " + employeeId + " deleted ? " +  result);
			}			
			
			List<Employee> employees = employeeDAO.getAllEmployees();
			System.out.println("insertResult employees ==>" + employees.size());
			
			request.setAttribute("employees", employees);
			//request.setAttribute("deleteUser", result+"");
			redirectToList(request, response);
		}
		catch (Exception e) 
		{			
			e.printStackTrace();
		}
	}
	
	private void redirectToList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/employeeView.jsp");		
		dispatcher.forward(request, response);
	}
}