package com.manohar.vo;

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
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", salary=" + salary
				+ ", managerId=" + managerId + "]";
	}
}
