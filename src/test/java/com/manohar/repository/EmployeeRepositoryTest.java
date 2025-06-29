package com.manohar.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.manohar.vo.Employee;

public class EmployeeRepositoryTest {

	private EmployeeRepository employeeRepository = new EmployeeRepository();



	@Test
	public void test() {
		List<Employee> expectedEmployees = List.of(
				new Employee(123, "Joe", "Doe", 60000d, null),
				new Employee(124, "Martin", "Chekov", 45000d, 123),
				new Employee(125, "Bob", "Ronstad", 47000d, 123),
				new Employee(300, "Alice", "Hasacat", 50000d, 124),
				new Employee(305, "Brett", "Hardleaf", 34000d, 300));

		List<Employee> actualEmployees =	employeeRepository.getEmployees();
		System.out.println(actualEmployees);

		Assert.assertEquals(expectedEmployees, actualEmployees);
	}

}
