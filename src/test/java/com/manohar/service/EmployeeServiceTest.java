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
}
