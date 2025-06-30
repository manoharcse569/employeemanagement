package com.manohar.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.manohar.vo.Employee;

public class EmployeeServiceTest {
	
	private EmployeeService employeeService = new EmployeeService();
	
	@Test
	public void getManagersGettingLessThanElgibleFromCsvTest() {
		//Arrange
		Map<Integer, Double> expectedOutput = Map.of(124,-15000.0);
		
		//Act
		Map<Integer, Double> managerEarningLessThanElgible = employeeService.getManagersGettingLessThanEligible();
		
		//Assert
		Assert.assertEquals(expectedOutput, managerEarningLessThanElgible);
	}

	@Test
	public void getManagersGettingLessThanElgibleFromInputTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		//Arrange
		List<Employee> employees = List.of(
				new Employee(123, "Joe", "Doe", 60000d, null),
				new Employee(124, "Martin", "Chekov", 45000d, 123),
				new Employee(125, "Bob", "Ronstad", 47000d, 123),
				new Employee(300, "Alice", "Hasacat", 50000d, 124),
				new Employee(305, "Brett", "Hardleaf", 34000d, 300));
		
		Map<Integer, Double> expectedOutput = Map.of(124,-15000.0);
		
		//Act
	Method method =	employeeService.getClass().getDeclaredMethod("getManagersGettingLessThanEligible", List.class);
	method.setAccessible(true);
	Map<Integer, Double> managerEarningLessThanElgible = (Map<Integer, Double>) method.invoke(employeeService, employees);
	
	
	//Assert
	Assert.assertEquals(expectedOutput, managerEarningLessThanElgible);
	}
	
	@Test
	public void getManagersGettingMoreThanElgibleFromCsvTest() {
		//Arrange
		Map<Integer, Double> expectedOutput = Map.of();
		
		//Act
		Map<Integer, Double> managerEarningMoreThanElgible = employeeService.getManagersGettingMoreThanEligible();
		
		//Assert
		Assert.assertEquals(expectedOutput, managerEarningMoreThanElgible);
	}

	@Test
	public void getManagersGettingMoreThanElgibleFromInputTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		//Arrange
		List<Employee> employees = List.of(
				new Employee(123, "Joe", "Doe", 70000d, null),
				new Employee(124, "Martin", "Chekov", 45000d, 123),
				new Employee(125, "Bob", "Ronstad", 47000d, 123),
				new Employee(300, "Alice", "Hasacat", 50000d, 124),
				new Employee(305, "Brett", "Hardleaf", 34000d, 300));

		Map<Integer, Double> expectedOutput = Map.of(123,1000.0);

		//Act
		Method method =	employeeService.getClass().getDeclaredMethod("getManagersGettingMoreThanEligible", List.class);
		method.setAccessible(true);
		Map<Integer, Double> managerEarningMoreThanElgible = (Map<Integer, Double>) method.invoke(employeeService, employees);



		//Assert
		Assert.assertEquals(expectedOutput, managerEarningMoreThanElgible);
	}
	
	@Test
	public void getEmployeeWithHighestReportingLineSizeFromCSVTest() {
		//Arrange
		Map<Integer, List<Employee>> expectedOutput = Map.of(3, List.of(new Employee(305, "Brett", "Hardleaf", 34000d, 300)));
		
		//Act
		Map<Integer, List<Employee>> highestReportingLineSizeEmployees = employeeService.getEmployeeWithHighestReportingLineSize();
		
		//Assert
		Assert.assertEquals(expectedOutput, highestReportingLineSizeEmployees);
	}
	
	@Test
	public void getEmployeeWithHighestReportingLineSizeFromInputTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		//Arrange
		List<Employee> employees = List.of(
				new Employee(123, "Joe", "Doe", 60000d, null),
				new Employee(124, "Martin", "Chekov", 45000d, 123),
				new Employee(125, "Bob", "Ronstad", 47000d, 123),
				new Employee(300, "Alice", "Hasacat", 50000d, 124),
				new Employee(305, "Brett", "Hardleaf", 34000d, 300));
		Map<Integer, List<Employee>> expectedOutput = Map.of(3, List.of(new Employee(305, "Brett", "Hardleaf", 34000d, 300)));
		
		//Act
		Method method =	employeeService.getClass().getDeclaredMethod("getEmployeeWithHighestReportingLineSize", List.class);
		method.setAccessible(true);
		Map<Integer, List<Employee>> highestReportingLineSizeEmployees = (Map<Integer, List<Employee>>) method.invoke(employeeService, employees);
		
		//Assert
		Assert.assertEquals(expectedOutput, highestReportingLineSizeEmployees);
	}
	
	@Test
	public void getEmployeesWithMoreThanFourManagersInBetweenThemAndCEOFromCSVTest() {
		//Arrange
		Map<Employee, Integer> expectedOutput = Map.of();
		
		//Act
		Map<Employee, Integer> employeeWithMoreThanGivenReportLineSizeMap = employeeService.getEmployeesWithMoreThanFourManagersInBetweenThemAndCEO();
		
		//Assert
		Assert.assertEquals(expectedOutput, employeeWithMoreThanGivenReportLineSizeMap);
	}
	
	@Test
	public void getEmployeesWithMoreThanGivenManagersCountInBetweenThemAndCEOFromInputTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		//Arrange
		List<Employee> employees = List.of(
				new Employee(123, "Joe", "Doe", 60000d, null),
				new Employee(124, "Martin", "Chekov", 45000d, 123),
				new Employee(125, "Bob", "Ronstad", 47000d, 123),
				new Employee(300, "Alice", "Hasacat", 50000d, 124),
				new Employee(305, "Brett", "Hardleaf", 34000d, 300));
		
		Map<Employee, Integer> expectedOutput = Map.of(new Employee(305, "Brett", "Hardleaf", 34000d, 300),3);
		
		
		//Act
		Method method =	employeeService.getClass().getDeclaredMethod("getEmployeesWithMoreThanGivenManagersCountInBetweenThemAndCEO", int.class, List.class);
		method.setAccessible(true);
		
		// here we need to identify employees having managers count in between employee and CEO is more than given number. 
		// the manager hierarchy for employee 305 is 305->300->124->123.  in between employee(305) and CEO(123) having 2 managers(300,124)
		// so to satisfy this given input number of managers as 1 
		Map<Employee, Integer> employeeWithMoreThanGivenReportLineSizeMap  = (Map<Employee, Integer>) method.invoke(employeeService, 1,employees);
		System.out.println(employeeWithMoreThanGivenReportLineSizeMap);
		
		//Assert
		Assert.assertEquals(expectedOutput, employeeWithMoreThanGivenReportLineSizeMap);
	}
}
