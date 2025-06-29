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
		employeeService.getManagersGettingLessThanElgible();
		
		
		
	}
	
	

	public Map<Integer, Double> getManagersGettingLessThanElgible() {
		
		// get employees from csv
		List<Employee> employees = employeeRepository.getEmployees();
		System.out.println("employees:"+employees);
		
		Map<Integer, Double> managerEarningLessThanElgible = getManagersGettingLessThanElgible(employees);
		
		return managerEarningLessThanElgible;
	}



	private Map<Integer, Double> getManagersGettingLessThanElgible(List<Employee> employees) {
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
		System.out.println("managerEmployeeMap:"+managerEmployeeMap);
		
		
		// get map of manager and reporting employees with at least one reporting employee to manager 
		Map<Employee, List<Employee>> managerWithEmployeeMap =	 managerEmployeeMap.entrySet().stream().filter(es->es.getValue().size()>0).collect(Collectors.toMap(es->es.getKey(), es->es.getValue()));
		System.out.println("managerWithEmployeeMap:"+managerWithEmployeeMap);
		
		// get average salaries of reporting employees of each manager
		Map<Employee, Double> managerAvgSalaryMap =	managerWithEmployeeMap.entrySet().stream().collect(Collectors.toMap(es->es.getKey(), es->es.getValue().stream().map(e->e.getSalary()).collect(Collectors.averagingDouble(s->s))));
		System.out.println("managerAvgSalaryMap:"+managerAvgSalaryMap);
		
		// least eligible salary for each manager
		Map<Employee, Double> managerWithLeastEligibleSalary = managerAvgSalaryMap.entrySet().stream().collect(Collectors.toMap(es->es.getKey(), es->es.getValue()*1.2));
		System.out.println("managerWithLeastEligibleSalary:"+managerWithLeastEligibleSalary);
		
		// managers earning less than least eligible to them
		Map<Integer, Double> managerEarningLessThanElgible = managerWithLeastEligibleSalary.entrySet().stream().filter(es-> es.getKey().getSalary()<es.getValue()).collect(Collectors.toMap(es->es.getKey().getId(), es->es.getKey().getSalary()-es.getValue()));
		System.out.println("managerEarningLessThanEligible:"+managerEarningLessThanElgible);
		return managerEarningLessThanElgible;
	}
	
}
