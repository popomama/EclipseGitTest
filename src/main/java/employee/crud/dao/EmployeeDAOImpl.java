package employee.crud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import employee.crud.beans.Employee;
import employee.crud.db.DBConnection;

public class EmployeeDAOImpl implements EmployeeDAO
{	
	static Connection connection = DBConnection.connection;

	@Override
	public boolean addEmployee(Employee emp) throws SQLException 
	{
		try
		{
			String insertStatement = "INSERT INTO employee_db.employee (name, email, phone, address) VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
			preparedStatement.setString(1, emp.getName());		
			preparedStatement.setString(2, emp.getEmail());
			preparedStatement.setString(3, emp.getPhone());
			preparedStatement.setString(4, emp.getAddress());
			
			int result = preparedStatement.executeUpdate();
			return result == 1 ? true : false;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean deleteEmployee(int id) throws SQLException 
	{
		try
		{
			String deleteStatement = "delete from employee_db.employee where id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement);
			
			preparedStatement.setInt(1, id);
			boolean result = preparedStatement.executeUpdate() == 1 ? true : false;
			return result;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Employee getEmployee(int id)
	{
		try
		{
			String query = "select * from employee_db.employee where id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			
			Employee employee = new Employee();
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) 
			{
				employee.setId(resultSet.getInt("id"));
				employee.setName(resultSet.getString("name"));
				employee.setAddress(resultSet.getString("address"));
				employee.setEmail(resultSet.getString("email"));
				employee.setPhone(resultSet.getString("phone"));
			}
			return employee;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Employee> getAllEmployees() throws SQLException 
	{
		try
		{
			Connection connection = DBConnection.connection;
			
			String query = "SELECT * FROM employee_db.employee;";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			List<Employee> employees = new ArrayList<Employee>();

			while(rs.next()) 
			{
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setAddress(rs.getString("address"));
				emp.setPhone(rs.getString("phone"));
				emp.setEmail(rs.getString("email"));
				
				employees.add(emp);
			}
			
			return employees;			
		}
		catch (Exception e) {
			System.out.println("exception===================" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateEmployee(Employee emp) throws SQLException 
	{
		try
		{
			String query = "update employee_db.employee SET NAME = ?, email = ?, phone = ?, address = ? WHERE id = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, emp.getName());
			preparedStatement.setString(2, emp.getEmail());		
			preparedStatement.setString(3, emp.getAddress());
			preparedStatement.setString(4, emp.getPhone());
			preparedStatement.setInt(5, emp.getId());
			
			boolean result = preparedStatement.executeUpdate() == 1 ? true : false;
			return result;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) throws SQLException 
	{
		EmployeeDAOImpl employeeDAOImpl = new EmployeeDAOImpl();
		Employee employee = new Employee();
		//employee.setId(10);
		employee.setName("Jennifer1");
		employee.setEmail("jennifer@gamil.com");
		employee.setPhone("00335446666");
		employee.setAddress("Spain");
		//boolean result = employeeDAOImpl.addEmployee(employee);
		//employeeDAOImpl.updateEmployee(employee);
		//Employee employee2 = employeeDAOImpl.getEmployee(10);
		//List<Employee> employees = new ArrayList<Employee>();
		//employees = employeeDAOImpl.getAllEmployees();
		//boolean result = employeeDAOImpl.deleteEmployee(10);
		//System.out.println(result);
		
		List<Employee> employees = employeeDAOImpl.getAllEmployees();
		System.out.println("listAllEmployees employees size ==>" + employees.size());
				
	}
}

