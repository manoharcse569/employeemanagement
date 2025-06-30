package com.manohar.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.manohar.repository.EmployeeRepository;
import com.manohar.vo.Employee;

public class EmployeeService {
	
	private EmployeeRepository employeeRepository = new EmployeeRepository();

	public static void main(String[] args) {
		
		EmployeeService employeeService = new EmployeeService();
		
		//1. which managers earn less than they should, and by how much
		Map<Integer, Double> managerEarningLessThanEligible = employeeService.getManagersGettingLessThanEligible();
		System.out.println("managerEarningLessThanEligible:"+managerEarningLessThanEligible);
		
		//2. managers earn more than they should, and by how much
		Map<Integer, Double> managerEarningMoreThanEligible = employeeService.getManagersGettingMoreThanEligible();
		System.out.println("managerEarningMoreThanEligible:"+managerEarningMoreThanEligible);
		
		//3. employees have a reporting line which is too long, and by how much
		Map<Integer, List<Employee>> highestReportingLineSizeEmployees = employeeService.getEmployeeWithHighestReportingLineSize();
		System.out.println("highestReportingLineSizeEmployees:"+highestReportingLineSizeEmployees);
		
		//identify all employees which have more than 4 managers between them and the CEO.
		Map<Employee, Integer> employeeWithMoreThanGivenReportLineSizeMap = employeeService.getEmployeesWithMoreThanFourManagersInBetweenThemAndCEO();
		System.out.println("employeeWithMoreThanGivenReportLineSizeMap:"+employeeWithMoreThanGivenReportLineSizeMap);
	}
	
	

	public Map<Integer, Double> getManagersGettingLessThanEligible() {
		
		// get employees from csv
		List<Employee> employees = employeeRepository.getEmployees();
		//System.out.println("employees:"+employees);
		
		Map<Integer, Double> managerEarningLessThanEligible = getManagersGettingLessThanEligible(employees);
		
		return managerEarningLessThanEligible;
	}
	
	public Map<Integer, Double> getManagersGettingMoreThanEligible() {
		
		// get employees from csv
		List<Employee> employees = employeeRepository.getEmployees();
		//System.out.println("employees:"+employees);
		
		Map<Integer, Double> managerEarningMoreThanEligible = getManagersGettingMoreThanEligible(employees);
		
		return managerEarningMoreThanEligible;
	}
	
	public Map<Integer, List<Employee>> getEmployeeWithHighestReportingLineSize() {
		// get employees from csv
		List<Employee> employees = employeeRepository.getEmployees();
		//System.out.println("employees:"+employees);
		
		Map<Integer, List<Employee>> highestReportingLineSizeEmployees = getEmployeeWithHighestReportingLineSize(employees);
		//System.out.println("highestReportingLineSizeEmployees:"+highestReportingLineSizeEmployees);
		return highestReportingLineSizeEmployees;
	}



	private Map<Integer, List<Employee>> getEmployeeWithHighestReportingLineSize(List<Employee> employees) {
		Map<Employee, Integer> employeeReportLineSizeMap = getEmployeeReportLineSize(employees);
		
		var entry = employeeReportLineSizeMap.entrySet().stream().collect(Collectors.groupingBy(es->es.getValue())).entrySet().stream().max((e1,e2)->e1.getKey()-e2.getKey()).orElse(null);
		Map<Integer, List<Employee>> highestReportingLineSizeEmployees = new LinkedHashMap<>();
		if(entry!=null) {
			highestReportingLineSizeEmployees.put(entry.getKey(),entry.getValue().stream().map(e->e.getKey()).toList());
		}
		return highestReportingLineSizeEmployees;
	}
	
	public Map<Employee, Integer> getEmployeesWithMoreThanFourManagersInBetweenThemAndCEO() {
		// get employees from csv
		List<Employee> employees = employeeRepository.getEmployees();
		//System.out.println("employees:"+employees);
		
		
		Map<Employee, Integer> employeeWithMoreThanGivenReportLineSizeMap = getEmployeesWithMoreThanGivenManagersCountInBetweenThemAndCEO(
				4, employees);
		//System.out.println("employeeWithMoreThanGivenReportLineSizeMap:"+employeeWithMoreThanGivenReportLineSizeMap);
	    return employeeWithMoreThanGivenReportLineSizeMap;
	}



	private Map<Employee, Integer> getEmployeesWithMoreThanGivenManagersCountInBetweenThemAndCEO(int numberOfManagers,
			List<Employee> employees) {
		Map<Employee, Integer> employeeReportLineSizeMap = getEmployeeReportLineSize(employees);
		
		// here we need to identify employees having managers count in between employee and CEO is more than given number. 
		// condition is employeeReportingLineSize > inputNumberOfManagers+1 . As user given value doesn't include CEO so added +1.
		Map<Employee, Integer> employeeWithMoreThanGivenReportLineSizeMap = employeeReportLineSizeMap.entrySet().stream().filter(es->es.getValue()>numberOfManagers+1).collect(Collectors.toMap(es->es.getKey(), es->es.getValue()));
		return employeeWithMoreThanGivenReportLineSizeMap;
	}
	
	public Map<Employee, Integer> getEmployeeReportLineSize() {
		
		// get employees from csv
		List<Employee> employees = employeeRepository.getEmployees();
		//System.out.println("employees:"+employees);
		
		Map<Employee, Integer> employeeReportLineSizeMap = getEmployeeReportLineSize(employees);
		
		return employeeReportLineSizeMap;
	}



	private Map<Employee, Integer> getEmployeeReportLineSize(List<Employee> employees) {
		Map<Employee, Integer> employeeReportLineSizeMap = new LinkedHashMap<>();
		for(Employee employee: employees) {
			Employee currentEmployee = employee;
			int managerCount = 0;
			while(currentEmployee.getManagerId() != null) {
				Employee manager = getEmployeeById(employees, currentEmployee.getManagerId());
				managerCount++;
				currentEmployee = manager;
			}
			
			employeeReportLineSizeMap.put(employee, managerCount);
		}
		//System.out.println("employeeReportLineSizeMap:"+employeeReportLineSizeMap);
		return employeeReportLineSizeMap;
	}

	private Employee getEmployeeById(List<Employee> employees, Integer employeeId) {
		return employees.stream().filter(e->e.getId().equals(employeeId)).findFirst().orElse(null);
	}


	private Map<Integer, Double> getManagersGettingMoreThanEligible(List<Employee> employees) {
		Map<Employee, Double> managerAvgSalaryMap = getManagerWithAverageSalaryOfReportingEmployees(employees);
		
		// most eligible salary for each manager
		Map<Employee, Double> managerWithMostEligibleSalary = managerAvgSalaryMap.entrySet().stream().collect(Collectors.toMap(es->es.getKey(), es->es.getValue()*1.5));
		//System.out.println("managerWithMostEligibleSalary:"+managerWithMostEligibleSalary);
		
		// managers earning more than most eligible to them
		Map<Integer, Double> managerEarningMoreThanEligible = managerWithMostEligibleSalary.entrySet().stream().filter(es-> es.getKey().getSalary()>es.getValue()).collect(Collectors.toMap(es->es.getKey().getId(), es->es.getKey().getSalary()-es.getValue()));
		//System.out.println("managerEarningMoreThanEligible:"+managerEarningMoreThanEligible);
		return managerEarningMoreThanEligible;
	}



	private Map<Integer, Double> getManagersGettingLessThanEligible(List<Employee> employees) {
		Map<Employee, Double> managerAvgSalaryMap = getManagerWithAverageSalaryOfReportingEmployees(employees);
		
		// least eligible salary for each manager
		Map<Employee, Double> managerWithLeastEligibleSalary = managerAvgSalaryMap.entrySet().stream().collect(Collectors.toMap(es->es.getKey(), es->es.getValue()*1.2));
		//System.out.println("managerWithLeastEligibleSalary:"+managerWithLeastEligibleSalary);
		
		// managers earning less than least eligible to them
		Map<Integer, Double> managerEarningLessThanElgible = managerWithLeastEligibleSalary.entrySet().stream().filter(es-> es.getKey().getSalary()<es.getValue()).collect(Collectors.toMap(es->es.getKey().getId(), es->es.getKey().getSalary()-es.getValue()));
		//System.out.println("managerEarningLessThanEligible:"+managerEarningLessThanElgible);
		return managerEarningLessThanElgible;
	}



	private Map<Employee, Double> getManagerWithAverageSalaryOfReportingEmployees(List<Employee> employees) {
		// map manager and reporting employees 
		Map<Employee, List<Employee>> managerEmployeeMap =	employees.stream().collect(Collectors.toMap(m->m, m-> {
			return employees.stream().filter(e->
			{
				if(m.getId().equals(e.getManagerId())) {
					return true;
				}
				return false;
			}).toList();
		},(e1,e2)->e1, LinkedHashMap::new));
		//System.out.println("managerEmployeeMap:"+managerEmployeeMap);
		
		
		// get map of manager and reporting employees with at least one reporting employee to manager 
		Map<Employee, List<Employee>> managerWithEmployeeMap =	 managerEmployeeMap.entrySet().stream().filter(es->es.getValue().size()>0).collect(Collectors.toMap(es->es.getKey(), es->es.getValue()));
		//System.out.println("managerWithEmployeeMap:"+managerWithEmployeeMap);
		
		// get average salaries of reporting employees of each manager
		Map<Employee, Double> managerAvgSalaryMap =	managerWithEmployeeMap.entrySet().stream().collect(Collectors.toMap(es->es.getKey(), es->es.getValue().stream().map(e->e.getSalary()).collect(Collectors.averagingDouble(s->s))));
		//System.out.println("managerAvgSalaryMap:"+managerAvgSalaryMap);
		return managerAvgSalaryMap;
	}
	
}
