package com.manohar.repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.manohar.exception.CSVParsingException;
import com.manohar.service.EmployeeService;
import com.manohar.vo.Employee;

public class EmployeeRepository {

	private static List<Employee> employees;

	static {
		employees = loadEmployees("/employees.csv");
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}

	private static List<Employee> loadEmployees(String filePath) {
		List<Employee> employees = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(EmployeeService.class.getResourceAsStream(filePath)))) {
			String line;
			br.readLine();
			while((line = br.readLine())!=null) {
				String[] values = line.split(",", -1);
				if(values.length!=5) {
					throw new CSVParsingException("invalid csv file format. Please validate");
				}
				employees.add(new Employee(Integer.parseInt(values[0]), values[1], values[2], Double.parseDouble(values[3]), values[4]==""? null:Integer.parseInt(values[4])));
			}
		} 
		catch (CSVParsingException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CSVParsingException("exception occurred while parsing csv file", e);
		}
		return employees;
	}
}
