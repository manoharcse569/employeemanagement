package com.manohar.vo;

import java.util.Objects;

public class Employee {

	private Integer id;
	private String firstName;
	private String lastName;
	private Double salary;
	private Integer managerId;
	
	public Employee() {
		
	}

	public Employee(Integer id, String firstName, String lastName, Double salary, Integer managerId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.managerId = managerId;
	}



	public Integer getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Double getSalary() {
		return salary;
	}

	public Integer getManagerId() {
		return managerId;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, lastName, managerId, salary);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(managerId, other.managerId)
				&& Objects.equals(salary, other.salary);
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", salary=" + salary
				+ ", managerId=" + managerId + "]";
	}
}
