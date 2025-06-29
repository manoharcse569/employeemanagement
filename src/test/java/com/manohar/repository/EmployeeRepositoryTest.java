package com.manohar.repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.manohar.exception.CSVParsingException;
import com.manohar.vo.Employee;

public class EmployeeRepositoryTest {

	private EmployeeRepository employeeRepository = new EmployeeRepository();



	@Test
	public void getEmployeesTest() {
		List<Employee> expectedEmployees = List.of(
				new Employee(123, "Joe", "Doe", 60000d, null),
				new Employee(124, "Martin", "Chekov", 45000d, 123),
				new Employee(125, "Bob", "Ronstad", 47000d, 123),
				new Employee(300, "Alice", "Hasacat", 50000d, 124),
				new Employee(305, "Brett", "Hardleaf", 34000d, 300));

		List<Employee> actualEmployees =	employeeRepository.getEmployees();
		System.out.println("actualEmployees:"+actualEmployees);
		Assert.assertEquals(expectedEmployees, actualEmployees);
	}

	@Test
	public void loadEmployeesWithInvalidFormatExceptionTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		Method method = employeeRepository.getClass().getDeclaredMethod("loadEmployees", String.class);
		method.setAccessible(true);

		InvocationTargetException exception =	Assert.assertThrows(InvocationTargetException.class, ()-> {
			method.invoke(employeeRepository, "/employees_invalid_format.csv");
		});

		CSVParsingException csvParsingException = (CSVParsingException) exception.getCause();
		Assert.assertEquals("invalid csv file format. Please validate", csvParsingException.getMessage());
	}
	
	@Test
	public void loadEmployeesWithNumberFormatExceptionTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		Method method = employeeRepository.getClass().getDeclaredMethod("loadEmployees", String.class);
		method.setAccessible(true);

		InvocationTargetException exception =	Assert.assertThrows(InvocationTargetException.class, ()-> {
			method.invoke(employeeRepository, "/employees_invalid_number.csv");
		});

		CSVParsingException csvParsingException = (CSVParsingException) exception.getCause();
		Assert.assertEquals("exception occurred while parsing csv file", csvParsingException.getMessage());
	}

}
