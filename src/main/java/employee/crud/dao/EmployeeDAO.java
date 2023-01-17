package employee.crud.dao;

import java.sql.SQLException;
import java.util.List;

import employee.crud.beans.Employee;

public interface EmployeeDAO {

	// 1- Insert new Employee
	public boolean addEmployee(Employee employee) throws SQLException;
	
	// 2- Delete an Employee
	public boolean deleteEmployee(int employeeId) throws SQLException;
	
	// 3- Update an Employee
	public boolean updateEmployee(Employee employee) throws SQLException;
	
	// 4- Get an Employee using Employee ID
	public Employee getEmployee(int employeeId) throws SQLException;
	
	// 5- Get All Employees
	public List<Employee> getAllEmployees() throws SQLException;
}
